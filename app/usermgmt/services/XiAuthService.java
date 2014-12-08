package usermgmt.services;

import org.mindrot.jbcrypt.BCrypt;
import usermgmt.formbeans.LoginFormBean;
import usermgmt.models.User;

public class XiAuthService implements AuthService {

	@Override
	public boolean isCorrectPassword(User user, LoginFormBean loginFormBean) {
		return user != null && BCrypt.checkpw(loginFormBean.password, user.passwordHash);
	}

}
