package bethinktech.mx.pantallas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import bethinktech.mx.R;

public class CodigoScreen extends AppCompatActivity {

    EditText edit_uno, edit_dos, edit_tres, edit_cuatro, edit_cinco, edit_seis;
    EditText edit_uno_email, edit_dos_email, edit_tres_email, edit_cuatro_email, edit_cinco_email, edit_seis_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_screen);



        /*edit_uno = (EditText) findViewById(R.id.edit_uno);
        edit_dos = (EditText) findViewById(R.id.edit_dos);
        edit_tres = (EditText) findViewById(R.id.edit_tres);
        edit_cuatro = (EditText) findViewById(R.id.edit_cuatro);
        edit_cinco = (EditText) findViewById(R.id.edit_cinco);
        edit_seis = (EditText) findViewById(R.id.edit_seis);


        edit_uno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edit_dos.requestFocus();
            }
        });*/

    }

}
