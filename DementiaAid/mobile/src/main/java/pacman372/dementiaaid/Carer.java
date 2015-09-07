package pacman372.dementiaaid;

import android.util.JsonWriter;
import android.util.Log;

/**
 * Created by fuqian on 1/09/2015.
 */
public class Carer {
    private int ID = -1;
    private String name;
    private int phone = -1;
    private String email;
    private String address;
    private String device_id;

    public void Carer() {

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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }


    /*Turns the current Carer object into it's JSON form.
    * The if statements are to check if the value of the field is null, and if so, call .nullValue()
    * instead of calling .value()
     */
    public void serializeJson(JsonWriter jw) {
        try {
            jw.beginObject();
            if (getID() == -1) {
                jw.name("id").nullValue();
            } else {
                jw.name("id").value(getID());
            }
            if (getName() == null) {
                jw.name("name").nullValue();
            } else {
                jw.name("name").value(getName());
            }
            if (getPhone() == -1 ){
                jw.name("phone").nullValue();
            } else {
                jw.name("phone").value(getPhone());
            }
            if (getEmail() == null) {
                jw.name("email").nullValue();
            } else {
                jw.name("email").value(getEmail());
            }
            if (getAddress() == null) {
                jw.name("address").nullValue();
            } else{
                jw.name("address").value(getAddress());
            }
            if (getDevice_id() == null ){
                jw.name("device_id").nullValue();
            } else{
                jw.name("device_id").value(getDevice_id());
            }

            jw.endObject();
        } catch (java.io.IOException e) {
            Log.d("carerSerialize", "Error:" + e.toString());
        }
    }
}
