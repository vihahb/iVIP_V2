package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 2/10/2017.
 */

public class ImageEntity {
    @Expose
    private String name;
    @Expose
    private String server_path;
    @Expose
    private String uri;

    public ImageEntity(String name, String server_path, String uri) {
        this.name = name;
        this.server_path = server_path;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer_path() {
        return server_path;
    }

    public void setServer_path(String server_path) {
        this.server_path = server_path;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "ImageEntity{" +
                "name='" + name + '\'' +
                ", server_path='" + server_path + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
