package com.xtel.ivipu.model.RESP;

import com.google.gson.annotations.Expose;
import com.xtel.ivipu.model.entity.HistoryTransactionObj;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

import java.util.ArrayList;

/**
 * Created by vuhavi on 11/03/2017.
 */

public class RESP_ListHistoryTransaction extends RESP_Basic {

    @Expose
    private ArrayList<HistoryTransactionObj> data;

    public ArrayList<HistoryTransactionObj> getData() {
        return data;
    }

    public void setData(ArrayList<HistoryTransactionObj> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RESP_ListHistoryTransaction{" +
                "data=" + data +
                '}';
    }
}
