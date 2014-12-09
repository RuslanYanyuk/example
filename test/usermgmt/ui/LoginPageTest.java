package usermgmt.ui;

import org.junit.Test;
import play.libs.F;
import play.test.TestBrowser;
import usermgmt.YAML;
import usermgmt.ui.pages.FluentTestConstants;
import usermgmt.ui.pages.LoginPage;

import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;
import static usermgmt.Parameters.*;
import static org.fest.assertions.Assertions.assertThat;
import static usermgmt.TestHelper.createFakeApplication;

public class LoginPageTest implements FluentTestConstants{

    private static final String ERROR_TEXT = "Invalid username or password!";
    private static final String SUCCESS_TEXT = "You've been logged out";
    public static final int PORT = 3333;

    @Test
    public void login_existingUserCanLogin() {
        running(testServer(PORT, createFakeApplication()), HTMLUNIT, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                YAML.GENERAL_USERS.load();

                LoginPage loginPage = new LoginPage(browser);
                loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);

                assertThat(browser.url()).isEqualTo(INDEX_URL);
            }
        });
    }

    @Test
    public void login_showMessageOnError() {
        running(testServer(PORT, createFakeApplication()), HTMLUNIT, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                YAML.GENERAL_USERS.load();

                LoginPage loginPage = new LoginPage(browser);
                loginPage.login(FIRST_USER_NAME, INCORRECT_PASSWORD);
                loginPage.waitForLoginForm();

                assertTrue(loginPage.hasError(ERROR_TEXT));
            }
        });
    }

    @Test
    public void login_userWillBeRedirectedToRequestPage() {
        running(testServer(PORT, createFakeApplication()), HTMLUNIT, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                YAML.GENERAL_USERS.load();
                LoginPage loginPage = new LoginPage(browser);

                browser.goTo(GET_ALL_USERS_URL);
                loginPage.waitForLoginForm();
                assertThat(browser.url()).isEqualTo(LOGIN_URL);

                loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);

                assertThat(browser.url()).isEqualTo(GET_ALL_USERS_URL);

            }
        });
    }

    @Test
    public void login_showMessageOnSuccessLogout() {
        running(testServer(PORT, createFakeApplication()), HTMLUNIT, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                YAML.GENERAL_USERS.load();

                LoginPage loginPage = new LoginPage(browser);
                loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);

                assertThat(browser.url()).isEqualTo(INDEX_URL);

                browser.goTo(LOGOUT_URL);
                loginPage.waitForLoginForm();

                assertTrue(loginPage.hasSuccess(SUCCESS_TEXT));
            }
        });
    }

}
