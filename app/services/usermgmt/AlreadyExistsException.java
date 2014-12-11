package services.usermgmt;

public class AlreadyExistsException extends Exception {

	AlreadyExistsException() {
		super();
	}

	AlreadyExistsException(String message) {
		super(message);
	}
	
}
