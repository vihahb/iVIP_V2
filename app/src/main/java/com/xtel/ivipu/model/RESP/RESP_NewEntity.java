package com.xtel.ivipu.model.RESP;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by vivhp on 2/14/2017.
 */

public class RESP_NewEntity implements Serializable {

    @Expose
    private int id;
    @Expose
    private String banner;
    @Expose
    private String logo;
    @Expose
    private String store_name;
    @Expose
    private int like;
    @Expose
    private int comment;
    @Expose
    private int rate;
    @Expose
    private int view;
    @Expose
    private long create_time;
    @Expose
    private int seen;
    @Expose
    private int bg_position;
    @Expose
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getSeen() {
        return seen;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }

    public int getBg_position() {
        return bg_position;
    }

    public void setBg_position(int bg_position) {
        this.bg_position = bg_position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "RESP_NewEntity{" +
                "id=" + id +
                ", banner='" + banner + '\'' +
                ", logo='" + logo + '\'' +
                ", store_name='" + store_name + '\'' +
                ", like=" + like +
                ", comment=" + comment +
                ", rate=" + rate +
                ", view=" + view +
                ", create_time=" + create_time +
                ", seen=" + seen +
                ", bg_position=" + bg_position +
                ", title='" + title + '\'' +
                '}';
    }
}
