package controllers.usermgmt;

import be.objectify.deadbolt.java.actions.Dynamic;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import formbeans.usermgmt.LoginFormBean;
import static utils.usermgmt.ApplicationConf.*;
import views.html.usermgmt.login;
import views.html.usermgmt.logout;
import static play.data.Form.form;
import static utils.usermgmt.Constants.*;
import static utils.usermgmt.Helper.readText;
import static ximodels.usermgmt.Role.Names.*;

public class AuthController extends Controller {

    static final String LOGOUT_SUCCESS_MESSAGE = "usermgmt.logout.success";

    public Result loginForm() {
        if (session().get("userName") != null) {
            return redirect(INDEX_PAGE);
        }
        return ok(login.render(
                        form(LoginFormBean.class),
                        LOGO_TEXT.value(),
                        readText(LOGIN_HTML.value())
                )
        );
    }

    public Result login() {
        Form<LoginFormBean> loginForm = form(LoginFormBean.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(
                            loginForm,
                            LOGO_TEXT.value(),
                            readText(LOGIN_HTML.value())
                    )
            );
        }
        String redirectUrl = session().get("redirect");
        session().clear();
        session().put("userName", loginForm.get().userName);
        return redirect(redirectUrl != null ? redirectUrl : INDEX_PAGE);
    }

    @Dynamic(LOGGED_IN)
    public Result logoutForm() {
        return ok(logout.render(
                        LOGO_TEXT.value(),
                        readText(LOGOUT_HTML.value())
                )
        );
    }

    public Result logout() {
        session().clear();
        flash().put("success", Messages.get(LOGOUT_SUCCESS_MESSAGE));
        return redirect(controllers.usermgmt.routes.AuthController.loginForm());
    }

}
