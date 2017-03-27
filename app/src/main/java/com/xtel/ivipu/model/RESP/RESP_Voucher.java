package com.xtel.ivipu.model.RESP;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

/**
 * Created by vivhp on 2/24/2017.
 */

public class RESP_Voucher extends RESP_Basic {
    @Expose
    private String code;
    @Expose
    private String qr_code;
    @Expose
    private String bar_code;
    @Expose
    private long create_time;
    @Expose
    private long expired_time;
    @Expose
    private int status;

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

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getExpired_time() {
        return expired_time;
    }

    public void setExpired_time(long expired_time) {
        this.expired_time = expired_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RESP_Voucher{" +
                "code='" + code + '\'' +
                ", qr_code='" + qr_code + '\'' +
                ", bar_code='" + bar_code + '\'' +
                ", create_time=" + create_time +
                ", expired_time=" + expired_time +
                ", status=" + status +
                '}';
    }
}
