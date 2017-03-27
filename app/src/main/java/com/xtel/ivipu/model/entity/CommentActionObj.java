package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 2/20/2017.
 */

public class CommentActionObj {

    @Expose
    private int news_id;
    @Expose
    private String comment;

    public CommentActionObj() {
    }

    public CommentActionObj(int news_id, String comment) {
        this.news_id = news_id;
        this.comment = comment;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CommentActionObj{" +
                "news_id:" + news_id +
                ", comment:'" + comment + '\'' +
                '}';
    }
}
