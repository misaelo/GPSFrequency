package com.misael.tesis.frequency.app.gpsfrequency.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.misael.tesis.frequency.app.gpsfrequency.R;

public class LoadActivity extends AppCompatActivity {
    ConecLogin conecLogin = new ConecLogin();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        myClickHandler(this.getApplicationContext());
    }

    public void myClickHandler(Context view) {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            conec();
        } else {
            Toast.makeText(getApplicationContext(),"Sin Conexion",Toast.LENGTH_LONG).show();
            //this.recreate();
        }
    }

    public void conec(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                final String User = sharedPreferences.getString("paciente", null);
                final String Pass = sharedPreferences.getString("pass", null);

                if (User == null && Pass == null){
                    //Toast.makeText(getApplicationContext(),"no hay session",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final String resultado = conecLogin.EnviarDatos(User, Pass );
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int r = conecLogin.Valida(resultado);
                                    if (r > 0) {
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        //Toast.makeText(getApplicationContext(), "Usuario o Contraseña Incorrectos", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });
                        }
                    }).start();
                }
            }
        }, 3000);
    }

}
