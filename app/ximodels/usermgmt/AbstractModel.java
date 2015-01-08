package ximodels.usermgmt;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import play.db.ebean.Model;

import static utils.usermgmt.Constants.EBEAN_SERVER;


@MappedSuperclass
public abstract class AbstractModel extends Model {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@Override
	public void save(){
		super.save(EBEAN_SERVER);
	}
	
	@Override
	public void update(){
		super.update(EBEAN_SERVER);
	}
	
	@Override
	public void delete(){
		super.delete(EBEAN_SERVER);
	}
	
}
