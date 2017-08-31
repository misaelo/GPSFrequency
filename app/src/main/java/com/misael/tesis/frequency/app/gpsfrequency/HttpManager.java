package com.misael.tesis.frequency.app.gpsfrequency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Misael on 12-nov-16.
 */

public class HttpManager {

    public static String getData(String uri){
        BufferedReader reader = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            StringBuilder stringBuilder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
             return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if (reader != null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    public static String getDataEnvio(RequestPackage requestPackage){
        BufferedReader reader = null;
        String uri = requestPackage.getUri();

        if (requestPackage.getMethod().equals("GET")){
            uri += "?"+requestPackage.getEncodeParams();
        }
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            StringBuilder stringBuilder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if (reader != null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
}
