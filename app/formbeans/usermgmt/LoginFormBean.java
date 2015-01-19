package formbeans.usermgmt;

import play.i18n.Messages;
import services.usermgmt.AuthService;
import services.usermgmt.XiAuthService;

public class LoginFormBean {
    private static final String LOGIN_ERROR_MESSAGE = "usermgmt.login.error";
    public String userName;
    public String password;

    private AuthService authService = new XiAuthService();

    public LoginFormBean(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginFormBean() {}

    public String validate() {
        return authService.isCorrectPassword(this) ? null :  Messages.get(LOGIN_ERROR_MESSAGE);
    }

}
