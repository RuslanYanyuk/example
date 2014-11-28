import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import play.Application;
import play.GlobalSettings;
import usermgmt.services.AuthService;
import usermgmt.services.XiAuthService;
import usermgmt.services.XiUserService;
import usermgmt.services.UserService;

public class Global extends GlobalSettings {

    private Injector injector;

    @Override
    public void onStart(Application application) {
        injector = Guice.createInjector(new AbstractModule() {

        	@Override
        	protected void configure() {
        	}
        	
        	@Singleton
            @Provides
            public UserService getUserService(){
        		return new XiUserService();
        	}
        	
        	@Singleton
            @Provides
            public AuthService getAuthService(){
        		return new XiAuthService();
        	}

        });
    }

    @Override
    public <T> T getControllerInstance(Class<T> aClass) throws Exception {
        return injector.getInstance(aClass);
    }
    
}