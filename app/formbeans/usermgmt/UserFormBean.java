package formbeans.usermgmt;

import models.usermgmt.Role;
import models.usermgmt.User;

public class UserFormBean implements FormBean<User> {

	public String userName;
	
    public String fullName;
    
    public String role;

    public UserFormBean(){}
    
	public UserFormBean(String userName, String fullName, String role) {
		this.userName = userName;
		this.fullName = fullName;
		this.role = role;
	}

	@Override
	public void populateModelWithData(User model) {
		model.userName = userName;
        model.fullName = fullName;
        model.role = Role.valueOf(role);
	}
	
	public static UserFormBean from(User model){
		return new UserFormBean(model.userName, model.fullName, model.role.toString());
	}
	
}
