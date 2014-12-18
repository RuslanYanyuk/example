package views.usermgmt.ui;

import org.junit.Before;
import org.junit.Test;
import usermgmt.YAML;
import views.usermgmt.AbstractUITest;
import views.usermgmt.ui.pages.*;

import static org.junit.Assert.assertTrue;
import static usermgmt.Parameters.*;

public class LoginPageTest extends AbstractUITest{

    @Override
    @Before
    public void setUp(){
        super.setUp();
        YAML.GENERAL_USERS.load();
    }

    @Test
    public void login_existingUserCanLogin() {
        IndexPage indexPage = loginAndGoTo(FIRST_USER_NAME, FIRST_USER_PASSWORD, IndexPage.class);

        assertTrue(indexPage.isAt());
    }

    @Test
    public void login_showMessageOnError() {
        LoginPage loginPage = loginAndGoTo(FIRST_USER_NAME, INCORRECT_PASSWORD, LoginPage.class);

        assertTrue(loginPage.hasError());
    }

    @Test
    public void login_redirectsToRequestPage() {

        LoginPage loginPage = new LoginPage(getBrowser());
        UsersPage usersPage = new UsersPage(getBrowser());
        
        usersPage.load();
        
        assertTrue(loginPage.isAt());

        loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);

        assertTrue(usersPage.isAt());
    }
    
    @Test
    public void login_redirectsToIndexPageIfRequestPageIsLogoutPage() {
        LogoutPage logoutPage = new LogoutPage(getBrowser());
        LoginPage loginPage = new LoginPage(getBrowser());
        IndexPage indexPage = new IndexPage(getBrowser());

        logoutPage.load(false);
        
        assertTrue(loginPage.isAt());

        loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);

        assertTrue(indexPage.isAt());
    }

    @Test
    public void login_showMessageOnSuccessLogout() {
        LoginPage loginPage = new LoginPage(getBrowser());
        LogoutPage logoutPage = loginPage.loginAndGoTo(FIRST_USER_NAME, FIRST_USER_PASSWORD, LogoutPage.class);

        logoutPage.logout();

        assertTrue(loginPage.hasSuccess());
    }

    private <T extends Page> T loginAndGoTo(String userName, String password, Class<T> redirectPageClass) {
        LoginPage loginPage = new LoginPage(getBrowser());
        return loginPage.loginAndGoTo(userName, password, redirectPageClass);
    }

}