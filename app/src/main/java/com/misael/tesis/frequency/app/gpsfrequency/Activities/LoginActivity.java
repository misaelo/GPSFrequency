package com.misael.tesis.frequency.app.gpsfrequency.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.misael.tesis.frequency.app.gpsfrequency.R;

public class LoginActivity extends AppCompatActivity {
    ConecLogin conecLogin = new ConecLogin();
    public EditText EdtUsuario, EdtPass;
    public Button BtnIngresar;
    public static final String MyPREFERENCES = "Login" ;
    public static final String Pass = "pass";
    public static final String User = "paciente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EdtUsuario = (EditText) findViewById(R.id.usuario);
        EdtPass = (EditText) findViewById(R.id.pass);
        BtnIngresar = (Button) findViewById(R.id.ingresar);



        BtnIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (EdtPass.getText().toString().isEmpty() && EdtUsuario.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Debe ingresar un usuario y contraseña", Toast.LENGTH_SHORT).show();
                }else {
                    //Toast.makeText(getApplicationContext(), "llama al metodo", Toast.LENGTH_SHORT).show();
                    myClickHandler();
                    //boton();
                }
            }
        });

    }

    public void myClickHandler() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            boton();
        } else {
            Toast.makeText(getApplicationContext(), "Sin Conexion", Toast.LENGTH_LONG).show();
            //this.recreate();
        }
    }
    public void boton(){
        final SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String paciente = EdtUsuario.getText().toString();
                final String pas= EdtPass.getText().toString();
                final String resultado = conecLogin.EnviarDatos(paciente, pas );

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = conecLogin.Valida(resultado);
                        if (r > 0) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(User, paciente);
                            editor.putString(Pass, pas);
                            editor.apply();

                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Usuario o Contraseña Incorrectos", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).start();
    }
}
