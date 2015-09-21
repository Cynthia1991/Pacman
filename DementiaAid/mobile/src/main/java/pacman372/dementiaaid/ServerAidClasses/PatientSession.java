package pacman372.dementiaaid.ServerAidClasses;

import java.util.List;

import pacman372.dementiaaid.EntityClasses.Carer;
import pacman372.dementiaaid.EntityClasses.Entity;
import pacman372.dementiaaid.EntityClasses.Patient;
import pacman372.dementiaaid.PatientAlert;
import pacman372.dementiaaid.SetFence.CircularFence;

/**
 * Created by fuqian on 21/09/2015.
 * This class responsible for
 */
public class PatientSession {
    public PatientSession(Entity<Carer> carer) {

    }
    public Entity<CircularFence> addFence(CircularFence fence, Entity<Patient> patient) {
        throw new UnsupportedOperationException();
    }
    public List<Entity<PatientAlert>> getPatientAlerts(){
        throw new UnsupportedOperationException();
    }
    public void addCarerApproval(){


    }
}
