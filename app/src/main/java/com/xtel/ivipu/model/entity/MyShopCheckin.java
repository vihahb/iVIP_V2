package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 2/17/2017.
 */

public class MyShopCheckin {
    @Expose
    private int store_id;
    @Expose
    private String store_name;
    @Expose
    private String logo;
    @Expose
    private String banner;
    @Expose
    private long checkin_time;
    private String date;
    private boolean isTitle = false;



    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MyShopCheckin() {
    }

    public MyShopCheckin(boolean isTitle, String date) {
        this.date = date;
        this.isTitle = isTitle;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
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

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public long getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(long checkin_time) {
        this.checkin_time = checkin_time;
    }

    @Override
    public String toString() {
        return "MyShopCheckin{" +
                "store_id=" + store_id +
                ", store_name='" + store_name + '\'' +
                ", logo='" + logo + '\'' +
                ", banner='" + banner + '\'' +
                ", checkin_time=" + checkin_time +
                '}';
    }
}
