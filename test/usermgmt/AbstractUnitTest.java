package usermgmt;

import static play.test.Helpers.*;
import static usermgmt.TestHelper.createFakeApplication;

import org.junit.After;
import org.junit.Before;

import play.test.FakeApplication;
import usermgmt.utils.AdditionalConfiguration;


public abstract class AbstractUnitTest {
	
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

}
