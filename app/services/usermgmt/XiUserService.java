package services.usermgmt;

import formbeans.usermgmt.SecuredUserFormBean;
import formbeans.usermgmt.UserFormBean;

import org.apache.commons.lang3.StringUtils;

import ximodels.usermgmt.User;

import java.util.ArrayList;
import java.util.List;

public class XiUserService implements UserService {

	static final String NAME_CAN_NOT_BE_CHANGED = "User name can not be changed.";
	static final String CAN_NOT_BE_CHANGED_CURRENT_USER_ROLE = "Current user role can not be changed.";
	static final String CAN_NOT_BE_DELETED_CURRENT_USER = "Can not be deleted current user.";
	static final String USER_NOT_EXIST = "User entity with user name %s doesn't exist.";
	static final String USER_ALREADY_EXIST = "User name %s already exist.";
	static final String REQUIRED_FIELDS = "User name and password are required. They can not be empty.";

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
	public UserFormBean get(String userName) throws NotFoundException {
		return UserFormBean.from(findUserByUserName(userName));
	}

	@Override
	public UserFormBean create(SecuredUserFormBean bean) throws AlreadyExistsException {
		isBlank(bean.password);
		validateUserName(bean.userName);
		User newUser = new User();
		bean.populateModelWithData(newUser);
		newUser.save();
		return UserFormBean.from(newUser);
	}

	@Override
	public UserFormBean update(String userName, SecuredUserFormBean bean, boolean isCurrentUser) throws NotFoundException, AlreadyExistsException {
		if (!userName.equals(bean.userName)){
			throw new IllegalArgumentException(NAME_CAN_NOT_BE_CHANGED);
		}
		User user = findUserByUserName(userName);

		if (isCurrentUser && !user.role.toString().equals(bean.role)) {
			throw new IllegalArgumentException(CAN_NOT_BE_CHANGED_CURRENT_USER_ROLE);
		}

		String passwordHash = user.passwordHash;
		bean.populateModelWithData(user);
//TODO : refactor password validation
		if (StringUtils.isBlank(bean.password)) {
			user.passwordHash = passwordHash;
		}

		user.update();
		return UserFormBean.from(user);
	}

	@Override
	public void delete(String userName, boolean isCurrentUser) throws NotFoundException {
		if(isCurrentUser){
			throw new IllegalArgumentException(CAN_NOT_BE_DELETED_CURRENT_USER);
		}

		findUserByUserName(userName).delete();
	}

	private User findUserByUserName(String userName) throws NotFoundException {
		User user = User.findUserByUserName(userName);
		if (user == null){
			throw new NotFoundException(
					String.format(USER_NOT_EXIST, userName));
		}
		return user;
	}

	private void validateUserName(String userName) throws AlreadyExistsException {
		isBlank(userName);
		if (User.find.where().eq("userName", userName).findUnique() != null) {
			throw new AlreadyExistsException(
					String.format(USER_ALREADY_EXIST, userName));
		}
	}

	private void isBlank(String param) {
		if (StringUtils.isBlank(param)) {
			throw new IllegalArgumentException(REQUIRED_FIELDS);
		}
	}

}
