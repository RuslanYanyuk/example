package pages.usermgmt;

import static utils.usermgmt.ApplicationConf.LOGOUT_HTML;
import static utils.usermgmt.ApplicationConf.LOGO_TEXT;
import static utils.usermgmt.Helper.readText;
import play.twirl.api.Html;
import views.html.usermgmt.main;

public class LogoutPageTemplateContainer extends PageTemplateContainer {

	private static class Holder {
        private static final LogoutPageTemplateContainer INSTANCE = new LogoutPageTemplateContainer();
    }

    public static LogoutPageTemplateContainer getInstance() {
        return Holder.INSTANCE;
    }
	
	private LogoutPageTemplateContainer(){
	}
	
	private static final String LOGOUT_TITLE = "Logout";
	
	@Override
	PageTemplate createPageTemplate() {
		return new PageTemplate() {
			@Override
			public Html render(Html core) {
				return main.render(LOGOUT_TITLE, LOGO_TEXT.value(),
						readText(LOGOUT_HTML.value()), core);
			}
		};
	}

}
