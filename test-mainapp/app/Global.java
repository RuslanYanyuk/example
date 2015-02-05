import pages.usermgmt.LoginPageTemplateContainer;
import pages.usermgmt.LogoutPageTemplateContainer;
import pages.usermgmt.AdministrationPageTemplateContainer;
import pages.usermgmt.PageTemplate;
import play.Application;
import play.GlobalSettings;
import play.twirl.api.Html;
import views.html.*;

public class Global extends GlobalSettings {

	@Override
    public void onStart(Application app) {
		PageTemplate customTemplate = createCustomTemplate();
		LoginPageTemplateContainer.getInstance().setPageTemplate(customTemplate);
		LogoutPageTemplateContainer.getInstance().setPageTemplate(customTemplate);
		AdministrationPageTemplateContainer.getInstance().setPageTemplate(customTemplate);
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
