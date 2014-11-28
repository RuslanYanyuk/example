package usermgmt.services;

import java.util.List;

import usermgmt.formbeans.SimpleUserFormBean;
import usermgmt.formbeans.UserFormBean;

public interface UserService {

	List<SimpleUserFormBean> getAll();
	
	SimpleUserFormBean get(String name);
	
	void create(UserFormBean userFormBean);
	
	void update(String name, UserFormBean userFormBean);
	
	void delete(String name);
	
}
