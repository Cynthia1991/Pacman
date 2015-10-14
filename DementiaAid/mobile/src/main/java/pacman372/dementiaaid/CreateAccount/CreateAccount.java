package pacman372.dementiaaid.CreateAccount;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonWriter;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pushbots.push.Pushbots;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import pacman372.dementiaaid.Login.LoginActivity;
import pacman372.dementiaaid.MyApplication;
import pacman372.dementiaaid.TapMainActivity;

/**
 * Created by jieliang on 8/10/2015.
 */


class Event {
    private String name;
    private Integer phone;
    private String device_id;
    //private String iid;
    public Event(String username, Integer password)
    {
        this.name = username;
        this.phone = password;
        //this.device_id=deviceid;

    }
}
public class CreateAccount extends AsyncTask<String,Void,Boolean>
{
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if(aBoolean)
        {
            Intent intent=new Intent(MyApplication.getAppContext(), LoginActivity.class);
            MyApplication.getAppContext().startActivity(intent);

        }
    }

    public CreateAccount()
{




    }

    @Override
    protected Boolean doInBackground(String... inputs) {

        HttpURLConnection urlConnection;
        String url=inputs[0];
        String data ;
        String result = null;
        Collection ser=new ArrayList();
        ser.add(new Event(inputs[1],Integer.parseInt(inputs[2])));
        Gson gson=new Gson();
        data= gson.toJson(ser);



        try {
            //Connect
            urlConnection = (HttpURLConnection) ((new URL(url).openConnection()));
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            //Write
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            //JsonWriter jw= new JsonWriter(writer);
            Log.d("json",data);
            writer.write(data);
            writer.close();
            outputStream.close();

            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            result = sb.toString();

            return true;
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
