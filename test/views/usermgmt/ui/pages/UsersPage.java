package views.usermgmt.ui.pages;

import org.fluentlenium.core.Fluent;

import static views.usermgmt.ui.pages.AdministrationPageUser.USER;

public class UsersPage extends AbstractPage{

    public static final String URL = "http://localhost:3333/administration";
    static final String USERS_CONTAINER = "#users-container";
    private static final String CREATE_BUTTON = "#add";
    private static final String LOGOUT_BUTTON = "#logout input[name='logout']";
    private static final String FULL_NAME = "#logout span";

    public UsersPage(Fluent browser) {
        super(browser, URL, USER);
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
        Fluent browser = getBrowser();
        browser.findFirst(LOGOUT_BUTTON).click();
        return new LoginPage(browser);
    }

    public String getDescriptionFullName() {
        return getBrowser().findFirst(FULL_NAME).getText();
    }

}
