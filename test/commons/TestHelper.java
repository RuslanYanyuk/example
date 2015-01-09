package commons;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import play.Configuration;
import play.test.FakeApplication;
import static utils.usermgmt.Constants.*;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

public class TestHelper {
	
	private static final String TEST_CONFIG_FILE = "conf/usermgmt.test.conf";
	private static final String PLAY_EVOLUTIONS_TABLE_NAME = "PLAY_EVOLUTIONS";
	
    public static FakeApplication createFakeApplication() {
    	return fakeApplication(getConfiguration());
    }
    
    private static Map<String, Object> getConfiguration(){
    	
    	Map<String, Object> configuration = new HashMap<String, Object>();
    	
    	configuration.putAll(inMemoryDatabase(EBEAN_SERVER));
    	
    	Config additionalConfig = ConfigFactory.parseFile(new File(TEST_CONFIG_FILE));
    	configuration.putAll(new Configuration(additionalConfig).asMap());
    	
    	return configuration;
    }
    
    public static void clearDb(){
		for (String tableName : getAllTableNames()){
			Ebean.getServer(EBEAN_SERVER).createSqlUpdate(
					String.format("TRUNCATE TABLE %s", tableName)).execute();	
		}
	}
	
	private static List<String> getAllTableNames(){
		
		List<String> tableNames = new ArrayList<String>();
		
		Transaction tx = Ebean.getServer(EBEAN_SERVER).beginTransaction();
	    String sql = "SHOW TABLES";
	    try {
	        ResultSet rs = tx.getConnection().createStatement().executeQuery(sql);
	        while (rs.next()) {
	        	String tableName = rs.getString(1);
	        	if (!tableName.toUpperCase().equals(PLAY_EVOLUTIONS_TABLE_NAME)){
	        		tableNames.add(tableName);
	        	}
	        }
	        Ebean.getServer(EBEAN_SERVER).commitTransaction();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return tableNames;
	}
    
}
