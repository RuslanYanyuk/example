package usermgmt.services;

import java.util.ArrayList;
import java.util.List;

import usermgmt.formbeans.UserFormBean;
import usermgmt.formbeans.SecuredUserFormBean;
import usermgmt.models.User;

public class XiUserService implements UserService {

	@Override
	public List<? extends UserFormBean> getAll() {
		List<User> users = User.find.all();
		List<UserFormBean> result = new ArrayList<>(users.size());
		for (User user : users){
			result.add(UserFormBean.from(user));
		}
		return result;
	}

	@Override
	public UserFormBean get(String userName) throws UserNotFoundException {
		return UserFormBean.from(findUserByUserName(userName));
	}

	@Override
	public UserFormBean create(SecuredUserFormBean bean) throws UserNameAlreadyExistsException {
		validateUserName(bean.userName);
		User newUser = new User();
		bean.populateModelWithData(newUser);
		newUser.save();
		return UserFormBean.from(newUser);
	}

	@Override
	public UserFormBean update(String userName, SecuredUserFormBean bean) throws UserNotFoundException, UserNameAlreadyExistsException {
		if (!userName.equals(bean.userName)){
			validateUserName(bean.userName);
		}
		User user = findUserByUserName(userName);
		bean.populateModelWithData(user);
		user.update();
		return UserFormBean.from(user);
	}

	@Override
	public void delete(String userName) throws UserNotFoundException {
		findUserByUserName(userName).delete();
	}

	private User findUserByUserName(String userName) throws UserNotFoundException {
		User result = User.find.where().eq("userName", userName).findUnique();// throws exception if found more than 1
		if (result == null){
			throw new UserNotFoundException(
					String.format("User entity with user name %s doesn't exist.", userName));
		}
		return result;
	}
	
	private void validateUserName(String userName) throws UserNameAlreadyExistsException{
		if (User.find.where().eq("userName", userName).findUnique() != null) {
			throw new UserNameAlreadyExistsException(
					String.format("User name %s already exist.", userName));
		}
	}
	
}
