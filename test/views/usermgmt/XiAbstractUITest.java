package views.usermgmt;

import org.junit.Before;

import views.usermgmt.pages.LoginPage;
import commons.ui.AbstractUITest;
import commons.ui.pages.AbstractPage;

public abstract class XiAbstractUITest extends AbstractUITest {

    private LoginPage loginPage;

    @Before
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(getBrowser());
    }
    
    @Override
    protected void login(String userName, String password) {
        loginPage.login(userName, password);
    }

    @Override
    protected <T extends AbstractPage> T loginAndLoad(String userName, String password, Class<T> redirectPageClass) {
        return loginPage.loginAndLoad(userName, password, redirectPageClass);
    }

}
