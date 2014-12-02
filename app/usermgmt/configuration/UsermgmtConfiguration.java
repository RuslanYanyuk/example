package usermgmt.configuration;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;

public class UsermgmtConfiguration {

	public static final String EBEAN_SERVER_NAME = "usermgmt";
	
	private static EbeanServer ebeanServer;
	
	public static EbeanServer getEbeanServer(){
		if (ebeanServer == null){
			ebeanServer = Ebean.getServer(EBEAN_SERVER_NAME);
		}
		return ebeanServer;
	}
	
}
