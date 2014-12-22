package views.usermgmt.ui.pages;

import org.fluentlenium.core.Fluent;

import static views.usermgmt.ui.pages.AdministrationPageUser.USER;

public class UsersPage extends AbstractPage{

    public static final String URL = "http://localhost:3333/administration";
    static final String USERS_CONTAINER = "#users-container";
    private static final String CREATE_BUTTON = "#add";

    public UsersPage(Fluent browser) {
        super(browser, URL, USERS_CONTAINER);
    }

    public int getUsersCount(){
        return getBrowser().$(USER).size();
    }

    public AdministrationPageUser getUserByUserName(String userName){
        return new AdministrationPageUser(this, userName);
    }

    public AdministrationPageDialog addNewUser(){
        getBrowser().findFirst(CREATE_BUTTON).click();
        return AdministrationPageDialog.getDialog(this);
    }

    public LoginPage logout() {//TODO implement
        return null;
    }

}
