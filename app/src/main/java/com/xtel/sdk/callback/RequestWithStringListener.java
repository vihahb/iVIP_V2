package com.xtel.sdk.callback;

/**
 * Created by Vũ Hà Vi on 12/1/2016.
 */

public interface RequestWithStringListener {
    void onSuccess(String url, String server_path);

    void onError();
}