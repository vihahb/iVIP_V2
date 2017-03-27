package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.xtel.ivipu.model.entity.MyShopCheckin;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/7/2017.
 */

public interface IMyShopActivity {

    void onSuccess();
    void onError();

    void onNetworkDisable();

    void startActivityAndFinish(Class clazz);

    void showShortToast(String mes);

    void onLoadMore();

    void onGetMyShopData(ArrayList<MyShopCheckin> arrayList);

    void onItemClick(int position, MyShopCheckin myShopCheckin, View view);

    Activity getActivity();
    Context getContext();
}
