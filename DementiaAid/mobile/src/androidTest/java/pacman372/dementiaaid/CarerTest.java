package pacman372.dementiaaid;

import android.util.JsonWriter;

import junit.framework.TestCase;

/**
 * Created by fuqian on 8/09/2015.
 */
public class CarerTest extends TestCase {
    Carer testCarer = new Carer();
    public void setUp() throws Exception {
        super.setUp();
        Carer testCarer = new Carer();
        testCarer.setAddress("64 blamey street, KG");
        testCarer.setDevice_id("1234567890");
        testCarer.setEmail("qiananger1234@gmial.com");
        testCarer.setName("Cynthia");
        testCarer.setPhone(040);
        testCarer.setID(06);

    }

    public void tearDown() throws Exception {

    }

    public void testCarer() throws Exception {


    }

    public void testGetID() throws Exception {
        testCarer.setID(06);
        assertEquals(06, testCarer.getID());

    }


    public void testGetName() throws Exception {
        testCarer.setName("Cynthia");
        assertEquals("Cynthia", testCarer.getName());

    }

    public void testSetName() throws Exception {
        testCarer.setName("Lambert");
        assertEquals("Lambert", testCarer.getName());


    }

    public void testGetPhone() throws Exception {
        testCarer.setPhone(1234);
        assertEquals(1234, testCarer.getPhone());

    }

    public void testSetPhone() throws Exception {
        testCarer.setPhone(1213);
        assertEquals(1213,testCarer.getPhone());

    }

    public void testGetEmail() throws Exception {
        testCarer.setEmail("qiananger12342gmail.com");
        assertEquals("qiananger12342gmail.com", testCarer.getEmail());
    }

    public void testSetEmail() throws Exception {
        testCarer.setEmail("anger1234@qq.com");
        assertEquals("anger1234@qq.com",testCarer.getEmail());

    }

    public void testGetAddress() throws Exception {
        testCarer.setAddress("64 blamey street, KG");
        assertEquals("64 blamey street, KG", testCarer.getAddress());

    }

    public void testSetAddress() throws Exception {
        testCarer.setAddress("65 street");
        assertEquals("65 street",testCarer.getAddress());

    }

    public void testGetDevice_id() throws Exception {
        testCarer.setDevice_id("1234567890");
        assertEquals("1234567890", testCarer.getDevice_id());

    }

    public void testSetDevice_id() throws Exception {
        testCarer.setDevice_id("0987654321");
        assertEquals("0987654321",testCarer.getDevice_id());

    }

    public void testSerializeJson() throws Exception {
        //JsonWriter jw = new JsonWriter();
        //testCarer.serializeJson(jw);

    }
}