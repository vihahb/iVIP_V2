package com.xtel.ivipu.model.RESP;

import com.google.gson.annotations.Expose;
import com.xtel.ivipu.model.entity.VoucherListObj;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

import java.util.ArrayList;

/**
 * Created by vivhp on 4/5/2017.
 */

public class RESP_Voucher_list extends RESP_Basic {
    @Expose
    private ArrayList<VoucherListObj> data;

    public ArrayList<VoucherListObj> getData() {
        return data;
    }

    public void setData(ArrayList<VoucherListObj> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RESP_Voucher_list{" +
                "data=" + data +
                '}';
    }
}
