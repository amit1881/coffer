package com.asmobisoft.coffer.webservices;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by root on 1/17/17.
 */

public class CallPostService {
    private String urlString;
    private String requestParms;
    private Context mContext;

    public CallPostService(Context mContext) {
        this.mContext = mContext;
       // this.requestParms = requestParms;
    }

    public String getJsonResponce(String urlString,String requestParms) throws IOException {

        URL url = new URL(urlString);
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setDoInput(true);
        httpUrlConnection.setRequestMethod("POST");
        httpUrlConnection.setRequestProperty("Content-Type", "application/json");
        httpUrlConnection.setUseCaches (false);

        DataOutputStream outputStream = new DataOutputStream(httpUrlConnection.getOutputStream());
       // what should I write here to output stream to post params to server ?
        outputStream.writeBytes(requestParms);
        outputStream.flush();
        outputStream.close();

        // get response
        InputStream responseStream = new BufferedInputStream(httpUrlConnection.getInputStream());
        BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = responseStreamReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        responseStreamReader.close();

        String response = stringBuilder.toString();

        if(response !=null){

            return response;
        }else{
            return "";
        }
    }


}
