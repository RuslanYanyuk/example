package usermgmt.formbeans;

import usermgmt.models.User;

public class SecuredUserFormBean extends UserFormBean {

	public String password;
	
	public SecuredUserFormBean(){}
	
	public SecuredUserFormBean(String userName, String fullName, String role, String password) {
		super(userName, fullName, role);
		this.password = password;
	}

	@Override
	public void populateModelWithData(User model) {
		super.populateModelWithData(model);
		model.password = password;
	}
	
}
