package pacman372.dementiaaid;

import android.util.JsonWriter;
import android.util.Log;

/**
 * Created by fuqian on 1/09/2015.
 */
public class Fence {
    private int ID;
    private int id_location;
    private double radius;
    private String description;
    private int id_carer;
    private int id_patient;

    public Fence(){

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getId_location() {
        return id_location;
    }

    public void setId_location(int id_location) {
        this.id_location = id_location;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    public void serializeJson(JsonWriter jw) {
        try {
            jw.beginObject();
            if (getID() == -1) {
                jw.name("id").nullValue();
            } else {
                jw.name("id").value(getID());
            }
            if (getId_location() == -1) {
                jw.name("id_location").nullValue();
            } else {
                jw.name("id_location").value(getId_location());
            }
            if (getId_carer() == -1 ){
                jw.name("id_carer").nullValue();
            } else {
                jw.name("id_carer").value(getId_carer());
            }
            if (getId_patient() == -1) {
                jw.name("id_patient").nullValue();
            } else {
                jw.name("id_patient").value(getId_patient());
            }
            if (getRadius() == -1) {
                jw.name("radius").nullValue();
            } else{
                jw.name("radius").value(getRadius());
            }
            if (getDescription() == null ){
                jw.name("description").nullValue();
            } else{
                jw.name("description").value(getDescription());
            }

            jw.endObject();
        } catch (java.io.IOException e) {
            Log.d("carerSerialize", "Error:" + e.toString());
        }
    }
}
