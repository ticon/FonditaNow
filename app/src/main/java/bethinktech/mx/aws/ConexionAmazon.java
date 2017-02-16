package bethinktech.mx.aws;

import android.content.Context;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.regions.Regions;

/**
 * Created by ricksonplancher on 12/15/16.
 */

public class ConexionAmazon {

    //variable para conectar a amazon
    private static String usuarioPoolId = "us-west-2_k6391LyL4";
    private static  String usuarioClienteId = "5cp0iju93he0clpcevtlk7eqel";
    private static String usuarioPoolSecret = "1bem7ds5mtrf6llo3a64qfb2cpdrlrv9rj5cd1c0pef0prjb8kmm";


    private static final Regions cognitoRegion = Regions.US_WEST_2;

    private static ConexionAmazon conexionAmazon;
    private static CognitoUserPool usuarioPool;

    private static String user;
    private static CognitoDevice newDevice;
    private static CognitoUserSession currSession;


    public static void init(Context contexto)
    {
        if(conexionAmazon != null && usuarioPool != null)
        {
            return;
        }

        if (conexionAmazon == null) {
            conexionAmazon = new ConexionAmazon();
        }

        if(usuarioPool == null)
        {
            usuarioPool = new CognitoUserPool(contexto, usuarioPoolId, usuarioClienteId, usuarioPoolSecret, cognitoRegion);

        }
    }

    public static CognitoUserPool getPool()
    {
        return usuarioPool;
    }

    public static void setUser(String newUser) {
        user = newUser;
    }

    public static void newDevice(CognitoDevice device) {
        newDevice = device;
    }

    public static void setCurrSession(CognitoUserSession session) {
        currSession = session;
    }

    public static  CognitoUserSession getCurrSession() {
        return currSession;
    }

    public static String formatException(Exception exception) {
        String formattedString = "Internal Error";

        Log.e("App Error",exception.toString());
        Log.getStackTraceString(exception);

        String temp = exception.getMessage();

        if(temp != null && temp.length() > 0) {
            formattedString = temp.split("\\(")[0];
            if(temp != null && temp.length() > 0) {

                if(temp.split("\\(")[0].equals("User does not exist. "))
                {
                    return formattedString.replace("User does not exist","Usuario no exite!!");
                }
                else
                {
                    return formattedString;
                }

            }

        }

        return  formattedString;
    }

}
