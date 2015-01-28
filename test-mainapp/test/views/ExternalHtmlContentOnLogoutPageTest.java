package views;

import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeGlobal;
import static usermgmt.Parameters.FIRST_USER_NAME;
import static usermgmt.Parameters.FIRST_USER_PASSWORD;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.Color;

import play.test.FakeApplication;
import usermgmt.YAML;
import views.usermgmt.XiAbstractUITest;
import views.usermgmt.pages.LogoutPage;
import commons.XiTestHelper;

public class ExternalHtmlContentOnLogoutPageTest extends XiAbstractUITest {

	private static final String BACKGROUND_COLOR = "#008800";
    private static final String LOGO_TEXT = "External logo";
    private static final String EXTERNAL_CONTENT_TEXT = "This is external content";

    @Override
    @Before
    public void setUp(){
        super.setUp();
        YAML.GENERAL_USERS.load();
        loginAndLoad(FIRST_USER_NAME, FIRST_USER_PASSWORD, LogoutPage.class);
    }

    @Test
    public void canSeeCustomLogoOnLoginPage(){
        assertTrue(getBrowser().findFirst("#logo-banner").getElement().getText().equals(LOGO_TEXT));
    }

    @Test
    public void canSeeExternalContentOnLogoutPage(){
        assertTrue(getBrowser().findFirst("#test-title").getText().equals(EXTERNAL_CONTENT_TEXT));
    }

    @Test
    public void canSeeCustomStyleOnLogoutPage(){
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
        configuration.put("usermgmt.logout.html", "public/views/mainapp.html");
        configuration.put("usermgmt.logo.text", LOGO_TEXT);        
        return configuration;
    }

}
