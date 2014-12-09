package usermgmt;

import play.test.FakeApplication;
import usermgmt.utils.AdditionalConfiguration;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

public class TestHelper {
    public static FakeApplication createFakeApplication() {
        return fakeApplication(inMemoryDatabase(AdditionalConfiguration.EBEAN_SERVER.getValue()));
    }
}
