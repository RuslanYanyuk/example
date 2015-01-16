package commons;

import org.junit.After;
import org.junit.Before;

import play.test.FakeApplication;

import static commons.XiTestHelper.cleanDb;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;


public abstract class AbstractUnitTest {

	private FakeApplication fakeApplication;
	
	@Before
	public void setUp(){
		fakeApplication = fakeApplication(XiTestHelper.getConfiguration());
		start(fakeApplication);
		cleanDb();
	}
	
	@After
	public void tearDown(){
		stop(fakeApplication);
	}
	
}
