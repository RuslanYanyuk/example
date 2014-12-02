package usermgmt.models;

import javax.inject.Inject;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import play.db.ebean.Model;
import usermgmt.configuration.UsermgmtConfiguration;


@MappedSuperclass
public abstract class AbstractModel extends Model {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@Override
	public void save(){
		super.save(UsermgmtConfiguration.EBEAN_SERVER_NAME);
	}
	
	@Override
	public void update(){
		super.update(UsermgmtConfiguration.EBEAN_SERVER_NAME);
	}
	
	@Override
	public void delete(){
		super.delete(UsermgmtConfiguration.EBEAN_SERVER_NAME);
	}
	
}
