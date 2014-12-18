package views.usermgmt;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import play.Configuration;
import play.test.FakeApplication;
import utils.usermgmt.AdditionalConfiguration;

import java.io.File;
import java.util.Map;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

public class TestHelper {
    public static FakeApplication createFakeApplication() {
        Config additionalConfig = ConfigFactory.parseFile(new File("conf/usermgmt.test.conf"));
        Map<String, Object> additionalConfigurations = new Configuration(additionalConfig).asMap();
        additionalConfigurations.putAll(inMemoryDatabase(AdditionalConfiguration.EBEAN_SERVER.getValue()));
        return fakeApplication(additionalConfigurations);
    }
}