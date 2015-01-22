package pages.usermgmt;

import static utils.usermgmt.ApplicationConf.LOGIN_HTML;
import static utils.usermgmt.ApplicationConf.LOGO_TEXT;
import static utils.usermgmt.Helper.readText;
import play.twirl.api.Html;
import views.html.usermgmt.main;

public class LoginPageTemplateContainer extends PageTemplateContainer {

	private static class Holder {
        private static final LoginPageTemplateContainer INSTANCE = new LoginPageTemplateContainer();
    }

    public static LoginPageTemplateContainer getInstance() {
        return Holder.INSTANCE;
    }
	
	private LoginPageTemplateContainer(){
	}
	
	private static final String LOGIN_TITLE = "Login";
	
	@Override
	PageTemplate createPageTemplate() {
		return new PageTemplate() {
			@Override
			public Html render(Html core) {
				return main.render(LOGIN_TITLE, LOGO_TEXT.value(),
						readText(LOGIN_HTML.value()), core);
			}
		};
	}

}
