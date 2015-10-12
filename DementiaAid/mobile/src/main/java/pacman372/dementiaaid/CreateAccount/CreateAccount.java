package pacman372.dementiaaid.CreateAccount;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

// Include the following imports to use table APIs
import com.google.gson.stream.JsonWriter;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.table.*;
import com.microsoft.azure.storage.table.TableQuery.*;
import com.pushbots.push.Pushbots;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import pacman372.dementiaaid.Login.LoginActivity;
import pacman372.dementiaaid.MyApplication;
import pacman372.dementiaaid.R;

/**
 * Created by jieliang on 8/10/2015.
 */
public class CreateAccount extends AsyncTask<String,Boolean,Boolean>
{
    CreateAccountView createAccountView;
    Context context;
    // Define the connection-string with your values.
    static  Integer count=100;

public CreateAccount()
{



    }


    @Override
    protected void onPostExecute(Boolean res)
    {

        super.onPostExecute(res);
        if(res)
        {
            Intent intent=new Intent(MyApplication.getAppContext(),LoginActivity.class);
            MyApplication.getAppContext().startActivity(intent);
            count++;
            }
     else {

          }

    }


    @Override
    protected Boolean doInBackground(String...input) {


        HttpURLConnection urlConnection;
        String url;

        String result = null;
        try {
            //Connect
            urlConnection = (HttpURLConnection) ((new URL(input[0]).openConnection()));
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            //urlConnection.setConnectTimeout(5000);
           // urlConnection.setReadTimeout(5000);
            urlConnection.connect();

            //Write
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
           // StringWriter stringWriter=new StringWriter();
            JsonWriter jsonWriter=new JsonWriter(writer);
            jsonWriter.beginObject();
            jsonWriter.name("ID").value(count.toString());
            jsonWriter.name("name").value(input[1]);
            jsonWriter.name("phone").value(input[2]);
            jsonWriter.name("email").value("whatever");
            jsonWriter.name("email").value("whatever");
            jsonWriter.name("address").value("wtf");
            jsonWriter.name("device_id").value(Pushbots.sharedInstance().getSenderId());
            jsonWriter.endObject();
            //stringWriter.close();
            //writer.write(.);
            //writer.close();
            jsonWriter.close();
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

            return  true;


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return  false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
 /*
    public static String makeRequest(String uri, String json) {
        HttpURLConnection urlConnection;
        String url;
        String data = json;
        String result = null;
        try {
            //Connect
            urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            //Write
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
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

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

*/
    public Void Create(String password, String username) {
        return null;
    }
}

