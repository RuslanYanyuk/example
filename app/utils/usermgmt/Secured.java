package utils.usermgmt;


import play.mvc.Result;

import static play.mvc.Http.Context;
import static play.mvc.Security.Authenticator;

public class Secured extends Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("userName");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        ctx.session().put("redirect", ctx.request().uri());
        return redirect(controllers.usermgmt.routes.AuthController.loginForm());
    }
}