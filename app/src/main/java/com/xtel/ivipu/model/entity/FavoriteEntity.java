package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 2/10/2017.
 */

public class FavoriteEntity {
    @Expose
    private String favorite_name;
    @Expose
    private String favorite_address;
    @Expose
    private String favorite_time;
    @Expose
    private String favorite_date;
    @Expose
    private String favorite_view;
    @Expose
    private String favorite_like;
    @Expose
    private String favorite_comment;

    public FavoriteEntity() {
    }

    public FavoriteEntity(String favorite_name, String favorite_address, String favorite_time, String favorite_date, String favorite_view, String favorite_like, String favorite_comment) {
        this.favorite_name = favorite_name;
        this.favorite_address = favorite_address;
        this.favorite_time = favorite_time;
        this.favorite_date = favorite_date;
        this.favorite_view = favorite_view;
        this.favorite_like = favorite_like;
        this.favorite_comment = favorite_comment;
    }

    public String getFavorite_name() {
        return favorite_name;
    }

    public void setFavorite_name(String favorite_name) {
        this.favorite_name = favorite_name;
    }

    public String getFavorite_address() {
        return favorite_address;
    }

    public void setFavorite_address(String favorite_address) {
        this.favorite_address = favorite_address;
    }

    public String getFavorite_time() {
        return favorite_time;
    }

    public void setFavorite_time(String favorite_time) {
        this.favorite_time = favorite_time;
    }

    public String getFavorite_date() {
        return favorite_date;
    }

    public void setFavorite_date(String favorite_date) {
        this.favorite_date = favorite_date;
    }

    public String getFavorite_view() {
        return favorite_view;
    }

    public void setFavorite_view(String favorite_view) {
        this.favorite_view = favorite_view;
    }

    public String getFavorite_like() {
        return favorite_like;
    }

    public void setFavorite_like(String favorite_like) {
        this.favorite_like = favorite_like;
    }

    public String getFavorite_comment() {
        return favorite_comment;
    }

    public void setFavorite_comment(String favorite_comment) {
        this.favorite_comment = favorite_comment;
    }

    @Override
    public String toString() {
        return "FavoriteEntity{" +
                "favorite_comment='" + favorite_comment + '\'' +
                ", favorite_name='" + favorite_name + '\'' +
                ", favorite_address='" + favorite_address + '\'' +
                ", favorite_time='" + favorite_time + '\'' +
                ", favorite_date='" + favorite_date + '\'' +
                ", favorite_view='" + favorite_view + '\'' +
                ", favorite_like='" + favorite_like + '\'' +
                '}';
    }
}
