package pacman372.dementiaaid.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
import pacman372.dementiaaid.R;
import pacman372.dementiaaid.TapMainActivity;

public class LoginResult extends AppCompatActivity {

    public final static String NAME_KEY = "pacman372.dementiaaid.LOGINNAME";
    public final static String PASS_KEY = "pacman372.dementiaaid.LOGINPASS";
    public final String ERROR_KEY = "LoginResult: ";
    private int IDCarer=-1;
    public String uName = "";
    public String uPass = "";
    public boolean loggedin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_result);
        uName = getIntent().getStringExtra(NAME_KEY);
        uPass = getIntent().getStringExtra(PASS_KEY);
        makeCall(uName,uPass);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_login_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void makeCall(String username, String pass) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute("http://pacmandementiaaid.azurewebsites.net/api/Login/"+uName+"/"+uPass);
        } else {
            Log.d(ERROR_KEY,"No Network Connection");
        }

    }

    public void loggedIn() {
        SharedPreferences userDetails = getSharedPreferences(getString(R.string.sharedPreferences),0);
        SharedPreferences.Editor editor = userDetails.edit();
        editor.putString("userID",String.valueOf(IDCarer));
        editor.commit();
        Intent intent = new Intent(this, TapMainActivity.class);
        startActivity(intent);
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {



        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                downloadUrl(urls[0], 1);
                 return "success";
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        public void onPostExecute(String result) {
            if (loggedin)
            {
                loggedIn();
            } else {
                TextView textView=(TextView)findViewById(R.id.login_result_text);
                textView.setText("Please go back, Login Failed");
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
}
