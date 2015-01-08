package services.usermgmt;

import org.mindrot.jbcrypt.BCrypt;

import ximodels.usermgmt.User;
import formbeans.usermgmt.LoginFormBean;

public class XiAuthService implements AuthService {

	@Override
	public boolean isCorrectPassword(LoginFormBean loginFormBean) {
		User user = User.find.where().eq("userName", loginFormBean.userName).findUnique();
		return user != null && BCrypt.checkpw(loginFormBean.password, user.passwordHash);
	}

}