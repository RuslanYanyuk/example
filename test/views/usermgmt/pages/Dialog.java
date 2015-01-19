package views.usermgmt.pages;

import commons.ui.pages.AbstractPage;
import org.fluentlenium.core.domain.FluentWebElement;
import play.i18n.Messages;

public abstract class Dialog<P extends AbstractPage> {

	private static final String DIALOG_CONTAINER = ".ui-dialog";
	protected static final String MESSAGE_CONTAINER = ".ui-dialog .message";
	
	protected static final String BUTTONS = ".ui-dialog-buttonset button";
	public static final String SUCCESS_MESSAGE = "usermgmt.users.success";
	public static final String USER_NOT_FOUND = "usermgmt.users.notFound";
	
	protected final P page;
	
	protected Dialog(P page){
		this.page = page;
	}
	
	protected static <P extends AbstractPage> FluentWebElement getWebElement(P page){
		return page.getBrowser().findFirst(DIALOG_CONTAINER);
	}
	
	protected abstract FluentWebElement getSubmitButton();
	
	protected abstract FluentWebElement getCancelButton();
	
    public P submit(String text) {
        getSubmitButton().click();
        page.waitForElementHasText(MESSAGE_CONTAINER, Messages.get(text));
        return page;
    }

    public P cancel() {
        getCancelButton().click();
        page.waitForNotPresent(DIALOG_CONTAINER);
        return page;
    }
	
	protected void waitForDisplayed(){
		page.waitForDisplayed(DIALOG_CONTAINER);
	}
	
}
