package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;

import com.xtel.ivipu.model.RESP.RESP_NewsObject;
import com.xtel.ivipu.model.RESP.RESP_Voucher;

/**
 * Created by vivhp on 1/24/2017.
 */

public interface IActivityInfo {
    void onShowQrCode(String url);
    void onShowBarCode(String url_bar_code);

    void onNetworkDisable();

    void showShortToast(String mes);

    void onGetNewsObjSuccess(RESP_NewsObject resp_newsObject);

    void onGetVoucherSuccess(RESP_Voucher voucher);

    void startActivityAndFinish(Class clazz);

    void onLikeSuccess();

    void onRateSuccess();

    Activity getActivity();
}
