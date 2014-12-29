package views.usermgmt.pages;

import org.fluentlenium.core.Fluent;

import commons.ui.pages.AbstractPage;
import static views.usermgmt.pages.UserRow.USER;

public class AdministrationPage extends AbstractPage{

    public static final String URL = "http://localhost:3333/administration";
    static final String USERS_CONTAINER = "#users-container";
    private static final String CREATE_BUTTON = "#add";
    private static final String LOGOUT_BUTTON = "#logout input[name='logout']";
    private static final String FULL_NAME = "#logout span";

    public AdministrationPage(Fluent browser) {
        super(browser, URL, USER);
    }

    public int getUsersCount(){
        return getBrowser().$(USER).size();
    }

    public UserRow<AdministrationPage> getUserByUserName(String userName){
        return new UserRow<AdministrationPage>(this, userName);
    }

    public UserCreateUpdateDialog<AdministrationPage> addNewUser(){
        getBrowser().findFirst(CREATE_BUTTON).click();
        return UserCreateUpdateDialog.getDialog(this);
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
