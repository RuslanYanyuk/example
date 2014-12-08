package usermgmt.services;

public class AlreadyExistsException extends Exception {

	AlreadyExistsException() {
		super();
	}

	AlreadyExistsException(String message) {
		super(message);
	}
	
}
