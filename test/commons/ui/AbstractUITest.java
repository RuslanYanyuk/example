package commons.ui;

import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.Fluent;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import commons.ui.pages.AbstractPage;

import play.test.TestBrowser;
import play.test.TestServer;
import views.usermgmt.pages.LoginPage;
import static commons.TestHelper.createFakeApplication;
import static play.test.Helpers.*;

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
        return new HtmlUnitDriver(true);
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
