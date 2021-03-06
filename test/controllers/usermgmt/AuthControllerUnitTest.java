package controllers.usermgmt;

import com.google.common.collect.ImmutableMap;
import commons.AbstractUnitTest;

import org.junit.Test;

import play.i18n.Messages;
import play.mvc.Result;
import usermgmt.YAML;
import formbeans.usermgmt.UserFormBean;

import static controllers.usermgmt.AuthController.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static usermgmt.Parameters.*;

public class AuthControllerUnitTest extends AbstractUnitTest {

    public static final String REQUEST_PAGE_URL = "/users";

    @Test
    public void loginForm_renderLoginPage() {
        Result result = callAction(
                controllers.usermgmt.routes.ref.AuthController.loginForm()
        );

        assertThat(status(result), is(OK));
        assertThat(contentType(result), is("text/html"));
        assertTrue(contentAsString(result).contains("login-form"));
        assertNull(session(result).get("userName"));
    }

    @Test
    public void login_loginExistingUserAndRedirectToRoot() {
        YAML.GENERAL_USERS.load();
        UserFormBean expectedUserFormBean = new UserFormBean(FIRST_USER_NAME,
                FIRST_USER_FULLNAME, FIRST_USER_ROLE);

        Result result = callAction(
                controllers.usermgmt.routes.ref.AuthController.login(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                        "userName", FIRST_USER_NAME,
                        "password", FIRST_USER_PASSWORD)));

        assertThat(status(result), is(SEE_OTHER));
        assertThat(redirectLocation(result), is("/"));
        assertThat(session(result).get("userName"), is(FIRST_USER_NAME));
    }

    @Test
    public void login_loginExistingUserAndRedirectToRequestPage() {
        YAML.GENERAL_USERS.load();

        Result result = callAction(
                controllers.usermgmt.routes.ref.AuthController.login(),
                fakeRequest().
                        withSession("redirect", REQUEST_PAGE_URL).
                        withFormUrlEncodedBody(ImmutableMap.of(
                                "userName", FIRST_USER_NAME,
                                "password", FIRST_USER_PASSWORD)));

        assertThat(status(result), is(SEE_OTHER));
        assertThat(redirectLocation(result), is(REQUEST_PAGE_URL));
        assertThat(session(result).get("userName"), is(FIRST_USER_NAME));

    }

    @Test
    public void login_doNotLoginUserWithIncorrectPassword() {
        YAML.GENERAL_USERS.load();

        Result result = callAction(
                controllers.usermgmt.routes.ref.AuthController.login(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                        "userName", FIRST_USER_NAME,
                        "password", INCORRECT_PASSWORD)));

        assertThat(status(result), is(OK));
        assertThat(contentType(result), is("text/html"));
        assertTrue(contentAsString(result).contains("login-form"));
        assertTrue(contentAsString(result).contains("error"));
        assertNull(session(result).get("userName"));
    }

    @Test
    public void login_doNotLoginNotExistingUser() {
        YAML.GENERAL_USERS.load();

        Result result = callAction(
                controllers.usermgmt.routes.ref.AuthController.login(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                        "userName", NOT_EXISTED_USER_NAME,
                        "password", FIRST_USER_PASSWORD)));

        assertThat(status(result), is(OK));
        assertThat(contentType(result), is("text/html"));
        assertTrue(contentAsString(result).contains("login-form"));
        assertTrue(contentAsString(result).contains("error"));
        assertNull(session(result).get("userName"));
    }

    @Test
    public void logoutForm_renderLogoutPage() {
    	YAML.GENERAL_USERS.load();
    	
        Result result = callAction(
                controllers.usermgmt.routes.ref.AuthController.logoutForm(),
                fakeRequest().withSession("userName", FIRST_USER_NAME)
        );

        assertThat(status(result), is(OK));
        assertThat(contentType(result), is("text/html"));
        assertTrue(contentAsString(result).contains("logout"));
        assertNull(session(result).get("userName"));
    }

    @Test
    public void logout() {
        YAML.GENERAL_USERS.load();

        Result result = callAction(controllers.usermgmt.routes.ref.AuthController.logout(),
                fakeRequest().withSession("userName", FIRST_USER_NAME));

        assertThat(status(result), is(SEE_OTHER));
        assertThat(cookie("PLAY_SESSION", result).value(), is(""));
        assertThat(flash(result).get("success"), is(Messages.get(LOGOUT_SUCCESS_MESSAGE)));
        assertThat(redirectLocation(result), is("/login"));
    }
}