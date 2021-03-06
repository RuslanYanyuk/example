package services.usermgmt;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mindrot.jbcrypt.BCrypt;

import commons.AbstractUnitTest;
import usermgmt.YAML;
import ximodels.usermgmt.Role;
import ximodels.usermgmt.User;
import formbeans.usermgmt.SecuredUserFormBean;
import formbeans.usermgmt.UserFormBean;
import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.assertTrue;
import static services.usermgmt.XiUserService.*;
import static usermgmt.Parameters.*;

public class XiUserServiceUnitTest extends AbstractUnitTest {

	private UserService service = new XiUserService();
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void getAllUsers_ReturnsAllExistingUsers() {
		YAML.GENERAL_USERS.load();
		
		List<? extends UserFormBean> beans = service.getAll();
		assertThat(beans.size()).isEqualTo(4);
		checkBean(beans.get(0), "user1Username", "user1Fullname", Role.USER);
		checkBean(beans.get(1), "user2Username", "user2Fullname", Role.USER);
		checkBean(beans.get(2), "user3Username", "user3Fullname", Role.ADMIN);
		checkBean(beans.get(3), "Admin", "admin", Role.ADMIN);
	}
	
	@Test
	public void get_ReturnsFormBeanByUserName() throws NotFoundException {
		YAML.GENERAL_USERS.load();
		
		UserFormBean bean = service.get(FIRST_USER_NAME);
		checkBean(bean, FIRST_USER_NAME, FIRST_USER_FULLNAME, Role.USER);
	}
	
	@Test(expected=NotFoundException.class)
	public void get_ThrowsExceptionUnlessUserNameFound() throws NotFoundException {
		YAML.GENERAL_USERS.load();
		service.get(NOT_EXISTED_USER_NAME);
	}
	
	@Test
	public void create_CreatesNewUser() throws AlreadyExistsException {
		SecuredUserFormBean bean = createUserFormBean(NEW_USER_NAME, FIRST_USER_FULLNAME, Role.ADMIN, FIRST_USER_UPDATED_PASSWORD);
		service.create(bean);
		List<User> users = User.find.all();
		assertThat(users.size()).isEqualTo(1);
		checkBean(bean, users.get(0));
	}

	 @Test
	public void create_ThrowsExceptionIfUserNameIsEmpty() throws AlreadyExistsException {
		YAML.GENERAL_USERS.load();

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(REQUIRED_FIELDS);

		SecuredUserFormBean bean = createUserFormBean(EMPTY_PARAMETER, FIRST_USER_FULLNAME, Role.ADMIN, FIRST_USER_UPDATED_PASSWORD);
		service.create(bean);
	}

	@Test
	public void create_ThrowsExceptionIfPasswordIsEmpty() throws AlreadyExistsException {
		YAML.GENERAL_USERS.load();

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(REQUIRED_FIELDS);

		SecuredUserFormBean bean = createUserFormBean(ADMIN_USER_NAME, FIRST_USER_FULLNAME, Role.ADMIN, EMPTY_PARAMETER);
		service.create(bean);
	}

	@Test(expected=AlreadyExistsException.class)
	public void create_ThrowsExceptionIfUserNameAlreadyExists() throws AlreadyExistsException {
		YAML.GENERAL_USERS.load();
		
		SecuredUserFormBean bean = createUserFormBean(ADMIN_USER_NAME, FIRST_USER_FULLNAME, Role.ADMIN, FIRST_USER_UPDATED_PASSWORD);
		service.create(bean);
	}
	
	@Test
	public void create_DoesNothingIfUserNameAlreadyExists() throws AlreadyExistsException {
		YAML.GENERAL_USERS.load();

        exception.expect(AlreadyExistsException.class);
        exception.expectMessage(String.format(USER_ALREADY_EXIST, ADMIN_USER_NAME));

		SecuredUserFormBean bean = createUserFormBean(ADMIN_USER_NAME, FIRST_USER_FULLNAME, Role.ADMIN, FIRST_USER_UPDATED_PASSWORD);
		service.create(bean);

		assertThat(User.find.all().size()).isEqualTo(4);
	}

	@Test
	public void update_ThrowsExceptionIfUserNameWasUpdated() throws NotFoundException, AlreadyExistsException {
		YAML.GENERAL_USERS.load();

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(NAME_CAN_NOT_BE_CHANGED);

		SecuredUserFormBean bean = createUserFormBean(FIRST_USER_NAME, FIRST_USER_FULLNAME, Role.ADMIN, FIRST_USER_UPDATED_PASSWORD);
		service.update(FIRST_USER_UPDATED_USER_NAME, bean, false);
	}

