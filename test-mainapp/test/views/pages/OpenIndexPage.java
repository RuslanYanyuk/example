package views.pages;

import org.fluentlenium.core.Fluent;

import commons.ui.pages.AbstractPage;

public class OpenIndexPage extends AbstractPage{

    public static final String URL = "/openIndex";
    public static final String SELECTOR = "#news";

    public OpenIndexPage(Fluent browser) {
        super(browser, URL, SELECTOR);
    }
}
