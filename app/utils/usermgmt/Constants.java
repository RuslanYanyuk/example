package utils.usermgmt;

import play.Logger;

public interface Constants {

	String INDEX_PAGE = "/";
	String LOGOUT_PAGE = "/logout";
	String EBEAN_SERVER = "usermgmt";
	
	Logger.ALogger LOGGER = Logger.of("usermgmt");
	
}
