package pacman372.dementiaaid;

import android.graphics.Color;
import android.test.ServiceTestCase;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Ramona on 23/08/2015.
 */
public class CircularFence {
    protected double radius;
    protected LatLng center;
    protected GoogleMap map;
    protected Marker currentMarker;
    protected Circle currentPerimeter;

    public CircularFence(GoogleMap map) {
        this.map = map;
    }

    public void SetRadius(double radius) {
        this.radius = radius;
        OnPerimeterChanged();
    }

    private void OnPerimeterChanged() {
        if (currentPerimeter != null) {
            currentPerimeter.remove();
        }
        if (center != null) {
            currentPerimeter = map.addCircle(new CircleOptions().radius(radius).center(center).strokeWidth(3).strokeColor(Color.RED));
        }
    }

    public void SetCenter(LatLng center) {
        this.center = center;
        OnCenterChanged();
    }

    private void OnCenterChanged() {
        if (currentMarker != null) {
            currentMarker.remove();
        }
        currentMarker = map.addMarker(new MarkerOptions().position(center));

        OnPerimeterChanged();
    }

//    public double GetRadius(){
//        return radius;
//    }
//
//    public LatLng GetCenter(){
//        return center;
//    }
}