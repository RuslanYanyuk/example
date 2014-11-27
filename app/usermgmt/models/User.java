package usermgmt.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.data.validation.*;

@Entity
public class User extends AbstractModel {

	@Column(unique=true)
	@Constraints.Required
	public String username;
	
	@Constraints.Required
	public String password;

	@Constraints.Required
	public String fullname;
	
	@Constraints.Required
	@Enumerated(EnumType.STRING)
	public Role role;
	
	public User(String username, String password, String fullname, Role role) {
		this.username = username;
		this.fullname = fullname;
		this.password = password;
		this.role = role;
	}
	
	public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);
	
}
