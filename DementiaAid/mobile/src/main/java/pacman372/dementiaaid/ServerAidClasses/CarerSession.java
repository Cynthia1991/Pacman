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
    public CarerSession(Entity<Carer> carer) {

    }
    public Entity<Patient> addPatient(Patient patient) {
        throw new UnsupportedOperationException();
    }
    public List<Entity<Patient>> getPatients(){
        throw new UnsupportedOperationException();
    }
    public Entity<CircularFence> addFence(CircularFence fence, Entity<Patient> patient) {
        throw new UnsupportedOperationException();
    }
    public List<Entity<PatientAlert>> getPatientAlerts(){
        throw new UnsupportedOperationException();
    }
}
