package views;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static usermgmt.Parameters.FIRST_USER_NAME;
import static usermgmt.Parameters.FIRST_USER_PASSWORD;

import org.junit.Test;

import usermgmt.YAML;
import views.pages.OpenIndexPage;
import views.usermgmt.XiAbstractUITest;
import views.usermgmt.pages.IndexPage;
import views.usermgmt.pages.LoginPage;

public class IndexPageTest extends XiAbstractUITest {
	
	@Test
	public void indexPageCanAccessLoginedUser(){
		YAML.GENERAL_USERS.load();
		
		IndexPage indexPage = new IndexPage(getBrowser());
		LoginPage loginPage = new LoginPage(getBrowser()); 
		
		indexPage.goTo();
		assertTrue(loginPage.isAt());
		
		loginPage.login(FIRST_USER_NAME, FIRST_USER_PASSWORD);
		assertTrue(indexPage.isAt());
	}
	
	@Test
	public void indexPageCannotAccessNotLoginedUser(){
        IndexPage indexPage = new IndexPage(getBrowser());
        indexPage.goTo();
		assertFalse(indexPage.isAt());
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
