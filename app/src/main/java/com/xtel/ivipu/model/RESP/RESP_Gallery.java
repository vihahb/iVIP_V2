package com.xtel.ivipu.model.RESP;

import com.google.gson.annotations.Expose;
import com.xtel.ivipu.model.entity.GalleryObj;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/27/2017.
 */

public class RESP_Gallery extends RESP_Basic {

    @Expose
    private ArrayList<GalleryObj> data;

    public ArrayList<GalleryObj> getData() {
        return data;
    }

    public void setData(ArrayList<GalleryObj> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RESP_Gallery{" +
                "data=" + data +
                '}';
    }
}
