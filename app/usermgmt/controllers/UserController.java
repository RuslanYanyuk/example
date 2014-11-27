package usermgmt.controllers;

import com.google.inject.Inject;

import play.mvc.*;
import usermgmt.services.UserService;
import views.html.*;

public class UserController extends Controller {
	
	@Inject
	private UserService userService;
	
	public Result getAll() {
        return ok();
    }
	
	public Result get(String username) {
        return ok();
    }
	
	public Result create() {
        return ok();
    }
	
	public Result update(String username) {
        return ok();
    }
	
	public Result delete(String username) {
		return ok();
	}
	
    
}
