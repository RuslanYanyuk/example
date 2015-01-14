package commons.ui;

import commons.XiTestHelper;
import org.fluentlenium.adapter.FluentTest;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import play.test.TestBrowser;
import play.test.TestServer;

import java.util.Map;

import static commons.TestHelper.createFakeApplication;
import static commons.XiTestHelper.cleanDb;
import static play.test.Helpers.*;

public abstract class AbstractUITest extends FluentTest {

	private TestServer testServer;
	
	public static final String HOST = "localhost";
	public static final int PORT = 3333;

    @Before
    public void setUp() {
        testServer = testServer(PORT, createFakeApplication(getConfiguration()));
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

    protected Map<String, Object> getConfiguration(){
        return XiTestHelper.getConfiguration();
    }

}
