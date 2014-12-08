package usermgmt.controllers;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Result;
import usermgmt.AbstractTest;
import usermgmt.YAML;
import usermgmt.formbeans.UserFormBean;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static usermgmt.Parameters.*;

public class AuthControllerTest extends AbstractTest {

    @Test
    public void login_loginExistingUser() {
        YAML.GENERAL_USERS.load();
        UserFormBean expectedUserFormBean = new UserFormBean(FIRST_USER_NAME,
                FIRST_USER_FULLNAME, FIRST_USER_ROLE);

        Result result = callAction(
                usermgmt.controllers.routes.ref.AuthController.login(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                        "userName", FIRST_USER_NAME,
                        "password", FIRST_USER_PASSWORD)));

        assertThat(status(result), is(OK));
        assertThat(contentAsString(result), is(Json.toJson(expectedUserFormBean).toString()));
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
    }

    @Test
    public void logout() {
        YAML.GENERAL_USERS.load();

        Result result = callAction(usermgmt.controllers.routes.ref.AuthController.logout(),
                fakeRequest().withSession("userName", FIRST_USER_NAME));

        assertThat(status(result), is(SEE_OTHER));
        assertThat(cookie("PLAY_SESSION", result).value(), is(""));
        assertThat(redirectLocation(result), is("/login"));
    }
}