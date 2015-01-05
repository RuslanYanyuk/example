package views.usermgmt.pages;

import org.fluentlenium.core.domain.FluentWebElement;

import commons.ui.pages.AbstractPage;

public abstract class Dialog<P extends AbstractPage> {

	private static final String DIALOG_CONTAINER = ".ui-dialog";
	private static final String MESSAGE_CONTAINER = ".ui-dialog .message";
	
	protected static final String BUTTONS = ".ui-dialog-buttonset button";
	
	public static final String SUCCESS_MESSAGE = "Completed successfully!";
	public static final String USER_NOT_FOUND = "User not found!";
	
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
        page.waitForElementHasText(MESSAGE_CONTAINER, text);
        return page;
    }

    public P cancel() {
        getCancelButton().click();
        page.waitForNotPresent(DIALOG_CONTAINER);
        return page;
    }

	public boolean isNotPresent() {
		return page.getBrowser().find(DIALOG_CONTAINER).isEmpty();
	}
	
	protected void waitForDisplayed(){
		page.waitForDisplayed(DIALOG_CONTAINER);
	}
	
}