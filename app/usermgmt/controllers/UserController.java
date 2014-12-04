package usermgmt.controllers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import play.Logger;
import play.libs.Json;
import play.mvc.*;
import usermgmt.formbeans.UserFormBean;
import usermgmt.formbeans.SecuredUserFormBean;
import usermgmt.services.UserService;
import usermgmt.services.XiUserService;

public class UserController extends Controller {
	
	private UserService service = new XiUserService();
	
	public Result getAll() {
		List<? extends UserFormBean> beans = service.getAll();
        return ok(Json.toJson(beans));
    }
	
	public Result get(String userName) {
		UserFormBean bean = service.get(userName);
        return ok(Json.toJson(bean));
    }
	
	public Result create() {
		service.create(getFromBeanFromRequest());
        return ok();
    }
	
	public Result update(String userName) {
		service.update(userName, getFromBeanFromRequest());
        return ok();
    }
	
	public Result delete(String userName) {
		service.delete(userName);
		return ok();
	}
	
	private SecuredUserFormBean getFromBeanFromRequest(){
		SecuredUserFormBean result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.readValue(request().body().asJson().toString(), SecuredUserFormBean.class);
        } catch (IOException e) {
            Logger.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
	}
    
}
