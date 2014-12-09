package usermgmt.controllers;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import play.mvc.Result;
import usermgmt.AbstractUnitTest;
import usermgmt.YAML;
import usermgmt.formbeans.UserFormBean;

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
                usermgmt.controllers.routes.ref.AuthController.loginForm()
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
                usermgmt.controllers.routes.ref.AuthController.login(),
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
                usermgmt.controllers.routes.ref.AuthController.login(),
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
                usermgmt.controllers.routes.ref.AuthController.login(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                        "userName", FIRST_USER_NAME,
                        "password", INCORRECT_PASSWORD)));

        assertThat(status(result), is(BAD_REQUEST));
        assertThat(contentType(result), is("text/html"));
        assertTrue(contentAsString(result).contains("login-form"));
        assertTrue(contentAsString(result).contains("error"));
        assertNull(session(result).get("userName"));
    }

    @Test
    public void login_doNotLoginNotExistingUser() {
        YAML.GENERAL_USERS.load();

        Result result = callAction(
                usermgmt.controllers.routes.ref.AuthController.login(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                        "userName", NOT_EXISTED_USER_NAME,
                        "password", FIRST_USER_PASSWORD)));

        assertThat(status(result), is(BAD_REQUEST));
        assertThat(contentType(result), is("text/html"));
        assertTrue(contentAsString(result).contains("login-form"));
        assertTrue(contentAsString(result).contains("error"));
        assertNull(session(result).get("userName"));
    }

    @Test
    public void logout() {
        YAML.GENERAL_USERS.load();

        Result result = callAction(usermgmt.controllers.routes.ref.AuthController.logout(),
                fakeRequest().withSession("userName", FIRST_USER_NAME));

        assertThat(status(result), is(SEE_OTHER));
        assertThat(cookie("PLAY_SESSION", result).value(), is(""));
        assertThat(flash(result).get("success"), is("You've been logged out"));
        assertThat(redirectLocation(result), is("/login"));
    }
}