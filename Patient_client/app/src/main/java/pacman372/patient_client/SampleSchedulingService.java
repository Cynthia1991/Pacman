package pacman372.patient_client;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by chuan on 21/09/2015.
 */
public class SampleSchedulingService extends IntentService implements
        ConnectionCallbacks, OnConnectionFailedListener{
   UploadInfo upload=new UploadInfo();
   private Thread newThread;
    private final static String locationUrl1="http://pacmandementiaaid.azurewebsites.net/api/Location";
    public SampleSchedulingService() {
        super("SchedulingService");
    }

    //public static final String TAG = "Scheduling Demo";
    // An ID used to post the notification.
    public static final int NOTIFICATION_ID = 1;
    // The string the app searches for in the Google home page content. If the app finds
    // the string, it indicates the presence of a doodle.
    public static final String SEARCH_STRING = "doodle";
    // The Google home page URL from which the app fetches content.
    // You can find a list of other Google domains with possible doodles here:
    // http://en.wikipedia.org/wiki/List_of_Google_domains
    public static final String URL = "http://www.google.com";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public double x,y;

    protected static final String TAG = "basic-location-sample";
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    @Override
    protected void onHandleIntent(Intent intent) {
        // BEGIN_INCLUDE(service_onhandle)
        // The URL from which to fetch content.
        String urlString = URL;

        String result ="";
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        if(mGoogleApiClient.isConnected()){
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {


                    x = mLastLocation.getLatitude();
                    y = mLastLocation.getLongitude();

                sendNotification("location: "+x+" , "+ y);



                // new DownloadWebpageTask().execute(locationUrl1);
                //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));



            } else {
                Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
            }
        }
        else{
            mGoogleApiClient.connect();
        }


        // Try to connect to the Google homepage and download content.
        /*try {
            result = loadFromNetwork(urlString);
        } catch (IOException e) {
            Log.i(TAG, getString(R.string.connection_error));
        }
    */
        // If the app finds the string "doodle" in the Google home page content, it
        // indicates the presence of a doodle. Post a "Doodle Alert" notification.
        /*if (result.indexOf(SEARCH_STRING) != -1) {
            //sendNotification(getString(R.string.doodle_found));
            sendNotification("location: "+x+y);
            Log.i(TAG, "Found doodle!!");
        } else {
            //sendNotification(getString(R.string.no_doodle));
            sendNotification("location: "+x+y);
            Log.i(TAG, "No doodle found. :-(");
        }*/
        // Release the wake lock provided by the BroadcastReceiver.
        SampleAlarmReceiver.completeWakefulIntent(intent);
        // END_INCLUDE(service_onhandle)
    }


    // Post a notification indicating whether a doodle was found.
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.doodle_alert))
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

//
// The methods below this line fetch content from the specified URL and return the
// content as a string.
//
    /** Given a URL string, initiate a fetch operation. */
    private String loadFromNetwork(String urlString) throws IOException {
        InputStream stream = null;
        String str ="";

        try {
            stream = downloadUrl(urlString);
            str = readIt(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return str;
    }

    /**
     * Given a string representation of a URL, sets up a connection and gets
     * an input stream.
     * @param urlString A string representation of a URL.
     * @return An InputStream retrieved from a successful HttpURLConnection.
     * @throws IOException
     */
    private InputStream downloadUrl(String urlString) throws IOException {

        java.net.URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Start the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }

    /**
     * Reads an InputStream and converts it to a String.
     * @param stream InputStream containing HTML from www.google.com.
     * @return String version of InputStream.
     * @throws IOException
     */
    private String readIt(InputStream stream) throws IOException {

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        for(String line = reader.readLine(); line != null; line = reader.readLine())
            builder.append(line);
        reader.close();
        return builder.toString();
    }
    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            x = mLastLocation.getLatitude();
            y = mLastLocation.getLongitude();
            sendNotification("location: "+x+" , "+ y);
            new Thread() {
                @Override
                public void run() {
                    //a new thread to send patient location
                    try {
                        upload.uploadInformation(locationUrl1,x,y);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        } else {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect(); }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
// Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }


}
