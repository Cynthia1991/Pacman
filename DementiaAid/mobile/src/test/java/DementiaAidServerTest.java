import com.google.android.gms.maps.model.LatLng;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import pacman372.dementiaaid.SetFence.CircularFence;
import pacman372.dementiaaid.ServerAidClasses.DementiaAidServer;


public class DementiaAidServerTest {

    DementiaAidServer server;
    @Before
    public void setup() {
        server = new DementiaAidServer();
    }

    @Test
    public void carerAddPatientAndPatientApproval() {

        setupCarerWithOnePatient("carer1Email", "patient1Email");

        //Carer1 app
        String carer1Key = "";//loaded from device storage
        CarerSession carer1Session = server.getCarerSession("carer1Email", carer1Key);
        List<Entity<Patient>> carer1Patients = carer1Session.getPatients();
        Assert.assertEquals(1, carer1Patients.size());
    }

    @Test
    /*Story 1: */
    public void patientLocationUpdateShouldAlertApprovedCarersIfOutsideFence() {
        setupCarerWithOnePatient("carer1Email", "patient1Email");

        String carer1Key = "";//loaded from device storage
        CarerSession carer1Session = server.getCarerSession("carer1Email", carer1Key);
        Entity<Patient> persistedPatient1 = carer1Session.getPatients().get(0);

        CircularFence boundary = new CircularFence(new LatLng(-127, 27), 100);
        Entity<CircularFence> persistedPatient1Boundary = carer1Session.addFence(boundary, persistedPatient1);

        List<Entity<PatientAlert>> patientAlerts = carer1Session.getPatientAlerts();
        Assert.assertTrue(patientAlerts.isEmpty());

        String patient1Key = "";//loaded from device storage
        PatientSession patient1Session = server.getPatientSession("patient1Email", patient1Key);
        patient1Session.addLocationUpdate(new LatLng(-200, 33));

        List<Entity<PatientAlert>> patientAlertsAfterPatientLeftFence = carer1Session.getPatientAlerts();
        Assert.assertEquals(1, patientAlertsAfterPatientLeftFence.size());
    }

    public void setupCarerWithOnePatient(String carerEmail, String patientEmail){

        //Carer1 app
        Carer carer1 = new Carer(carerEmail);
        Entity<Carer> persistedCarer1 = server.addCarer(carer1);
        //Server sends email to carer1 with authorization key
        String carer1Key = "";//typed in from email
        CarerSession carer1Session = server.getCarerSession(carerEmail, carer1Key);

        List<Entity<Patient>> carer1Patients = carer1Session.getPatients();

        Assert.assertTrue(carer1Patients.isEmpty());

        Patient patient1 = new Patient(patientEmail);

        Entity<Patient> persistedPatient1 = carer1Session.addPatient(patient1);
        //Server sends email to patient1 with authorization key

        //Patient1 app
        String patient1Key = ""; //typed in from email
        PatientSession patient1Session = server.getPatientSession(patientEmail, patient1Key);

        List<Entity<CarerRequest>> patient1CarerRequests = patient1Session.getCarerRequest();

        Assert.assertEquals(1, patient1CarerRequests.size());

        patient1Session.addCarerApproval(patient1CarerRequests.get(0));

    }


    /*public class DementiaAidServer {
        public DementiaAidServer() {

        }

        public Entity<Carer> addCarer(Carer carer){
            //Send REST request to server url with details of carer
            //If error
            //Return persisted carer details from server
        }

        public CarerSession getCarerSession(String email, String key) {
            throw new UnsupportedOperationException();
        }

        public PatientSession getPatientSession(String email, String key) {
            throw new UnsupportedOperationException();
        }
    }*/
    /*public class LocationUpdate {
        LatLng location;
        long creationTimeStamp;
        public LocationUpdate(LatLng location){
            this.location = location;
            creationTimeStamp = System.currentTimeMillis();
        }
    }
    public class PatientSession {
        public PatientSession(Entity<Patient> patent) {

        }

        public Entity<LocationUpdate> addLocationUpdate(LatLng location) {
            throw new UnsupportedOperationException();
        }

        public List<Entity<CarerRequest>> getCarerRequest() {
            throw new UnsupportedOperationException();

        }

        public void addCarerApproval(Entity<CarerRequest> carerRequestEntity) {
            throw new UnsupportedOperationException();
        }
    }
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

    public class Carer {
        public Carer(String email) {

        }
    }
    public class Patient {
        public Patient(String email) {

        }
    }

    public class Entity<T>
    {
        public Entity(int id, T data){
            this.id = id;
            this.data = data;
        }
        public T data;
        public int id;
    }

    public class PatientAlert {
    }

    public class CarerRequest{
    }*/

}
