package com.xtel.ivipu.model.entity;

import java.io.Serializable;

/**
 * Created by vihahb on 1/16/2017.
 */

public class TestRecycle implements Serializable {

    private String shopName;
    private String shopMenber;
    private String shopLocation;
    private String shopComment;
    private int bg_position;

    public TestRecycle() {

    }

    public TestRecycle(String shopName, String shopMenber, String shopLocation, String shopComment, int bg_position) {
        this.shopName = shopName;
        this.shopMenber = shopMenber;
        this.shopLocation = shopLocation;
        this.shopComment = shopComment;
        this.bg_position = bg_position;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopMenber() {
        return shopMenber;
    }

    public void setShopMenber(String shopMenber) {
        this.shopMenber = shopMenber;
    }

    public String getShopLocation() {
        return shopLocation;
    }

    public void setShopLocation(String shopLocation) {
        this.shopLocation = shopLocation;
    }

    public String getShopComment() {
        return shopComment;
    }

    public void setShopComment(String shopComment) {
        this.shopComment = shopComment;
    }

    public int getBg_position() {
        return bg_position;
    }

    public void setBg_position(int bg_position) {
        this.bg_position = bg_position;
    }

    @Override
    public String toString() {
        return "TestRecycle{" +
                "shopName='" + shopName + '\'' +
                ", shopMenber='" + shopMenber + '\'' +
                ", shopLocation='" + shopLocation + '\'' +
                ", shopComment='" + shopComment + '\'' +
                ", bg_position=" + bg_position +
                '}';
    }
}
