package usermgmt.formbeans;

import play.db.ebean.Model;

public interface FormBean<M extends Model> {
	
	void populateModelWithData(M model);
	
}
