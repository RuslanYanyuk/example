package usermgmt.services;

import org.junit.Before;
import org.junit.Test;
import usermgmt.AbstractUnitTest;
import usermgmt.YAML;
import usermgmt.formbeans.LoginFormBean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static usermgmt.Parameters.*;


public class XiAuthServiceUnitTest extends AbstractUnitTest {
    private XiAuthService service = new XiAuthService();

    @Override
    @Before
    public void setUp() {
        super.setUp();
        YAML.GENERAL_USERS.load();
    }

    @Test
    public void isCorrectUser_loginExistingUser() {
        LoginFormBean loginFormBean = new LoginFormBean(FIRST_USER_NAME, FIRST_USER_PASSWORD);

        assertTrue(service.isCorrectPassword(loginFormBean));
    }

    @Test
    public void isCorrectUser_doNotLoginUserWithIncorrectPassword() {
        LoginFormBean loginFormBean = new LoginFormBean(FIRST_USER_NAME, INCORRECT_PASSWORD);

        assertFalse(service.isCorrectPassword(loginFormBean));
    }

    @Test
    public void isCorrectUser_doNotLoginWhenUserIsNull() {
        LoginFormBean loginFormBean = new LoginFormBean(null, FIRST_USER_PASSWORD);

        assertFalse(service.isCorrectPassword(loginFormBean));
    }

}