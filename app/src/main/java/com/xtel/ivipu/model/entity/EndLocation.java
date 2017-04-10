package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by Lê Công Long Vũ on 12/7/2016.
 */

public class EndLocation {
    @Expose
    private Double lat;
    @Expose
    private Double lng;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
