package usermgmt.services;

import java.util.List;

import org.junit.Test;

import resources.usermgmt.YAML;
import usermgmt.AbstractTest;
import usermgmt.formbeans.SimpleUserFormBean;
import usermgmt.formbeans.UserFormBean;
import usermgmt.models.Role;
import usermgmt.models.User;
import static org.fest.assertions.Assertions.*;

public class TestXiUserService extends AbstractTest {

	private XiUserService service = new XiUserService();
	
	@Test
	public void getAllUsers_ReturnsAllExistingUsers() throws Exception {
		YAML.GENERAL_USERS.load();
		
		List<? extends SimpleUserFormBean> beans = service.getAll();
		
		assertThat(beans.size()).isEqualTo(4);
		checkFirstGeneralUser(beans.get(0));
		checkSecondGeneralUser(beans.get(1));
		checkThirdGeneralUser(beans.get(2));
		checkFourthGeneralUser(beans.get(3));
	}
	
	@Test
	public void get_ReturnsFormBeanByUsername(){
		YAML.GENERAL_USERS.load();
		SimpleUserFormBean bean = service.get("user1Username");
		assertThat(bean).isNotNull();
		checkFirstGeneralUser(bean);
	}
	
	@Test
	public void create_CreatesNewUser(){
		UserFormBean bean = createUserFormBean();
		service.create(bean);
		
		List<User> users = User.find.all();
		assertThat(users.size()).isEqualTo(1);
		checkUserBasedOnUserFormBean(users.get(0));
	}
	
	@Test
	public void update_UpdatesExistedUser(){
		YAML.GENERAL_USERS.load();
		UserFormBean bean = createUserFormBean();
		service.update("user1Username", bean);
		List<User> users = User.find.all();
		checkUserBasedOnUserFormBean(users.get(0));
	}
	
	@Test
	public void delete_RemovesUserByUsername(){
		YAML.GENERAL_USERS.load();
		List<User> users = User.find.all();
		assertThat(users.size()).isEqualTo(4);
		User firstUser = User.find.where().eq("username", "user1Username").findUnique();
		assertThat(firstUser).isNotNull();
		service.delete("user1Username");
		assertThat(users.size()).isEqualTo(3);
		firstUser = User.find.where().eq("username", "user1Username").findUnique();
		assertThat(firstUser).isNull();
	}
	
	private UserFormBean createUserFormBean(){
		return new UserFormBean("username", "fullname", Role.ADMIN.toString(), "password");
	}
	
	private void checkUserBasedOnUserFormBean(User user){
		assertThat(user.username).isEqualTo("username");
		assertThat(user.fullname).isEqualTo("fullname");
		assertThat(user.role).isEqualTo(Role.ADMIN);
		assertThat(user.password).isEqualTo("password");
	}
	
	private void checkFirstGeneralUser(SimpleUserFormBean formbean){
		assertThat(formbean.username).isEqualTo("user1Username");
		assertThat(formbean.fullname).isEqualTo("user1Fullname");
		assertThat(formbean.role).isEqualTo(Role.USER.toString());
	}
	
	private void checkSecondGeneralUser(SimpleUserFormBean formbean){
		assertThat(formbean.username).isEqualTo("user2Username");
		assertThat(formbean.fullname).isEqualTo("user2Fullname");
		assertThat(formbean.role).isEqualTo(Role.USER.toString());
	}
	
	private void checkThirdGeneralUser(SimpleUserFormBean formbean){
		assertThat(formbean.username).isEqualTo("user3Username");
		assertThat(formbean.fullname).isEqualTo("user3Fullname");
		assertThat(formbean.role).isEqualTo(Role.ADMIN.toString());
	}
	
	private void checkFourthGeneralUser(SimpleUserFormBean formbean){
		assertThat(formbean.username).isEqualTo("Admin");
		assertThat(formbean.fullname).isEqualTo("admin");
		assertThat(formbean.role).isEqualTo(Role.ADMIN.toString());
	}
	
}
