package pages.usermgmt;

import static utils.usermgmt.ApplicationConf.LOGIN_HTML;
import static utils.usermgmt.ApplicationConf.LOGO_TEXT;
import static utils.usermgmt.Helper.readText;
import play.twirl.api.Html;
import views.html.usermgmt.container;

public class AdministrationPageTemplateContainer extends PageTemplateContainer {

	private static class Holder {
        private static final AdministrationPageTemplateContainer INSTANCE = new AdministrationPageTemplateContainer();
    }

    public static AdministrationPageTemplateContainer getInstance() {
        return Holder.INSTANCE;
    }
	
	private AdministrationPageTemplateContainer(){
	}
	
	private static final String ADMINISTRATION_TITLE = "Users administration";
	
	@Override
	PageTemplate createPageTemplate() {
		return new PageTemplate() {
			@Override
			public Html render(Html core) {
				return container.render(ADMINISTRATION_TITLE, LOGO_TEXT.value(), core);
			}
		};
	}

}
