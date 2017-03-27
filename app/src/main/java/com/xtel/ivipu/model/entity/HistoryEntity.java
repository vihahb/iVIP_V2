package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 2/10/2017.
 */

public class HistoryEntity {
    @Expose
    private String history_name;
    @Expose
    private String history_address;
    @Expose
    private String history_time;
    @Expose
    private String history_date;
    @Expose
    private String history_view;
    @Expose
    private String history_like;
    @Expose
    private String history_comment;

    public HistoryEntity() {
    }

    public HistoryEntity(String history_name, String history_address, String history_time, String history_date, String history_view, String history_like, String history_comment) {
        this.history_name = history_name;
        this.history_address = history_address;
        this.history_time = history_time;
        this.history_date = history_date;
        this.history_view = history_view;
        this.history_like = history_like;
        this.history_comment = history_comment;
    }

    public String getHistory_name() {
        return history_name;
    }

    public void setHistory_name(String history_name) {
        this.history_name = history_name;
    }

    public String getHistory_address() {
        return history_address;
    }

    public void setHistory_address(String history_address) {
        this.history_address = history_address;
    }

    public String getHistory_time() {
        return history_time;
    }

    public void setHistory_time(String history_time) {
        this.history_time = history_time;
    }

    public String getHistory_date() {
        return history_date;
    }

    public void setHistory_date(String history_date) {
        this.history_date = history_date;
    }

    public String getHistory_view() {
        return history_view;
    }

    public void setHistory_view(String history_view) {
        this.history_view = history_view;
    }

    public String getHistory_like() {
        return history_like;
    }

    public void setHistory_like(String history_like) {
        this.history_like = history_like;
    }

    public String getHistory_comment() {
        return history_comment;
    }

    public void setHistory_comment(String history_comment) {
        this.history_comment = history_comment;
    }

    @Override
    public String toString() {
        return "HistoryEntity{" +
                "history_name='" + history_name + '\'' +
                ", history_address='" + history_address + '\'' +
                ", history_time='" + history_time + '\'' +
                ", history_date='" + history_date + '\'' +
                ", history_view='" + history_view + '\'' +
                ", history_like='" + history_like + '\'' +
                ", history_comment='" + history_comment + '\'' +
                '}';
    }
}
