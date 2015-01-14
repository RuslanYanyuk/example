package views;

import org.junit.Test;
import views.usermgmt.XiAbstractUITest;
import views.usermgmt.pages.LoginPage;

import java.util.Map;

import static org.junit.Assert.assertTrue;

public class ExternalHtmlContentTest extends XiAbstractUITest {


	@Test
	public void canSeeCustomTitleOnLoginPage(){
        LoginPage loginPage = new LoginPage(getBrowser());
        loginPage.load();
        assertTrue(getBrowser().findFirst("#test-title").getText().equals("This is custom header"));
	}

    @Test
    public void canSeeCustomStyleOnLoginPage(){
        LoginPage loginPage = new LoginPage(getBrowser());
        loginPage.load();
        assertTrue(getBrowser().findFirst(".test-class").getElement().getCssValue("background-color").equals("rgb(0, 136, 0)"));
    }

    @Override
    protected Map<String, Object> getConfiguration(){
        Map<String, Object> configuration = super.getConfiguration();
        configuration.put("usermgmt.login.html", "public/views/mainapp.html");
        return configuration;
    }

}
