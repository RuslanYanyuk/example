var users;

function usersModel(dialog) {
    var self = this;
    self.users = ko.observableArray();

    var color = {
            error: "#cc0000",
            success: "#008800",
            none: "transparent"
        },
        message = {
            success: "Completed successfully!",
            validation: "User Name and Password are required",
            none: "",
            getError: function(errorStatus) {
                var message;

                switch(errorStatus) {
                    case 500:
                        message = "User name already exist!";
                        break;
                    case 404:
                        message = "User not found!";
                        break;
                    default:
                        message = "Bad Request!";
                }

                return message;
            }
        };

    var wrapSelfUser = function(user){
        if (user.userName == self.currentUser.userName) {
            user.self = true;
        }
        return user;
    }

    var getAll = function() {
        $.get("/users", function(data) {
            var usersFromServer = [];
            $.each(data, function(key, value) {
                var user = new User(value.userName, value.fullName, value.role);
                usersFromServer.push(wrapSelfUser(user));
            });
            self.users(usersFromServer);
        });
    };

    var getFormUser = function() {
        var inputs = dialog.find('form')[0].elements,
            user = new User(inputs['userName'].value, inputs['fullName'].value, $('option:selected').text());

        if (!/\S/.test(user.fullName)) {
           user.fullName = user.userName
        }
        user.password = inputs['password'].value;

        return user;
    };

    var configureDialog = function(saveHandler, title, isUserNameDisabled, currentUser) {
        var dialogForm = dialog,
            form = dialogForm.find('form')[0];

        dialogForm.dialog( "option", "title", title );
        dialogForm.dialog( "option", "buttons",
            [
                {
                    text: "Save",
                    click: function() {
                        saveHandler(currentUser);
                    }
                },
                {
                    text: "Cancel",
                    click: function() {
                        dialogForm.dialog( "close" );
                    }
                }
            ]
        );

        showDialogMessage(message.none, color.none, true);
        form.elements["userName"].disabled = isUserNameDisabled;
        form.elements["password"].onblur = null;
        form.reset();

        dialogForm.dialog( "open" );
    };

    var showDialogMessage = function(text, color, isImmediate) {
        var message = $('#message'),
            textContainer = message.find('span'),
            colorTime = isImmediate ? 0 : 500,
            textTime = isImmediate ? 0 : 300;

        textContainer.hide();
        message.animate({
            backgroundColor: color}, colorTime, function() {
                textContainer.text(text).fadeIn(textTime);
            }
        );
    };

    self.editDialog = function(currentUser) {//TODO:don't like it
        configureDialog(update, "Edit user", true, currentUser);

        var inputs = dialog.find('form')[0].elements;
        inputs['userName'].value = currentUser.userName;
        inputs['fullName'].value = currentUser.fullName;
        inputs['role'].value = currentUser.role;
        var password = inputs['password'];
        password.value = 'd.e.f.a.u.l.t';
        password.onblur = function() {
            if (this.value == '') {
                this.value = 'd.e.f.a.u.l.t';
            }
        };
    };

    var update = function(currentUser) {
        var url = '/users/' + currentUser.userName;
        var formUser = getFormUser();

        if (formUser.password == 'd.e.f.a.u.l.t') {
            formUser.password = '';
        }

        $.ajax({
            type: 'PUT',
            url: url,
            data: JSON.stringify(formUser),
            success: function(data) {
                var user = new User(data.userName, data.fullName, data.role);
                self.users.replace(currentUser, wrapSelfUser(user));

                $("#logout span").text(user.fullName);

                showDialogMessage(message.success, color.success);
                setTimeout(function() {
                    dialog.dialog( "close" );
                },1500);
            },
            error: function(error) {
                showDialogMessage(message.getError(error.status), color.error);
            },
            contentType: "application/json",
            dataType: 'json'
        });
    };

    self.createDialog = function() {
        configureDialog(create, "Create new user", false);
    };

    var create = function() {
        var user = getFormUser();

        if (validateCreation(user)) {
            $.ajax({
                type: 'POST',
                url: '/users',
                data: JSON.stringify(user),
                success: function (data) {
                    var user = new User(data.userName, data.fullName, data.role);
                    self.users.push(user);
                    showDialogMessage(message.success, color.success);
                    setTimeout(function () {
                        dialog.dialog("close");
                    }, 1500);
                },
                error: function (error) {
                    showDialogMessage(message.getError(error.status), color.error);
                },
                contentType: "application/json",
                dataType: 'json'
            });
        } else {
            showDialogMessage(message.validation, color.error);
        }
    };

    var validateCreation = function(userForm) {
        return /\S/.test(userForm.password) && /\S/.test(userForm.userName);
    };

    self.remove = function(index, currentUser) {
        function removeUser(user){
            var url = '/users/' + user.userName;
            $.ajax({
                url: url,
                type: 'DELETE'
            }).always(function(){
                self.users.remove(user);
            });
        };
        var $dialog = $('#dialog-confirm-delete');
        $dialog.find(".user").text(currentUser.fullName);
        var dialog = $dialog.dialog({
            title: "Delete user",
            modal: true,
            resizable: false,
            dialogClass: "confirm",
            open: function(event, ui){
                $(".ui-dialog.confirm button").blur();
            },
            buttons: [
                {
                    text: "Delete",
                    click: function() {
                        removeUser(currentUser);
                        $(this).dialog( "close" );
                    }
                },
                {
                    text: "Cancel",
                    click: function () {
                        $(this).dialog("close");
                    }
                }
            ]
        });
    };

    getAll();

    function User(userName, fullName, role) {
        this.userName = userName;
        this.fullName = fullName;
        this.role = role;
    }
};

$(function() {
    var dialog = $( "#dialog-form" ).dialog({
        autoOpen: false,
        height: 420,
        width: 350,
        modal: true,
        resizable: false
    });

    users = new usersModel(dialog);
    ko.applyBindings(users);
});