	@Test
    public void update_ThrowsExceptionIfCurrentUserRoleWasUpdated() throws NotFoundException, AlreadyExistsException {
        YAML.GENERAL_USERS.load();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(CAN_NOT_BE_CHANGED_CURRENT_USER_ROLE);

        SecuredUserFormBean bean = createUserFormBean(ADMIN_USER_NAME, ADMIN_FULL_NAME, Role.USER, ADMIN_PASSWORD);
        service.update(ADMIN_USER_NAME, bean, true);
    }
	
	@Test
	public void update_UpdatesExistedUserWithUpdatingPassword() throws NotFoundException, AlreadyExistsException {
		YAML.GENERAL_USERS.load();
		
		SecuredUserFormBean bean = createUserFormBean(FIRST_USER_NAME, FIRST_USER_FULLNAME, Role.ADMIN, FIRST_USER_UPDATED_PASSWORD);
		service.update(FIRST_USER_NAME, bean, false);
		List<User> users = User.find.all();
		checkBean(bean, users.get(0));
	}

	@Test
	public void update_NotReplacePasswordIfItIsEmpty() throws NotFoundException, AlreadyExistsException {
		YAML.GENERAL_USERS.load();
		String initialPassword = User.find.all().get(0).passwordHash;

		SecuredUserFormBean bean = createUserFormBean(FIRST_USER_NAME, FIRST_USER_FULLNAME, Role.ADMIN, EMPTY_PARAMETER);
		service.update(FIRST_USER_NAME, bean, false);

		User user = User.find.all().get(0);
		checkBean(bean, user.userName, user.fullName, user.role);
		assertTrue(user.passwordHash.equals(initialPassword));
	}


	@Test(expected=NotFoundException.class)
	public void update_ThrowsExceptionUnlessUserNameFound() throws NotFoundException, AlreadyExistsException {
		YAML.GENERAL_USERS.load();
		
		SecuredUserFormBean bean = createUserFormBean(NOT_EXISTED_USER_NAME, FIRST_USER_FULLNAME, Role.ADMIN, FIRST_USER_UPDATED_PASSWORD);
		service.update(NOT_EXISTED_USER_NAME, bean, false);
	}

	@Test
	public void delete_RemovesUserByUsername() throws NotFoundException {
		YAML.GENERAL_USERS.load();
		
		List<User> users = User.find.all();
		assertThat(users.size()).isEqualTo(4);
		User firstUser = User.find.where().eq("userName", FIRST_USER_NAME).findUnique();
		assertThat(firstUser).isNotNull();
		service.delete(FIRST_USER_NAME, false);
		users = User.find.all();
		assertThat(users.size()).isEqualTo(3);
		firstUser = User.find.where().eq("userName", FIRST_USER_NAME).findUnique();
		assertThat(firstUser).isNull();
	}
	
	@Test(expected=NotFoundException.class)
	public void delete_ThrowsExceptionUnlessUserNameFound() throws NotFoundException {
		YAML.GENERAL_USERS.load();
		
		service.delete(NOT_EXISTED_USER_NAME, false);
	}

	@Test
	public void delete_ThrowsExceptionWhenDeletingCurrentUser() throws NotFoundException {
		YAML.GENERAL_USERS.load();

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(CAN_NOT_BE_DELETED_CURRENT_USER);

		service.delete(ADMIN_USER_NAME, true);
	}

	private SecuredUserFormBean createUserFormBean(String userName, String fullName, Role role, String password){
		return new SecuredUserFormBean(userName, fullName, role.toString(), password);
	}
	
	private void checkBean(SecuredUserFormBean bean, User user){
		assertThat(bean.userName).isEqualTo(user.userName);
		assertThat(bean.fullName).isEqualTo(user.fullName);
		assertThat(bean.role).isEqualTo(user.role.toString());
		assertTrue(BCrypt.checkpw(bean.password, user.passwordHash));
	}
	
	private void checkBean(UserFormBean bean, String userName, String fullName, Role role){
		assertThat(bean.userName).isEqualTo(userName);
		assertThat(bean.fullName).isEqualTo(fullName);
		assertThat(bean.role).isEqualTo(role.toString());
	}
	
	private void checkUser(User user, String userName, String fullName, Role role, String password){
		assertThat(user.userName).isEqualTo(userName);
		assertThat(user.fullName).isEqualTo(fullName);
		assertThat(user.role).isEqualTo(role);
		assertTrue(BCrypt.checkpw(password, user.passwordHash));
	}
	
}
