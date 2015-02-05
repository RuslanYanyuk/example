package views;

import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static usermgmt.Parameters.ADMIN_USER_NAME;
import static usermgmt.Parameters.ADMIN_PASSWORD;;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import pages.usermgmt.AdministrationPageTemplateContainer;
import pages.usermgmt.PageTemplate;
import play.Application;
import play.GlobalSettings;
import play.test.FakeApplication;
import play.twirl.api.Html;
import usermgmt.YAML;
import views.html.main;
import views.usermgmt.XiAbstractUITest;
import views.usermgmt.pages.AdministrationPage;
import commons.XiTestHelper;

public class CustomAdministrationPageTest extends XiAbstractUITest {
	
	private static final String CUSTOM_TEXT = "This is custom template";
		
	@Override
    @Before
    public void setUp(){
		super.setUp();
        YAML.GENERAL_USERS.load();
        loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
    }
	
	@Test
	public void administrationPageCanBeCustomizable(){
		assertTrue(getBrowser().$("#custom").getText().equals(CUSTOM_TEXT));
	}
	
	@Override
	protected FakeApplication createFakeApplication(){
		return fakeApplication(XiTestHelper.getConfiguration(), createGlobalSettings());
	}
	
	private static GlobalSettings createGlobalSettings(){
		return new GlobalSettings(){
			@Override
			public void onStart(Application app){
				AdministrationPageTemplateContainer.getInstance().setPageTemplate(createAdministrationPageTemplate());
			}
			@Override
			public void onStop(Application app){
				AdministrationPageTemplateContainer.getInstance().setPageTemplate(null);
			}
		};
	}
	
	private static PageTemplate createAdministrationPageTemplate(){
			return new PageTemplate() {
				@Override
				public Html render(Html content) {
					return main.render(content);
			}
		};
	}
	
}
