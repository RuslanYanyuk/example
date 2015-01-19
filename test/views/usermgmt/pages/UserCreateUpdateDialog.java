package views.usermgmt.pages;

import commons.ui.pages.AbstractPage;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.domain.FluentWebElement;
import ximodels.usermgmt.Role;

import static org.fluentlenium.core.filter.FilterConstructor.withText;

public class UserCreateUpdateDialog<P extends AbstractPage> extends Dialog<P> {

    private static final String USER_NAME_FIELD = "input[name=userName]";
    private static final String FULL_NAME_FIELD = "input[name=fullName]";
    private static final String PASSWORD_FIELD = "input[name=password]";
    private static final String ROLE_FIELD = "select";
    private static final String ROLE_OPTION = "select option";

    private static final String SAVE = "Save";
    private static final String CANCEL = "Cancel";
    
    public static final String ALREADY_EXIST_MESSAGE = "usermgmt.users.alreadyExist";
    public static final String VALIDATION_MESSAGE = "usermgmt.users.validation";
    
    private FluentWebElement webElement;

    private UserCreateUpdateDialog (P page, FluentWebElement webElement){
        super(page);
        this.webElement = webElement;
    }

    static <P extends AbstractPage> UserCreateUpdateDialog<P> getDialog(P page) {
        FluentWebElement webElement = getWebElement(page);
        UserCreateUpdateDialog<P> dialog = new UserCreateUpdateDialog<P>(page, webElement);
        dialog.waitForDisplayed();
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
        webElement.find(ROLE_OPTION, withText(role.name())).click();
        return this;
    }

    public boolean isUserNameFieldDisabled() {
        return webElement.find(USER_NAME_FIELD).getAttribute("disabled").equals("true");
    }

    public boolean isRoleFieldDisabled() {
        return webElement.find(ROLE_FIELD).getAttribute("disabled").equals("true");
    }
    
	@Override
	protected FluentWebElement getSubmitButton() {
		return webElement.findFirst(BUTTONS, withText(SAVE));
	}

	@Override
	protected FluentWebElement getCancelButton() {
		return webElement.findFirst(BUTTONS, withText(CANCEL));
	}

}
