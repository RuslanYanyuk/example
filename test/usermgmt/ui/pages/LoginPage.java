package usermgmt.ui.pages;

import org.fluentlenium.core.Fluent;

public class LoginPage implements FluentTestConstants{

    private Fluent browser;
    private static final String USER_NAME_FIELD = "input[type=text]";
    private static final String PASSWORD_FIELD = "input[type=password]";
    private static final String SUBMIT_BUTTON = "input[type=submit]";
    private static final String LOGIN_FORM = "#login-form";
    private static final String ERROR = ".error";
    private static final String SUCCESS = ".success";
    private static final String ERROR_TEXT = "Invalid username or password!";
    private static final String SUCCESS_TEXT = "You've been logged out";


    public LoginPage(Fluent browser) {
        this.browser = browser;
    }

    public LoginPage login(String userName, String password) {
        browser.goTo(LOGIN_URL);
        waitForLoginForm();
        browser.fill(USER_NAME_FIELD).with(userName);
        browser.fill(PASSWORD_FIELD).with(password);
        browser.submit(SUBMIT_BUTTON);
        return this;
    }

    public boolean hasError() {
        return browser.findFirst(ERROR).getText().equals(ERROR_TEXT);
    }

    public boolean hasSuccess() {
        return browser.findFirst(SUCCESS).getText().equals(SUCCESS_TEXT);
    }

    public LoginPage waitForLoginForm() {
        browser.await().atMost(WAIT_TIME, TIME_UNIT).until(LOGIN_FORM).isPresent();
        return this;
    }

}
