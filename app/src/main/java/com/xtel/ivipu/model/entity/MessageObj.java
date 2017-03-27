package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by vivhp on 3/15/2017.
 */

public class MessageObj implements Serializable {

    @Expose
    private String title;
    @Expose
    private String body;
    @Expose
    private int action;
    @Expose
    private String content;

    public MessageObj() {
    }

    public MessageObj(String title, String body, int action, String content) {
        this.title = title;
        this.body = body;
        this.action = action;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageObj{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", action=" + action +
                ", content='" + content + '\'' +
                '}';
    }
}
