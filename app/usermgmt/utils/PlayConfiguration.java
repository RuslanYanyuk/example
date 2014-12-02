package usermgmt.utils;

import org.apache.commons.lang3.StringUtils;

public enum PlayConfiguration implements Configuration {

	;
	
    private String configurationKey;
    
    private PlayConfiguration(String configurationKey) {
        this.configurationKey = configurationKey;
    }
    
    @Override
    public String getValue() {
        return play.Play.application().configuration().getString(configurationKey);
    }

    public String[] getCommaSeparatedValues() {
        return StringUtils.split(StringUtils.defaultString(getValue()), ',');
    }
    
    @Override
    public String getKey() {
        return configurationKey;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
