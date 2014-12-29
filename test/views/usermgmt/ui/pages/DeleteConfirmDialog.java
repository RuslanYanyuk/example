package views.usermgmt.ui.pages;

import org.fluentlenium.core.domain.FluentWebElement;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static views.usermgmt.ui.pages.AdministrationPageUser.USER_NAME_CONTAINER;

public class DeleteConfirmDialog {

    private static final String DIALOG_CONTAINER = ".confirm";
    private static final String BUTTONS = ".ui-dialog-buttonpane button";
    private static final String DELETE = "Delete";
    private static final String CANCEL = "Cancel";

    private UsersPage page;

    private FluentWebElement webElement;
    private String deletedUserName;

    private DeleteConfirmDialog (UsersPage page, FluentWebElement webElement, String deletedUserName){
        this.page = page;
        this.webElement = webElement;
        this.deletedUserName = deletedUserName;
    }

    static DeleteConfirmDialog getDialog(UsersPage page, String deletedUserName) {
        FluentWebElement webElement = page.getBrowser().findFirst(DIALOG_CONTAINER);
        DeleteConfirmDialog dialog = new DeleteConfirmDialog(page, webElement, deletedUserName);
        page.waitForDisplayed(DIALOG_CONTAINER);
        return dialog;
    }

    public UsersPage submit() {
        webElement.find(BUTTONS, withText(DELETE)).click();
        page.waitForNotPresent(DIALOG_CONTAINER, "Delete user");
        page.waitForNotPresent(USER_NAME_CONTAINER, deletedUserName);
        return page;
    }

    public UsersPage cancel() {
        webElement.find(BUTTONS, withText(CANCEL)).click();
        page.waitForNotPresent(DIALOG_CONTAINER, "Delete user");
        return page;
    }
}
