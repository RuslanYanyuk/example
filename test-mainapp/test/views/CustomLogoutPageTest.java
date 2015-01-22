package views;

import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static usermgmt.Parameters.FIRST_USER_NAME;
import static usermgmt.Parameters.FIRST_USER_PASSWORD;

import org.junit.Before;
import org.junit.Test;

import commons.XiTestHelper;
import play.Application;
import play.GlobalSettings;
import play.test.FakeApplication;
import play.twirl.api.Html;
import usermgmt.YAML;
import views.html.main;
import views.usermgmt.XiAbstractUITest;
import views.usermgmt.pages.LogoutPage;
import controllers.usermgmt.AuthController;
import controllers.usermgmt.AuthTemplates;
import controllers.usermgmt.PageTemplate;

public class CustomLogoutPageTest extends XiAbstractUITest {
	
	private static final String CUSTOM_TEXT = "This is custom template";
	
	@Override
    @Before
    public void setUp(){		
		super.setUp();
        YAML.GENERAL_USERS.load();
        loginAndLoad(FIRST_USER_NAME, FIRST_USER_PASSWORD, LogoutPage.class);
    }
	
	@Test
	public void loginPageCanBeCustomizable(){
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
				AuthTemplates.setLogoutTemplate(createLogoutPageTemplate());
			}
			@Override
			public void onStop(Application app){
				AuthTemplates.setLogoutTemplate(null);
			}
		};
	}
	
	private static PageTemplate createLogoutPageTemplate(){
			return new PageTemplate() {
				@Override
				public Html render(Html content) {
					return main.render(content);
			}
		};
	}
	
}
