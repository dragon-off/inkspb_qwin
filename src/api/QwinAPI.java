package api;

import java.util.Properties;

import com.qmatic.QwinFactory.QWinFactory;
import com.qmatic.connection.CommandException;
import com.qmatic.network.Session;
import com.qmatic.network.SessionFactory;
import com.qmatic.scripting.Script;
import com.qmatic.scripting.ScriptException;
import com.qmatic.scripting.ScriptManager;

public class QwinAPI {
    private static Properties qProp = new Properties();
    private static SessionFactory ses;
    private static Session f1;
    private static QWinFactory qwf;
    public static ScriptManager sm;
    private static QwinAPI qwinapi;
    public static String host="127.0.0.1";

    public static QwinAPI getInstance() {
        if (qwinapi == null) {
            try {
                qwinapi = new QwinAPI();
            } catch (Exception var1) {
                ;
            }
        }
        return qwinapi;
    }

    public static void main(String[] args) throws ScriptException, CommandException {
       // host="192.168.0.90";
       // System.out.println(QwinAPI.getInstance().run_script("TIME_NO"));
       // session_close();
    }

    public QwinAPI(){
        setup();
    }

    public static String run_script(String script){
        try {
            Script sc=sm.createScript("test",script);
            return sc.execute();
        } catch (Exception e){
            return "Error script:"+e;
        }
    }

    public static void session_close(){
        try {
            ses.closeSession(f1.getSessionID());
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    public static void setup() {
        try {
            qProp.setProperty("host", host);
            qProp.setProperty("port", "500");
            qProp.setProperty("version", "S");
            qProp.setProperty("encryption", "true");
            SessionFactory.setProperties(qProp);
            ses = SessionFactory.getInstance();
            create_session();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void create_session()
            throws Exception
    {
        synchronized (ses)
        {
            f1 = SessionFactory.getInstance().createSession();
            qwf = f1.getQWinFactory();
            sm = qwf.getScriptManager();
            Thread.sleep(5000L);
        }
    }
}