package pacman372.dementiaaid;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by Ramona on 21/08/2015.
 */
public class LocationHelper {
    LocationManager locationManager;
    public LocationHelper(Context context){
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

    }

    public Location getMostRecentLastKnownLocation() {

        Location[] lastKnownLocations = new Location[] {

                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER),
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER),
                locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
        };
        Location mostRecentLastKnownLocation = null;
        for(Location l : lastKnownLocations) {
            if (l != null &&
                    (mostRecentLastKnownLocation == null ||
                            l.getElapsedRealtimeNanos() < mostRecentLastKnownLocation.getElapsedRealtimeNanos())){
                mostRecentLastKnownLocation = l;
            }
        }
        return mostRecentLastKnownLocation;
    }
}
