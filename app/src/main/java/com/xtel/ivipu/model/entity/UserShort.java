package com.xtel.ivipu.model.entity;

/**
 * Created by vivhp on 2/13/2017.
 */

public class UserShort {

    private String avatar;
    private String fullname;
    private int new_notify;

    public UserShort() {
    }

    public UserShort(String avatar, String fullname, int new_notify) {
        this.avatar = avatar;
        this.fullname = fullname;
        this.new_notify = new_notify;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getNew_notify() {
        return new_notify;
    }

    public void setNew_notify(int new_notify) {
        this.new_notify = new_notify;
    }

    @Override
    public String toString() {
        return "UserShort{" +
                "avatar='" + avatar + '\'' +
                ", fullname='" + fullname + '\'' +
                ", new_notify=" + new_notify +
                '}';
    }
}
