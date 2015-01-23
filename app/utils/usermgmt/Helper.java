package utils.usermgmt;

import static utils.usermgmt.Constants.LOGGER;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Helper {

	static final String EXCEPTION_MESSAGE = "IOException in utils.usermgmt.Helper.readText";
	
    public static String readText(String path) {
        StringBuilder html = new StringBuilder();
        if (path != null) {
            try(BufferedReader in = new BufferedReader(new FileReader(path))) {
                String str;
                while ((str = in.readLine()) != null)
                    html.append(str);
            } catch (IOException e) {
                LOGGER.error(EXCEPTION_MESSAGE, e);
            }
        }
        
        return html.toString();
    }
    
}
