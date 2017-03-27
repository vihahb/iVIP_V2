package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 2/18/2017.
 */

public class CommentObj {
    @Expose
    private int id;
    @Expose
    private String fullname;
    @Expose
    private long comment_time;
    @Expose
    private String comment;
    @Expose
    private int news_id;
    @Expose
    private String avatar;

    public CommentObj() {
    }

    public CommentObj(int id, String fullname, long comment_time, String comment, int news_id, String avatar) {
        this.id = id;
        this.fullname = fullname;
        this.comment_time = comment_time;
        this.comment = comment;
        this.news_id = news_id;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public long getComment_time() {
        return comment_time;
    }

    public void setComment_time(long comment_time) {
        this.comment_time = comment_time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "CommentObj{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", comment_time=" + comment_time +
                ", comment='" + comment + '\'' +
                ", news_id=" + news_id +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
