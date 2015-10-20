package com.example.pacmanandroidnew.ShareLocation;



/**
 * Created by lichuan on 11/10/2015.
 */
public class Location {
    public int ID;
    public double coordinates_x=-1;
    public double coordinates_y=-1;
    public int id_patient;
    public int id_carer;
    public void Location(){

    }
    public void Location(int id,double x,double y,int id_P,int id_C){
        this.ID = id;
        this.coordinates_x = x;
        this.coordinates_y = y;
        this.id_patient = id_P;
        this.id_carer = id_C;
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
        return id_patient;
    }

    public void setId_Patient(int id_patient) {
        this.id_patient = id_patient;
    }

    public int getId_Carer() {
        return id_carer;
    }

    public void setId_Carer(int id_carer) {
        this.id_carer = id_carer;
    }

}
