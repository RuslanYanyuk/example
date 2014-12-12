package views.usermgmt.ui.pages;

import java.util.concurrent.TimeUnit;

public interface Page {
    int WAIT_TIME = 2;
    TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    <T extends Page> T load();
    boolean isAt();
}
