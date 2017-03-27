package com.xtel.ivipu.model.RESP;

import com.google.gson.annotations.Expose;
import com.xtel.ivipu.model.entity.MyShopCheckin;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/17/2017.
 */

public class RESP_MyShopCheckin extends RESP_Basic {

    @Expose
    private ArrayList<MyShopCheckin> data;

    public ArrayList<MyShopCheckin> getData() {
        return data;
    }

    public void setData(ArrayList<MyShopCheckin> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RESP_MyShopCheckin{" +
                "data=" + data +
                '}';
    }
}
