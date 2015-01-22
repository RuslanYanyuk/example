import pages.usermgmt.LoginPageTemplateContainer;
import pages.usermgmt.LogoutPageTemplateContainer;
import pages.usermgmt.PageTemplate;
import play.Application;
import play.GlobalSettings;
import play.twirl.api.Html;
import views.html.*;

public class Global extends GlobalSettings {

	@Override
    public void onStart(Application app) {
		LoginPageTemplateContainer.getInstance().setPageTemplate(createCustomTemplate());
		LogoutPageTemplateContainer.getInstance().setPageTemplate(createCustomTemplate());
	}
	
	private static PageTemplate createCustomTemplate(){
		return new PageTemplate() {
				@Override
				public Html render(Html content) {
					return main.render(content);
			}
		};
	}
}
