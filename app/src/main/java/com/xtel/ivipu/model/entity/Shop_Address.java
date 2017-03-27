package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 3/1/2017.
 */

public class Shop_Address {

    @Expose
    private int id;
    @Expose
    private String store_name;
    @Expose
    private String logo;
    @Expose
    private String address;
    @Expose
    private double location_lng;
    @Expose
    private double location_lat;

    public Shop_Address() {
    }

    public Shop_Address(int id, String store_name, String logo, String address, double location_lng, double location_lat) {
        this.id = id;
        this.store_name = store_name;
        this.logo = logo;
        this.address = address;
        this.location_lng = location_lng;
        this.location_lat = location_lat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(double location_lng) {
        this.location_lng = location_lng;
    }

    public double getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(double location_lat) {
        this.location_lat = location_lat;
    }

    @Override
    public String toString() {
        return "Shop_Address{" +
                "id=" + id +
                ", store_name='" + store_name + '\'' +
                ", logo='" + logo + '\'' +
                ", address='" + address + '\'' +
                ", location_lng=" + location_lng +
                ", location_lat=" + location_lat +
                '}';
    }
}
