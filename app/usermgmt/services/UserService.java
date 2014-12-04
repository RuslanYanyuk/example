package usermgmt.services;

import java.util.List;

import usermgmt.formbeans.UserFormBean;
import usermgmt.formbeans.SecuredUserFormBean;

public interface UserService {

	List<? extends UserFormBean> getAll();
	
	UserFormBean get(String userName);
	
	void create(SecuredUserFormBean bean);
	
	void update(String userName, SecuredUserFormBean bean);
	
	void delete(String userName);
	
}
