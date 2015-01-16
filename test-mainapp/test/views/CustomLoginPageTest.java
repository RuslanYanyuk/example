package views;

import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;

import org.junit.Before;
import org.junit.Test;

import commons.XiTestHelper;
import play.Application;
import play.GlobalSettings;
import play.test.FakeApplication;
import play.twirl.api.Html;
import views.html.main;
import views.usermgmt.XiAbstractUITest;
import views.usermgmt.pages.LoginPage;
import controllers.usermgmt.AuthController;
import controllers.usermgmt.AuthTemplates;
import controllers.usermgmt.PageTemplate;

public class CustomLoginPageTest extends XiAbstractUITest {
	
	private static final String CUSTOM_TEXT = "This is custom template";
		
	@Override
    @Before
    public void setUp(){
		super.setUp();
        new LoginPage(getBrowser()).load();
    }
	
	@Test
	public void logoutPageCanBeCustomizable(){
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
				AuthTemplates.getInstance().setLoginTemplate(createLoginPageTemplate());
			}
			@Override
			public void onStop(Application app){
				AuthTemplates.getInstance().setLoginTemplate(null);
			}
		};
	}
	
	private static PageTemplate createLoginPageTemplate(){
			return new PageTemplate() {
				@Override
				public Html render(Html content) {
					return main.render(content);
			}
		};
	}
	
}
