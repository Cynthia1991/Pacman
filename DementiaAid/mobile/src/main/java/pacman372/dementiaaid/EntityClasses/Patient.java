/**
 * Created by fuqian on 31/08/2015.
 */
package pacman372.dementiaaid.EntityClasses;

import com.pushbots.push.Pushbots;

public class Patient {
    private int ID;
    private String name;
    private String phone;
    private String device_id;
    public Patient(String name){

        this.name = name;




    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}
