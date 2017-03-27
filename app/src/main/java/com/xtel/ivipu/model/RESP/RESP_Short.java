package com.xtel.ivipu.model.RESP;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 2/13/2017.
 */

public class RESP_Short extends com.xtel.nipservicesdk.model.entity.RESP_Basic {
    @Expose
    private String fullname;
    @Expose
    private String avatar;
    @Expose
    private int new_notify;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getNew_notify() {
        return new_notify;
    }

    public void setNew_notify(int new_notify) {
        this.new_notify = new_notify;
    }

    @Override
    public String toString() {
        return "RESP_Short{" +
                "fullname='" + fullname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", new_notify=" + new_notify +
                '}';
    }
}
