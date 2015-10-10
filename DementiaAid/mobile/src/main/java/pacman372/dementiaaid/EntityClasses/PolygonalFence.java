package pacman372.dementiaaid.EntityClasses;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chong Lu on 10/10/2015.
 */
public class PolygonalFence {
    protected List<LatLng> points;
    protected int id_carer;
    protected int id_patient;

    public PolygonalFence(){
        points = new ArrayList<LatLng>();
    }


    public List<LatLng> getPoints() {
        return this.points;
    }
    public int getId_carer() {
        return id_carer;
    }

    public void setId_carer(int id_carer) {
        this.id_carer = id_carer;
    }

    public int getId_patient() {
        return id_patient;
    }

    public void setId_patient(int id_patient) {
        this.id_patient = id_patient;
    }
}
