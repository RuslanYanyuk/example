package usermgmt.services;

public interface AuthService {

	void login(String userName, String password);
	
	void logout();
	
}
