package pacman372.dementiaaid.ServerAidClasses;

import java.util.List;

import pacman372.dementiaaid.SetFence.CircularFence;
import pacman372.dementiaaid.EntityClasses.Entity;
import pacman372.dementiaaid.EntityClasses.Patient;
import pacman372.dementiaaid.EntityClasses.Carer;
import pacman372.dementiaaid.PatientAlert;

/**
 * Created by fuqian on 21/09/2015.
 */
public class CarerSession {
    Entity<Carer> currentCarer;
    public CarerSession(Entity<Carer> carer) {
      this.currentCarer = carer;
    }
    /*Add patient to this carer*/
    public Entity<Patient> addPatient(Patient patient) {
        throw new UnsupportedOperationException();
    }
    /*Get all the patients info of this carer.*/
    public List<Entity<Patient>> getPatients(){

        throw new UnsupportedOperationException();
    }
    /*Add fence of this Carer.*/
    public Entity<CircularFence> addFence(CircularFence fence, Entity<Patient> patient) {
        throw new UnsupportedOperationException();
    }
    /*Get Patient Alert.*/
    public List<Entity<PatientAlert>> getPatientAlerts(){

        throw new UnsupportedOperationException();
    }
}
