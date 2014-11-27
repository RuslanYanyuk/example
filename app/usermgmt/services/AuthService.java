package usermgmt.services;

public interface AuthService {

	void login(String username, String password);
	
	void logout();
	
}
