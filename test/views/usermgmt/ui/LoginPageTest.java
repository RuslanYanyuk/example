package views.usermgmt.ui;

import org.junit.Before;
import org.junit.Test;
import usermgmt.YAML;
import views.usermgmt.AbstractUITest;
import views.usermgmt.ui.pages.FluentTestConstants;
import views.usermgmt.ui.pages.LoginPage;
import views.usermgmt.ui.pages.LogoutPage;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static usermgmt.Parameters.*;

public class LoginPageTest extends AbstractUITest implements FluentTestConstants {

    @Override
    @Before
    public void setUp(){
        super.setUp();
        YAML.GENERAL_USERS.load();
    }

    @Test
    public void login_existingUserCanLogin() {
        LoginPage loginPage = new LoginPage(getBrowser());
        loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);

        assertThat(url()).isEqualTo(INDEX_URL);
    }

    @Test
    public void login_showMessageOnError() {
        LoginPage loginPage = new LoginPage(getBrowser());
        loginPage.login(FIRST_USER_NAME, INCORRECT_PASSWORD).waitForLoginForm();

        assertTrue(loginPage.hasError());
    }

    @Test
    public void login_userWillBeRedirectedToRequestPage() {
        goTo(GET_ALL_USERS_URL);
        LoginPage loginPage = new LoginPage(getBrowser());
        loginPage.waitForLoginForm();
        assertThat(url()).isEqualTo(LOGIN_URL);

        loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);

        assertThat(url()).isEqualTo(GET_ALL_USERS_URL);
    }

    @Test
    public void login_showMessageOnSuccessLogout() {
        LoginPage loginPage = new LoginPage(getBrowser());
        loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);

        assertThat(url()).isEqualTo(INDEX_URL);

        new LogoutPage(getBrowser()).load().logout();
        loginPage.waitForLoginForm();

        assertTrue(loginPage.hasSuccess());
    }

}