package views;

import org.junit.Before;
import org.junit.Test;
import views.usermgmt.XiAbstractUITest;
import views.usermgmt.pages.LoginPage;
import views.usermgmt.pages.LogoutPage;

import java.util.Map;

import static org.junit.Assert.assertTrue;
import static usermgmt.Parameters.FIRST_USER_NAME;
import static usermgmt.Parameters.FIRST_USER_PASSWORD;
import static usermgmt.Parameters.FIRST_USER_PASSWORD;
import usermgmt.YAML;

public class ExternalHtmlContentOnLogoutPageTest extends XiAbstractUITest {

    private static final String LOGO_TEXT = "Awesome app";

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
        assertTrue(getBrowser().findFirst("#test-title").getText().equals("This is custom header"));
    }

    @Test
    public void canSeeCustomStyleOnLogoutPage(){
        assertTrue(getBrowser().findFirst(".test-class").getElement().getCssValue("background-color").equals("rgb(0, 136, 0)"));
    }


    @Override
    protected Map<String, Object> getConfiguration(){
        Map<String, Object> configuration = super.getConfiguration();
        configuration.put("usermgmt.logout.html", "public/views/mainapp.html");
        configuration.put("usermgmt.logo.text", LOGO_TEXT);
        return configuration;
    }

}
