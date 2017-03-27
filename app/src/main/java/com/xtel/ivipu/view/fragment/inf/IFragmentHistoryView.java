package com.xtel.ivipu.view.fragment.inf;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.xtel.ivipu.model.RESP.RESP_NewEntity;
import com.xtel.ivipu.model.entity.HistoryEntity;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/10/2017.
 */

public interface IFragmentHistoryView {
    void onGetHistorySuccess(ArrayList arrayList);

    void onGetHistoryError();

    void showShortToast(String mes);

    void showLongToast(String mes);

    void onItemClick(int position, RESP_NewEntity newObjEntity, View view);

    void startActivityAndFinish(Class clazz);

    void onNetworkDisable();

    void onLoadMore();

    Activity getActivity();
    Context getContext();
}
