package com.xtel.ivipu.model.RESP;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 2/8/2017.
 */

public class RESP_Profile extends com.xtel.nipservicesdk.model.entity.RESP_Basic {

    @Expose
    private String code;
    @Expose
    private String qr_code;
    @Expose
    private String bar_code;
    @Expose
    private int gender;
    @Expose
    private long birthday;
    @Expose
    private String address;
    @Expose
    private String avatar;
    @Expose
    private String email;
    @Expose
    private String phonenumber;
    @Expose
    private int status;
    @Expose
    private int general_point;
    @Expose
    private String level;
    @Expose
    private long join_date;
    @Expose
    private String fullname;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getGeneral_point() {
        return general_point;
    }

    public void setGeneral_point(int general_point) {
        this.general_point = general_point;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public long getJoin_date() {
        return join_date;
    }

    public void setJoin_date(long join_date) {
        this.join_date = join_date;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return "RESP_Profile{" +
                "code='" + code + '\'' +
                ", qr_code='" + qr_code + '\'' +
                ", bar_code='" + bar_code + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", status=" + status +
                ", general_point=" + general_point +
                ", level='" + level + '\'' +
                ", join_date=" + join_date +
                ", fullname='" + fullname + '\'' +
                '}';
    }
}
