package usermgmt.controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import usermgmt.formbeans.LoginFormBean;
import usermgmt.utils.Secured;
import views.html.login;
import views.html.logout;

import static play.data.Form.form;

public class AuthController extends Controller {

    public static final String INDEX_PAGE = "/";

    public Result loginForm() {
        return ok(login.render(form(LoginFormBean.class)));
    }

    public Result login() {
        Form<LoginFormBean> loginForm = form(LoginFormBean.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        }
        String redirectUrl = session().get("redirect");

        session().clear();
        session().put("userName", loginForm.get().userName);

        return redirect(redirectUrl != null ? redirectUrl : INDEX_PAGE);
    }

    @Security.Authenticated(Secured.class)
    public Result logoutForm() {
        return ok(logout.render());
    }

    public Result logout() {
        session().clear();
        flash().put("success", "You've been logged out");
        return redirect(usermgmt.controllers.routes.AuthController.loginForm());
    }

}
