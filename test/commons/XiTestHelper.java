package commons;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import play.Configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static play.test.Helpers.inMemoryDatabase;
import static utils.usermgmt.Constants.EBEAN_SERVER;

public class XiTestHelper {

    private static final String TEST_CONFIG_FILE = "conf/usermgmt.test.conf";
    private static final String PLAY_EVOLUTIONS_TABLE_NAME = "PLAY_EVOLUTIONS";

    public static Map<String, Object> getConfiguration(){

        Map<String, Object> configuration = new HashMap<String, Object>();

        configuration.putAll(inMemoryDatabase(EBEAN_SERVER));

        Config additionalConfig = ConfigFactory.parseFile(new File(TEST_CONFIG_FILE));
        configuration.putAll(new Configuration(additionalConfig).asMap());

        return configuration;
    }

    public static void cleanDb(){
        TestHelper.cleanDb(EBEAN_SERVER, PLAY_EVOLUTIONS_TABLE_NAME);
    }

}
