package controllers.usermgmt;

import static utils.usermgmt.ApplicationConf.LOGIN_HTML;
import static utils.usermgmt.ApplicationConf.LOGOUT_HTML;
import static utils.usermgmt.ApplicationConf.LOGO_TEXT;
import static utils.usermgmt.Helper.readText;
import play.twirl.api.Html;
import views.html.usermgmt.main;

public class AuthTemplates {

	private static AuthTemplates instance = new AuthTemplates();
	
	private static final String LOGIN_TITLE = "Login";
	
	private static final String LOGOUT_TITLE = "Logout";
	
	private PageTemplate loginTemplate;
	
	private PageTemplate logoutTemplate;
	
	private AuthTemplates(){
	}
	
	synchronized void initialize(){
		if (loginTemplate == null) loginTemplate = createDefaultLoginTemplate();
		if (logoutTemplate == null) logoutTemplate = createDefaultLogoutTemplate();
	}
	
	PageTemplate getLoginTemplate(){
		return loginTemplate;
	}
	
	PageTemplate getLogoutTemplate(){
		return logoutTemplate;
	}
	
	public synchronized void setLoginTemplate(PageTemplate loginTemplate){
		if (loginTemplate == null){
			loginTemplate = createDefaultLoginTemplate();
		}
		this.loginTemplate = loginTemplate;
	}
	
	public synchronized void setLogoutTemplate(PageTemplate logoutTemplate){
		if (logoutTemplate == null){
			logoutTemplate = createDefaultLogoutTemplate();
		}
		this.logoutTemplate = logoutTemplate;
	}
	
	public static AuthTemplates getInstance(){
		return instance;
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
