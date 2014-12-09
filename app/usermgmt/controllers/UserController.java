package usermgmt.controllers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.Logger;
import play.libs.Json;
import play.mvc.*;
import usermgmt.formbeans.UserFormBean;
import usermgmt.formbeans.SecuredUserFormBean;
import usermgmt.services.AlreadyExistsException;
import usermgmt.services.NotFoundException;
import usermgmt.services.UserService;
import usermgmt.services.XiUserService;
import usermgmt.utils.Secured;
import be.objectify.deadbolt.java.actions.*;

@Security.Authenticated(Secured.class)
public class UserController extends Controller {
	
	private UserService service = new XiUserService();
	
	@Dynamic(value = "ADMIN")
	public Result getAll() {
		List<? extends UserFormBean> beans = service.getAll();
        return ok(Json.toJson(beans));
    }
	
	@Dynamic(value = "ADMIN")
	public Result get(String userName) {
		UserFormBean bean;
		try {
			bean = service.get(userName);
		} catch (NotFoundException e) {
			return notFound();
		}
        return ok(Json.toJson(bean));
    }
	
	@Dynamic(value = "ADMIN")
	public Result create() {
		UserFormBean bean;
		JsonNode node = request().body().asJson();
		if (node == null){
			return badRequest();
		}
		try {
			bean = service.create(getFormBeanFromRequest(node));
		} catch (AlreadyExistsException e) {
			return internalServerError();
		}
        return ok(Json.toJson(bean));
    }
	
	@Dynamic(value = "ADMIN")
	public Result update(String userName) {
		UserFormBean bean;
		JsonNode node = request().body().asJson();
		if (node == null){
			return badRequest();
		}
		try {
			bean = service.update(userName, getFormBeanFromRequest(node));
		} catch (NotFoundException e) {
			return notFound();
		} catch (AlreadyExistsException e) {
			return internalServerError();
		}
        return ok(Json.toJson(bean));
    }
	
	@Dynamic(value = "ADMIN")
	public Result delete(String userName) {
		try {
			service.delete(userName);
		} catch (NotFoundException e) {
			return notFound();
		}
		return ok();
	}
	
	private SecuredUserFormBean getFormBeanFromRequest(JsonNode node){
		SecuredUserFormBean result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.readValue(node.toString(), SecuredUserFormBean.class);
        } catch (IOException e) {
            Logger.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
	}
    
}
