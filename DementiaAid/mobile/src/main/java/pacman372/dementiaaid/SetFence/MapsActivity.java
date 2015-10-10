package pacman372.dementiaaid.SetFence;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

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
import java.util.ArrayList;
import java.util.List;

import pacman372.dementiaaid.EntityClasses.Carer;
import pacman372.dementiaaid.EntityClasses.Fence;
import pacman372.dementiaaid.EntityClasses.IJsonStreamable;
import pacman372.dementiaaid.EntityClasses.Location;
import pacman372.dementiaaid.EntityClasses.PolygonalFence;
import pacman372.dementiaaid.Login.carerVM;
import pacman372.dementiaaid.R;
import pacman372.dementiaaid.TapMainActivity;


public class MapsActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    //private MobileServiceClient mClient;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private FenceView viewModel;
    private Carer carer;
    private SeekBar radiusSlider;
    private RadioButton circularRadioButton;
    private RadioButton polygonRadioButton;
    private RadioGroup fenceRadioGroup;
    private AlertDialog.Builder alertDialog;
    private CircularFence currentFence;
    private PolygonalFence pFence;
    private pacman372.dementiaaid.EntityClasses.Location newCenter;
    private String Location = null;
    private final static String locationURL="http://pacmandementiaaid.azurewebsites.net/api/Location";
    private final static String fenceURL="http://pacmandementiaaid.azurewebsites.net/api/Fence";
    private int IDCarer=-1;
    private int IDLocation=-1;
    private double latestX=-1;
    private double latestY=-1;
    private int latestRadius=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        viewModel = new FenceView();
        currentFence = new CircularFence();
        pFence = new PolygonalFence();
        newCenter = new Location();
        SharedPreferences userDetails = getSharedPreferences(getString(R.string.sharedPreferences),0);
        carer = new Carer();
        carer.setID(Integer.parseInt(userDetails.getString("userID", "0")));
        //Pushbots.sharedInstance().init(this);
        viewModel = new FenceView();

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


        fenceRadioGroup = (RadioGroup) findViewById(R.id.fenceRadioGroup);
        circularRadioButton = (RadioButton)findViewById(R.id.circularRadioButton);
        polygonRadioButton = (RadioButton) findViewById(R.id. polygonRadioButton);
        fenceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                syncFromModel();
            }
        });

        syncFromModel();

        //Pushbots.sharedInstance().regID();//device register id
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        //add menu
        MenuItem doneAction = menu.add(0, 0, 0, "DONE");

        //MenuItem del=menu.add(0,0,0,"del");
        //MenuItem save=menu.add(0,0,0,"save");
        //bind to ActionBar
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
                if (circularRadioButton.isChecked()) {
                    viewModel.mapClicked(latLng);
                }
                else if (polygonRadioButton.isChecked()) {
                    pFence.getPoints().add(latLng);
                }
                syncFromModel();
            }
        });
    }

    private void syncFromModel() {
        mMap.clear();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-27.4667, 153.0333)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        if (circularRadioButton.isChecked()){
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
                radiusSlider.setProgress(viewModel.fence.getRadius());
            }
            if ((null != currentFence) && (null != viewModel.fence)) {
                currentFence.setRadius(viewModel.fence.getRadius());
                currentFence.setCenter(viewModel.fence.getCenter());
            }
        }
        else if (polygonRadioButton.isChecked()){
            if (pFence != null && pFence.getPoints().size() > 0){

                List<LatLng> fencePoints = pFence.getPoints();
                for (LatLng point:fencePoints) {
                    mMap.addMarker(new MarkerOptions().position(point));
                }
                //line = new PolylineOptions();
                //line.addAll(fencePoints).width(5).color(Color.RED);
                //mMap.addPolyline(line);
                PolygonOptions po = new PolygonOptions().addAll(fencePoints).strokeWidth(3).strokeColor(Color.RED);
                 mMap.addPolygon(po);
            }

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
            new DownloadWebpageTask().execute(locationURL);
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

                //Set up the Location to be sent
                Location location = new Location();
                location.setId_Patient(121);
                location.setId_Carer(carer.getID());
                location.setCoordinateX(currentFence.getCoordinateX());
                latestX=currentFence.getCoordinateX();
                location.setCoordinateY(currentFence.getCoordinateY());
                latestY=currentFence.getCoordinateY();
                IDLocation = Integer.parseInt(downloadUrl(locationURL, location));
                Log.d("Maps Error", "After Location Store "+IDLocation);


                //Setup the Fence to be sent
                if (IDLocation > -1) {
                    Log.d("Maps Error", "In store Fence");
                    Fence fence = new Fence();
                    fence.setDescription("asd");
                    fence.setId_carer(carer.getID());
                    fence.setId_location(IDLocation);
                    fence.setRadius(currentFence.getRadius());
                    latestRadius = currentFence.getRadius();
                    fence.setId_patient(121);
                    downloadUrl(fenceURL, fence);

                }
                Log.d("Maps Error", "After Fence Store");
                //alertDialog.setMessage("success");

                return "success";


            } catch (IOException e) {
                Log.d("Maps Error","Error after call",e);
                return "Error";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        public void onPostExecute(String result) {
            Intent intent=new Intent(MapsActivity.this, TapMainActivity.class);
            intent.putExtra("x", latestX);
            intent.putExtra("y", latestY);
            intent.putExtra("radius",latestRadius);
            startActivity(intent);
            TextView textView=(TextView)findViewById(R.id.textView3);
            textView.setText(result);
        }

        private String downloadUrl(String myurl, IJsonStreamable toSend) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;

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
                toSend.serializeJson(writer);
                writer.close();

                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                if ((response >= 200) || (response < 300)) {
                    is = conn.getInputStream();
                    // Convert the InputStream into a string
                    String contentAsString = readIt(is, len);
                    JSONObject jsonObject = new JSONObject(contentAsString);
                    int ID = jsonObject.getInt("ID");
                    Log.d("Maps Error", "Returned ID: "+ID);
                    return Integer.toString(ID);
                } else {
                    Log.d("Maps Error", "Failed Web Call: " + response);
                    String weberror = readIt(conn.getErrorStream(),500);
                    Log.d("Maps Error", "Error stream: "+weberror);
                    return "Error";
                }
            } catch (JSONException e) {
                Log.d("Maps Error", "Json error",e);
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




