package services.usermgmt;

import java.util.List;

import formbeans.usermgmt.UserFormBean;
import formbeans.usermgmt.SecuredUserFormBean;

public interface UserService {

	List<? extends UserFormBean> getAll();
	
	UserFormBean get(String userName) throws NotFoundException;
	
	UserFormBean create(SecuredUserFormBean bean) throws AlreadyExistsException;
	
	UserFormBean update(String userName, SecuredUserFormBean bean, boolean isCurrentUser) throws AlreadyExistsException, NotFoundException;
	
	void delete(String userName, boolean isCurrentUser) throws NotFoundException;

}
