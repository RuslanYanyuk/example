package usermgmt.services;

import org.mindrot.jbcrypt.BCrypt;
import usermgmt.formbeans.LoginFormBean;
import usermgmt.models.User;

public class XiAuthService implements AuthService {

	@Override
	public boolean isCorrectPassword(LoginFormBean loginFormBean) {
		User user = User.find.where().eq("userName", loginFormBean.userName).findUnique();
		return user != null && BCrypt.checkpw(loginFormBean.password, user.passwordHash);
	}

}