package views.usermgmt;

import org.fluentlenium.adapter.FluentTest;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import play.test.TestBrowser;
import play.test.TestServer;
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
        return new HtmlUnitDriver(true);
    }

    public TestBrowser getBrowser() {
        return testBrowser(getDriver());
    }

    public void login(String userName, String password) {
        LoginPage loginPage = new LoginPage(getBrowser());
        loginPage.login(userName, password);
    }

}
