package com.example.pacmanandroidnew;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonWriter;

import java.io.IOException;

/**
 * Created by lichuan on 11/10/2015.
 */
public class Patient {
    public int ID;
    public String name;
    public String phone;
    public String device_id;
    public void Patient(){

    }

    public void Patient(int ID,String name,String phone,String device_id){
        this.ID = ID;
        this.name = name;
        this.phone = phone;
        this.device_id = device_id;

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

    //@TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    public void serializeJson(JsonWriter jw){
//        try{
//            jw.beginObject();
//            if (getID() == -1) {
//                jw.name("id").nullValue();
//            } else {
//                jw.name("id").value(getID());
//            }
//            if (getName() == null) {
//                jw.name("name").nullValue();
//            } else {
//                jw.name("name").value(getName());
//            }
//            if (getName() == null) {
//                jw.name("phone").nullValue();
//            } else {
//                jw.name("phone").value(getPhone());
//            }
//            if (getName() == null) {
//                jw.name("device_id").nullValue();
//            } else {
//                jw.name("device_id").value(getDevice_id());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
