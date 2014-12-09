package usermgmt.ui.pages;

import java.util.concurrent.TimeUnit;

public interface FluentTestConstants {
    int WAIT_TIME = 2;
    TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    String LOGIN_URL = "http://localhost:3333/login";
    String INDEX_URL = "http://localhost:3333/";
    String LOGOUT_URL = "http://localhost:3333/logout";
    String GET_ALL_USERS_URL = "http://localhost:3333/users";

}
