package api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Savermatrix {
    public static JSONObject read_json(String len) throws IOException {
        JSONParser parser=new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(len);
            //   System.out.
            return json;
        } catch (ParseException e) {
            return (JSONObject)new JSONObject().put("windows",new JSONObject().put("Error","Error"));
        }
    }
    public static void save_matrix(JSONObject json){
        StringBuffer sb=new StringBuffer();
        JSONObject windows=(JSONObject) json.get("windows");

        JSONArray priority=(JSONArray)windows.get("priority");

        StringBuffer prior_len=new StringBuffer();
        for(int i=0;i<priority.size();i++){
            prior_len.append(priority.get(i));
        }

        System.out.println("windows"+windows.get("id"));
        String script="\t\tB=%s{button}\n" +
                "\t\tW=%s{window}\n" +
                "\t\tV=%s{value}\n" +
                "\t\tM=%s{length}\n" +
                "\t\tIF(B EQ 1)\n" +
                "\t\t\tSETARRAY(600+W STRNTS(V)+STRSUB(ARRAY(600+W) 2 M))\n" +
                "\t\tELSE\n" +
                "\t\t\tSETARRAY(600+W STRSUB(ARRAY(600+W) 1 B-1)+STRNTS(V)+STRSUB(ARRAY(600+W) B+1 M))";

        script="SETARRAY(600+%s \"%s\"";
        script=String.format(script, windows.get("id"), prior_len);

        System.out.println("script="+script);
//        QwinAPI.run_script(script);
    }

    public String get_matrix() {

        JSONObject res;
        return "";

    }

    public String set_matrix() {

        JSONObject res;
        return "";
    }
}
