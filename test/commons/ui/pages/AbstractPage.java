package commons.ui.pages;

import java.util.concurrent.TimeUnit;

import org.fluentlenium.core.Fluent;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import commons.ui.AbstractUITest;

public abstract class AbstractPage {

    public static final int WAIT_TIME = 3;
    public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    
    private Fluent browser;

    private String url;

    private String selector;

    public AbstractPage(Fluent browser, String url, String selector){
        this.browser = browser;
        this.url = getFullUrl(url);
        this.selector = selector;
    }

    public AbstractPage load() {
        goTo();
        waitForPageLoads();
        return this;
    }
    
    public AbstractPage goTo(){
    	browser.goTo(url);
    	return this;
    }

    public boolean isAt() {
        return browser.url().equals(url);
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
    
    public void waitForContainsText(String element, String text){
    	browser.await().atMost(WAIT_TIME, TIME_UNIT).until(element).containsText(text);
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
    
    public void waitUntilAjaxCompleted() {
    	new WebDriverWait(browser.getDriver(), WAIT_TIME)
    		.until(new ExpectedCondition<Boolean>(){
    			
			    public Boolean apply(WebDriver driver) {
			        JavascriptExecutor js = (JavascriptExecutor) driver;
			        return (Boolean)js.executeScript("return jQuery.active == 0");
			    }
			    
			});
    }
    
    private final String getFullUrl(String url){
    	checkUrl(url);
    	if (!url.startsWith("/")){
    		url = '/' + url;
    	}
    	return getBaseUrl() + url;
    }
    
    private final void checkUrl(String url){
    	if (url.startsWith("http")){
    		throw new IllegalArgumentException();
    	}
    }
    
    private final String getBaseUrl(){
    	//TODO get HOST and PORT values at runtime
    	return String.format("http://%s:%s", AbstractUITest.HOST, AbstractUITest.PORT);
    }    
}
