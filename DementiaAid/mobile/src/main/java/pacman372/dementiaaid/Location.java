package pacman372.dementiaaid;

import android.util.JsonWriter;
import android.util.Log;

/**
 * Created by fuqian on 1/09/2015.
 */
public class Location {
    public int id=-1;
    public double coordinates_x=-1;
    public double coordinates_y=-1;
    public int id_Patient;
    public int id_Carer;
    public void Location(){

    }
    public void Location(int id,double x,double y,int id_P,int id_C){
        this.id = id;
        this.coordinates_x = x;
        this.coordinates_y = y;
        this.id_Patient = id_P;
        this.id_Carer = id_C;
    }
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
    public Double getCoordinateX() {
        return coordinates_x;
    }

    public void setCoordinateX(double coordinate_X) {
        this.coordinates_x = coordinate_X;
    }
    public Double getCoordinateY() {
        return coordinates_y;
    }

    public void setCoordinateY(double coordinate_Y) {
        this.coordinates_y = coordinate_Y;
    }

    public int getId_Patient() {
        return id_Patient;
    }

    public void setId_Patient(int id_patient) {
        this.id_Patient = id_patient;
    }


    public int getId_Carer() {
        return id_Carer;
    }

    public void setId_Carer(int id_carer) {
        this.id_Carer = id_carer;
    }
    public void serializeJson(JsonWriter jw) {
        try {
            jw.beginObject();
            if (getID() == -1) {
                jw.name("id").nullValue();
            } else {
                jw.name("id").value(getID());
            }
            if (getCoordinateY() == -1 ){
                jw.name("coordinates_y").nullValue();
            } else {
                jw.name("coordinates_y").value(getCoordinateY());
            }
            if (getCoordinateX() == -1 ){
                jw.name("coordinates_x").nullValue();
            } else {
                jw.name("coordinates_x").value(getCoordinateX());
            }
            if (getId_Patient() == -1) {
                jw.name("id_patient").nullValue();
            } else {
                jw.name("id_patient").value(getId_Patient());
            }
            if (getId_Carer() == -1) {
                jw.name("id_carer").nullValue();
            } else{
                jw.name("id_carer").value(getId_Carer());
            }
            jw.endObject();
        } catch (java.io.IOException e) {
            Log.d("carerSerialize", "Error:" + e.toString());
        }
    }
}
