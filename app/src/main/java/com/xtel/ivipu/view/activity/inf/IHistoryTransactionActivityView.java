package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;
import android.content.Context;

import com.xtel.ivipu.model.entity.HistoryTransactionObj;

import java.util.ArrayList;

/**
 * Created by vuhavi on 11/03/2017.
 */

public interface IHistoryTransactionActivityView {

    void onGetTransactionSuccess(ArrayList<HistoryTransactionObj> arrayList);
    void onGetTransactionError();
    void showShortToast(String mes);
    void startActivityFinish(Class clazz);
    void onLoadMore();
    void onNetworkDisable();

    Activity getActivity();
    Context getContext();
}
