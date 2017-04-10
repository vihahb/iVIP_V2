package com.xtel.ivipu.model.RESP;

import com.google.gson.annotations.Expose;
import com.xtel.ivipu.model.entity.HotSaleNewsObj;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

import java.util.ArrayList;

/**
 * Created by vivhp on 4/8/2017.
 */

public class RESP_NewsHotSales extends RESP_Basic {

    @Expose
    private ArrayList<HotSaleNewsObj> data;

    public ArrayList<HotSaleNewsObj> getData() {
        return data;
    }

    public void setData(ArrayList<HotSaleNewsObj> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RESP_NewsHotSales{" +
                "data=" + data +
                '}';
    }
}
