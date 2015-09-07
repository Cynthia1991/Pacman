package pacman372.dementiaaid;

/**
 * Created by fuqian on 7/09/2015.
 */
public class Fence {
    protected int id;
    protected int id_Location;
    protected double radius;
    protected String description;
    protected int id_carer;
    protected int id_patient;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getId_carer() {
        return id_carer;
    }

    public void setId_carer(int id_carer) {
        this.id_carer = id_carer;
    }

    public int getId_Location() {
        return id_Location;
    }

    public void setId_Location(int id_Location) {
        this.id_Location = id_Location;
    }

    public int getId_patient() {
        return id_patient;
    }

    public void setId_patient(int id_patient) {
        this.id_patient = id_patient;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }


}
