package controllers.usermgmt;

import be.objectify.deadbolt.java.actions.Dynamic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import formbeans.usermgmt.SecuredUserFormBean;
import formbeans.usermgmt.UserFormBean;
import play.Logger;
import play.i18n.Messages;
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

import static ximodels.usermgmt.Role.Names.ADMIN;

public class UserController extends Controller {

	private static final String SUCCESS_MESSAGE = "usermgmt.users.success";
	private static final String BAD_REQUEST_MESSAGE = "usermgmt.users.badRequest";
	private static final String NOT_FOUND_MESSAGE = "usermgmt.users.notFound";
	private static final String ALREADY_EXIST_MESSAGE = "usermgmt.users.alreadyExist";
	
	private static UserService service = new XiUserService();

	@Dynamic(ADMIN)
	public static Result getAdministration() {
		Role[] roles = Role.values();
		return ok(views.html.usermgmt.users.render(roles));
	}

	@Dynamic(ADMIN)
	public static Result getAll() {
		List<? extends UserFormBean> beans = service.getAll();
        return ok(Json.toJson(beans));
    }
	
	@Dynamic(ADMIN)
	public static Result get(String userName) {
		UserFormBean bean;
		try {
			bean = service.get(userName);
		} catch (NotFoundException e) {
			return notFound(Messages.get(NOT_FOUND_MESSAGE));
		}
        return ok(Json.toJson(bean));
    }

	@Dynamic(ADMIN)
	public static Result create() {
		UserFormBean bean;
		JsonNode node = request().body().asJson();
		if (node == null){
			return badRequest(Messages.get(BAD_REQUEST_MESSAGE));
		}
		try {
			bean = service.create(getFormBeanFromRequest(node));
		} catch (AlreadyExistsException e) {
			return badRequest(Messages.get(ALREADY_EXIST_MESSAGE));
		}
        return ok(createSuccessJson(bean));
    }
	
	@Dynamic(ADMIN)
	public static Result update(String userName) {
		UserFormBean bean;
		JsonNode node = request().body().asJson();
		if (node == null){
			return badRequest(Messages.get(BAD_REQUEST_MESSAGE));
		}
		try {
			bean = service.update(userName, getFormBeanFromRequest(node), isCurrentUser(userName));
		} catch (NotFoundException e) {
			return notFound(Messages.get(NOT_FOUND_MESSAGE));
		} catch (IllegalArgumentException e) {
			return badRequest(Messages.get(BAD_REQUEST_MESSAGE));
		} catch (AlreadyExistsException e) {
			return badRequest(Messages.get(ALREADY_EXIST_MESSAGE));
		}
		return ok(createSuccessJson(bean));
    }

	@Dynamic(ADMIN)
	public static Result delete(String userName) {
		try {
			service.delete(userName, isCurrentUser(userName));
		} catch (NotFoundException e) {
			return notFound(Messages.get(NOT_FOUND_MESSAGE));
		}catch (IllegalArgumentException e) {
			return badRequest(Messages.get(BAD_REQUEST_MESSAGE));
		}
		return ok(Messages.get(SUCCESS_MESSAGE));
	}

	private static ObjectNode createSuccessJson(UserFormBean bean) {
		ObjectNode jsonNodes = Json.newObject();
		jsonNodes.put("user", Json.toJson(bean));
		jsonNodes.put("message", Messages.get(SUCCESS_MESSAGE));
		return jsonNodes;
	}
	
	private static SecuredUserFormBean getFormBeanFromRequest(JsonNode node){
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

    private static boolean isCurrentUser(String name) {
        return session().get("userName").equals(name);
    }
}
