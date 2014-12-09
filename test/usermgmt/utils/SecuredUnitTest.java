package usermgmt.utils;

import org.junit.Test;
import play.libs.Json;
import play.mvc.Result;
import usermgmt.AbstractUnitTest;
import usermgmt.YAML;
import usermgmt.formbeans.UserFormBean;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static play.mvc.Http.Status.SEE_OTHER;
import static play.test.Helpers.*;
import static usermgmt.Parameters.*;

public class SecuredUnitTest extends AbstractUnitTest {

    @Test
    public void noAccessToUserControllerForNotLoggedInUser() {
        YAML.GENERAL_USERS.load();

        Result result = callAction(usermgmt.controllers.routes.ref.UserController.get(FIRST_USER_NAME));

        assertThat(status(result), is(SEE_OTHER));
        assertThat(contentAsString(result), is(""));
        assertThat(redirectLocation(result), is("/login"));
    }

    @Test
    public void accessToUserControllerForLoggedInUser() {
        YAML.GENERAL_USERS.load();
        UserFormBean expectedUserFormBean = new UserFormBean(FIRST_USER_NAME, FIRST_USER_FULLNAME, FIRST_USER_ROLE);

        Result result = callAction(usermgmt.controllers.routes.ref.UserController.get(FIRST_USER_NAME),
                fakeRequest().withSession("userName", ADMIN_USER_NAME));

        assertThat(status(result), is(OK));
        assertThat(contentAsString(result), is(Json.toJson(expectedUserFormBean).toString()));
    }
}