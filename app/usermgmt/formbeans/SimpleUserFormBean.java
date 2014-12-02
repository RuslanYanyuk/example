package usermgmt.formbeans;

import usermgmt.models.Role;
import usermgmt.models.User;

public class SimpleUserFormBean implements FormBean<User> {

	public String username;
	
    public String fullname;
    
    public String role;

    public SimpleUserFormBean(){}
    
	protected SimpleUserFormBean(String username, String fullname, String role) {
		this.username = username;
		this.fullname = fullname;
		this.role = role;
	}

	@Override
	public void populateModelWithData(User model) {
		model.username = username;
        model.fullname = fullname;
        model.role = Role.valueOf(role);
	}
	
	public static SimpleUserFormBean from(User model){
		return new SimpleUserFormBean(model.username, model.fullname, model.role.toString());
	}
	
}
