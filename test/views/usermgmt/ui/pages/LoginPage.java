package views.usermgmt.ui.pages;

import org.fluentlenium.core.Fluent;

public class LoginPage implements Page{

    public static final String URL = "http://localhost:3333/login";
    private static final String USER_NAME_FIELD = "input[type=text]";
    private static final String PASSWORD_FIELD = "input[type=password]";
    private static final String SUBMIT_BUTTON = "input[type=submit]";
    private static final String LOGIN_FORM = "#login-form";
    private static final String ERROR = ".error";
    private static final String SUCCESS = ".success";
    private static final String ERROR_TEXT = "Invalid username or password!";
    private static final String SUCCESS_TEXT = "You've been logged out";

    private Fluent browser;

    public LoginPage(Fluent browser) {
        this.browser = browser;
    }

    @Override
    public  LoginPage load() {
        return this;
    }

    @Override
    public boolean isAt() {
        return browser.url().equals(URL) ? true : false;
    }

    public <T extends Page> T loginAndGoTo(String userName, String password, Class<T> redirectPageClass) {
        login(userName, password);
        try {
            return redirectPageClass.getConstructor(Fluent.class).newInstance(browser).load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean hasError() {
        return browser.findFirst(ERROR).getText().equals(ERROR_TEXT);
    }

    public boolean hasSuccess() {
        return browser.findFirst(SUCCESS).getText().equals(SUCCESS_TEXT);
    }

    private void login(String userName, String password) {
        browser.goTo(URL);
        waitForLoginForm();
        browser.fill(USER_NAME_FIELD).with(userName);
        browser.fill(PASSWORD_FIELD).with(password);
        browser.submit(SUBMIT_BUTTON);
    }

    private LoginPage waitForLoginForm() {
        browser.await().atMost(WAIT_TIME, TIME_UNIT).until(LOGIN_FORM).isPresent();
        return this;
    }

}
