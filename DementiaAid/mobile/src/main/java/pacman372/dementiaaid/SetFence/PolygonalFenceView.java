package pacman372.dementiaaid.SetFence;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import pacman372.dementiaaid.EntityClasses.PolygonalFence;

/**
 * Created by Chong Lu 10/10/2015.
 */
public class PolygonalFenceView {
    protected PolygonalFence pFence;

    public void mapClicked(LatLng latLng) {
        if (pFence == null) {
            pFence = new PolygonalFence();
        }
        pFence.getPoints().add(latLng);
    }
}
