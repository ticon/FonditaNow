package bethinktech.mx.pantallas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bethinktech.mx.R;

public class RegistrarScreen extends AppCompatActivity {

    CognitoUserAttributes atributoUsuario;
    String numeroTelefonico;

    EditText edtCel_registro, edtContraseña_registro, edtEmail_registro;
    EditText edtNombreRegistro, edtApellidoRegistro, edtDireccionRegistro;
    Button bttnFondero, bttnComensal, bttnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_screen);
        atributoUsuario = new CognitoUserAttributes();

        edtCel_registro = (EditText)findViewById(R.id.edtCel_registro);
        edtContraseña_registro = (EditText)findViewById(R.id.edtContraseña_registro);
        edtEmail_registro = (EditText)findViewById(R.id.edtEmail_registro);
        edtNombreRegistro = (EditText)findViewById(R.id.edtNombreRegistro);
        edtApellidoRegistro = (EditText)findViewById(R.id.edtApellidoRegistro);
        edtDireccionRegistro = (EditText)findViewById(R.id.edtDireccionRegistro);

        bttnFondero = (Button)findViewById(R.id.bttnFondero);
        bttnComensal = (Button)findViewById(R.id.bttnComensal);
        bttnRegistrar = (Button)findViewById(R.id.bttnRegistrar);

        Bundle extra = getIntent().getExtras();
        if(extra != null)
        {
            if(extra.containsKey("telefono"))
            {
                numeroTelefonico = extra.getString("telefono");
                edtCel_registro.setText(numeroTelefonico);
            }
        }
    }

    public void onClickRegistraNuevo(View v)
    {
        final String email = edtEmail_registro.getText().toString();
        final String pass = edtContraseña_registro.getText().toString();

        if (!isValidEmail(email)) {
            edtEmail_registro.setError("Invalid Email");
        }
        else
        {
            if (!isValidPassword(pass)) {
                edtContraseña_registro.setError("Invalid Password");
            }
            else
            {
                atributoUsuario.addAttribute("phone_number",numeroTelefonico);
                atributoUsuario.addAttribute("email",email);
            }
        }





        Intent goCodigoScreen = new Intent(RegistrarScreen.this, CodigoScreen.class);
        startActivity(goCodigoScreen);
    }

    //Metodos para confirmar si fue registrato el usuario
    SignUpHandler confirmarUsuario = new SignUpHandler()
    {
        @Override
        public void onSuccess(CognitoUser user, boolean signUpConfirmationState, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
            if(signUpConfirmationState)
            {
                //mostrarDialogo("Usuario "+numeroTelefonico.substring(1), "Ha sido confirmado!!");
            }
            else
            {
                Intent irPantallaVerificacion = new Intent(RegistrarScreen.this, CodigoScreen.class);
                irPantallaVerificacion.putExtra("source","signup");
                irPantallaVerificacion.putExtra("telefono",numeroTelefonico);
                irPantallaVerificacion.putExtra("destination", cognitoUserCodeDeliveryDetails.getDestination());
                irPantallaVerificacion.putExtra("deliveryMed", cognitoUserCodeDeliveryDetails.getDeliveryMedium());
                irPantallaVerificacion.putExtra("attribute", cognitoUserCodeDeliveryDetails.getAttributeName());
                startActivityForResult(irPantallaVerificacion,10);
            }
        }

        @Override
        public void onFailure(Exception exception) {
            String leerError = exception.getMessage();
            if(leerError.contains("User already exists"))
            {
                //ConexionAmazon.getPool().getUser(numeroTelefonico.substring(1)).resendConfirmationCodeInBackground(resendConfCodeHandler);
            }
            else
            {
                //mostrarDialogo("Sign up failed", ConexionAmazon.formatException(exception));
            }
        }
    };


    // validating email id
    private boolean isValidEmail(String email)
    {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 8) {
            return true;
        }
        return false;
    }

}
