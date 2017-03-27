package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 3/20/2017.
 */

public class NotificationActionObj {
    @Expose
    private int action;
    @Expose
    private String content;

    public NotificationActionObj() {
    }

    public NotificationActionObj(int action, String content) {
        this.action = action;
        this.content = content;
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
        return "NotificationActionObj{" +
                "action=" + action +
                ", content='" + content + '\'' +
                '}';
    }
}
