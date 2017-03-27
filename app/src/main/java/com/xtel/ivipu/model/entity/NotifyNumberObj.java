package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

/**
 * Created by vivhp on 3/25/2017.
 */

public class NotifyNumberObj extends RESP_Basic {
    @Expose
    private int notify;

    public int getNotify() {
        return notify;
    }

    public void setNotify(int notify) {
        this.notify = notify;
    }

    @Override
    public String toString() {
        return "NotifyNumberObj{" +
                "notify=" + notify +
                '}';
    }
}
