package usermgmt.services;

public class UserNotFoundException extends Exception {

	UserNotFoundException() {
		super();
	}

	UserNotFoundException(String message) {
		super(message);
	}
	
}
