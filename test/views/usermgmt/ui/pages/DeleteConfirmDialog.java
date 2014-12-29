package views.usermgmt.ui.pages;

import org.fluentlenium.core.domain.FluentWebElement;
import static org.fluentlenium.core.filter.FilterConstructor.withText;

public class DeleteConfirmDialog {

    private static final String DIALOG_CONTAINER = ".confirm";
    private static final String BUTTONS = ".ui-dialog-buttonpane button";
    private static final String DELETE = "Delete";
    private static final String CANCEL = "Cancel";

    private UsersPage page;

    private FluentWebElement webElement;

    private DeleteConfirmDialog (UsersPage page, FluentWebElement webElement){
        this.page = page;
        this.webElement = webElement;
    }

    static DeleteConfirmDialog getDialog(UsersPage page) {
        FluentWebElement webElement = page.getBrowser().findFirst(DIALOG_CONTAINER);
        DeleteConfirmDialog dialog = new DeleteConfirmDialog(page, webElement);
        page.waitForDisplayed(DIALOG_CONTAINER);
        return dialog;
    }

    public UsersPage submit() {
        webElement.find(BUTTONS, withText(DELETE)).click();
        page.waitForNotPresent(DIALOG_CONTAINER, "Delete user");
        return page;
    }

    public UsersPage cancel() {
        webElement.find(BUTTONS, withText(CANCEL)).click();
        page.waitForNotPresent(DIALOG_CONTAINER, "Delete user");
        return page;
    }
}
