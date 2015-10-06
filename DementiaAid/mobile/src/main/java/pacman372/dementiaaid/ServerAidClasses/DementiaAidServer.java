package pacman372.dementiaaid.ServerAidClasses;

import pacman372.dementiaaid.EntityClasses.Carer;
import pacman372.dementiaaid.EntityClasses.Entity;

/**
 * Created by fuqian on 21/09/2015.
 */
public class DementiaAidServer {
    Entity<Carer> currentCarerE;
    public DementiaAidServer() {

    }
/**/
    public Entity<Carer> addCarer(Carer carer){
        //Send REST request to server url with details of carer
        //If error
        //Return persisted carer details from server
        Entity<Carer> aCarer = new Entity<Carer>(carer.getID(),carer);
        this.currentCarerE = aCarer;
        return aCarer;
    }
/*Get carer session from email and verify key, need load carer detail from server*/
    public CarerSession getCarerSession(String email, String key) {
        CarerSession carerSession;
        if(currentCarerE != null){
        carerSession = new CarerSession(currentCarerE);
         }
        else{
            return null;
        }
        return carerSession;
        //throw new UnsupportedOperationException();
    }
/*Patient session*/
    public PatientSession getPatientSession(String email, String key) {
        throw new UnsupportedOperationException();
    }
}
