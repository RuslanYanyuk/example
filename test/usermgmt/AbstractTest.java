package usermgmt;

import static play.test.Helpers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

import play.test.FakeApplication;
import play.test.TestServer;
import usermgmt.configuration.UsermgmtConfiguration;
import usermgmt.models.AbstractModel;
import usermgmt.models.User;


public abstract class AbstractTest {

	private static final int DEFAULT_PORT = 3333;
	
	private TestServer server;
	
	@Before
	public void setUp(){
		server = createTestServer();
		start(server);
		cleanDatabase(User.class);
	}
	
	@After
	public void tearDown(){
		stop(server);
	}
	
	private static TestServer createTestServer(){
		FakeApplication fakeApplication = fakeApplication(getUsermgmtDbParams());
		TestServer result = testServer(DEFAULT_PORT, fakeApplication);
		return result;
	}
	
	private static Map<String, String> getUsermgmtDbParams(){
		Map<String, String> result = new HashMap<String, String>();
        result.put("db.usermgmt.driver", "com.mysql.jdbc.Driver");
        result.put("db.usermgmt.url", "jdbc:mysql://localhost:3306/usermgmt?characterEncoding=UTF-8");
        result.put("db.usermgmt.user", "test");
        result.put("db.usermgmt.password", "test");
        result.put("ebean.usermgmt", "usermgmt.models.*");
        result.put("applyEvolutions.default", "true");

        return result;
	}
	
	@SafeVarargs
	private static void cleanDatabase(Class<? extends AbstractModel>... entitiesTypes){
		for (Class<? extends AbstractModel> classValue : entitiesTypes) {
            clearTable(classValue);
        }
	}
	
	private static void clearTable(Class<? extends AbstractModel> classValue) {
        clearEntities(classValue);
        alterTable(classValue);
    }
	
	private static void clearEntities(Class<? extends AbstractModel> classValue){
		Iterable<? extends AbstractModel> models = UsermgmtConfiguration.getEbeanServer().find(classValue).findList();
        for (AbstractModel model : models) {
        	UsermgmtConfiguration.getEbeanServer().delete(model);
        }
	}
	
	private static void alterTable(Class<? extends AbstractModel> classValue){
		String tableName = classValue.getSimpleName().toLowerCase();
		alterTable(tableName);
	}
	
	private static void alterTable(String tableName){
		UsermgmtConfiguration.getEbeanServer().createSqlUpdate(String.format("ALTER TABLE %s AUTO_INCREMENT = 1", tableName)).execute();
	}
	
}
