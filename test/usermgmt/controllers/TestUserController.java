package usermgmt.controllers;

import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.mvc.Result;
import static resources.usermgmt.Parameters.*;
import resources.usermgmt.YAML;
import usermgmt.AbstractTest;
import usermgmt.formbeans.SecuredUserFormBean;
import usermgmt.models.Role;
import usermgmt.models.User;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class TestUserController extends AbstractTest {

	@Test
	public void getAll_ReturnsAllExistedUsers(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(usermgmt.controllers.routes.ref.UserController.getAll()/*, requestWithAuthentication(fakeRequest(), "admin", "pass")*/);
		checkJsonResponse(result, OK);
		String expectedResponse = "["
				+ "{\"userName\":\"user1Username\",\"fullName\":\"user1Fullname\",\"role\":\"USER\"},"
				+ "{\"userName\":\"user2Username\",\"fullName\":\"user2Fullname\",\"role\":\"USER\"},"
				+ "{\"userName\":\"user3Username\",\"fullName\":\"user3Fullname\",\"role\":\"ADMIN\"},"
				+ "{\"userName\":\"Admin\",\"fullName\":\"admin\",\"role\":\"ADMIN\"}"
				+ "]";
		assertThat(contentAsString(result)).isEqualTo(expectedResponse);
	}
	
	@Test
	public void get_ReturnsUserByUserNameIfExists(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(usermgmt.controllers.routes.ref.UserController.get(FIRST_USER_NAME)/*, requestWithAuthentication(fakeRequest(), "admin", "pass")*/);
		checkJsonResponse(result, OK);
		String expectedResponse = "{\"userName\":\"user1Username\",\"fullName\":\"user1Fullname\",\"role\":\"USER\"}";
		assertThat(contentAsString(result)).isEqualTo(expectedResponse);
	}
	
	@Test
	public void get_ReturnsNotFoundUnlessUserNameExists(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(usermgmt.controllers.routes.ref.UserController.get(NOT_EXISTED_USER_NAME)/*, requestWithAuthentication(fakeRequest(), "admin", "pass")*/);
		checkResponse(result, NOT_FOUND);
	}
	
	@Test
	public void create_CreatesNewUser(){
		YAML.GENERAL_USERS.load();
		
		JsonNode node = createUserJsonNode("userName1", "fullName1", Role.ADMIN, "password1");
		Result result = callAction(usermgmt.controllers.routes.ref.UserController.create(),
                fakeRequest().withJsonBody(node));
		checkResponse(result, OK);
		List<User> users = User.find.all();
		assertThat(users.size()).isEqualTo(5);
		User user = users.get(4);
		checkUser(user, "userName1", "fullName1", Role.ADMIN, "password1");
	}
	
	@Test
	public void create_ReturnsBadRequestIfRequestIsEmpty(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(usermgmt.controllers.routes.ref.UserController.create());
		checkResponse(result, BAD_REQUEST);
	}
	
	@Test
	public void create_ReturnsBadRequestIfUserNameAlreadyExists(){
		YAML.GENERAL_USERS.load();
		
		JsonNode node = createUserJsonNode("Admin", "fullName1", Role.ADMIN, "password1");
		Result result = callAction(usermgmt.controllers.routes.ref.UserController.create(),
                fakeRequest().withJsonBody(node));
		checkResponse(result, BAD_REQUEST);
		List<User> users = User.find.all();
		assertThat(users.size()).isEqualTo(4);
	}
	
	@Test
	public void update_UpdatesExistedUser(){
		YAML.GENERAL_USERS.load();
		
		JsonNode node = createUserJsonNode("userName1", "fullName1", Role.ADMIN, "password1");
		Result result = callAction(usermgmt.controllers.routes.ref.UserController.update(FIRST_USER_NAME),
                fakeRequest().withJsonBody(node));
		checkResponse(result, OK);
		List<User> users = User.find.all();
		assertThat(users.size()).isEqualTo(4);
		User user = users.get(0);
		checkUser(user, "userName1", "fullName1", Role.ADMIN, "password1");
	}
	
	@Test
	public void update_ReturnsNotFoundUnlessUserNameExists(){
		YAML.GENERAL_USERS.load();
		JsonNode node = createUserJsonNode("usermame1", "fullName1", Role.ADMIN, "password1");
		
		Result result = callAction(usermgmt.controllers.routes.ref.UserController.update(NOT_EXISTED_USER_NAME),
				fakeRequest().withJsonBody(node));
		checkResponse(result, NOT_FOUND);
	}
	
	@Test
	public void update_ReturnsBadRequestIfRequestIsEmpty(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(usermgmt.controllers.routes.ref.UserController.update(FIRST_USER_NAME));
		checkResponse(result, BAD_REQUEST);
	}
	
	@Test
	public void update_ReturnsBadRequestIfUserNameAlreadyExists(){
		YAML.GENERAL_USERS.load();
		
		JsonNode node = createUserJsonNode("Admin", "fullName1", Role.ADMIN, "password1");
		Result result = callAction(usermgmt.controllers.routes.ref.UserController.update(FIRST_USER_NAME),
                fakeRequest().withJsonBody(node));
		checkResponse(result, BAD_REQUEST);
		List<User> users = User.find.all();
		assertThat(users.size()).isEqualTo(4);
		User user = users.get(0);
		checkUser(user, "user1Username", "user1Fullname", Role.USER, "$2a$10$jyQzbaf0L46TtxQl/j8RvOZOLMm//qStOPjP1.ac6Oy8Del1I.B66");
	}
	
	@Test
	public void delete_RemovesExistedUser(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(usermgmt.controllers.routes.ref.UserController.delete(FIRST_USER_NAME));
		checkResponse(result, OK);
		List<User> users = User.find.all();
		assertThat(users.size()).isEqualTo(3);
		User user = User.find.where().eq("userName", FIRST_USER_NAME).findUnique();
		assertThat(user).isNull();
	}
	
	@Test
	public void delete_ReturnsNotFoundUnlessUserNameExists(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(usermgmt.controllers.routes.ref.UserController.delete(NOT_EXISTED_USER_NAME)/*, requestWithAuthentication(fakeRequest(), "admin", "pass")*/);
		checkResponse(result, NOT_FOUND);
	}
	
	private void checkResponse(Result result, int status){
		assertThat(status(result)).isEqualTo(status);
	}
	
	private void checkJsonResponse(Result result, int status){
		checkResponse(result, status);
		assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
	}
	
	private void checkUser(User user, String userName, String fullName, Role role, String password){
		assertThat(user.userName).isEqualTo(userName);
		assertThat(user.fullName).isEqualTo(fullName);
		assertThat(user.role).isEqualTo(role);
		assertThat(user.password).isEqualTo(password);
	}
	
	private JsonNode createUserJsonNode(String userName, String fullName, Role role, String password){
		ObjectMapper mapper = new ObjectMapper();
		SecuredUserFormBean bean = new SecuredUserFormBean(userName, fullName, role.toString(), password);
		return mapper.convertValue(bean, JsonNode.class); 
	}
	
}
