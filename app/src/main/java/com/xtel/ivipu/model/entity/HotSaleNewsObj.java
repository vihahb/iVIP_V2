package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 4/8/2017.
 */

public class HotSaleNewsObj {

    @Expose
    private int id;
    @Expose
    private String title;
    @Expose
    private String banner;
    @Expose
    private int type;

    public HotSaleNewsObj() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "HotSaleNewsObj{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", banner='" + banner + '\'' +
                ", type=" + type +
                '}';
    }
}
