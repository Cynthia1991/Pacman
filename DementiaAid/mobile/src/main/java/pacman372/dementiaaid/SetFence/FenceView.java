package pacman372.dementiaaid.SetFence;

import android.graphics.Color;

import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Chong Lu on 23/08/2015.
 */
public class FenceView {
    protected CircularFence fence;

    public static final LatLng DEFAULT_CAMERA = new LatLng(-27.4667, 153.0333);
    public boolean canChangeRadius() {
        return !isCenterUndefined();
    }

    private boolean isCenterUndefined() {
        return fence == null || fence.center == null;
    }
    public void mapClicked(LatLng latLng) {
        if (fence == null) {
            fence = new CircularFence(latLng, 10);
        }

        fence.setCenter(latLng);
    }

    public void radiusChanged(int radius) {
        fence.setRadius(radius);
    }

    public CircleOptions getPerimeterOptions(){
        if (isCenterUndefined() || fence.radius <= 0) {
            return null;
        }


        return new CircleOptions().strokeColor(Color.RED).strokeWidth(2).radius(fence.radius).center(fence.center);
    }

    public LatLng getCameraLocation() {
        if (isCenterUndefined()) {
            return DEFAULT_CAMERA;
        }

        return fence.center;

    }


    public MarkerOptions getCenterOptions(){
        if (isCenterUndefined())
        {
            return null;
        }

        return new MarkerOptions().position(fence.center);
    }
    public void setFence(CircularFence fence){
        this.fence = fence;

    }
    public CircularFence getFence(){
        return fence;

    }

}
