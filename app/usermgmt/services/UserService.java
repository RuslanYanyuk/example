package usermgmt.services;

import java.util.List;

import usermgmt.formbeans.UserFormBean;
import usermgmt.formbeans.SecuredUserFormBean;

public interface UserService {

	List<? extends UserFormBean> getAll();
	
	UserFormBean get(String userName) throws NotFoundException;
	
	UserFormBean create(SecuredUserFormBean bean) throws AlreadyExistsException;
	
	UserFormBean update(String userName, SecuredUserFormBean bean) throws AlreadyExistsException, NotFoundException;
	
	void delete(String userName) throws NotFoundException;
	
}
