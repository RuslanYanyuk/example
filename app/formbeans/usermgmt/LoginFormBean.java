package formbeans.usermgmt;

import services.usermgmt.AuthService;
import services.usermgmt.XiAuthService;

public class LoginFormBean {
    public String userName;
    public String password;

    private AuthService authService = new XiAuthService();

    public LoginFormBean(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginFormBean() {}

    public String validate() {
        return authService.isCorrectPassword(this) ? null : "Invalid username or password!";
    }

}
