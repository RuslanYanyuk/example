package usermgmt.services;

import org.junit.Before;
import org.junit.Test;
import usermgmt.AbstractTest;
import usermgmt.YAML;
import usermgmt.formbeans.LoginFormBean;
import usermgmt.models.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static usermgmt.Parameters.FIRST_USER_NAME;
import static usermgmt.Parameters.FIRST_USER_PASSWORD;
import static usermgmt.Parameters.INCORRECT_PASSWORD;


public class XiAuthServiceTest extends AbstractTest{
    private XiAuthService service = new XiAuthService();
    User user = new User(FIRST_USER_NAME, FIRST_USER_PASSWORD, null , null);

    @Override
    @Before
    public void setUp() {
        super.setUp();
        YAML.GENERAL_USERS.load();
    }

    @Test
    public void isCorrectUser_loginExistingUser() {
        LoginFormBean loginFormBean = new LoginFormBean(FIRST_USER_NAME, FIRST_USER_PASSWORD);

        boolean isValidUser = service.isCorrectPassword(user, loginFormBean);
        assertThat(isValidUser, is(true));
    }

    @Test
    public void isCorrectUser_doNotLoginUserWithIncorrectPassword() {
        LoginFormBean loginFormBean = new LoginFormBean(FIRST_USER_NAME, INCORRECT_PASSWORD);

        boolean isValidUser = service.isCorrectPassword(user, loginFormBean);
        assertThat(isValidUser, is(false));
    }

    @Test
    public void isCorrectUser_doNotLoginWhenUserIsNull() {
        LoginFormBean loginFormBean = new LoginFormBean(null, FIRST_USER_PASSWORD);

        boolean isValidUser = service.isCorrectPassword(null, loginFormBean);
        assertThat(isValidUser, is(false));
    }

}