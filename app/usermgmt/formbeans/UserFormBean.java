package usermgmt.formbeans;

import usermgmt.models.User;

public class UserFormBean extends SimpleUserFormBean {

	public String password;
	
	public UserFormBean(String username, String fullname, String role, String password) {
		super(username, fullname, role);
		this.password = password;
	}

	@Override
	public void populateModelWithData(User model) {
		super.populateModelWithData(model);
		model.password = password;
	}
	
}
