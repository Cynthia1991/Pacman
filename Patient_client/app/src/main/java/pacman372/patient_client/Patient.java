package pacman372.patient_client;

import android.util.JsonWriter;

import java.io.IOException;

/**
 * Created by chuan on 21/09/2015.
 */

    public class Patient {
        private int ID;
        private String name;
        private String phone;
        private String device_id;

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

    public void serializeJson(JsonWriter jw){
        try{
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
            if (getName() == null) {
                jw.name("phone").nullValue();
            } else {
                jw.name("phone").value(getPhone());
            }
            if (getName() == null) {
                jw.name("device_id").nullValue();
            } else {
                jw.name("device_id").value(getDevice_id());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

