package usermgmt.ui;

import org.junit.Test;
import usermgmt.AbstractUITest;
import usermgmt.YAML;
import usermgmt.ui.pages.FluentTestConstants;
import usermgmt.ui.pages.LoginPage;
import usermgmt.ui.pages.LogoutPage;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static usermgmt.Parameters.*;

public class LogoutPageTest extends AbstractUITest implements FluentTestConstants{

    @Test
    public void logout_SuccessLogoutAndRedirect() {
        YAML.GENERAL_USERS.load();

        LoginPage loginPage = new LoginPage(getBrowser());
        loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);

        LogoutPage logoutPage = new LogoutPage(getBrowser());
        logoutPage.load().logout();

        assertThat(url()).isEqualTo(LOGIN_URL);
        assertTrue(loginPage.hasSuccess());
    }

    @Test
    public void logout_RedirectToIndex() {
        YAML.GENERAL_USERS.load();

        LoginPage loginPage = new LoginPage(getBrowser());
        loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);

        LogoutPage logoutPage = new LogoutPage(getBrowser());
        logoutPage.load().redirectToRoot();

        assertThat(url()).isEqualTo(INDEX_URL);
    }

    @Test
    public void logout_noFormIfNotLogged() {
        YAML.GENERAL_USERS.load();

        goTo(LOGOUT_URL);

        assertThat(url()).isEqualTo(LOGIN_URL);
    }

}