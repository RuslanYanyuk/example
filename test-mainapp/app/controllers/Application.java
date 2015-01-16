package controllers;

import be.objectify.deadbolt.java.actions.Dynamic;
import play.*;
import play.mvc.*;
import views.html.*;

import static ximodels.usermgmt.Role.Names.*;

public class Application extends Controller {
	
	@Dynamic(LOGGED_IN)
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

	
    public static Result openIndex() {
        return ok(index.render("Your new application is ready."));
    }

}
