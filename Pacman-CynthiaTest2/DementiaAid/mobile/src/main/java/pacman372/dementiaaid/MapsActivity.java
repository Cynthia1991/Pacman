package pacman372.dementiaaid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import com.microsoft.windowsazure.mobileservices.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    private MobileServiceClient mClient;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private FenceView viewModel;
    private SeekBar radiusSlider;
    private AlertDialog.Builder alertDialog;
    private CircularFence currentFence;
    private Location newCenter;
    private String Location = null;
    private final static String locationUrl1="http://pacmandementiaaid.azurewebsites.net/api/Carer";
    private final static String locationUrl2="http://pacmandementiaaid.azurewebsites.net/api/Location";
    private final static String locationUrl3="http://pacmandementiaaid.azurewebsites.net/api/Fence";
    private int IDCarer=-1;
    private int IDLocation=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        viewModel = new FenceView();
        currentFence = new CircularFence();
        newCenter = new Location();
        setUpMapIfNeeded();
        radiusSlider = (SeekBar) findViewById(R.id.radiusSlider);
        radiusSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    viewModel.radiusChanged(progress);
                    syncFromModel();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        syncFromModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        //添加菜单项
        MenuItem doneAction = menu.add(0, 0, 0, "DONE");

        //MenuItem del=menu.add(0,0,0,"del");
        //MenuItem save=menu.add(0,0,0,"save");
        //绑定到ActionBar
        doneAction.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        //del.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        //save.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                viewModel.mapClicked(latLng);
                syncFromModel();
            }
        });
    }

    private void syncFromModel() {
        mMap.clear();
        MarkerOptions markerOptions = viewModel.getCenterOptions();
        if (markerOptions != null) {
            mMap.addMarker(markerOptions);
        }

        CircleOptions circleOptions = viewModel.getPerimeterOptions();
        if (circleOptions != null) {
            mMap.addCircle(circleOptions);
        }

        LatLng cameraLocation = viewModel.getCameraLocation();
        if (!mMap.getProjection().getVisibleRegion().latLngBounds.contains(cameraLocation)) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(cameraLocation));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
        boolean radiusEnabled = viewModel.canChangeRadius();
        radiusSlider.setEnabled(radiusEnabled);
        if (radiusEnabled) {
            radiusSlider.setProgress(viewModel.fence.radius);
        }
        if ((null != currentFence) && (null != viewModel.fence)) {
            currentFence.setRadius(viewModel.fence.radius);
            currentFence.setCenter(viewModel.fence.center);
        }

    }

    /**
     * Called when the user clicks the Send button
     */
    public void DoneSetFence() throws UnsupportedEncodingException, JSONException, InterruptedException {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
          new DownloadWebpageTask().execute(locationUrl1);
        }else {
            alertDialog.setMessage("No network connection available.");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case 0:
                try {
                    DoneSetFence();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            // action with ID action_settings was selected
            case android.R.id.home:
                Intent intent = new Intent(this, TapMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                //Location location=new Location();
                // JSONObject jsonObject=new JSONObject(downloadUrl(urls[0],1));
                // location.id = jsonObject.getString("ID");
                //location.id_Carer=jsonObject.getInt("ID");
                downloadUrl(urls[0], 1);
                downloadUrl(locationUrl2, 2);
                downloadUrl(locationUrl3, 3);
                //alertDialog.setMessage("success");
                return "success";


            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        public void onPostExecute(String result) {
            TextView textView=(TextView)findViewById(R.id.textView3);
            textView.setText(result);
        }



        private String downloadUrl(String myurl, int n) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int s = -1;
            int m = -1;
            Carer carer = new Carer();
            Fence fence = new Fence();
            Location location = new Location();
            int len = 500;
            switch (n) {
                case 1:
                    carer.setAddress("Street");
                    carer.setEmail("a@a.com");
                    carer.setName("NotGeoff");
                    carer.setPhone(786);
                    m = 1;
                    break;
                case 2:
                    //location.setID(ID);
                    location.setId_Patient(121);
                    location.setId_Carer(IDCarer);
                    location.setCoordinateX(currentFence.getCoordinateX());
                    location.setCoordinateY(currentFence.getCoordinateY());
                    m = 2;
                    //location.setId_Carer(carer.getID());
                    break;
                case 3:
                    //fence.setID(ID);
                    fence.setDescription("asd");
                    fence.setId_carer(IDCarer);
                    fence.setId_location(IDLocation);
                    fence.setRadius(currentFence.getRadius());
                    fence.setId_patient(121);
                    m = 3;
                    break;

            }


            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                JsonWriter writer = new JsonWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                switch (m) {
                    case 1:
                        carer.serializeJson(writer);
                        s = 1;
                        break;
                    case 2:
                        location.serializeJson(writer);
                        s = 2;
                        break;
                    case 3:
                        fence.serializeJson(writer);
                        break;
                }
                // carer.serializeJson(writer);
                writer.close();

                // Starts the query
                conn.connect();
                //int response = conn.getResponseCode();
                is = conn.getInputStream();
                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);
                switch (s) {
                    case 1:
                        JSONObject jsonObject = new JSONObject(contentAsString);
                        IDCarer = jsonObject.getInt("ID");

                        break;
                    case 2:
                        JSONObject jsonObject1 = new JSONObject(contentAsString);
                        IDLocation = jsonObject1.getInt("ID");
                        break;


                }

                return contentAsString;

            } catch (JSONException e) {
                return "json error";
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




