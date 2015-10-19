package com.example.pacmanandroidnew;

import android.util.JsonWriter;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by lichuan on 11/10/2015.
 */


public class UploadInfo {
    public String uploadInformation(String myurl, double x, double y) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        Gson gson=new Gson();

        Location location=new Location();
        int len = 500;
        //location.setID(10001);
        location.setId_Patient(8);
        location.setCoordinateX(x);
        location.setCoordinateY(y);
        String data= gson.toJson(location);
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) ((new URL(myurl).openConnection()));
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            //JsonWriter writer = new JsonWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            //Log.d("json",);
            //location.serializeJson(writer);
            //Log.d("json", writer.toString());
            //writer.close();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            //JsonWriter jw= new JsonWriter(writer);
            Log.d("json", data);
            writer.write(data);
            writer.flush();
            writer.close();
            //outputStream.close();
            Log.d("upload_location",conn.toString());
            conn.connect();

            // Starts the query
            //conn.connect();
          int response = conn.getResponseCode();
           String message = conn.getResponseMessage();
            BufferedReader re=new BufferedReader(new InputStreamReader(conn.getErrorStream(),"UTF-8"));
            String line=null;
            StringBuilder sb=new StringBuilder();
            while ((line = re.readLine()) != null) {
                sb.append(line);
            }

            re.close();
            String result = sb.toString();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

        } finally {
            if (is != null) {
                is.close();
            }
        }


    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;

        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
