package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 2/23/2017.
 */

public class RateObject {

    @Expose
    private int news_id;
    @Expose
    private double rates;

    public RateObject() {
    }

    public RateObject(int news_id, double rates) {
        this.news_id = news_id;
        this.rates = rates;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public double getRates() {
        return rates;
    }

    public void setRates(double rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "RateObject{" +
                "news_id:" + news_id +
                ", rates:" + rates +
                '}';
    }
}
