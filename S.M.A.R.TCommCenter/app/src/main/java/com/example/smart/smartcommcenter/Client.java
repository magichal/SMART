package com.example.smart.smartcommcenter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import android.os.AsyncTask;
import android.widget.TextView;

/**
 * Created by Antoine on 26/04/2016.
 */
public class Client extends AsyncTask<Void, Void, Void>  {
    String dstAdress;
    int dstPort;
    String reponse;
    TextView textResponse;

    Client(String addr, int port, TextView textResponse) {

        dstAdress = addr;
        dstPort = port;
        String reponse = "";
        this.textResponse = textResponse;

    }


    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;

        try{

            socket = new Socket(dstAdress, dstPort);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                reponse += byteArrayOutputStream.toString("UTF-8");
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
            reponse = "UnknownHostException : " + e.toString();
        } catch (IOException e){
            e.printStackTrace();
            reponse = "IOException "+ e.toString();
        } finally {
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
