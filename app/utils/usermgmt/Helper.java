package utils.usermgmt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Helper {

    public static String readText(String path) {
        StringBuilder html = new StringBuilder();
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

}
