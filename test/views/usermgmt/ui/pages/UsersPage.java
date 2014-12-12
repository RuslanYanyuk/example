package views.usermgmt.ui.pages;

import org.fluentlenium.core.Fluent;

public class UsersPage implements Page{

    private Fluent browser;

    public static final String URL = "http://localhost:3333/users";

    public UsersPage(Fluent browser) {
        this.browser = browser;
    }

    @Override
    public UsersPage load() {
        browser.goTo(URL);
        return this;
    }

    @Override
    public boolean isAt() {
        return browser.url().equals(URL) ? true : false;
    }

}
