package pacman372.dementiaaid.ServerAidClasses;

import pacman372.dementiaaid.EntityClasses.Carer;
import pacman372.dementiaaid.EntityClasses.Entity;

/**
 * Created by fuqian on 21/09/2015.
 */
public class DementiaAidServer {
    public DementiaAidServer() {

    }
/**/
    public Entity<Carer> addCarer(Carer carer){
        //Send REST request to server url with details of carer
        //If error
        //Return persisted carer details from server
        Entity<Carer> aCarer = new Entity<Carer>(carer.getID(),carer);
        return aCarer;
    }
/**/
    public CarerSession getCarerSession(String email, String key) {
        throw new UnsupportedOperationException();
    }
/**/
    public PatientSession getPatientSession(String email, String key) {
        throw new UnsupportedOperationException();
    }
}
