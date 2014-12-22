package views.usermgmt.ui.pages;

import org.fluentlenium.core.Fluent;

public class IndexPage extends AbstractPage{

    public static final String URL = "http://localhost:3333/";
    public static final String SELECTOR = "html";

    public IndexPage(Fluent browser) {
        super(browser, URL, SELECTOR);
    }
}
