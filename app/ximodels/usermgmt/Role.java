package ximodels.usermgmt;

public enum Role {
	
	USER,
	ADMIN;
	
	public static class Names {
		
		private Names(){}
		
		public static final String ADMIN = "ADMIN";
		public static final String LOGGED_IN = "Logged in";	
		
	}
}
