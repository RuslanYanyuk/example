package views.usermgmt.pages;

import commons.ui.pages.AbstractPage;
import org.fluentlenium.core.Fluent;
import play.i18n.Messages;

public class LoginPage extends AbstractPage {

    private static final String URL = "/login";
    private static final String USER_NAME_FIELD = "input[type=text]";
    private static final String PASSWORD_FIELD = "input[type=password]";
    private static final String SUBMIT_BUTTON = "input[type=submit]";
    private static final String LOGIN_FORM = "#login-form";
    private static final String ERROR = ".error";
    private static final String SUCCESS = ".success";
    private static final String ERROR_TEXT = "usermgmt.login.error";
    private static final String SUCCESS_TEXT = "usermgmt.logout.success";

    public LoginPage(Fluent browser) {
        super(browser, URL, LOGIN_FORM);
    }

    public <T extends AbstractPage> T loginAndLoad(String userName, String password, Class<T> redirectPageClass) {
        T page = null;
        login(userName, password);
        try {
            page = redirectPageClass.getConstructor(Fluent.class).newInstance(getBrowser());
            page.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    public boolean hasError() {
        return getBrowser().findFirst(ERROR).getText().equals(Messages.get(ERROR_TEXT));
    }

    public boolean hasSuccess() {
        return getBrowser().findFirst(SUCCESS).getText().equals(Messages.get(SUCCESS_TEXT));
    }

    public boolean submitCredentials(String userName, String password){
    	load();
        getBrowser().fill(USER_NAME_FIELD).with(userName);
        getBrowser().fill(PASSWORD_FIELD).with(password);
        getBrowser().submit(SUBMIT_BUTTON);
        return !isAt();
    }
    
    public void login(String userName, String password) {
        if (!submitCredentials(userName, password)){
        	throw new IllegalAccessError();
        }
    }

}
