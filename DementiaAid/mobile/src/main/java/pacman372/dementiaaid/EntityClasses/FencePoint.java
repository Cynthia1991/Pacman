package pacman372.dementiaaid.EntityClasses;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Chong Lu on 10/10/2015.
 */
public class FencePoint {
    public FencePoint(LatLng ll) {
        this.latitude = ll.latitude;
        this.longitude = ll.longitude;
    }

    @SerializedName("Latitude")
    public double latitude;
    @SerializedName("Longitude")
    public double longitude;
}
