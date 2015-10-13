package pacman372.dementiaaid.SetFence;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import pacman372.dementiaaid.R;

public class MapsActivity extends AppCompatActivity {
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private FenceView viewModel;
    private SeekBar radiusSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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



        RadioGroup fenceRadioGroup = (RadioGroup) findViewById(R.id.fenceRadioGroup);
        fenceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.circularRadioButton: viewModel.SetMode(FenceView.MODE.Circular); break;
                    case R.id.polygonRadioButton: viewModel.SetMode(FenceView.MODE.Polygonal); break;
                }

                syncFromModel();
            }
        });

        syncFromModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        viewModel.mapClicked(latLng);
                        syncFromModel();
                    }
                });
            }
        }
    }

    private void syncFromModel() {
        viewModel.configureMap(mMap);
        boolean radiusEnabled = viewModel.canChangeRadius();
        radiusSlider.setEnabled(radiusEnabled);
        if (radiusEnabled) {
            radiusSlider.setProgress(viewModel.getRadius());
        }
        switch (viewModel.mode){
            case Polygonal: break;
            case Circular: break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.onPause();
    }

}




