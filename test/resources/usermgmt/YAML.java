package usermgmt;

import java.util.List;

import com.avaje.ebean.Ebean;

import play.libs.Yaml;
import static utils.usermgmt.Constants.*;

public enum YAML {

	GENERAL_USERS("usermgmt/db/usermgmt_general_users.yml");
	
	private String fileName;
	
	private YAML(String fileName){
		this.fileName = fileName;
	}
	
	public void load(){
		List<?> objects = (List<?>)Yaml.load(fileName);
		Ebean.getServer(EBEAN_SERVER).save(objects);
	}
	
}
