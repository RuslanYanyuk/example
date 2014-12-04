package usermgmt.services;

import java.util.List;

import org.junit.Test;

import resources.usermgmt.YAML;
import usermgmt.AbstractTest;
import usermgmt.formbeans.SecuredUserFormBean;
import usermgmt.formbeans.UserFormBean;
import usermgmt.models.Role;
import usermgmt.models.User;
import static org.fest.assertions.Assertions.*;

public class TestXiUserService extends AbstractTest {

	private XiUserService service = new XiUserService();
	
	@Test
	public void getAllUsers_ReturnsAllExistingUsers() throws Exception {
		YAML.GENERAL_USERS.load();
		
		List<? extends UserFormBean> beans = service.getAll();
		assertThat(beans.size()).isEqualTo(4);
		checkBean(beans.get(0), "user1Username", "user1Fullname", Role.USER);
		checkBean(beans.get(1), "user2Username", "user2Fullname", Role.USER);
		checkBean(beans.get(2), "user3Username", "user3Fullname", Role.ADMIN);
		checkBean(beans.get(3), "Admin", "admin", Role.ADMIN);
	}
	
	@Test
	public void get_ReturnsFormBeanByUsername(){
		YAML.GENERAL_USERS.load();
		
		UserFormBean bean = service.get("user1Username");
		assertThat(bean).isNotNull();
		checkBean(bean, "user1Username", "user1Fullname", Role.USER);
	}
	
	@Test
	public void create_CreatesNewUser(){
		SecuredUserFormBean bean = createUserFormBean("userName1", "fullName1", Role.ADMIN, "password1");
		service.create(bean);
		List<User> users = User.find.all();
		assertThat(users.size()).isEqualTo(1);
		checkBean(bean, users.get(0));
	}
	
	@Test
	public void update_UpdatesExistedUser(){
		YAML.GENERAL_USERS.load();
		
		SecuredUserFormBean bean = createUserFormBean("userName1", "fullName1", Role.ADMIN, "password1");
		service.update("user1Username", bean);
		List<User> users = User.find.all();
		checkBean(bean, users.get(0));
	}
	
	@Test
	public void delete_RemovesUserByUsername(){
		YAML.GENERAL_USERS.load();
		
		List<User> users = User.find.all();
		assertThat(users.size()).isEqualTo(4);
		User firstUser = User.find.where().eq("userName", "user1Username").findUnique();
		assertThat(firstUser).isNotNull();
		service.delete("user1Username");
		users = User.find.all();
		assertThat(users.size()).isEqualTo(3);
		firstUser = User.find.where().eq("userName", "user1Username").findUnique();
		assertThat(firstUser).isNull();
	}
	
	private SecuredUserFormBean createUserFormBean(String userName, String fullName, Role role, String password){
		return new SecuredUserFormBean(userName, fullName, Role.ADMIN.toString(), password);
	}
	
	private void checkBean(SecuredUserFormBean bean, User user){
		assertThat(bean.userName).isEqualTo(user.userName);
		assertThat(bean.fullName).isEqualTo(user.fullName);
		assertThat(bean.role).isEqualTo(user.role.toString());
		assertThat(bean.password).isEqualTo(user.password);
	}
	
	private void checkBean(UserFormBean bean,
			String userName, String fullName, Role role){
		assertThat(bean.userName).isEqualTo(userName);
		assertThat(bean.fullName).isEqualTo(fullName);
		assertThat(bean.role).isEqualTo(role.toString());
	}
	
}
