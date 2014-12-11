package views.usermgmt.ui;

import org.junit.Test;
import usermgmt.YAML;
import views.usermgmt.ui.pages.FluentTestConstants;
import views.usermgmt.ui.pages.LoginPage;
import views.usermgmt.AbstractUITest;
import views.usermgmt.ui.pages.LogoutPage;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static usermgmt.Parameters.ADMIN_PASSWORD;
import static usermgmt.Parameters.ADMIN_USER_NAME;

public class LogoutPageTest extends AbstractUITest implements FluentTestConstants{

    @Test
    public void logout_SuccessLogoutAndRedirect() {
        YAML.GENERAL_USERS.load();

        login(ADMIN_USER_NAME, ADMIN_PASSWORD);

        LogoutPage logoutPage = new LogoutPage(getBrowser());
        logoutPage.load().logout();

        assertThat(url()).isEqualTo(LOGIN_URL);
        assertTrue(new LoginPage(getBrowser()).hasSuccess());
    }

    @Test
    public void logout_RedirectToIndex() {
        YAML.GENERAL_USERS.load();

        login(ADMIN_USER_NAME, ADMIN_PASSWORD);

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