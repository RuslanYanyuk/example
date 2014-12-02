package usermgmt.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import play.db.ebean.Model;
import usermgmt.utils.AdditionalConfiguration;


@MappedSuperclass
public abstract class AbstractModel extends Model {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@Override
	public void save(){
		super.save(AdditionalConfiguration.EBEAN_SERVER.getValue());
	}
	
	@Override
	public void update(){
		super.update(AdditionalConfiguration.EBEAN_SERVER.getValue());
	}
	
	@Override
	public void delete(){
		super.delete(AdditionalConfiguration.EBEAN_SERVER.getValue());
	}
	
}
