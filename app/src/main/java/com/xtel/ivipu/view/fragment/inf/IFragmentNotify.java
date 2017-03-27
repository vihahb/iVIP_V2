package com.xtel.ivipu.view.fragment.inf;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.xtel.ivipu.model.RESP.RESP_NewEntity;

import java.util.ArrayList;

/**
 * Created by vivhp on 3/17/2017.
 */

public interface IFragmentNotify {
    void onGetNotificationSuccess(ArrayList<RESP_NewEntity> arrayList);

    void onGetNotificationError();

    void showShortToast(String mes);

    void startActivityAndFinish(Class clazz);

    void onNetworkDisable();

    Activity getActivity();

    Context getContext();

    void onLoadMore();

    void onItemClick(int position, RESP_NewEntity newsEntity, View v);
}
