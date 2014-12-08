package usermgmt.services;

import usermgmt.formbeans.LoginFormBean;
import usermgmt.models.User;

public interface AuthService {

	boolean isCorrectPassword(User user, LoginFormBean loginFormBean);
}
