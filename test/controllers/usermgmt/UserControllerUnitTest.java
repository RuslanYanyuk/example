package controllers.usermgmt;

import static junit.framework.Assert.assertTrue;
import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.INTERNAL_SERVER_ERROR;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.FORBIDDEN;
import static play.test.Helpers.callAction;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.status;
import static usermgmt.Parameters.*;

import java.util.List;

import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import play.mvc.Result;
import usermgmt.YAML;
import ximodels.usermgmt.Role;
import ximodels.usermgmt.User;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import commons.AbstractUnitTest;
import formbeans.usermgmt.SecuredUserFormBean;

public class UserControllerUnitTest extends AbstractUnitTest {

	@Test
	public void getAdministration_ReturnsAdministrationPage(){
		YAML.GENERAL_USERS.load();

		Result result = callAction(controllers.usermgmt.routes.ref.UserController.getAdministration(),
				fakeRequest().withSession("userName", ADMIN_USER_NAME));

		checkResponse(result, OK);
		assertThat(contentType(result)).isEqualTo("text/html");
		assertTrue(contentAsString(result).contains("users-container"));
		assertThat(charset(result)).isEqualTo("utf-8");
	}

	@Test
	public void getAdministration_ReturnsForbiddenIfNoPermissions(){
		YAML.GENERAL_USERS.load();

		Result result = callAction(controllers.usermgmt.routes.ref.UserController.getAdministration(),
				fakeRequest().withSession("userName", FIRST_USER_NAME));
		checkResponse(result, FORBIDDEN);
	}

