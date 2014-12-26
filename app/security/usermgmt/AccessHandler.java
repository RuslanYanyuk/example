package security.usermgmt;

import play.libs.F;
import play.libs.F.Promise;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import models.usermgmt.Role;
import models.usermgmt.User;
import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import static utils.usermgmt.Constants.*;

public class AccessHandler extends AbstractDeadboltHandler implements DynamicResourceHandler {

	public DynamicResourceHandler getDynamicResourceHandler(Http.Context context) {
        return this;
    }
	
	/**
	 * If your method has annotated like @Dynamic("Logged in"),
	 * then to method have access all logined users
	 * 
	 * If your method has annotated like @Dynamic("MY_ROLE_NAME"), 
	 * then to method have access users with user.role == Role.valueOf("MY_ROLE_NAME")
	 * 
	 * If your method has annotated like @Dynamic("MY_ROLE_NAME1, MY_ROLE_NAME2, ..."), 
	 * then to method have users with user.role == Role.valueOf("MY_ROLE_NAME1") 
	 * OR with user.role == Role.valueOf("MY_ROLE_NAME2") etc
	 */
	@Override
    public boolean isAllowed(String name, String meta, DeadboltHandler deadboltHandler, Http.Context context) {
		User user = User.findUserByUserName(getUserName(context));
		if (name.toUpperCase().equals("LOGGED IN")){
			return true;
		} else {
			for (String roleName : name.split(",")){
				if (user.role == Role.valueOf(roleName.trim())){
					return true;
				}
			}
		}
		return false;
    }

    @Override
    public boolean checkPermission(String s, DeadboltHandler deadboltHandler, Http.Context context) {
        return true;
    }

	@Override
	public Promise<Result> beforeAuthCheck(final Context context) {
		String userName = context.session().get("userName");
		if (userName == null){
			return onUnauthorized(context);
		}
		User user = User.findUserByUserName(userName);
		if (user == null){
			context.session().clear();
			return onUnauthorized(context);
		}
		return null;
	}
	
	private String getUserName(Context context){
		return context.session().get("userName");
	}
	
	private Promise<Result> onUnauthorized(final Context context){
		return F.Promise.promise(new F.Function0<Result>()
        {
            @Override
            public Result apply() throws Throwable {
            	String redirectUrl = context.request().uri();
            	if (!redirectUrl.equals(LOGOUT_PAGE)){
            		context.session().put("redirect", redirectUrl);
            	}
                return redirect(controllers.usermgmt.routes.AuthController.loginForm());
            }
        });
	}
}
