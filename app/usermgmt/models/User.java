package usermgmt.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.data.validation.*;
import usermgmt.utils.AdditionalConfiguration;

@Entity
public class User extends AbstractModel {

	@Column(unique=true)
	@Constraints.Required
	public String userName;
	
	@Constraints.Required
	public String password;

	@Constraints.Required
	public String fullName;
	
	@Constraints.Required
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
	
}