	@Test
	public void getAll_ReturnsAllExistedUsers(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.getAll(),
				fakeRequest().withSession("userName", ADMIN_USER_NAME));
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
	public void getAll_ReturnsForbiddenIfNoPermissions(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.getAll(),
				fakeRequest().withSession("userName", FIRST_USER_NAME));
		checkResponse(result, FORBIDDEN);
	}
	
	@Test
	public void get_ReturnsUserByUserNameIfExists(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.get(FIRST_USER_NAME),
				fakeRequest().withSession("userName", ADMIN_USER_NAME));
		checkJsonResponse(result, OK);
		String expectedResponse = "{\"userName\":\"user1Username\",\"fullName\":\"user1Fullname\",\"role\":\"USER\"}";
		assertThat(contentAsString(result)).isEqualTo(expectedResponse);
	}
	
	@Test
	public void get_ReturnsNotFoundUnlessUserNameExists(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.get(NOT_EXISTED_USER_NAME),
				fakeRequest().withSession("userName", ADMIN_USER_NAME));
		checkResponse(result, NOT_FOUND);
	}
	
	@Test
	public void get_ReturnsForbiddenIfNoPermissions(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.get(""),
				fakeRequest().withSession("userName", FIRST_USER_NAME));
		checkResponse(result, FORBIDDEN);
	}

	@Test
	public void create_CreatesNewUser(){
		YAML.GENERAL_USERS.load();

		JsonNode node = createUserJsonNode(NEW_USER_NAME, FIRST_USER_FULLNAME, Role.ADMIN, FIRST_USER_PASSWORD);
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.create(),
                fakeRequest().withJsonBody(node).withSession("userName", ADMIN_USER_NAME));
		checkResponse(result, OK);
		List<User> users = User.find.all();
		assertThat(users.size()).isEqualTo(5);
		User user = users.get(4);
		checkUser(user, NEW_USER_NAME, FIRST_USER_FULLNAME, Role.ADMIN, FIRST_USER_PASSWORD);
	}

	@Test
	public void create_ReturnsBadRequestIfRequestIsEmpty(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.create(),
				fakeRequest().withSession("userName", ADMIN_USER_NAME));
		checkResponse(result, BAD_REQUEST);
	}
	
	@Test
	public void create_ReturnsBadRequestIfUserNameAlreadyExists(){
		YAML.GENERAL_USERS.load();
		
		JsonNode node = createUserJsonNode(ADMIN_USER_NAME, FIRST_USER_FULLNAME, Role.ADMIN, FIRST_USER_PASSWORD);
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.create(),
				fakeRequest().withJsonBody(node).withSession("userName", ADMIN_USER_NAME));
		checkResponse(result, BAD_REQUEST);
		List<User> users = User.find.all();
		assertThat(users.size()).isEqualTo(4);
	}
	
	@Test
	public void create_ReturnsForbiddenIfNoPermissions(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.create(),
                fakeRequest().withSession("userName", FIRST_USER_NAME));
		checkResponse(result, FORBIDDEN);
	}

	@Test
	public void update_UpdatesExistedUserWithoutUpdatingUserName(){
		YAML.GENERAL_USERS.load();
		
		JsonNode node = createUserJsonNode(FIRST_USER_NAME, "fullName1", Role.ADMIN, "password1");
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.update(FIRST_USER_NAME),
                fakeRequest().withJsonBody(node).withSession("userName", ADMIN_USER_NAME));
		checkResponse(result, OK);
		List<User> users = User.find.all();
		assertThat(users.size()).isEqualTo(4);
		User user = users.get(0);
		checkUser(user, FIRST_USER_NAME, "fullName1", Role.ADMIN, "password1");
	}
	
	@Test
	public void update_ReturnsNotFoundUnlessUserNameExists(){
		YAML.GENERAL_USERS.load();
		JsonNode node = createUserJsonNode(NOT_EXISTED_USER_NAME, FIRST_USER_FULLNAME, Role.ADMIN, FIRST_USER_UPDATED_PASSWORD);
		
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.update(NOT_EXISTED_USER_NAME),
				fakeRequest().withJsonBody(node).withSession("userName", ADMIN_USER_NAME));
		checkResponse(result, NOT_FOUND);
	}
	
	@Test
	public void update_ReturnsBadRequestIfRequestIsEmpty(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.update(FIRST_USER_NAME), fakeRequest().withSession("userName", ADMIN_USER_NAME));
		checkResponse(result, BAD_REQUEST);
	}
	
	@Test
	public void update_ReturnsBadRequestIfUserNameWasChanged(){
		YAML.GENERAL_USERS.load();
		
		JsonNode node = createUserJsonNode(ADMIN_USER_NAME, FIRST_USER_FULLNAME, Role.ADMIN, FIRST_USER_UPDATED_PASSWORD);
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.update(FIRST_USER_NAME),
                fakeRequest().withJsonBody(node).withSession("userName", ADMIN_USER_NAME));
		checkResponse(result, BAD_REQUEST);
		List<User> users = User.find.all();
		assertThat(users.size()).isEqualTo(4);
		User user = users.get(0);
		checkUser(user, FIRST_USER_NAME, FIRST_USER_FULLNAME, Role.USER, FIRST_USER_PASSWORD);
	}

    @Test
    public void update_ReturnsBadRequestIfCurrentUserRoleWasChanged(){
        YAML.GENERAL_USERS.load();

        JsonNode node = createUserJsonNode(ADMIN_USER_NAME, ADMIN_FULL_NAME, Role.USER, ADMIN_PASSWORD);
        Result result = callAction(controllers.usermgmt.routes.ref.UserController.update(ADMIN_USER_NAME),
                fakeRequest().withJsonBody(node).withSession("userName", ADMIN_USER_NAME));
        checkResponse(result, BAD_REQUEST);
        User user = User.findUserByUserName(ADMIN_USER_NAME);

        assertTrue(user.role == Role.ADMIN);
    }
	
	@Test
	public void update_ReturnsForbiddenIfNoPermissions(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.update(""),
                fakeRequest().withSession("userName", FIRST_USER_NAME));
		checkResponse(result, FORBIDDEN);
	}
	
	@Test
	public void delete_RemovesExistedUser(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.delete(FIRST_USER_NAME),
				fakeRequest().withSession("userName", ADMIN_USER_NAME));
		checkResponse(result, OK);
		List<User> users = User.find.all();
		assertThat(users.size()).isEqualTo(3);
		User user = User.find.where().eq("userName", FIRST_USER_NAME).findUnique();
		assertThat(user).isNull();
	}
	
	@Test
	public void delete_ReturnsNotFoundUnlessUserNameExists(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.delete(NOT_EXISTED_USER_NAME),
				fakeRequest().withSession("userName", ADMIN_USER_NAME));
		checkResponse(result, NOT_FOUND);
	}
	
	@Test
	public void delete_ReturnsForbiddenIfNoPermissions(){
		YAML.GENERAL_USERS.load();
		
		Result result = callAction(controllers.usermgmt.routes.ref.UserController.delete(""),
				fakeRequest().withSession("userName", FIRST_USER_NAME));
		checkResponse(result, FORBIDDEN);
	}

	@Test
	public void delete_ReturnBadRequestWhenDeletingCurrentUser(){
		YAML.GENERAL_USERS.load();

		Result result = callAction(controllers.usermgmt.routes.ref.UserController.delete(ADMIN_USER_NAME),
				fakeRequest().withSession("userName", ADMIN_USER_NAME));
		checkResponse(result, BAD_REQUEST);
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
		assertTrue(BCrypt.checkpw(password, user.passwordHash));
	}
	
	private JsonNode createUserJsonNode(String userName, String fullName, Role role, String password){
		ObjectMapper mapper = new ObjectMapper();
		SecuredUserFormBean bean = new SecuredUserFormBean(userName, fullName, role.toString(), password);
		return mapper.convertValue(bean, JsonNode.class); 
	}
	
}
