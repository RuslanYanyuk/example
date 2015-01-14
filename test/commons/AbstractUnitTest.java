package commons;

import org.junit.After;
import org.junit.Before;
import play.test.FakeApplication;

import java.util.Map;

import static commons.TestHelper.createFakeApplication;
import static commons.XiTestHelper.cleanDb;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;


public abstract class AbstractUnitTest {

	private FakeApplication fakeApplication;
	
	@Before
	public void setUp(){
		fakeApplication = createFakeApplication(getConfiguration());
		start(fakeApplication);
		cleanDb();
	}
	
	@After
	public void tearDown(){
		stop(fakeApplication);
	}

    protected Map<String, Object> getConfiguration(){
        return XiTestHelper.getConfiguration();
    }

}
