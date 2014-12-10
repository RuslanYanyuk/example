package usermgmt.ui;

import org.junit.Test;
import usermgmt.AbstractUITest;
import usermgmt.YAML;
import usermgmt.ui.pages.FluentTestConstants;
import usermgmt.ui.pages.LoginPage;

import static org.junit.Assert.assertTrue;
import static usermgmt.Parameters.*;
import static org.fest.assertions.Assertions.assertThat;

public class LoginPageTest extends AbstractUITest implements FluentTestConstants{

    private static final String ERROR_TEXT = "Invalid username or password!";
    private static final String SUCCESS_TEXT = "You've been logged out";

    @Test
    public void login_existingUserCanLogin() {
        YAML.GENERAL_USERS.load();

        LoginPage loginPage = new LoginPage(getBrowser());
        loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);

        assertThat(url()).isEqualTo(INDEX_URL);
    }

    @Test
    public void login_showMessageOnError() {
        YAML.GENERAL_USERS.load();

        LoginPage loginPage = new LoginPage(getBrowser());
        loginPage.login(FIRST_USER_NAME, INCORRECT_PASSWORD).waitForLoginForm();

        assertTrue(loginPage.hasError(ERROR_TEXT));
    }

    @Test
    public void login_userWillBeRedirectedToRequestPage() {
        YAML.GENERAL_USERS.load();

        goTo(GET_ALL_USERS_URL);
        LoginPage loginPage = new LoginPage(getBrowser());
        loginPage.waitForLoginForm();
        assertThat(url()).isEqualTo(LOGIN_URL);

        loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);

        assertThat(url()).isEqualTo(GET_ALL_USERS_URL);
    }

    @Test
    public void login_showMessageOnSuccessLogout() {
        YAML.GENERAL_USERS.load();

        LoginPage loginPage = new LoginPage(getBrowser());
        loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);

        assertThat(url()).isEqualTo(INDEX_URL);

        goTo(LOGOUT_URL);
        loginPage.waitForLoginForm();

        assertTrue(loginPage.hasSuccess(SUCCESS_TEXT));
    }

}