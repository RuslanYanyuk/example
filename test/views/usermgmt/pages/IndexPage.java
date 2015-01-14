package views.usermgmt.pages;

import org.fluentlenium.core.Fluent;

import commons.ui.pages.AbstractPage;

public class IndexPage extends AbstractPage{

    public static final String URL = "/";
    public static final String SELECTOR = "html";

    public IndexPage(Fluent browser) {
        super(browser, URL, SELECTOR);
    }
}
