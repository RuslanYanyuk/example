package views.usermgmt.pages;

import models.usermgmt.Role;

import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.domain.FluentWebElement;

import commons.ui.pages.AbstractPage;

import static org.fluentlenium.core.filter.FilterConstructor.withText;

public class UserCreateUpdateDialog<P extends AbstractPage> {

    private static final String DIALOG_CONTAINER = ".ui-dialog";

    private static final String USER_NAME_FIELD = "input[name=userName]";
    private static final String FULL_NAME_FIELD = "input[name=fullName]";
    private static final String PASSWORD_FIELD = "input[name=password]";
    private static final String ROLE_FIELD = "select option";
    private static final String BUTTONS = ".ui-dialog-buttonset button";
    private static final String SAVE = "Save";
    private static final String CANCEL = "Cancel";
    private static final String MESSAGE_CONTAINER = "#message";

    //TODO move it
    public static final String SUCCESS_MESSAGE = "Completed successfully!";
    public static final String ALREADY_EXIST_MESSAGE = "User name already exist!";
    public static final String VALIDATION_MESSAGE = "User Name and Password are required";
    public static final String USER_NOT_FOUND = "User not found!";

    private P page;

    private FluentWebElement webElement;

    private UserCreateUpdateDialog (P page, FluentWebElement webElement){
        this.page = page;
        this.webElement = webElement;
    }

    static <P extends AbstractPage> UserCreateUpdateDialog<P> getDialog(P page) {
        FluentWebElement webElement = page.getBrowser().findFirst(DIALOG_CONTAINER);
        UserCreateUpdateDialog<P> dialog = new UserCreateUpdateDialog<P>(page, webElement);
        page.waitForDisplayed(DIALOG_CONTAINER);
        return dialog;
    }

    public UserCreateUpdateDialog<P> fillCreateDialog(String userName, String fullName, String password, Role role) {
        page.getBrowser().fill(USER_NAME_FIELD).with(userName);
        return fillEditDialog(fullName, password, role);
    }

    public UserCreateUpdateDialog<P> fillEditDialog(String fullName, String password, Role role) {
        Fluent browser = page.getBrowser();
        browser.fill(FULL_NAME_FIELD).with(fullName);
        browser.fill(PASSWORD_FIELD).with(password);
        webElement.find(ROLE_FIELD, withText(role.name())).click();
        return this;
    }

    public P save(String text) {
        webElement.find(BUTTONS, withText(SAVE)).click();
        page.waitForElementHasText(MESSAGE_CONTAINER, text);
        return page;
    }

    public P cancel() {
        webElement.find(BUTTONS, withText(CANCEL)).click();
        page.waitForElementHasText(DIALOG_CONTAINER, "");
        return page;
    }

    public boolean isUserNameFieldDisabled() {
        return webElement.find(USER_NAME_FIELD).getAttribute("disabled").equals("true");
    }

}
