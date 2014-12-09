package usermgmt.security;

import play.libs.F.Promise;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.SimpleResult;
import usermgmt.models.Role;
import usermgmt.models.User;
import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;

public class AccessHandler extends AbstractDeadboltHandler implements DynamicResourceHandler {

	public DynamicResourceHandler getDynamicResourceHandler(Http.Context context) {
        return this;
    }
	
	@Override
    public boolean isAllowed(String name, String meta, DeadboltHandler deadboltHandler, Http.Context context) {
		String userName = Context.current().session().get("userName");
		if (userName == null){
			return true;
		} else {
			//if (name.equals(Role.ADMIN.name())){
				User user = User.findUserByUserName(userName);
				if (user != null && user.role == Role.ADMIN){
					return true;
				}
			//}
			return false;
		}
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
