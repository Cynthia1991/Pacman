package pacman372.dementiaaid;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Tab1ShowMapActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private FenceView viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1_show_map);


        Intent intent =getIntent();
                //getXxxExtra方法获取Intent传递过来的数据
        double x=0,y=0;
        int radius=0;
        double latestX=intent.getDoubleExtra("x1",x);
        double latestY=intent.getDoubleExtra("y1",y);
        int latestRadius=intent.getIntExtra("radius1",radius);

        viewModel = new FenceView();
        LatLng centerLocation = new LatLng(latestX,latestY);
        CircularFence newFence = new CircularFence();
        newFence.setCenter(centerLocation);
        newFence.setRadius(latestRadius);
        viewModel.setFence(newFence);

        setUpMapIfNeeded();
        syncFromModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
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
        /*radiusSlider.setEnabled(radiusEnabled);
        if (radiusEnabled) {
            radiusSlider.setProgress(viewModel.fence.radius);
        }*/

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