package views.usermgmt.pages;

import org.fluentlenium.core.domain.FluentWebElement;

import commons.ui.pages.AbstractPage;
import static org.fluentlenium.core.filter.FilterConstructor.withText;

public class UserDeleteDialog<P extends AbstractPage> extends Dialog<P> {

    private static final String DELETE = "Delete";
    private static final String CANCEL = "Cancel";

    private FluentWebElement webElement;

    private UserDeleteDialog (P page, FluentWebElement webElement){
    	super(page);
        this.webElement = webElement;
    }

    static <P extends AbstractPage> UserDeleteDialog<P> getDialog(P page) {
        FluentWebElement webElement = getWebElement(page);
        UserDeleteDialog<P> dialog = new UserDeleteDialog<P>(page, webElement);
        dialog.waitForDisplayed();
        return dialog;
    }

	@Override
	protected FluentWebElement getSubmitButton() {
		return webElement.findFirst(BUTTONS, withText(DELETE));
	}

	@Override
	protected FluentWebElement getCancelButton() {
		return webElement.findFirst(BUTTONS, withText(CANCEL));
	}
}
