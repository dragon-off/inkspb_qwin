

import api.Suo_matrix;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class API_servlet extends HttpServlet {


    public void init(ServletConfig config)
            throws ServletException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();


        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        data=java.net.URLDecoder.decode(data, "UTF-8");

        if(request.getParameter("save_windows")!=null){

            Suo_matrix.save_matrix(Suo_matrix.read_json(data.replace("value=",""))); //data payload post

            String script="\"{\\\"windows\\\":[\"\n" +
                    "\n" +
                    "\n" +
                    "\tI=1\n" +
                    "\tM=16\n" +
                    "\tWHILE(I LE M)\n" +
                    "\tBEGIN\n" +
                    "\t\t\"{\"\n" +
                    "\t\t\t\"\\\"id\\\":\"I\",\"\n" +
                    "\t\t\t\"\\\"name\\\": \\\"\" COUNTER_NAME(I)   \"\\\",\"\n" +
                    "\t\t\t\n" +
                    "\t\t\"\\\"scheduls\\\": [\"\n" +
                    "\t\tJ=1\n" +
                    "\t\tE=7\n" +
                    "\t\tWHILE(J LE E)\n" +
                    "\t\tBEGIN\n" +
                    "\t\t\tP=(J-1)*200\n" +
                    "\t\t\tX=STRSTN(ARRAY(1100+(P)+I))\n" +
                    "\t\t\tY=STRSTN(ARRAY(1200+(P)+I))\n" +
                    "\t\t\t\n" +
                    "\t\t\t\"{\"\n" +
                    "\t\t\t\t\"\\\"from\\\": \" X\",\"\n" +
                    "\t\t\t\t\"\\\"to\\\": \" Y\"\"\n" +
                    "\t\t\t\"}\"\n" +
                    "\t\t\tIF(J NE E)\n" +
                    "\t\t\t\t\",\"\n" +
                    "\t\t\tJ=J+1\n" +
                    "\t\tEND\n" +
                    "\t\t\n" +
                    "\t\t\n" +
                    "\t\t\"],\"\n" +
                    "\t\t\t\"\\\"type\\\": \"ARRAY(2700+I) \",\"\n" +
                    "\t\t\t\n" +
                    "\t\t\t\"\\\"priority\\\": [\"\n" +
                    "\t\t\tJ=1\n" +
                    "\t\t\tE=122\n" +
                    "\t\t\tWHILE(J LE E)\n" +
                    "\t\t\tBEGIN\n" +
                    "\t\t\t\tK=ARRAY(600+I)\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t\t\"{\"\n" +
                    "\t\t\t\t\"\\\"id\\\": \"J\",\"\n" +
                    "\t\t\t\t\"\\\"priority\\\":\" STRSTN(STRSUB(K J J))\n" +
                    "\t\t\t\t\"}\"\n" +
                    "\t\t\t\tIF(J NE E)\n" +
                    "\t\t\t\t\t\",\"\n" +
                    "\t\t\t\tJ=J+1\n" +
                    "\t\t\tEND\n" +
                    "\t\n" +
                    "\t\n" +
                    "\t\t\"]}\"\n" +
                    "\t\tIF(I NE M)\n" +
                    "\t\t\t\",\"\n" +
                    "\t\tI=I+1\n" +
                    "\tEND\n" +
                    "\n" +
                    "\"]}\"";
            out.print(api.QwinAPI.getInstance().run_script(script));
        }

        //save operations
        if(request.getParameter("save_operations")!=null){
            Suo_matrix.save_operations(Suo_matrix.read_json_operations(data.replace("value=",""))); //data payload post
            String script="\"{\\\"operations\\\":[\"\n" +
                    "\n" +
                    "\tI=1\n" +
                    "\tM=122\n" +
                    "\tWHILE(I LE M)\n" +
                    "\tBEGIN\n" +
                    "\t\t\"{\"\n" +
                    "\t\t\t\"\\\"id\\\":\"I\",\"\n" +
                    "\t\t\t\"\\\"name\\\": \\\"\" BUTTON_NAME(I)  \"\\\",\"\n" +
                    "\t\t\t\"\\\"type\\\": \"ARRAY(200+I)\n" +
                    "\t\t\"}\"\n" +
                    "\t\tIF(I NE M)\n" +
                    "\t\t\t\",\"\n" +
                    "\t\tI=I+1\n" +
                    "\tEND\n" +
                    "\n" +
                    "\"]}\"";
            out.print(api.QwinAPI.getInstance().run_script(script));
        }

    }
}
