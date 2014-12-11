package services.usermgmt;

import formbeans.usermgmt.LoginFormBean;

public interface AuthService {

	boolean isCorrectPassword(LoginFormBean loginFormBean);
}
