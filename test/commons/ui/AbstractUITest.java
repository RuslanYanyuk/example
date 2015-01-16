package commons.ui;

import commons.XiTestHelper;

import org.fluentlenium.adapter.FluentTest;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import play.test.FakeApplication;
import play.test.TestBrowser;
import play.test.TestServer;

import static commons.XiTestHelper.cleanDb;
import static play.test.Helpers.*;

public abstract class AbstractUITest extends FluentTest {

	private TestServer testServer;
	
	public static final String HOST = "localhost";
	public static final int PORT = 3333;

    @Before
    public void setUp() {
        testServer = testServer(PORT, createFakeApplication());
        start(testServer);
        cleanDb();
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

    protected FakeApplication createFakeApplication(){
    	return fakeApplication(XiTestHelper.getConfiguration(), fakeGlobal());
    }

}
