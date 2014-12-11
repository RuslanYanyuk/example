package utils.usermgmt;

public enum AdditionalConfiguration implements Configuration {

	EBEAN_SERVER("usermgmt");
	
	private String key;
	
	private String value;
	
	private AdditionalConfiguration(String value){
		this.value = value;
	}
	
	private AdditionalConfiguration(String key, String value){
		this.key = key;
		this.value = value;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString(){
		return getValue();
	}
	
	
	
}
