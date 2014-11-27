package usermgmt.controllers;

import com.google.inject.Inject;

import play.*;
import play.mvc.*;
import usermgmt.services.AuthService;
import views.html.*;

public class AuthController extends Controller {

	@Inject
	private AuthService authService;
	
    public Result loginForm() {
        return ok(login.render());
    }

    public Result login() {
        return ok();
    }
    
    public Result logout() {
        return ok();
    }
    
}
