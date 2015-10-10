package pacman372.dementiaaid.SetFence;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Chong Lu on 23/08/2015.
 */
public class CircularFence {
    public LatLng getCenter() {
        return center;
    }

    protected int radius;
    protected LatLng center;

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
        this.center = center;
    }

    public int getRadius() {

        return this.radius;

    }
    public double getCoordinateX() {

        return this.center.latitude;

    }
    public double getCoordinateY() {

        return this.center.longitude;

    }

}
