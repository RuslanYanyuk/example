package security.usermgmt;

import play.libs.F.Promise;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import models.usermgmt.Role;
import models.usermgmt.User;
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
		if (userName != null){
			User user = User.findUserByUserName(userName);
			/*Must be splitted for 'USER' and 'ADMIN' access. Now we expect that name equals "ADMIN"*/
			if (user != null && user.role == Role.ADMIN){
				return true;
			}
		}
		return false;
    }

    @Override
    public boolean checkPermission(String s, DeadboltHandler deadboltHandler, Http.Context context) {
        return true;
    }

	@Override
	public Promise<Result> beforeAuthCheck(Context arg0) {
		// returning null means that everything is OK.  Return a real result if you want a redirect to a login page or
        // somewhere else
		return null;
	}

}
