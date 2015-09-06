package pacman372.dementiaaid;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        /*Intent intent = new Intent(this, MapsActivity.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = "123";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);*/
        if (null != currentFence) {

            new DownloadWebpageTask().execute("http://pacmandementiaaid.azurewebsites.net/api/Location", geturlParameters());
            //Thread.sleep(3000);

               // new  DownloadWebpageTask1().execute("http://pacmandementiaaid.azurewebsites.net/api/Fence", urlpara);

          /* if (Location != null) {
               JSONTokener jsonTokener = new JSONTokener(String.valueOf(Location));
               JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
               String id = jsonObject.getString("ID");
               newCenter.id = id;
               Fence newFence = new Fence();
               newFence.id_Location = newCenter.id;
               newFence.radius = currentFence.getRadius();
               newFence.description = "123";
               newFence.id_carer = "123";
               newFence.id_patient = "123";
               String urlpara = "ID=" + URLEncoder.encode(newCenter.id, "UTF-8") +
                        "&id_Location=" + URLEncoder.encode(newFence.id_Location, "UTF-8") +
                       "&description=" + URLEncoder.encode(newFence.description, "UTF-8") +
                       "&id_Patient=" + URLEncoder.encode(newFence.id_carer, "UTF-8") +
                       "&id_Carer=" + URLEncoder.encode(newFence.id_patient, "UTF-8");
                //creatNewFence(newFence);
               new DownloadWebpageTask().execute("http://pacmandementiaaid.azurewebsites.net/api/Fence", urlpara);

            }*/
        }
        /*alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Done ...");
        alertDialog.setMessage("Call set fence");
        alertDialog.show();*/
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


    private String geturlParameters() throws UnsupportedEncodingException {
        newCenter.coordinateX = currentFence.getCoordinateX();
        String coordinateX = String.valueOf(newCenter.coordinateX);
        newCenter.coordinateY = currentFence.getCoordinateY();
        String coordinateY = String.valueOf(newCenter.coordinateY);
        newCenter.id_Carer = "123";
        newCenter.id_Patient = "123";

        String urlPara =
                //"ID=" + URLEncoder.encode("???", "UTF-8") +
                "coordinates_x=" + URLEncoder.encode(coordinateX, "UTF-8") +
                        "&coordinates_y=" + URLEncoder.encode(coordinateY, "UTF-8") +
                        "&id_Patient=" + URLEncoder.encode(newCenter.id_Carer, "UTF-8") +
                        "&id_Carer=" + URLEncoder.encode(newCenter.id_Patient, "UTF-8");


        return urlPara;
    }


    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                excutePost(urls[0], urls[1]);


            } catch (Exception ex) {
                //printStackTrace();
                return "Unable to retrieve web page. URL may be invalid.";
            } finally {
                return "successfully";
            }
        }
    }


    public void excutePost(String targetURL, String urlParameters) {
        URL url;
        HttpURLConnection connection = null;
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            //connection.setRequestProperty("Content-Type",
            //"application/json");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            connection.setUseCaches(false);
            //Send request
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(postData);
            }
            //int status = connection.getResponseCode();


            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            Location = response.toString();
            connection.disconnect();
            if (Location != null) {
                JSONTokener jsonTokener = new JSONTokener(Location);
                JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
                String id = jsonObject.getString("ID");
                newCenter.id = id;
                Fence newFence = new Fence();
                newFence.id_Location = newCenter.id;
                //newFence.radius = currentFence.getRadius();
                newFence.radius = currentFence.getRadius();
                String value = String.valueOf(newFence.radius);
                newFence.description = "123";
                newFence.id_carer = "123";
                newFence.id_patient = "123";
                String urlpara = "ID=" + URLEncoder.encode(newCenter.id, "UTF-8") +
                        "&id_location=" + URLEncoder.encode(newFence.id_Location, "UTF-8") +
                        "&description=" + URLEncoder.encode(newFence.description, "UTF-8") +
                        "&id_patient=" + URLEncoder.encode(newFence.id_carer, "UTF-8") +
                        "&id_carer=" + URLEncoder.encode(newFence.id_patient, "UTF-8") +
                        "&radius=" + URLEncoder.encode(value, "UTF-8");
                excutePostFence("http://pacmandementiaaid.azurewebsites.net/api/Fence",urlpara);
            }

            // return r;
            // String r=Location;

        } catch (Exception e) {

            e.printStackTrace();


        }
    }



    public void excutePostFence(String targetURL, String urlParameters) {
        URL url;
        HttpURLConnection connection = null;
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            //connection.setRequestProperty("Content-Type",
            //"application/json");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            connection.setUseCaches(false);
            //Send request
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(postData);
            }

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            String T= response.toString();
            if(T!=null) {
                alertDialog.setTitle("success...");
                alertDialog.setMessage("Insert Fence succeeded!");
                alertDialog.show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




