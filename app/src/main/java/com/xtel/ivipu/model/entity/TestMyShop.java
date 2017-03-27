package com.xtel.ivipu.model.entity;

/**
 * Created by vivhp on 2/7/2017.
 */

public class TestMyShop {

    private String score;

    private String rank;

    private String content_name;

    private String url_img_icon;

    private String url_img_content;

    private String url_img_brand;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getContent_name() {
        return content_name;
    }

    public void setContent_name(String content_name) {
        this.content_name = content_name;
    }

    public String getUrl_img_icon() {
        return url_img_icon;
    }

    public void setUrl_img_icon(String url_img_icon) {
        this.url_img_icon = url_img_icon;
    }

    public String getUrl_img_content() {
        return url_img_content;
    }

    public void setUrl_img_content(String url_img_content) {
        this.url_img_content = url_img_content;
    }

    public String getUrl_img_brand() {
        return url_img_brand;
    }

    public void setUrl_img_brand(String url_img_brand) {
        this.url_img_brand = url_img_brand;
    }

    @Override
    public String toString() {
        return "TestMyShop{" +
                "score='" + score + '\'' +
                ", rank='" + rank + '\'' +
                ", content_name='" + content_name + '\'' +
                ", url_img_icon='" + url_img_icon + '\'' +
                ", url_img_content='" + url_img_content + '\'' +
                ", url_img_brand='" + url_img_brand + '\'' +
                '}';
    }
}
