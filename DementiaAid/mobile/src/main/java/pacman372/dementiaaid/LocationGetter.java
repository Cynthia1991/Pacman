package pacman372.dementiaaid;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.JsonWriter;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import pacman372.dementiaaid.EntityClasses.Location;

/**
 * Created by fuqian on 19/10/2015.
 */
public class LocationGetter {
    private final String ERROR_KEY = "LocationGetter";

    public int ID=-1;
    public double coordinates_x=-1;
    public double coordinates_y=-1;
    public int id_Patient;
    public int id_Carer;
    public Activity view;
    //public Carer jsonCarer;
    public Location jsonLocation;
/*Constructor about creat a new location getter for getting last patient location*/
    public LocationGetter(Activity caller) {
        view = caller;
        try {
            new DownloadDetailsTask().execute("http://pacmandementiaaid.azurewebsites.net/api/Location/LastPatientLocation/1").get();
        } catch(Exception e) {
            Log.d(ERROR_KEY, "in Constructor", e);
        }
    }

/*Location getter connection class */
    private class DownloadDetailsTask extends AsyncTask<String, Void, String> {

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
                    ID =jsonObject.getInt("ID");
                    ;
                    coordinates_x = jsonObject.getDouble("coordinates_x");
                    ;
                    coordinates_y = jsonObject.getDouble("coordinates_y");
                    ;
                    id_Patient = jsonObject.getInt("id_patient");
                    ;
                    id_Carer = jsonObject.getInt("id_carer");
                    ;

                    //Log.d(ERROR_KEY, "Name:"+carerName+" Phone:"+carerPhone);
                } else {
                    //Log.d(ERROR_KEY, "Failed Web Call");
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
