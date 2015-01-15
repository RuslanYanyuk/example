package utils.usermgmt;

public enum ApplicationConf {

    LOGIN_HTML("usermgmt.login.html"),
    LOGOUT_HTML("usermgmt.logout.html"),
    LOGO_TEXT("usermgmt.logo.text");

    private String propertyName;

    private ApplicationConf(String propertyName) {
        this.propertyName = propertyName;
    }

    public String value(){
        return play.Play.application().configuration().getString(propertyName);
    }

}
