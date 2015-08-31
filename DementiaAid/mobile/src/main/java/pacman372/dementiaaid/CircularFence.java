package pacman372.dementiaaid;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ramona on 23/08/2015.
 */
public class CircularFence {
    protected int radius;
    protected LatLng center;

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
