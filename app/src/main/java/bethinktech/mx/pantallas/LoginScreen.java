package bethinktech.mx.pantallas;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;

import java.util.Locale;

import bethinktech.mx.R;
import bethinktech.mx.aws.ConexionAmazon;
import bethinktech.mx.db.AdminSQLiteOpenHelper;

public class LoginScreen extends AppCompatActivity {
    //CognitoUserAttributes atributoUsuario;
    //private MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation;

    EditText edtCel_login, edtContraseña_login;

    private String usernameInput;
    private String userPasswd;
    int  flag =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ConexionAmazon.init(getApplicationContext());
        //atributoUsuario = new CognitoUserAttributes();

        edtCel_login = (EditText)findViewById(R.id.edtCel_login);
        edtContraseña_login = (EditText)findViewById(R.id.edtContraseña_login);

        int[] arreglo_uno = {3,4,6,6,9,11,16,17};
        int [] arreglo_dos = {8,9,9,40};
        int[] arr = arreglo_tres(arreglo_uno,arreglo_dos);

        System.out.println(arr);

    }

    public void onclickRegistro(View v)
    {
        registarUsuario();
    }

    public  void accionLogin(View v)
    {
        usernameInput = edtCel_login.getText().toString();
        userPasswd = edtContraseña_login.getText().toString();
        if(usernameInput.length() < 10 || usernameInput.isEmpty())
        {
            mostrarDialogo("Telefóno"," Favor de ingresar su numero telefónico");
        }
        else
        {
            if(userPasswd.length() < 8 || userPasswd.isEmpty())
            {
                mostrarDialogo("Contraseña"," Favor de ingresar 8 caracteres de contraseña");
            }
            else
            {
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"BDFonda",null,1);
                SQLiteDatabase sbd = admin.getWritableDatabase();

                String querySelect = "Select * from Cliente where usuario='"+usernameInput +"'"+" and contrasena='"+userPasswd+"'";
                Cursor fila = sbd.rawQuery(querySelect,null);

                if (fila.moveToFirst()) {
                    //et2.setText(fila.getString(0));
                    //et3.setText(fila.getString(1));
                } else
                    Toast.makeText(this, "No existe un artículo con dicho código",
                            Toast.LENGTH_LONG).show();
                sbd.close();
                //ConexionAmazon.getPool().getUser(usernameInput).getSessionInBackground(authenticationHandler);
            }
        }
    }

    public void onClickRecuperar(View v)
    {
        mostrarDialogRecuperacion();
    }

    public void mostrarDialogRecuperacion()
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_recuperando_contrasena, null);
        dialogBuilder.setView(dialogView);

        EditText emailRecuperar = (EditText) dialogView.findViewById(R.id.edtEmail_recuperar);

        dialogBuilder.setTitle("Recuperando tu contraseña");
        dialogBuilder.setMessage("Ingresar tu email aqui");
        dialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    public void registarUsuario()
    {
        Intent goRegistar = new Intent(LoginScreen.this, RegistrarScreen.class);
        goRegistar.putExtra("telefono",usernameInput);
        startActivity(goRegistar);
    }

    public void principalScreen()
    {
        Intent goPrincipalScreen = new Intent(LoginScreen.this, PrincipalScreen.class);
        startActivity(goPrincipalScreen);
    }

    // Callback handler for the sign-in process
    AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            principalScreen();
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String UserId) {
            // The API needs user sign-in credentials to continue
            AuthenticationDetails authenticationDetails = new AuthenticationDetails(UserId, userPasswd, null);

            // Pass the user sign-in credentials to the continuation
            authenticationContinuation.setAuthenticationDetails(authenticationDetails);

            // Allow the sign-in to continue
            authenticationContinuation.continueTask();
        }

        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

        }

        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {

        }

        @Override
        public void onFailure(Exception exception) {
            registarUsuario();
        }
    };

    //Crear un AlertDialog
    public void mostrarDialogo(String titulo, String descripcion)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(titulo)
                .setMessage(descripcion)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialog.show();
    }

    public  int[] arreglo_tres(int[] arreglo_uno, int[]  arreglo_dos)
    {

        int tamañoArregloUno = arreglo_uno.length;
        int tamañoArregloDos = arreglo_dos.length;

        int tamañoTotal = tamañoArregloUno+tamañoArregloDos;

        int arregloTres[] = new int[tamañoTotal];

        int numero_uno =0;
        int numero_dos =0;
        int numero1=0;
        while (numero1 < arregloTres.length)
        {
            if(numero_uno < tamañoArregloUno)
            {
                arregloTres[numero1]= arreglo_uno[numero_uno];
                numero_uno++;
            }
            else
            {
                if (numero_dos < tamañoArregloDos)
                {
                    arregloTres[numero1] = arreglo_dos[numero_dos];
                    numero_dos ++;
                }
            }
            numero1++;
        }

        int flag=0;
        for(int i=1;i<arregloTres.length;i++)
        {
            for(int j=0;j<arregloTres.length-i;j++)
            {
                if(arregloTres[j]>arregloTres[j+1])
                {
                    flag=arregloTres[j];
                    arregloTres[j]=arregloTres[j+1];
                    arregloTres[j+1]=flag;
                }
            }
        }
        return arregloTres;
    }


}
