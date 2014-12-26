package views.usermgmt;

import org.junit.Before;
import org.junit.Test;

import commons.ui.AbstractUITest;
import usermgmt.YAML;
import views.usermgmt.pages.*;
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
        IndexPage indexPage = loginAndLoad(FIRST_USER_NAME, FIRST_USER_PASSWORD, IndexPage.class);

        assertTrue(indexPage.isAt());
    }

    @Test
    public void login_showMessageOnError() {
        LoginPage loginPage = new LoginPage(getBrowser());
        loginPage.login(FIRST_USER_NAME, INCORRECT_PASSWORD);

        assertTrue(loginPage.hasError());
    }

    @Test
    public void login_redirectsToRequestPage() {
        LoginPage loginPage = new LoginPage(getBrowser());
        AdministrationPage usersPage = new AdministrationPage(getBrowser());
        
        goTo(AdministrationPage.URL);
        
        assertTrue(loginPage.isAt());

        loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);

        assertTrue(usersPage.isAt());
    }
    
    @Test
    public void login_redirectsToIndexPageIfRequestPageIsLogoutPage() {
        LoginPage loginPage = new LoginPage(getBrowser());
        IndexPage indexPage = new IndexPage(getBrowser());

        goTo(LogoutPage.URL);
        
        assertTrue(loginPage.isAt());

        loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);

        assertTrue(indexPage.isAt());
    }
    
    
    @Test
    public void login_redirectsToIndexPageIfAlreadyLoggedIn() {
    	IndexPage indexPage = new IndexPage(getBrowser());
        LoginPage loginPage = new LoginPage(getBrowser());
        
        loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);
        assertTrue(indexPage.isAt());
        
        goTo(LoginPage.URL);
        assertTrue(indexPage.isAt());
    }

    @Test
    public void login_showMessageOnSuccessLogout() {
        LoginPage loginPage = new LoginPage(getBrowser());
        LogoutPage logoutPage = loginPage.loginAndLoad(FIRST_USER_NAME, FIRST_USER_PASSWORD, LogoutPage.class);

        logoutPage.logout();

        assertTrue(loginPage.hasSuccess());
    }

}