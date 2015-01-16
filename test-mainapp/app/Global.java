import controllers.usermgmt.AuthTemplates;
import controllers.usermgmt.PageTemplate;
import play.Application;
import play.GlobalSettings;
import play.twirl.api.Html;
import views.html.*;

public class Global extends GlobalSettings {

	@Override
    public void onStart(Application app) {
		AuthTemplates.getInstance().setLoginTemplate(createCustomTemplate());
		AuthTemplates.getInstance().setLogoutTemplate(createCustomTemplate());
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
