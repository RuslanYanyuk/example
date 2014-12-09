package usermgmt.services;

import usermgmt.formbeans.LoginFormBean;

public interface AuthService {

	boolean isCorrectPassword(LoginFormBean loginFormBean);
}
