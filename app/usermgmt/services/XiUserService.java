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
	public UserFormBean get(String userName) {
		UserFormBean result = null;
		User user = findUserByUsername(userName);
		result = UserFormBean.from(user); 
		return result;
	}

	@Override
	public void create(SecuredUserFormBean bean) {
		User newUser = new User();
		bean.populateModelWithData(newUser);
		newUser.save();
	}

	@Override
	public void update(String userName, SecuredUserFormBean bean) {
		User user = findUserByUsername(userName);
		bean.populateModelWithData(user);
		user.update();
	}

	@Override
	public void delete(String userName) {
		User user = findUserByUsername(userName);
		user.delete();
	}

	private User findUserByUsername(String userName){
		User result = User.find.where().eq("userName", userName).findUnique();// throws exception if found more than 1
		return result;
	}
	
}
