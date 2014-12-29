package views.usermgmt.pages;

import org.fluentlenium.core.domain.FluentWebElement;

import commons.ui.pages.AbstractPage;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static views.usermgmt.pages.UserRow.USER_NAME_CONTAINER;

public class UserDeleteDialog<P extends AbstractPage> {

    private static final String DIALOG_CONTAINER = ".confirm";
    private static final String BUTTONS = ".ui-dialog-buttonpane button";
    private static final String DELETE = "Delete";
    private static final String CANCEL = "Cancel";

    private P page;

    private FluentWebElement webElement;
    private String deletedUserName;

    private UserDeleteDialog (P page, FluentWebElement webElement, String deletedUserName){
        this.page = page;
        this.webElement = webElement;
        this.deletedUserName = deletedUserName;
    }

    static <P extends AbstractPage> UserDeleteDialog<P> getDialog(P page, String deletedUserName) {
        FluentWebElement webElement = page.getBrowser().findFirst(DIALOG_CONTAINER);
        UserDeleteDialog<P> dialog = new UserDeleteDialog<P>(page, webElement, deletedUserName);
        page.waitForDisplayed(DIALOG_CONTAINER);
        return dialog;
    }

    public P submit() {
        webElement.find(BUTTONS, withText(DELETE)).click();
        page.waitForNotPresent(DIALOG_CONTAINER, "Delete user");
        page.waitForNotPresent(USER_NAME_CONTAINER, deletedUserName);
        return page;
    }

    public P cancel() {
        webElement.find(BUTTONS, withText(CANCEL)).click();
        page.waitForNotPresent(DIALOG_CONTAINER, "Delete user");
        return page;
    }
}
