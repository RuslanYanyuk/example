package views.usermgmt.ui.pages;

import org.fluentlenium.core.Fluent;

public class IndexPage implements Page{

    public static final String URL = "http://localhost:3333/";

    private Fluent browser;

    public IndexPage(Fluent browser) {
        this.browser = browser;
    }

    @Override
    public IndexPage load() {
        browser.goTo(URL);
        return this;
    }

    @Override
    public boolean isAt() {
        return browser.url().equals(URL) ? true : false;
    }
}
