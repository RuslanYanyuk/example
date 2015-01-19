var users;

function usersModel() {
    var self = this;
    self.users = ko.observableArray();

    var color = {
            error: "#cc0000",
            success: "#008800",
            none: "transparent"
        },
        message = {
            validation: "User Name and Password are required",
            none: ""
        };

    var wrapSelfUser = function(user){
        if (user.userName == self.currentUser.userName) {
            user.self = true;
        }
        return user;
    };

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

    var getFormUser = function(dialog) {
        var inputs = dialog.find('form')[0].elements,
            user = new User(inputs['userName'].value, inputs['fullName'].value, $('option:selected').text());

        if (!/\S/.test(user.fullName)) {
           user.fullName = user.userName
        }
        user.password = inputs['password'].value;

        return user;
    };

    var configureDialog = function(saveHandler, title, isUserNameDisabled, currentUser) {
        var dialog = $( "#dialog-form" ).dialog({
            autoOpen: false,
            height: 430,
            width: 350,
            modal: true,
            resizable: false,
            title: title,
            buttons: {
                "Save": function() {
                    saveHandler(dialog, currentUser);
                },
                Cancel: function() {
                  dialog.dialog( "destroy" );
                }
              }
        });
        
        showDialogMessage(message.none, color.none, true);
        
        var form = dialog.find('form')[0];
        form.elements["userName"].disabled = isUserNameDisabled;
        form.elements["role"].disabled = currentUser && currentUser.self;
        form.elements["password"].onblur = null;
        form.reset();

        dialog.dialog( "open" );
        return dialog;
    };

    var showDialogMessage = function(text, color, isImmediate) {
        var message = $('.message'),
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
        var dialog = configureDialog(update, "Edit user", true, currentUser);

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

    var update = function(dialog, currentUser) {
        var url = '/users/' + currentUser.userName;
        var formUser = getFormUser(dialog);

        if (formUser.password == 'd.e.f.a.u.l.t') {
            formUser.password = '';
        }

        $.ajax({
            type: 'PUT',
            url: url,
            data: JSON.stringify(formUser),
            success: function(data) {
                var jsonUser = data.user;
                var user = new User(jsonUser.userName, jsonUser.fullName, jsonUser.role);
                self.users.replace(currentUser, wrapSelfUser(user));

                if(user.userName == self.currentUser.userName){
                    $("#logout span").text(user.fullName);
                }

                showDialogMessage(data.message, color.success);
                setTimeout(function() {
                    dialog.dialog( "destroy" );
                },1500);
            },
            error: function(error) {
                showDialogMessage(error.responseText, color.error);
            },
            contentType: "application/json",
            dataType: 'json'
        });
    };

    self.createDialog = function() {
        configureDialog(create, "Create new user", false);
    };

    var create = function(dialog) {
        var user = getFormUser(dialog);

        if (validateCreation(user)) {
            $.ajax({
                type: 'POST',
                url: '/users',
                data: JSON.stringify(user),
                success: function (data) {
                    var jsonUser = data.user;
                    var user = new User(jsonUser.userName, jsonUser.fullName, jsonUser.role);
                    self.users.push(user);
                    showDialogMessage(data.message, color.success);
                    setTimeout(function () {
                        dialog.dialog("destroy");
                    }, 1500);
                },
                error: function (error) {
                    showDialogMessage(error.responseText, color.error);
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
                type: 'DELETE',
                success: function (message) {
                    showDialogMessage(message, color.success);
                    self.users.remove(user);
                    setTimeout(function () {
                        dialog.dialog("destroy");
                    }, 1500);

                },
                error: function (error) {
                    showDialogMessage(error.responseText, color.error);
                }
            });
        };

        var $dialog = $('#dialog-confirm-delete');
        $dialog.find(".user").text(currentUser.fullName);

        var dialog = $dialog.dialog({
            title: "Delete user",
            modal: true,
            resizable: false,
            height: 210,
            dialogClass: "confirm",
            open: function(event, ui){
                showDialogMessage(message.none, color.none, true);
                $(".ui-dialog.confirm button").blur();
            },
            buttons: [
                {
                    text: "Delete",
                    click: function() {
                        removeUser(currentUser);
                    }
                },
                {
                    text: "Cancel",
                    click: function () {
                        $(this).dialog("destroy");
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
    users = new usersModel();
    ko.applyBindings(users);
});