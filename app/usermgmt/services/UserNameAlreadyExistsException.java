package usermgmt.services;

public class UserNameAlreadyExistsException extends Exception {

	UserNameAlreadyExistsException() {
		super();
	}

	UserNameAlreadyExistsException(String message) {
		super(message);
	}
	
}
