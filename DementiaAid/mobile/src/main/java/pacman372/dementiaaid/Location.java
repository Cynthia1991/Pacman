package pacman372.dementiaaid;

/**
 * Created by fuqian on 7/09/2015.
 */
public class Location {
    protected int id;
    protected double coordinateX;
    protected double coordinateY;
    protected int id_Patient;

    public double getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }

    public int getId() {
        return id;
    }

    public int getId_Carer() {
        return id_Carer;
    }

    public void setId_Carer(int id_Carer) {
        this.id_Carer = id_Carer;
    }

    public int getId_Patient() {
        return id_Patient;
    }

    public void setId_Patient(int id_Patient) {
        this.id_Patient = id_Patient;
    }

    protected int id_Carer;

}
