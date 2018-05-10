package api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Suo_matrix {
    public static void save_matrix(JSONObject json){
        try {
            JSONArray windows = (JSONArray) json.get("windows");
            System.out.println(windows.size());
            for (int i = 0; i < windows.size(); i++) {
                save_matrix_window((JSONObject) windows.get(i));
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static void save_operations(JSONObject json){
        try {
            JSONArray operations = (JSONArray) json.get("operations");
            System.out.println(operations.size());
            for (int i = 0; i < operations.size(); i++) {
                save_matrix_operation((JSONObject) operations.get(i));
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static void save_matrix_operation(JSONObject json) {
        System.out.println("save_matrix_operation");
        String script="";
        String operation=json.get("id").toString();// operation id
        try { //тип операции
            String type=json.get("type").toString();
            if(type.equals("1")) {
                script += "{}SETARRAY(200+"+operation+" 0)";
            } else  if(type.equals("2")){
                script += "{}SETARRAY(200+"+operation+" 1)";
            } else {
                script += "{}SETARRAY(200+"+operation+" 2)";
            }
        }catch ( Exception e){
            System.out.println(e);
        }

        System.out.println(QwinAPI.getInstance().run_script(script));

    }
    public static void save_matrix_window(JSONObject json){
        System.out.println("save_matrix_window");

        String counter=json.get("id").toString();// window id
        String script="";
        //StringBuffer sb=new StringBuffer();
        try {
            JSONObject windows = json;
            try {
                JSONArray schedules = (JSONArray) json.get("scheduls");
                int iterator = 0;
                for (int i = 0; i < schedules.size(); i++) {
                    JSONObject tmp = (JSONObject) schedules.get(i);
                    script += "{}SETARRAY(1100+" + (100 * iterator) + "+" + counter + " " + tmp.get("from").toString() + ")";
                    iterator++;
                    script += "{}SETARRAY(1100+" + (100 * iterator) + "+" + counter + " " + tmp.get("to").toString() + ")";
                    iterator++;
                }
            } catch (Exception e) {
                //net elementa
                System.out.println("scheduls");
            }


            try {
                JSONArray priority = (JSONArray) json.get("priority");
                StringBuffer prior_len = new StringBuffer();
                prior_len.append(QwinAPI.getInstance().run_script("ARRAY(600+"+counter+")"));
                for (int i = 0; i < priority.size(); i++){
                    JSONObject tmp= (JSONObject) priority.get(i);
                    Integer button=Integer.parseInt(tmp.get("id").toString());
                    prior_len.replace(button-1,button,tmp.get("priority").toString());
                }
                script += String.format("{}SETARRAY(600+%s \"%s\")", json.get("id"), prior_len);
            } catch (Exception e){
                //net elementa
                System.out.println("priority");
            }

            try { //тип окна, обычное бесконтактное
                String type= json.get("type").toString();
                if(type.equals("1")) {
                    script += "{}SETARRAY(2700+"+counter+" 1)";
                } else {
                    script += "{}SETARRAY(2700+"+counter+" 0)";
                }
            } catch ( Exception e){

            }

            System.out.println(script);
            System.out.println(QwinAPI.getInstance().run_script(script));

        } catch (Exception e){
            System.out.println("error: "+e);
        }

    }

    public static JSONObject read_json(String len) throws IOException {
        JSONParser parser=new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(len);
            return json;
        } catch (ParseException e) {
            System.out.println("error parse");
            return (JSONObject)new JSONObject().put("windows",new JSONObject().put("Error","Error"));
        }
    }
    public static JSONObject read_json_operations(String len) throws IOException {
        JSONParser parser=new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(len);
            return json;
        } catch (ParseException e) {
            System.out.println("error parse");
            return (JSONObject)new JSONObject().put("operations",new JSONObject().put("Error","Error"));
        }
    }
}
