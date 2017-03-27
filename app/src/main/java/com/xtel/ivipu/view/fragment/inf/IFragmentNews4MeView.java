package com.xtel.ivipu.view.fragment.inf;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.xtel.ivipu.model.RESP.RESP_NewEntity;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/28/2017.
 */

public interface IFragmentNews4MeView {

    void onLoadNews4MeSuccess(ArrayList<RESP_NewEntity> arrayList);

    void onLoadNews4MeError();

    void onItemClick(int position, RESP_NewEntity newEntity, View view);

    void onLoadMore();

    void onNetworkDisable();

    void showShortToast(String mes);

    void startActivityAndFinish(Class clazz);

    Activity getActivity();

    Context getContext();
}
