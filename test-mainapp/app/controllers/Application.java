package controllers;

import be.objectify.deadbolt.java.actions.Dynamic;
import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	
	@Dynamic("Logged in")
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

	
    public static Result openIndex() {
        return ok(index.render("Your new application is ready."));
    }

}
