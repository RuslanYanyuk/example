package views.usermgmt.ui;

import org.junit.Test;
import usermgmt.YAML;
import views.usermgmt.ui.pages.IndexPage;
import views.usermgmt.ui.pages.LoginPage;
import views.usermgmt.AbstractUITest;
import views.usermgmt.ui.pages.LogoutPage;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static usermgmt.Parameters.ADMIN_PASSWORD;
import static usermgmt.Parameters.ADMIN_USER_NAME;

public class LogoutPageTest extends AbstractUITest{

    @Test
    public void logout_SuccessLogoutAndRedirect() {
        YAML.GENERAL_USERS.load();

        LoginPage loginPage = getLogoutPage().logout();

        assertTrue(loginPage.isAt());
        assertTrue(loginPage.hasSuccess());
    }

    @Test
    public void logout_RedirectToIndex() {
        YAML.GENERAL_USERS.load();

        IndexPage indexPage = getLogoutPage().redirectToRoot();

        assertTrue(indexPage.isAt());
    }

    @Test
    public void logout_noFormIfNotLogged() {
        YAML.GENERAL_USERS.load();

        goTo(LogoutPage.URL);

        assertThat(url()).isEqualTo(LoginPage.URL);
    }

    private LogoutPage getLogoutPage() {
        return new LoginPage(getBrowser()).loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, LogoutPage.class);
    }

}