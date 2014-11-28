package usermgmt.services;

import java.util.List;

import usermgmt.formbeans.SimpleUserFormBean;
import usermgmt.formbeans.UserFormBean;

public interface UserService {

	List<? extends SimpleUserFormBean> getAll();
	
	SimpleUserFormBean get(String username);
	
	void create(UserFormBean bean);
	
	void update(String username, UserFormBean bean);
	
	void delete(String username);
	
}
