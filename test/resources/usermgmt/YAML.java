package resources.usermgmt;

import java.io.File;
import java.util.List;

import play.libs.Yaml;
import usermgmt.configuration.UsermgmtConfiguration;

public enum YAML {

	GENERAL_USERS("usermgmt/db/usermgmt_general_users.yml");
	
	private String fileName;
	
	private YAML(String fileName){
		this.fileName = fileName;
	}
	
	public void load(){
		List<?> objects = (List<?>)Yaml.load(fileName);
		UsermgmtConfiguration.getEbeanServer().save(objects);
	}
	
	
	
}
