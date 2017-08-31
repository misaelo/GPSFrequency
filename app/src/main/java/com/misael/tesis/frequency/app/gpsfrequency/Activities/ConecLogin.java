package com.misael.tesis.frequency.app.gpsfrequency.Activities;

import android.content.Context;

import com.misael.tesis.frequency.app.gpsfrequency.URLs;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Misael on 07-nov-16.
 */

public class ConecLogin {

    URLs urls = new URLs();
    public Context context;

    public String EnviarDatos(String usu, String pas){
        URL url = null;
        String linea ="";
        int respuesta = 0;
        StringBuilder result = null ;

        try {
            url = new URL(urls.urlValida(usu,pas));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            respuesta = connection.getResponseCode();

            result = new StringBuilder() ;

            if (respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while ((linea = reader.readLine()) != null){
                    result.append(linea);
                }
            }
        }catch (Exception e){
            //Toast.makeText(context, "problemas al cargar", Toast.LENGTH_LONG).show();
        }

        return result.toString();
    }

    public int Valida(String response){
        int res=0;
        try {
            JSONArray jsonArray = new JSONArray(response);
            if (jsonArray.length() > 0){
                res=1;
            }
        }catch (Exception e){}
        return res;
    }
}
