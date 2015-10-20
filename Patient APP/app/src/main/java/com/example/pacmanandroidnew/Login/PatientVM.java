package com.example.pacmanandroidnew.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.pacmanandroidnew.ShareLocation.Location;
import com.example.pacmanandroidnew.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lichuan on 19/10/2015.
 */
public class PatientVM {


    //private Carer carer = new Carer();
    //private Patient patient=new Patient();
    //public final static String NAME_KEY = "pacman372.dementiaaid.LOGINNAME";
    //public final static String PASS_KEY = "pacman372.dementiaaid.LOGINPASS";
    public final String ERROR_KEY = "patientsVM: ";
    private AlertDialog.Builder alertDialog;
    private int IDPatient=-1;
    private int IDCarer=-1;
    //private int IDPatient;
    //public String uName = "";
    //public String uPass = "";
    public String returnedCarerString ="";
    public boolean loggedin = false;
    public boolean pdetails = false;
    private Activity caller;


    public boolean login(Activity caller, String uName, String uPass) {
        this.caller = caller;
        try {
            //new DownloadLoginTask().execute("http://pacmandementiaaid.azurewebsites.net/api/Login/" + uName + "/" + uPass).get();
            new DownloadLoginTask().execute("http://pacmandementiaaid.azurewebsites.net/api/LoginPatient/" + uName + "/" + uPass).get();
        } catch(Exception e) { }

        if (loggedin) {
            loggedIn();
            //Log.d(ERROR_KEY, " CarerPatientURL: " + "http://pacmandementiaaid.azurewebsites.net/api/CarerPatient?$filter=id_patient%20eq%20" + IDPatient);
            new DownloadPatientTask().execute("http://pacmandementiaaid.azurewebsites.net/api/CarerPatient?$filter=id_patient%20eq%20"+IDPatient);
            return true;
        } else {
            return false;
        }
    }

    public void loggedIn() {

        String string = caller.getString(R.string.sharedPreferences);
        SharedPreferences userDetails = caller.getSharedPreferences(string, 2);
        SharedPreferences.Editor editor = userDetails.edit();
        //editor.putString("userID",String.valueOf(carer.getID()));
        editor.putString("id_patient", String.valueOf(IDPatient));
        editor.commit();
        //Log.d(ERROR_KEY,"shared pref: "+userDetails.getString("userID","-1"));
    }

//    public void patientID() {
//        SharedPreferences userDetails = caller.getSharedPreferences(caller.getString(R.string.sharedPreferences), 2);
//        SharedPreferences.Editor editor = userDetails.edit();
//        editor.putString("patientID",String.valueOf(IDPatient));
//        editor.commit();
//        Log.d(ERROR_KEY, "PID " + userDetails.getString("patientID", "-1"));
//    }
//SharedPreferences userDetails = getSharedPreferences(getString(R.string.sharedPreferences), 0);
//    String cIDString = userDetails.getString("id_carer","-1");
//    id_Carer = Integer.parseInt(cIDString);
//    String pIDString = userDetails.getString("id_patient", "-1");
//    id_patient = Integer.parseInt(pIDString);
    public void careID() {
        SharedPreferences userDetails = caller.getSharedPreferences(caller.getString(R.string.sharedPreferences),2);
        SharedPreferences.Editor editor = userDetails.edit();
        editor.putString("id_carer", String.valueOf(IDCarer));
        editor.commit();
        //Log.d(ERROR_KEY, "PID "+ userDetails.getString("patientID","-1"));
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

                    IDPatient = jsonObject.getInt("ID");
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
            careID();
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
                    JSONArray jArray = new JSONArray(contentAsString);
                    JSONObject jsonObject = jArray.getJSONObject(0);
                    IDCarer = jsonObject.getInt("id_carer");
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
