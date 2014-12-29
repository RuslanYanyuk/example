package views;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import usermgmt.YAML;
import views.pages.OpenIndexPage;
import views.usermgmt.pages.IndexPage;
import views.usermgmt.pages.LoginPage;
import commons.ui.AbstractUITest;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static usermgmt.Parameters.*;

public class IndexPageTest extends AbstractUITest {
	
	@Test
	public void indexPageCanAccessLoginedUser(){
		YAML.GENERAL_USERS.load();
		
		IndexPage indexPage = new IndexPage(getBrowser());
		LoginPage loginPage = new LoginPage(getBrowser()); 
		
		goTo(IndexPage.URL);
		assertTrue(loginPage.isAt());
		
		loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);
		assertTrue(indexPage.isAt());
	}
	
	@Test
	public void indexPageCannotAccessNotLoginedUser(){				
		goTo(IndexPage.URL);
		assertFalse(new IndexPage(getBrowser()).isAt());
	}
	
	@Test
	public void openIndexPageCanAccessUnloggined(){
		OpenIndexPage openIndexPage = new OpenIndexPage(getBrowser());
		openIndexPage.load();
	}
	
	@Test
	public void openIndexPageCanAccessLoggined(){
		YAML.GENERAL_USERS.load();
		loginAndLoad(FIRST_USER_NAME, FIRST_USER_PASSWORD, OpenIndexPage.class);
	}
	
}
