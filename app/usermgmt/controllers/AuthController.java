package usermgmt.controllers;

import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import usermgmt.formbeans.LoginFormBean;
import usermgmt.formbeans.UserFormBean;
import usermgmt.models.User;
import usermgmt.services.AuthService;
import usermgmt.services.XiAuthService;
import views.html.*;

import static play.data.Form.form;

public class AuthController extends Controller {

    private AuthService authService = new XiAuthService();

    public Result loginForm() {
        return ok(login.render());
    }

    public Result login() {
        Form<LoginFormBean> loginForm = form(LoginFormBean.class).bindFromRequest();
        LoginFormBean loginFormBean = loginForm.get();
        User user = User.find.where().eq("userName", loginFormBean.userName).findUnique();

        if (loginForm.hasErrors() || !authService.isCorrectPassword(user, loginFormBean)) {
            return badRequest();
        }

        session().clear();
        session().put("userName", user.userName);

        return ok(Json.toJson(UserFormBean.from(user)));
    }

    public Result logout() {
        session().clear();
        return redirect(usermgmt.controllers.routes.AuthController.loginForm());
    }

}
