package pacman372.dementiaaid;

import junit.framework.TestCase;

import pacman372.dementiaaid.EntityClasses.Location;

/**
 * Created by fuqian on 8/09/2015.
 */
public class LocationTest extends TestCase {
Location testLocation = new Location();
    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    public void testLocation() throws Exception {


    }

    public void testGetID() throws Exception {
        testLocation.setID(1);
        assertEquals(1,testLocation.getID());

    }

    public void testSetID() throws Exception {
        testLocation.setID(2);
        assertEquals(2,testLocation.getID());

    }

    public void testGetCoordinateX() throws Exception {
        testLocation.setCoordinateX(123);
        assertEquals(123.0,testLocation.getCoordinateX());

    }

    public void testSetCoordinateX() throws Exception {
        testLocation.setCoordinateX(127);
        assertEquals(127.0,testLocation.getCoordinateX());


    }

    public void testGetCoordinateY() throws Exception {
        testLocation.setCoordinateY(123);
        assertEquals(123.0,testLocation.getCoordinateY());

    }

    public void testSetCoordinateY() throws Exception {
        testLocation.setCoordinateY(123);
        assertEquals(123.0,testLocation.getCoordinateY());

    }

    public void testGetId_Patient() throws Exception {
        testLocation.setId_Patient(12);
        assertEquals(12,testLocation.getId_Patient());

    }

    public void testSetId_Patient() throws Exception {
        testLocation.setId_Patient(13);
        assertEquals(13,testLocation.getId_Patient());

    }

    public void testGetId_Carer() throws Exception {
        testLocation.setId_Carer(8);
        assertEquals(8,testLocation.getId_Carer());

    }

    public void testSetId_Carer() throws Exception {
        testLocation.setId_Carer(7);
        assertEquals(7,testLocation.getId_Carer());

    }

    public void testSerializeJson() throws Exception {
        //testLocation.setCoordinateX(123);
        //assertEquals(123.0,testLocation.getCoordinateX());

    }
}