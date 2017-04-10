package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by Mr. M.2 on 12/7/2016.
 */

public class Polyline {
    @Expose
    private String points;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
