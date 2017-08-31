package com.misael.tesis.frequency.app.gpsfrequency;

/**
 * Created by Misael on 29-nov-16.
 */

public class URLs {

    //public String server = "http://192.168.56.1:80/tesis/webservice/";
    //public String imgServer = "http://192.168.56.1:80/tesis/imagen/";
    public String server = "http://frequency.hol.es/webservice/";
    public String imgServer = "http://frequency.hol.es:80/imagen/";

    public String urlValida(String idPaciente, String pass){
        String url = server+"valida_paciente.php?id="+idPaciente+"&pas="+pass;
        return url;
    }

    public String urlAlerta(){
        String url = server+"notifi_alerta.php";
        return url;
    }

}
