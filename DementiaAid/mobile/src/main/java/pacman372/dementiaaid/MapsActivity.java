package pacman372.dementiaaid;

import android.app.AlertDialog;
import android.content.Intent;
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
import java.net.MalformedURLException;

public class MapsActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    private MobileServiceClient mClient;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private FenceView viewModel;
    private SeekBar radiusSlider;
    private AlertDialog.Builder alertDialog;
    private CircularFence currentFence;
    private Location newCenter;
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
        radiusSlider = (SeekBar)findViewById(R.id.radiusSlider);
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
        MenuItem doneAction=menu.add(0,0,0,"DONE");

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
        if((null != currentFence)&&(null != viewModel.fence)){
        currentFence.setRadius(viewModel.fence.radius);
        currentFence.setCenter(viewModel.fence.center);
        }

    }
    /** Called when the user clicks the Send button */
    public void DoneSetFence() {
        /*Intent intent = new Intent(this, MapsActivity.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = "123";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);*/
        if(null != currentFence){
            uploadLocation(currentFence.center);

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

                DoneSetFence();
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
    public void uploadLocation(LatLng center){
        try {
            mClient = new MobileServiceClient(
                    "https://pacmanandroid.azure-mobile.net/",
                    "mpVjGDJAoCHcrHsuWkxJGTvjwBDZMk90",
                    this
            );


            newCenter.coordinateX = currentFence.getCoordinateX();
            newCenter.coordinateY = currentFence.getCoordinateY();
            newCenter.id_Carer = "123";
            newCenter.id_Patient = "123";
            alertDialog = new AlertDialog.Builder(this);
            mClient.getTable(Location.class).insert(newCenter, new TableOperationCallback<Location>() {
                public void onCompleted(Location entity, Exception exception, ServiceFilterResponse response) {
                    if (exception == null) {
                        // Insert succeeded
                        newCenter.id = entity.id;
                        Fence newFence = new Fence();

                        newFence.id_Location = newCenter.id;
                        newFence.radius = currentFence.getRadius();
                        creatNewFence(newFence);

                        /*alertDialog.setTitle("success...");
                        alertDialog.setMessage("Insert Location succeeded!");

                        alertDialog.show();*/
                        //return entity.id;

                    } else {
                        // Insert failed
                        newCenter.id = "Failed";
                        alertDialog.setTitle("Failed...");
                        alertDialog.setMessage("Insert Location failed!");
                        alertDialog.show();
                        //return entity.id;

                    }
                }
            });
        }catch (MalformedURLException e) {

            //createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        } catch (Exception e) {
            //createAndShowDialog(e, "Error");
        }
    }

    public void creatNewFence(Fence newFence){

        try {
            mClient = new MobileServiceClient(
                    "https://pacmanandroid.azure-mobile.net/",
                    "mpVjGDJAoCHcrHsuWkxJGTvjwBDZMk90",
                    this
            );
            alertDialog = new AlertDialog.Builder(this);
            newFence.description = "123";
            newFence.id_carer = "123";
            newFence.id_patient = "123";
            mClient.getTable(Fence.class).insert(newFence, new TableOperationCallback<Fence>() {
                public void onCompleted(Fence entity, Exception exception, ServiceFilterResponse response) {
                    if (exception == null) {
                        // Insert succeeded

                        alertDialog.setTitle("success...");
                        alertDialog.setMessage("Insert Fence succeeded!");
                        alertDialog.show();

                    } else {
                        // Insert failed
                        alertDialog.setTitle("Failed...");
                        alertDialog.setMessage("Insert Fence failed!");
                        alertDialog.show();

                    }
                }
            });
        }catch (MalformedURLException e) {

            //createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        } catch (Exception e){
            //createAndShowDialog(e, "Error");
        }

    }

}
