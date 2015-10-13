package pacman372.dementiaaid.SetFence;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Chong Lu on 23/08/2015.
 */
public class CircularFence {
    protected int radius;

    @SerializedName(value = "Latitude")
    protected double latitude;

    @SerializedName(value = "Longitude")
    protected double longitude;

    @SerializedName(value="id_carer")
    protected int carerID;

    @SerializedName(value="id_patient")
    protected int patientID;

    public CircularFence(){

    }

    public CircularFence(LatLng center, int radius) {
        setRadius(radius);
        setCenter(center);
    }
    public void setRadius(int newRadius) {
        this.radius = newRadius;
    }

    public void setCenter(LatLng center) {
        this.latitude = center.latitude;
        this.longitude = center.longitude;
    }

    public LatLng getCenter(){
        return new LatLng(latitude, longitude);
    }
    public int getRadius() {
        return this.radius;
    }
}
