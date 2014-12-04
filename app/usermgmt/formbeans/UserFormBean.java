package usermgmt.formbeans;

import usermgmt.models.Role;
import usermgmt.models.User;

public class UserFormBean implements FormBean<User> {

	public String userName;
	
    public String fullName;
    
    public String role;

    public UserFormBean(){}
    
	protected UserFormBean(String userName, String fullName, String role) {
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
