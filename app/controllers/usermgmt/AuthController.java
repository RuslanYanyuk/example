package controllers.usermgmt;

import be.objectify.deadbolt.java.actions.Dynamic;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import formbeans.usermgmt.LoginFormBean;
import views.html.usermgmt.*;
import static play.data.Form.form;
import static utils.usermgmt.Constants.*;
import static ximodels.usermgmt.Role.Names.*;

public class AuthController extends Controller {
	
	static final String LOGOUT_SUCCESS_MESSAGE = "usermgmt.logout.success";
	
    public static Result loginForm() {
        if (session().get("userName") != null) {
            return redirect(INDEX_PAGE);
        }
        return ok(AuthTemplates.getLoginTemplate().render(login.render(form(LoginFormBean.class))));
    }

    public static Result login() {
        Form<LoginFormBean> loginForm = form(LoginFormBean.class).bindFromRequest();
        if (loginForm.hasErrors()) {
        	//TODO return another status code (not bad request)
            return badRequest(AuthTemplates.getLoginTemplate().render(login.render(loginForm)));
        }
        String redirectUrl = session().get("redirect");
        session().clear();
        session().put("userName", loginForm.get().userName);
        return redirect(redirectUrl != null ? redirectUrl : INDEX_PAGE);
    }

    @Dynamic(LOGGED_IN)
    public static Result logoutForm() {
        return ok(AuthTemplates.getLogoutTemplate().render(logout.render()));
    }

    public static Result logout() {
        session().clear();
        flash().put("success", Messages.get(LOGOUT_SUCCESS_MESSAGE));
        return redirect(controllers.usermgmt.routes.AuthController.loginForm());
    }
    
}
