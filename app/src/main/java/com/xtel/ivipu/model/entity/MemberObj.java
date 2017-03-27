package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by vuhavi on 07/03/2017.
 */

public class MemberObj implements Serializable {

    @Expose
    private int id;
    @Expose
    private int store_id;
    @Expose
    private String store_name;
    @Expose
    private String store_logo;
    @Expose
    private String store_banner;
    @Expose
    private long create_time;
    @Expose
    private String member_card;
    @Expose
    private int current_point;
    @Expose
    private int total_point;

    public MemberObj() {
    }

    public MemberObj(int id, int store_id, String store_name, String store_logo, String store_banner, long create_time, String member_card, int current_point, int total_point) {
        this.id = id;
        this.store_id = store_id;
        this.store_name = store_name;
        this.store_logo = store_logo;
        this.store_banner = store_banner;
        this.create_time = create_time;
        this.member_card = member_card;
        this.current_point = current_point;
        this.total_point = total_point;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStore_logo() {
        return store_logo;
    }

    public void setStore_logo(String store_logo) {
        this.store_logo = store_logo;
    }

    public String getStore_banner() {
        return store_banner;
    }

    public void setStore_banner(String store_banner) {
        this.store_banner = store_banner;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getMember_card() {
        return member_card;
    }

    public void setMember_card(String member_card) {
        this.member_card = member_card;
    }

    public int getCurrent_point() {
        return current_point;
    }

    public void setCurrent_point(int current_point) {
        this.current_point = current_point;
    }

    public int getTotal_point() {
        return total_point;
    }

    public void setTotal_point(int total_point) {
        this.total_point = total_point;
    }

    @Override
    public String toString() {
        return "MemberObj{" +
                "id=" + id +
                ", store_id=" + store_id +
                ", store_name='" + store_name + '\'' +
                ", store_logo='" + store_logo + '\'' +
                ", store_banner='" + store_banner + '\'' +
                ", create_time=" + create_time +
                ", member_card='" + member_card + '\'' +
                ", current_point=" + current_point +
                ", total_point=" + total_point +
                '}';
    }
}
