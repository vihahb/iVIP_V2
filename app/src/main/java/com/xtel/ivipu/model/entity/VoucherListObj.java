package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 4/5/2017.
 */

public class VoucherListObj {
    @Expose
    private String banner;
    @Expose
    private int news_id;
    @Expose
    private int state;

    public VoucherListObj() {
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "VoucherListObj{" +
                "banner='" + banner + '\'' +
                ", news_id=" + news_id +
                ", state=" + state +
                '}';
    }
}
