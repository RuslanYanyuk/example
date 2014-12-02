package usermgmt;

import static play.test.Helpers.*;

import org.junit.After;
import org.junit.Before;

import play.test.FakeApplication;
import usermgmt.utils.AdditionalConfiguration;


public abstract class AbstractTest {
	
	private FakeApplication fakeApplication;
	
	@Before
	public void setUp(){
		fakeApplication = createFakeApplication();
		start(fakeApplication);
	}
	
	@After
	public void tearDown(){
		stop(fakeApplication);
	}
	
	protected FakeApplication createFakeApplication(){
		return fakeApplication(inMemoryDatabase(AdditionalConfiguration.EBEAN_SERVER.getValue()));
	}
	
	
}
