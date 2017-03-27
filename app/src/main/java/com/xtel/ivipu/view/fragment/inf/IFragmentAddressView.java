package com.xtel.ivipu.view.fragment.inf;

import android.app.Activity;
import android.content.Context;

import com.xtel.ivipu.model.RESP.RESP_NewsObject;
import com.xtel.ivipu.model.entity.Shop_Address;

import java.util.ArrayList;

/**
 * Created by vivhp on 3/1/2017.
 */

public interface IFragmentAddressView {

    void onGetAddressSuccess(ArrayList<Shop_Address> arrayList);

    void onGetNewsObjSuccess(RESP_NewsObject object);

    void onGetAddressError();

    void showShortToast(String mes);

    void startActivityAndFinish(Class clazz);

    Activity getActivity();

    Context getContext();

    void onNetworkDisable();
}
