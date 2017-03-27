package com.xtel.ivipu.view.fragment.inf;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.xtel.ivipu.model.RESP.RESP_NewEntity;

import java.util.ArrayList;

/**
 * Created by vivhp on 1/23/2017.
 */

public interface IFragmentFoodView {

    void onLoadMore();

    void onGetFoodSuccess(ArrayList<RESP_NewEntity> arrayList);

    void onGetFoodError();

    void startActivityAndFinish(Class clazz);

    void showShortToast(String mes);

    void showLongToast(String mes);

    void onItemClick(int position, RESP_NewEntity newObjEntity, View view);

    void onNetworkDisable();

    Activity getActivity();

    Context getContext();
}
