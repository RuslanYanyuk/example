package views;

import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeGlobal;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.Color;

import play.test.FakeApplication;
import views.usermgmt.XiAbstractUITest;
import views.usermgmt.pages.LoginPage;
import commons.XiTestHelper;

public class ExternalHtmlContentOnLoginPageTest extends XiAbstractUITest {

	private static final String BACKGROUND_COLOR = "#008800";
    private static final String LOGO_TEXT = "External logo";
    private static final String EXTERNAL_CONTENT_TEXT = "This is external content";

    @Override
    @Before
    public void setUp(){
        super.setUp();
        new LoginPage(getBrowser()).load();
    }

    @Test
    public void canSeeCustomLogoOnLoginPage(){
        assertTrue(getBrowser().findFirst("#logo-banner").getElement().getText().equals(LOGO_TEXT));
    }

	@Test
	public void canSeeExternalContentOnLoginPage(){
        assertTrue(getBrowser().findFirst("#test-title").getText().equals(EXTERNAL_CONTENT_TEXT));
	}

    @Test
    public void canSeeCustomStyleOnLoginPage(){
    	String currentBackgroundColor = getBrowser().findFirst(".test-class").getElement().getCssValue("background-color");
    	currentBackgroundColor = Color.fromString(currentBackgroundColor).asHex();
        assertTrue(currentBackgroundColor.equals(BACKGROUND_COLOR));
    }

    @Override
    protected FakeApplication createFakeApplication(){
    	return fakeApplication(getConfiguration(), fakeGlobal());
    }
    
    private Map<String, Object> getConfiguration(){
        Map<String, Object> configuration = XiTestHelper.getConfiguration();
        configuration.put("usermgmt.login.html", "public/views/mainapp.html");
        configuration.put("usermgmt.logo.text", LOGO_TEXT);
        return configuration;
    }

}
