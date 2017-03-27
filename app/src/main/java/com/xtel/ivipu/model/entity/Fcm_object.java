package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 3/16/2017.
 */

public class Fcm_object {

    @Expose
    private String fcm_cloud_key;

    public Fcm_object() {
    }

    public Fcm_object(String fcm_cloud_key) {
        this.fcm_cloud_key = fcm_cloud_key;
    }

    public String getFcm_cloud_key() {
        return fcm_cloud_key;
    }

    public void setFcm_cloud_key(String fcm_cloud_key) {
        this.fcm_cloud_key = fcm_cloud_key;
    }

    @Override
    public String toString() {
        return "Fcm_object{" +
                "fcm_cloud_key='" + fcm_cloud_key + '\'' +
                '}';
    }
}
