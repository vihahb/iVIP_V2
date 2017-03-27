package com.xtel.ivipu.model.RESP;

import com.google.gson.annotations.Expose;
import com.xtel.ivipu.model.entity.CommentObj;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/18/2017.
 */

public class RESP_Get_Comment_data extends RESP_Basic {

    @Expose
    private ArrayList<CommentObj> data;

    public ArrayList<CommentObj> getData() {
        return data;
    }

    public void setData(ArrayList<CommentObj> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RESP_Get_Comment_data{" +
                "data=" + data +
                '}';
    }
}
