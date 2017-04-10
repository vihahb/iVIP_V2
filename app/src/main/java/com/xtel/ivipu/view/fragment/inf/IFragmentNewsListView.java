package com.xtel.ivipu.view.fragment.inf;

import android.app.Activity;
import android.view.View;

import com.xtel.ivipu.model.RESP.RESP_NewEntity;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/17/2017.
 */

public interface IFragmentNewsListView {

    void onGetNewsListSuccess(ArrayList<RESP_NewEntity> arrayList);

    void onGetNewsListErr();


    void startActivityAndFinish(Class clazz);

    void onLoadMore();

    void showShortToast(String mes);

    void showLongToast(String mes);

    void onItemClick(int position, RESP_NewEntity testRecycle, View view);

    void onNetworkDisable();

    Activity getActivity();
}
