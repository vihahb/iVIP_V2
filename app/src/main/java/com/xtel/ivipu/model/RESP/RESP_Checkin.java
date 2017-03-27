package com.xtel.ivipu.model.RESP;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

/**
 * Created by vivhp on 2/17/2017.
 */

public class RESP_Checkin extends RESP_Basic {
    @Expose
    private String store_name;
    @Expose
    private String store_logo;
    @Expose
    private int point;

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_logo() {
        return store_logo;
    }

    public void setStore_logo(String store_logo) {
        this.store_logo = store_logo;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "RESP_Checkin{" +
                "store_name='" + store_name + '\'' +
                ", store_logo='" + store_logo + '\'' +
                ", point=" + point +
                '}';
    }
}
