package pacman372.dementiaaid;

import junit.framework.TestCase;

import pacman372.dementiaaid.EntityClasses.Fence;

/**
 * Created by fuqian on 8/09/2015.
 */
public class FenceTest extends TestCase {

    Fence testFence = new Fence();
    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    public void testGetID() throws Exception {
        testFence.setID(01);
        assertEquals(01,testFence.getID());

    }

    public void testSetID() throws Exception {
        testFence.setID(02);
        assertEquals(02,testFence.getID());
    }

    public void testGetId_location() throws Exception {
        testFence.setId_location(02);
        assertEquals(02,testFence.getId_location());

    }

    public void testSetId_location() throws Exception {
        testFence.setId_location(03);
        assertEquals(03,testFence.getId_location());

    }

    public void testGetRadius() throws Exception {
        testFence.setRadius(12.0);
        assertEquals(12.0,testFence.getRadius());
    }

    public void testSetRadius() throws Exception {
        testFence.setRadius(14.0);
        assertEquals(14.0, testFence.getRadius());

    }

    public void testGetDescription() throws Exception {
        testFence.setDescription("this is my test");
        assertEquals("this is my test", testFence.getDescription());

    }

    public void testSetDescription() throws Exception {
        testFence.setDescription("this is your test");
        assertEquals("this is your test",testFence.getDescription());

    }

    public void testGetId_carer() throws Exception {
        testFence.setId_carer(01);
        assertEquals(01,testFence.getId_carer());

    }

    public void testSetId_carer() throws Exception {
         testFence.setId_carer(02);
        assertEquals(02,testFence.getId_carer());
    }

    public void testGetId_patient() throws Exception {
        testFence.setId_patient(12);
        assertEquals(12,testFence.getId_patient());

    }

    public void testSetId_patient() throws Exception {
        testFence.setId_patient(13);
        assertEquals(13,testFence.getId_patient());

    }

    public void testSerializeJson() throws Exception {

    }
}