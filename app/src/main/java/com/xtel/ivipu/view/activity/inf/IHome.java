package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;

import com.xtel.ivipu.model.RESP.RESP_Short;
import com.xtel.ivipu.model.entity.HotSaleNewsObj;

import java.util.ArrayList;

/**
 * Created by vivhp on 12/29/2016.
 */

public interface IHome {

    void showShortToast(String mes);

    void onGetNew(ArrayList<HotSaleNewsObj> arrayList);

    void showLongToast(String mes);

    void startActivty(Class clazz);

    void startActivityFinish(Class clazz);

    void getShortUser(RESP_Short userShort);

    void getSuccessUser(String avatar, String qr_code, String fullname);

    void onNetworkDisable();

    void onShowQrCode(String url);

    Activity getActivity();
}
