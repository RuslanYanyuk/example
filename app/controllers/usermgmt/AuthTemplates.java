package controllers.usermgmt;

import static utils.usermgmt.ApplicationConf.LOGIN_HTML;
import static utils.usermgmt.ApplicationConf.LOGOUT_HTML;
import static utils.usermgmt.ApplicationConf.LOGO_TEXT;
import static utils.usermgmt.Helper.readText;
import play.twirl.api.Html;
import views.html.usermgmt.main;

public class AuthTemplates {
	
	private static final String LOGIN_TITLE = "Login";
	
	private static final String LOGOUT_TITLE = "Logout";
	
	private static PageTemplate loginTemplate;
	
	private static PageTemplate logoutTemplate;
	
	private static Object loginTemplateMutex = new Object();
	
	private static Object logoutTemplateMutex = new Object();
	
	private AuthTemplates(){
	}
		
	static PageTemplate getLoginTemplate(){
		if (loginTemplate == null){
			synchronized (loginTemplateMutex) {
				loginTemplate = createDefaultLoginTemplate();
			}
		}
		return loginTemplate;
	}
	
	static PageTemplate getLogoutTemplate(){
		if (logoutTemplate == null){
			synchronized (logoutTemplateMutex) {
				logoutTemplate = createDefaultLogoutTemplate();
			}
		}
		return logoutTemplate;
	}
	
	public static void setLoginTemplate(PageTemplate loginTemplate){
		synchronized (loginTemplateMutex) {
			AuthTemplates.loginTemplate = loginTemplate;			
		}
	}
	
	public static void setLogoutTemplate(PageTemplate logoutTemplate){
		synchronized (logoutTemplateMutex) {
			AuthTemplates.logoutTemplate = logoutTemplate;			
		}
	}
	
	private static PageTemplate createDefaultLoginTemplate(){
    	return new PageTemplate() {
			@Override
			public Html render(Html core) {
				return main.render(LOGIN_TITLE, LOGO_TEXT.value(),
						readText(LOGIN_HTML.value()), core);
			}
		};
    }
    
    private static PageTemplate createDefaultLogoutTemplate(){
    	return new PageTemplate() {
    		
			@Override
			public Html render(Html core) {
				return main.render(LOGOUT_TITLE, LOGO_TEXT.value(),
						readText(LOGOUT_HTML.value()), core);
			}
		};
    }
	
}
