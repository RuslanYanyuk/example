package utils.usermgmt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public enum OptionalHtmlLoader {

    LOGIN_HTML("usermgmt.login.html");

    private String propertyName;

    private OptionalHtmlLoader(String propertyName) {
        this.propertyName = propertyName;
    }

    public String get() {
        StringBuilder html = new StringBuilder();
        String path = getPath(propertyName);
        if (path != null) {
            try(BufferedReader in = new BufferedReader(new FileReader(path))) {
                String str;
                while ((str = in.readLine()) != null)
                    html.append(str);
            } catch (IOException e) {
                //TODO log it
                e.printStackTrace();
            }
        }
        return html.toString();
    }

    private static final String getPath(String propertyName){
        return play.Play.application().configuration().getString(propertyName);
    }

}
