package views.usermgmt.ui.pages;

import org.fluentlenium.core.domain.FluentWebElement;

import static org.fluentlenium.core.filter.FilterConstructor.withText;

public class AdministrationPageUser {

    private static final String EDIT_BUTTON = "input[name=edit]";
    private static final String DELETE_BUTTON = "input[name=delete]";
    static final String USER = UsersPage.USERS_CONTAINER + " .row";
    static final String USER_NAME_CONTAINER = "p span:first-of-type";
    static final String FULL_NAME_CONTAINER = "p span:nth-of-type(2)";
    static final String ROLE_CONTAINER = "p span:nth-of-type(3)";

    private UsersPage page;

    private String userName;

    AdministrationPageUser(UsersPage page, String userName){
        this.page = page;
        this.userName = userName;
    }

    private FluentWebElement getWebElement(){
        return page.getBrowser().findFirst(USER, withText().contains(userName));
    }

    public AdministrationPageDialog edit(){
        getWebElement().findFirst(EDIT_BUTTON).click();
        return AdministrationPageDialog.getDialog(page);
    }

    public void delete(){
        getWebElement().findFirst(DELETE_BUTTON).click();
        page.waitForNotPresent(USER_NAME_CONTAINER, userName);
    }

    public boolean isPresent() {
        return page.getBrowser().find(USER).find(USER_NAME_CONTAINER,  withText(userName)).size() > 0;
    }

    public String getFullName() {
        return getWebElement().find(FULL_NAME_CONTAINER).getText();
    }

    public String getRole() {
        return getWebElement().find(ROLE_CONTAINER).getText();
    }

}
