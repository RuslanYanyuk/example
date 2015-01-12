package controllers.usermgmt;

import be.objectify.deadbolt.java.actions.Dynamic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import formbeans.usermgmt.SecuredUserFormBean;
import formbeans.usermgmt.UserFormBean;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.usermgmt.AlreadyExistsException;
import services.usermgmt.NotFoundException;
import services.usermgmt.UserService;
import services.usermgmt.XiUserService;
import ximodels.usermgmt.Role;

import java.io.IOException;
import java.util.List;

public class UserController extends Controller {
	
	private UserService service = new XiUserService();

	@Dynamic("ADMIN")
	public Result getAdministration() {
		Role[] roles = Role.values();
		return ok(views.html.usermgmt.users.render(roles));
	}

	@Dynamic("ADMIN")
	public Result getAll() {
		List<? extends UserFormBean> beans = service.getAll();
        return ok(Json.toJson(beans));
    }
	
	@Dynamic("ADMIN")
	public Result get(String userName) {
		UserFormBean bean;
		try {
			bean = service.get(userName);
		} catch (NotFoundException e) {
			return notFound();
		}
        return ok(Json.toJson(bean));
    }

	@Dynamic("ADMIN")
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
	
	@Dynamic("ADMIN")
	public Result update(String userName) {
		UserFormBean bean;
		JsonNode node = request().body().asJson();
		if (node == null){
			return badRequest();
		}
		try {
			bean = service.update(userName, getFormBeanFromRequest(node), isCurrentUser(userName));
		} catch (NotFoundException e) {
			return notFound();
		} catch (AlreadyExistsException | IllegalArgumentException e) {
			return internalServerError();
		}
        return ok(Json.toJson(bean));
    }
	
	@Dynamic("ADMIN")
	public Result delete(String userName) {
		try {
			service.delete(userName, isCurrentUser(userName));
		} catch (NotFoundException e) {
			return notFound();
		}catch (IllegalArgumentException e) {
			return badRequest();
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

    private boolean isCurrentUser(String name) {
        return session().get("userName").equals(name);
    }
}
