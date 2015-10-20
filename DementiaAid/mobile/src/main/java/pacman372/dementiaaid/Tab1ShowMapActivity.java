package pacman372.dementiaaid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import pacman372.dementiaaid.CarerDetailsScreen.CarerDetailsVM;
import pacman372.dementiaaid.EntityClasses.FencePoint;
import pacman372.dementiaaid.EntityClasses.Location;
import pacman372.dementiaaid.EntityClasses.PolygonalFence;
import pacman372.dementiaaid.SetFence.CircularFence;
import pacman372.dementiaaid.SetFence.FenceView;

public class Tab1ShowMapActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private FenceView viewModel;

    private CircularFence cFence;
    private PolygonalFence pFence;
    private String fenceType;
    private LocationGetter locationGetter;

    private LatLng currentLocation;

    private FenceView locationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new FenceView();
        setContentView(R.layout.activity_tab1_show_map);
        setUpMapIfNeeded();
        setCurrentFence();



        //locationViewModel = new FenceView();
        //Get the patient's last location by using the location getter.
        locationGetter = new LocationGetter(this);
        currentLocation = new LatLng(locationGetter.coordinates_x,locationGetter.coordinates_y);
        //Display the last location on the map view
        Marker myPatients = mMap.addMarker(new MarkerOptions().position(currentLocation)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setCurrentFence(){
        String string = "pacman372.dementiaaid.userDetails";
        SharedPreferences userDetails = getSharedPreferences(string, 0);
        SharedPreferences.Editor editor = userDetails.edit();
        String fenceType = userDetails.getString("FenceType","");
        this.fenceType = fenceType;
        if(fenceType.equals("CircularFence")){

            Gson gson = new Gson();
            String json = userDetails.getString("Fence", "");
            CircularFence obj = gson.fromJson(json, CircularFence.class);
            this.cFence = obj;

            setFenceView(0);
        }else{
            Gson gson = new Gson();
            String json = userDetails.getString("Fence", "");
            PolygonalFence obj = gson.fromJson(json, PolygonalFence.class);
            this.pFence = obj;

            setFenceView(1);


        }

    }

    private void setFenceView(int fenceTypeInt) {
        switch (fenceTypeInt) {
            case 0:
               setCircularFenceView();
                break;
            case 1:
                setPolygonalFenceView();
                break;

        }
    }
    private void setCircularFenceView() {
        //1.Set mode of fence view.
        viewModel.SetMode(FenceView.MODE.Circular);
        if(cFence!= null){
            //2.Set center of fence view.
            viewModel.mapClicked(cFence.getCenter());
            //3.Set radius of fence view.
            viewModel.radiusChanged(cFence.getRadius());
            //4.Configure map by viewModel
            viewModel.configureMap(mMap);
        }

    }
    private void setPolygonalFenceView() {
        //1.Set mode of fence view.
        viewModel.SetMode(FenceView.MODE.Polygonal);
        if(pFence!= null){

            //2.Set point of fence view.
            for(FencePoint aFP : pFence.getPoints()){
                LatLng ll = new LatLng(aFP.latitude,aFP.longitude);
                viewModel.mapClicked(ll);

            }
            //3.Configure map by viewModel
            viewModel.configureMap(mMap);
        }
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
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2))
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab1_show_map, menu);
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
}
