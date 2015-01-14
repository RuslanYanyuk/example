package commons;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import org.apache.commons.lang3.ArrayUtils;
import play.test.FakeApplication;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static play.test.Helpers.fakeApplication;

public class TestHelper {

    public static FakeApplication createFakeApplication(Map<String, Object> configuration) {
    	return fakeApplication(configuration);
    }
    
    public static void cleanDb(String ebeanServer, String... skipTables){
		for (String tableName : getAllTableNames(ebeanServer)){
            if (!ArrayUtils.contains(skipTables, tableName)){
                Ebean.getServer(ebeanServer).createSqlUpdate(
                        String.format("TRUNCATE TABLE %s", tableName)).execute();
            }
		}
	}
	
	private static List<String> getAllTableNames(String ebeanServer){
		
		List<String> tableNames = new ArrayList<String>();
		
		Transaction tx = Ebean.getServer(ebeanServer).beginTransaction();
	    String sql = "SHOW TABLES";
	    try {
	        ResultSet rs = tx.getConnection().createStatement().executeQuery(sql);
	        while (rs.next()) {
	        	tableNames.add(rs.getString(1));
	        }
	        Ebean.getServer(ebeanServer).commitTransaction();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return tableNames;
	}
    
}
