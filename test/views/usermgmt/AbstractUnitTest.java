package views.usermgmt;

import org.junit.After;
import org.junit.Before;
import play.test.FakeApplication;

import static play.test.Helpers.start;
import static play.test.Helpers.stop;
import static views.usermgmt.TestHelper.*;


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
