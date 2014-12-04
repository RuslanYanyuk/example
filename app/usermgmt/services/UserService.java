package usermgmt.services;

import java.util.List;

import usermgmt.formbeans.UserFormBean;
import usermgmt.formbeans.SecuredUserFormBean;

public interface UserService {

	List<? extends UserFormBean> getAll();
	
	UserFormBean get(String userName) throws UserNotFoundException;
	
	UserFormBean create(SecuredUserFormBean bean) throws UserNameAlreadyExistsException;
	
	UserFormBean update(String userName, SecuredUserFormBean bean) throws UserNameAlreadyExistsException, UserNotFoundException;
	
	void delete(String userName) throws UserNotFoundException;
	
}
