package usermgmt.services;

import java.util.ArrayList;
import java.util.List;

import usermgmt.formbeans.SimpleUserFormBean;
import usermgmt.formbeans.UserFormBean;
import usermgmt.models.User;

public class XiUserService implements UserService {

	@Override
	public List<? extends SimpleUserFormBean> getAll() {
		List<User> users = User.find.all();
		List<SimpleUserFormBean> result = new ArrayList<>(users.size());
		for (User user : users){
			result.add(SimpleUserFormBean.from(user));
		}
		return result;
	}

	@Override
	public SimpleUserFormBean get(String username) {
		SimpleUserFormBean result = null;
		User user = findUserByUsername(username);
		result = SimpleUserFormBean.from(user); 
		return result;
	}

	@Override
	public void create(UserFormBean bean) {
		User newUser = new User();
		bean.populateModelWithData(newUser);
		newUser.save();
	}

	@Override
	public void update(String username, UserFormBean bean) {
		User user = findUserByUsername(username);
		bean.populateModelWithData(user);
		user.update();
	}

	@Override
	public void delete(String username) {
		User user = findUserByUsername(username);
		user.delete();
	}

	private User findUserByUsername(String username){
		User result = User.find.where().eq("username", username).findUnique();// throws exception if found more than 1
		return result;
	}
	
}
