package usermgmt.ui.pages;

import org.fluentlenium.core.Fluent;

public class LogoutPage implements FluentTestConstants{

    private Fluent browser;
    private static final String LOGOUT= "#logout";
    private static final String INPUT_TYPE_SUBMIT = "input[type=submit]";
    private static final String INPUT_TYPE_BUTTON = "input[type=button]";

    public LogoutPage(Fluent browser) {
        this.browser = browser;
    }

    public LoginPage logout() {
        browser.submit(INPUT_TYPE_SUBMIT);
        return new LoginPage(browser).waitForLoginForm();
    }

    public LogoutPage load() {
        browser.goTo(LOGOUT_URL);
        return this.waitForLogoutForm();
    }

    public void redirectToRoot() {
        browser.$(INPUT_TYPE_BUTTON).click();
    }

    public LogoutPage waitForLogoutForm() {
        browser.await().atMost(WAIT_TIME, TIME_UNIT).until(LOGOUT).isPresent();
        return this;
    }

}
