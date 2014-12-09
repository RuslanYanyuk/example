package usermgmt.formbeans;

import usermgmt.services.XiAuthService;

public class LoginFormBean {
    public String userName;
    public String password;

    public LoginFormBean(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginFormBean() {}

    public String validate() {
        XiAuthService x = new XiAuthService();
        if (x.isCorrectPassword(this)) {
            return null;
        }

        return "Invalid username or password!";
    }

}
