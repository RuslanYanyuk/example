package usermgmt.services;

import usermgmt.formbeans.LoginFormBean;
import usermgmt.models.User;

public class XiAuthService implements AuthService {

	@Override
	public boolean isCorrectPassword(User user, LoginFormBean loginFormBean) {
		return user != null && user.password.equals(loginFormBean.password);
	}

}
