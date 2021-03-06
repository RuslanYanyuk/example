package views.usermgmt.pages;

import org.fluentlenium.core.domain.FluentWebElement;

import commons.ui.pages.AbstractPage;

import static org.fluentlenium.core.filter.FilterConstructor.withText;

public class UserRow<P extends AbstractPage> {

    private static final String EDIT_BUTTON = "input[name=edit]";
    private static final String DELETE_BUTTON = "input[name=delete]";
    static final String USER = AdministrationPage.USERS_CONTAINER + " .row";
    static final String USER_NAME_CONTAINER = "p span[name='userName']";
    static final String FULL_NAME_CONTAINER = "p span[name='fullName']";
    static final String ROLE_CONTAINER = "p span[name='role']";

    private P page;

    private String userName;

    UserRow(P page, String userName){
        this.page = page;
        this.userName = userName;
    }

    private FluentWebElement getWebElement(){
    	page.waitForContainsText(USER, userName);
        return page.getBrowser().findFirst(USER, withText().contains(userName));
    }

    public UserCreateUpdateDialog<P> edit(){
        getWebElement().findFirst(EDIT_BUTTON).click();
        return UserCreateUpdateDialog.getDialog(page);
    }

    public UserDeleteDialog<P> delete(){
        getWebElement().findFirst(DELETE_BUTTON).click();
        return UserDeleteDialog.getDialog(page);
    }

    public boolean isPresent() {
        return !page.getBrowser().find(USER).find(USER_NAME_CONTAINER,  withText(userName)).isEmpty();
    }

    public String getFullName() {
        return getWebElement().find(FULL_NAME_CONTAINER).getText();
    }

    public String getRole() {
        return getWebElement().find(ROLE_CONTAINER).getText();
    }

    public boolean isDeleteButtonDisplayed() {
        return getWebElement().findFirst(DELETE_BUTTON).isDisplayed();
    }

}
