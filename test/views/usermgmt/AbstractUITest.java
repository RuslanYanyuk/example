package views.usermgmt;

import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.Fluent;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import play.test.TestBrowser;
import play.test.TestServer;
import views.usermgmt.ui.pages.AbstractPage;
import views.usermgmt.ui.pages.LoginPage;

import static play.test.Helpers.*;
import static views.usermgmt.TestHelper.createFakeApplication;

public class AbstractUITest extends FluentTest{

    private TestServer testServer;

    @Before
    public void setUp() {
        testServer = testServer(3333, createFakeApplication());
        start(testServer);
    }

    @After
    public void tearDown() {
        stop(testServer);
    }

    @Override
    public WebDriver getDefaultDriver() {
        //TODO fix test for working with HtmlUnitDriver
//        return new HtmlUnitDriver(true);
        return  super.getDefaultDriver();
    }

    public TestBrowser getBrowser() {
        return testBrowser(getDriver());
    }

    protected void login(String userName, String password) {
        //TODO move
        LoginPage loginPage = new LoginPage(getBrowser());
        loginPage.login(userName, password);
    }

    protected <T extends AbstractPage> T loginAndLoad(String userName, String password, Class<T> redirectPageClass) {
        //TODO move
        LoginPage loginPage = new LoginPage(getBrowser());
        return loginPage.loginAndLoad(userName, password, redirectPageClass);
    }

}
