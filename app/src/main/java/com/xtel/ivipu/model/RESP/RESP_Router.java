package com.xtel.ivipu.model.RESP;

import com.google.gson.annotations.Expose;
import com.xtel.ivipu.model.entity.Routers;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

import java.util.ArrayList;

/**
 * Created by Lê Công Long Vũ on 12/7/2016
 */

public class RESP_Router extends RESP_Basic {
    @Expose
    private ArrayList<Routers> routes;

    public ArrayList<Routers> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Routers> routes) {
        this.routes = routes;
    }
}
