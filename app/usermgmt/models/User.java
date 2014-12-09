package usermgmt.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import usermgmt.utils.AdditionalConfiguration;

@Entity
public class User extends AbstractModel {

	@Column(unique=true)
	public String userName;
	
	public String password;

	public String fullName;
	
	@Enumerated(EnumType.STRING)
	public Role role;
	
	public User() {	}
	
	public User(String userName, String password, String fullName, Role role) {
		this.userName = userName;
		this.fullName = fullName;
		this.password = password;
		this.role = role;
	}
	
	public static Finder<Long, User> find = new Finder<Long, User>(AdditionalConfiguration.EBEAN_SERVER.getValue(), Long.class, User.class);
	
	public static User findUserByUserName(String userName) {
		User user = User.find.where().eq("userName", userName).findUnique();// throws exception if found more than 1
		return user;
	}
	
}
