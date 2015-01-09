package commons.ui;

import static commons.TestHelper.clearDb;
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

public abstract class AbstractUITest extends FluentTest {

	private TestServer testServer;

    @Before
    public void setUp() {
        testServer = testServer(3333, createFakeApplication());
        start(testServer);
        clearDb();
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
    
}
