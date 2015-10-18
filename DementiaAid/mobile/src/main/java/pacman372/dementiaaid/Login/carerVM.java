package pacman372.dementiaaid.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import pacman372.dementiaaid.EntityClasses.Carer;
import pacman372.dementiaaid.EntityClasses.Location;
import pacman372.dementiaaid.R;
import pacman372.dementiaaid.TapMainActivity;

/**
 * Created by giririsss on 9/10/2015.
 */
public class carerVM {

    private Carer carer = new Carer();
    public final static String NAME_KEY = "pacman372.dementiaaid.LOGINNAME";
    public final static String PASS_KEY = "pacman372.dementiaaid.LOGINPASS";
    public final String ERROR_KEY = "carerVM: ";
    private AlertDialog.Builder alertDialog;
    private int IDCarer=-1;
    private int IDPatient;
    public String uName = "";
    public String uPass = "";
    public String returnedCarerString ="";
    public boolean loggedin = false;
    public boolean pdetails = false;
    private Activity caller;


    public boolean login(Activity caller, String uName, String uPass) {
        this.caller = caller;
        try {
            new DownloadLoginTask().execute("http://pacmandementiaaid.azurewebsites.net/api/Login/" + uName + "/" + uPass).get();
        } catch(Exception e) { }

        if (loggedin) {
            loggedIn();
            new DownloadPatientTask().execute("http://pacmandementiaaid.azurewebsites.net/api/CarerPatient?$filter=id_carer eq "+IDCarer);
            return true;
        } else {
            return false;
        }
    }

    public void loggedIn() {

        //JSONObject jsonObject = new JSONObject(returnedCarerString);
        //carer.setID(jsonObject.getInt("ID"));
        String string = caller.getString(R.string.sharedPreferences);
        SharedPreferences userDetails = caller.getSharedPreferences(string,0);
        SharedPreferences.Editor editor = userDetails.edit();
        //editor.putString("userID",String.valueOf(carer.getID()));
        editor.putString("userID",String.valueOf(IDCarer));
        editor.commit();
    }

    public void patientID() {
        SharedPreferences userDetails = caller.getSharedPreferences(caller.getString(R.string.sharedPreferences),0);
        SharedPreferences.Editor editor = userDetails.edit();
        editor.putString("patientID",String.valueOf(IDPatient));
        editor.commit();
    }

    private class DownloadLoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0], 1);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        public void onPostExecute(String result) {
            if (loggedin)
            {
                returnedCarerString = result;
            } else {
                //TextView textView=(TextView)findViewById(R.id.login_result_text);
                //textView.setText("Please go back, Login Failed");
            }
        }



        private String downloadUrl(String myurl, int n) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            Location location = new Location();
            int len = 500;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setDoOutput(false);
                conn.setDoInput(true);
                conn.setRequestMethod("GET");

                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                is = conn.getInputStream();
                String contentAsString = readIt(is, len);
                if (response == 200)
                {
                    // Convert the InputStream into a string
                    JSONObject jsonObject = new JSONObject(contentAsString);
                    IDCarer = jsonObject.getInt("ID");
                    loggedin = true;
                } else {
                    Log.d(ERROR_KEY, "Failed Web Call");
                }
                return contentAsString;
            } catch (JSONException e) {
                Log.d(ERROR_KEY, "Json Error", e);
                return "Error";
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
    private class DownloadPatientTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0], 1);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        public void onPostExecute(String result) {
            patientID();
        }



        private String downloadUrl(String myurl, int n) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            Location location = new Location();
            int len = 500;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setDoOutput(false);
                conn.setDoInput(true);
                conn.setRequestMethod("GET");

                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                is = conn.getInputStream();
                String contentAsString = readIt(is, len);
                if (response == 200)
                {
                    // Convert the InputStream into a string
                    JSONObject jsonObject = new JSONObject(contentAsString);
                    IDPatient = jsonObject.getInt("id_patient");
                    pdetails = true;
                } else {
                    Log.d(ERROR_KEY, "Failed Web Call");
                }
                return contentAsString;
            } catch (JSONException e) {
                Log.d(ERROR_KEY, "Json Error", e);
                return "Error";
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

}
