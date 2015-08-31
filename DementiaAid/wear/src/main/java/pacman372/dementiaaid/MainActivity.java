package pacman372.dementiaaid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.model.LatLng;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;

public class MainActivity extends Activity {

    private TextView mTextView;
    private MobileServiceClient mClient;
    //private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    //private FenceView viewModel;
    private SeekBar radiusSlider;
    private AlertDialog.Builder alertDialog;
    //private CircularFence currentFence;
    private LocationWear currentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });

        currentLocation = new LocationWear();
    }

    public void uploadLocation(){
        try {

            mClient = new MobileServiceClient(
                    "https://pacmanandroid.azure-mobile.net/",
                    "mpVjGDJAoCHcrHsuWkxJGTvjwBDZMk90",
                    this
            );


            alertDialog = new AlertDialog.Builder(this);
            mClient.getTable(LocationWear.class).insert(currentLocation, new TableOperationCallback<LocationWear>() {
                public void onCompleted(LocationWear entity, Exception exception, ServiceFilterResponse response) {
                    if (exception == null) {
                        // Insert succeeded


                        //alertDialog.setTitle("success...");
                        //alertDialog.setMessage("Insert location succeeded!");

                        //alertDialog.show();
                        //return entity.id;

                    } else {
                        // Insert failed

                        //alertDialog.setTitle("Failed...");
                        //alertDialog.setMessage("Insert Location failed!");
                        //alertDialog.show();
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
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        //Intent intent = new Intent(this, DisplayMessageActivityActivity.class);
        EditText editTextX = (EditText) findViewById(R.id.edit_LocationX);
        EditText editTextY = (EditText) findViewById(R.id.edit_LocationY);
        double lX = Double.valueOf(editTextX.getText().toString());
        double lY = Double.valueOf(editTextY.getText().toString());
        //intent.putExtra(EXTRA_MESSAGE, message);
        //startActivity(intent);
        currentLocation.coordinateX = lX;
        currentLocation.coordinateY = lY;
        currentLocation.id_Carer = "123";
        currentLocation.id_Patient = "123";
        uploadLocation();


    }
}
