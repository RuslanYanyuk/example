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


    public LoginPage(Fluent browser) {
        this.browser = browser;
    }

    public void login(String userName, String password) {
        browser.goTo(LOGIN_URL);
        waitForLoginForm();
        browser.fill(USER_NAME_FIELD).with(userName);
        browser.fill(PASSWORD_FIELD).with(password);
        browser.submit(SUBMIT_BUTTON);
    }

    public boolean hasError(String errorText) {
        return browser.findFirst(ERROR).getText().equals(errorText);
    }

    public boolean hasSuccess(String successText) {
        return browser.findFirst(SUCCESS).getText().equals(successText);
    }

    public void waitForLoginForm() {
        browser.await().atMost(WAIT_TIME, TIME_UNIT).until(LOGIN_FORM).isPresent();
    }

}
