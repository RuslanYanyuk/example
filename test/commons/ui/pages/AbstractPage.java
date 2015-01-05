package commons.ui.pages;

import org.fluentlenium.core.Fluent;

import java.util.concurrent.TimeUnit;

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

    public void waitForDisplayed(String element) {
        browser.await().atMost(WAIT_TIME, TIME_UNIT).until(element).areDisplayed();
    }

    public void waitForNotPresent(String element) {
        browser.await().atMost(WAIT_TIME, TIME_UNIT).until(element).isNotPresent();
    }

    public void waitForElementHasText(String element, String text) {
        browser.await().atMost(WAIT_TIME, TIME_UNIT).until(element).hasText(text);
    }

    public Fluent getBrowser(){
        return browser;
    }

}