package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 2/17/2017.
 */

public class CheckInEntity {
    @Expose
    private String store_code;
    @Expose
    private double location_lat;
    @Expose
    private double location_lng;

    public CheckInEntity() {
    }

    public CheckInEntity(String store_code, double location_lat, double location_lng) {
        this.store_code = store_code;
        this.location_lat = location_lat;
        this.location_lng = location_lng;
    }

    public String getStore_code() {
        return store_code;
    }

    public void setStore_code(String store_code) {
        this.store_code = store_code;
    }

    public double getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(double location_lat) {
        this.location_lat = location_lat;
    }

    public double getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(double location_lng) {
        this.location_lng = location_lng;
    }

    @Override
    public String toString() {
        return "CheckInEntity{" +
                "store_code='" + store_code + '\'' +
                ", location_lat=" + location_lat +
                ", location_lng=" + location_lng +
                '}';
    }
}
