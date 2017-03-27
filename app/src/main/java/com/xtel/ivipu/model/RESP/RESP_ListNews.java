package com.xtel.ivipu.model.RESP;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/14/2017.
 */

public class RESP_ListNews extends com.xtel.nipservicesdk.model.entity.RESP_Basic {
    @Expose
    private ArrayList<RESP_NewEntity> data;

    public ArrayList<RESP_NewEntity> getData() {
        return data;
    }

    public void setData(ArrayList<RESP_NewEntity> data) {
        this.data = data;
    }
}
