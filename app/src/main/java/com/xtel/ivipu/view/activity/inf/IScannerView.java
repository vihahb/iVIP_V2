package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;

import com.xtel.ivipu.model.RESP.RESP_Checkin;

/**
 * Created by vivhp on 2/15/2017.
 */

public interface IScannerView {
    void onStartChecking();

    void onResumeScanner();

    void showDialogNotification(String mes, String content);

    void onCheckinSuccess(RESP_Checkin respCheckin);

    void onCheckinError(Error error);

    void startActivityFinish(Class clazz);

    void showShortToast(String mes);

    Activity getActivity();

    void onNetworkDisable();
}
