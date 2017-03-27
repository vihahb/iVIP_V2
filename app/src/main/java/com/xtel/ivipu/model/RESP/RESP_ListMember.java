package com.xtel.ivipu.model.RESP;

import com.google.gson.annotations.Expose;
import com.xtel.ivipu.model.entity.MemberObj;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

import java.util.ArrayList;

/**
 * Created by vuhavi on 07/03/2017.
 */

public class RESP_ListMember extends RESP_Basic {

    @Expose
    private ArrayList<MemberObj> data;

    public ArrayList<MemberObj> getData() {
        return data;
    }

    public void setData(ArrayList<MemberObj> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RESP_ListMember{" +
                "data=" + data +
                '}';
    }
}
