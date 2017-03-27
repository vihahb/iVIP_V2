package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vuhavi on 11/03/2017.
 */

public class HistoryTransactionObj {

    @Expose
    private long action_time;
    @Expose
    private String action_desc;

    public HistoryTransactionObj() {
    }

    public HistoryTransactionObj(long action_time, String action_desc) {
        this.action_time = action_time;
        this.action_desc = action_desc;
    }

    public long getAction_time() {
        return action_time;
    }

    public void setAction_time(long action_time) {
        this.action_time = action_time;
    }

    public String getAction_desc() {
        return action_desc;
    }

    public void setAction_desc(String action_desc) {
        this.action_desc = action_desc;
    }

    @Override
    public String toString() {
        return "HistoryTransactionObj{" +
                "action_time=" + action_time +
                ", action_desc='" + action_desc + '\'' +
                '}';
    }
}
