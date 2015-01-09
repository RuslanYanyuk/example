package commons;

import org.junit.After;
import org.junit.Before;

import play.test.FakeApplication;
import static commons.TestHelper.*;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;


public abstract class AbstractUnitTest {
	
	private FakeApplication fakeApplication;
	
	@Before
	public void setUp(){
		fakeApplication = createFakeApplication();
		start(fakeApplication);
		clearDb();
	}
	
	@After
	public void tearDown(){
		stop(fakeApplication);
	}

}
