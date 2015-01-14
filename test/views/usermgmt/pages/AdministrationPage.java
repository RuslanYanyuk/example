package views.usermgmt.pages;

import org.fluentlenium.core.Fluent;

import commons.ui.pages.AbstractPage;
import static org.fluentlenium.core.filter.FilterConstructor.withText;

import static views.usermgmt.pages.UserRow.USER;

public class AdministrationPage extends AbstractPage{

    private static final String URL = "/administration";
    static final String USERS_CONTAINER = "#users-container";
    private static final String CREATE_BUTTON = "#add";
    private static final String LOGOUT_BUTTON = "#logout input[name='logout']";
    private static final String FULL_NAME = "#logout span";
    private static final String FORBIDDEN_MESSAGE = "Access denied";

    public AdministrationPage(Fluent browser) {
        super(browser, URL, USER);
    }

    public int getUsersCount(){
    	waitUntilAjaxCompleted();
        return getBrowser().$(USER).size();
    }

    public UserRow<AdministrationPage> getUserByUserName(String userName){
        return new UserRow<AdministrationPage>(this, userName);
    }

    public UserCreateUpdateDialog<AdministrationPage> addNewUser(){
        getBrowser().findFirst(CREATE_BUTTON).click();
        return UserCreateUpdateDialog.getDialog(this);
    }

    public LoginPage logout() {
        Fluent browser = getBrowser();
        browser.findFirst(LOGOUT_BUTTON).click();
        return new LoginPage(browser);
    }

    public String getDescriptionFullName() {
        return getBrowser().findFirst(FULL_NAME).getText();
    }

    public boolean isForbidden (){
        return !getBrowser().$("body", withText().contains(FORBIDDEN_MESSAGE)).isEmpty();
    }
}
