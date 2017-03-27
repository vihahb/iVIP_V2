package com.xtel.ivipu.model.RESP;

import com.google.gson.annotations.Expose;
import com.xtel.ivipu.model.entity.Shop_Address;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

import java.util.ArrayList;

/**
 * Created by vivhp on 3/1/2017.
 */

public class RESP_Address_Arr extends RESP_Basic {

    @Expose
    private ArrayList<Shop_Address> data;

    public ArrayList<Shop_Address> getData() {
        return data;
    }

    public void setData(ArrayList<Shop_Address> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RESP_Address_Arr{" +
                "data=" + data +
                '}';
    }
}
