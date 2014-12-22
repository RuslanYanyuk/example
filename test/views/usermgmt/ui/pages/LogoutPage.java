package views.usermgmt.ui.pages;

import org.fluentlenium.core.Fluent;

public class LogoutPage extends AbstractPage {

    public static final String URL = "http://localhost:3333/logout";
    private static final String LOGOUT = "#logout";
    private static final String INPUT_TYPE_SUBMIT = "input[type=submit]";
    private static final String INPUT_TYPE_BUTTON = "input[type=button]";

    public LogoutPage(Fluent browser) {
        super(browser, URL, LOGOUT);
    }

    public LoginPage logout() {
        getBrowser().submit(INPUT_TYPE_SUBMIT);
        return new LoginPage(getBrowser());
    }
    
    public IndexPage redirectToRoot() {
        getBrowser().$(INPUT_TYPE_BUTTON).click();
        return new IndexPage(getBrowser());
    }


}
