package pacman372.dementiaaid;

import junit.framework.TestCase;

/**
 * Created by fuqian on 8/09/2015.
 */
public class PatientTest extends TestCase {
Patient testPatient = new Patient();
    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    public void testGetID() throws Exception {
        testPatient.setID(2);
        assertEquals(2,testPatient.getID());
    }

    public void testSetID() throws Exception {

        testPatient.setID(3);
        assertEquals(3,testPatient.getID());

    }

    public void testGetName() throws Exception {

        testPatient.setName("Cynthia");
        assertEquals("Cynthia",testPatient.getName());

    }

    public void testSetName() throws Exception {

        testPatient.setName("Lambert");
        assertEquals("Lambert",testPatient.getName());

    }

    public void testGetPhone() throws Exception {

        testPatient.setPhone("12340");
        assertEquals("12340",testPatient.getPhone());

    }

    public void testSetPhone() throws Exception {

        testPatient.setPhone("12340");
        assertEquals("12340",testPatient.getPhone());

    }

    public void testGetDevice_id() throws Exception {

        testPatient.setDevice_id("123456");
        assertEquals("123456",testPatient.getDevice_id());

    }

    public void testSetDevice_id() throws Exception {

        testPatient.setDevice_id("124");
        assertEquals("124",testPatient.getDevice_id());

    }
}