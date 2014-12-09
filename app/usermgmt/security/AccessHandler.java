package usermgmt.security;

import play.libs.F.Promise;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.SimpleResult;
import usermgmt.models.Role;
import usermgmt.models.User;
import usermgmt.services.NotFoundException;
import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;

public class AccessHandler extends AbstractDeadboltHandler implements DynamicResourceHandler {

	public DynamicResourceHandler getDynamicResourceHandler(Http.Context context) {
        return this;
    }
	
	@Override
    public boolean isAllowed(String name, String meta, DeadboltHandler deadboltHandler, Http.Context context) {
		boolean result = false;
		if (name.equals(Role.ADMIN.name())){
			String userName = Context.current().session().get("userName");
			try {
				User user = User.findUserByUserName(userName);
				if (user.role == Role.ADMIN){
					result = true;
				}
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}
        return result;
    }

    @Override
    public boolean checkPermission(String s, DeadboltHandler deadboltHandler, Http.Context context) {
        return true;
    }

	@Override
	public Promise<SimpleResult> beforeAuthCheck(Context arg0) {
		// returning null means that everything is OK.  Return a real result if you want a redirect to a login page or
        // somewhere else
		return null;
	}

}
