package pacman372.dementiaaid.EntityClasses;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PolygonalFence {

    @SerializedName("FencePoints")
    protected List<FencePoint> points;
    @SerializedName("CarerID")
    public int carerID;
    @SerializedName("PatientID")
    public int patientID;

    public PolygonalFence(){
        points = new ArrayList<FencePoint>();
    }


    public List<FencePoint> getPoints() {
        return this.points;
    }
}
