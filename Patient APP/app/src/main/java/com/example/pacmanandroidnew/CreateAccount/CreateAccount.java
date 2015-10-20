package com.example.pacmanandroidnew.CreateAccount;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lichuan on 20/10/2015.
 */
class Patient {
    private int ID;
    private String name;
    private String phone;
    private String device_id;


    //private String iid;
    public Patient(String username, String password,String device_id)
    {
        this.name = username;
        this.phone = password;
        this.device_id=device_id;

    }
}
public class CreateAccount extends AsyncTask<String,Void,Boolean>
{
    Context context;
    @Override
    protected Boolean doInBackground(String... inputs) {



        HttpURLConnection urlConnection;
        String url=inputs[0];
        String data ;
        String result = null;
        Collection ser=new ArrayList();

        // ser.add();

        //ser.add(new Event(inputs[1],Integer.parseInt(inputs[2])));

        Gson gson=new Gson();
        data= gson.toJson(new Patient(inputs[1],inputs[2],inputs[3]));



        try {
            //Connect
            urlConnection = (HttpURLConnection) ((new URL(url).openConnection()));
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            //urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);


            //Write
            //OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            //JsonWriter jw= new JsonWriter(writer);
            Log.d("json", data);
            writer.write(data);
            writer.flush();
            writer.close();
            //outputStream.close();
            Log.d("CreateAccount",urlConnection.toString());
            urlConnection.connect();

            //Read
            Log.d("CreateAccount","Response Code: "+urlConnection.getResponseCode());
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                String line = null;
                StringBuilder sb = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                result = sb.toString();
                return true;
            } else {
                Log.d("CreateAccount", "Response Message: "+ urlConnection.getResponseMessage());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream(), "UTF-8"));
                String line = null;
                StringBuilder sb = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                Log.d("CreateAccount","Error Stream: "+sb.toString());
                return false;
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }





    public Void Create(String password, String username) {
        return null;
    }
}

