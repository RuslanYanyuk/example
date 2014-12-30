package commons.ui;

import static commons.TestHelper.createFakeApplication;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;
import static play.test.Helpers.testBrowser;
import static play.test.Helpers.testServer;

import org.fluentlenium.adapter.FluentTest;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import play.test.TestBrowser;
import play.test.TestServer;

import commons.ui.pages.AbstractPage;

public abstract class AbstractUITest extends FluentTest {

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
    
    protected abstract void login(String userName, String password);
    
    protected abstract <T extends AbstractPage> T loginAndLoad(String userName, String password, Class<T> redirectPageClass);
	
}
