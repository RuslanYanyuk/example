package views.usermgmt.ui.pages;

import org.fluentlenium.core.Fluent;

public class LogoutPage implements Page {

    public static final String URL = "http://localhost:3333/logout";
    private static final String LOGOUT = "#logout";
    private static final String INPUT_TYPE_SUBMIT = "input[type=submit]";
    private static final String INPUT_TYPE_BUTTON = "input[type=button]";

    private Fluent browser;

    public LogoutPage(Fluent browser) {
        this.browser = browser;
    }

    public LoginPage logout() {
        browser.submit(INPUT_TYPE_SUBMIT);
        return new LoginPage(browser);
    }

    @Override
    public LogoutPage load() {
    	browser.goTo(URL);
        return waitForLogoutForm();
    }

    @Override
    public boolean isAt() {
        return browser.url().equals(URL) ? true : false;
    }

    public IndexPage redirectToRoot() {
        browser.$(INPUT_TYPE_BUTTON).click();
        return new IndexPage(browser);
    }

    private LogoutPage waitForLogoutForm() {
        browser.await().atMost(WAIT_TIME, TIME_UNIT).until(LOGOUT).isPresent();
        return this;
    }

}
