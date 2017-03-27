package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

/**
 * Created by vihahb on 1/17/2017.
 */

public class SoftNewsObjectEntity extends RESP_Basic {

    @Expose
    private int id;
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

    public SoftNewsObjectEntity() {
    }

    public SoftNewsObjectEntity(int id, String banner, String logo, String store_name, int like, int comment, int rate, int view, long create_time, int seen) {
        this.id = id;
        this.banner = banner;
        this.logo = logo;
        this.store_name = store_name;
        this.like = like;
        this.comment = comment;
        this.rate = rate;
        this.view = view;
        this.create_time = create_time;
        this.seen = seen;
    }

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

    @Override
    public String toString() {
        return "SoftNewsObjectEntity{" +
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
                '}';
    }
}
