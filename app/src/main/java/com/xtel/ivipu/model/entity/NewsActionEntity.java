package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 2/15/2017.
 */

public class NewsActionEntity {
    @Expose
    private int news_id;
    @Expose
    private int action;

    public NewsActionEntity() {
    }

    public NewsActionEntity(int news_id, int action) {
        this.news_id = news_id;
        this.action = action;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "NewsActionEntity{" +
                "news_id=" + news_id +
                ", action=" + action +
                '}';
    }
}
