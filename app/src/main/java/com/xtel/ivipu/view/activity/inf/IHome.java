package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;

import com.xtel.ivipu.model.RESP.RESP_Short;

/**
 * Created by vivhp on 12/29/2016.
 */

public interface IHome {

    void showShortToast(String mes);

    void showLongToast(String mes);

    void startActivty(Class clazz);

    void startActivityFinish(Class clazz);

    void getShortUser(RESP_Short userShort);

    void onNetworkDisable();

    Activity getActivity();
}
