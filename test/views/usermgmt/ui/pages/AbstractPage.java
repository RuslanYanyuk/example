package views.usermgmt.ui.pages;

import org.fluentlenium.core.Fluent;

import java.util.concurrent.TimeUnit;

import static org.fluentlenium.core.filter.FilterConstructor.withText;

public abstract class AbstractPage {

    public static final int WAIT_TIME = 2;
    public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private Fluent browser;

    private String url;

    private String selector;

    public AbstractPage(Fluent browser, String url, String selector){
        this.browser = browser;
        this.url = url;
        this.selector = selector;
    }

    public AbstractPage load() {
        browser.goTo(url);
        waitForPageLoads();
        return this;
    }

    public boolean isAt() {
        return browser.url().equals(url) ? true : false;
    }

    private void waitForPageLoads() {
        waitForPresent(selector);
    }

    void waitForPresent(String element) {
        browser.await().atMost(WAIT_TIME, TIME_UNIT).until(element).isPresent();
    }

    void waitForDisplayed(String element) {
        browser.await().atMost(WAIT_TIME, TIME_UNIT).until(element).areDisplayed();
    }

    void waitForNotPresent(String element, String text) {
        browser.await().atMost(WAIT_TIME, TIME_UNIT).until(element).withText(text).isNotPresent();
    }

    void waitForElementHasText(String element, String text) {
        browser.await().atMost(WAIT_TIME, TIME_UNIT).until(element).hasText(text);
    }

    protected Fluent getBrowser(){
        return browser;
    }

    public boolean checkStatus (Status status){
        return getBrowser().$("body", withText(status.getContains())) != null;
    }

}
