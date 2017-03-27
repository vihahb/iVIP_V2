package com.xtel.ivipu.view.fragment.inf;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.xtel.ivipu.model.RESP.RESP_NewEntity;

import java.util.ArrayList;

/**
 * Created by vivhp on 1/23/2017.
 */

public interface IFragmentFashionView {

    void onLoadMore();

    void showShortToast(String mes);

    void showLongToast(String mes);

    void onNetworkDisable();

    void onGetNewsListSuccess(ArrayList<RESP_NewEntity> newEntities);

    void onGetNewsListError();

    void startActivityAndFinish(Class clazz);

    void onItemClick(int position, RESP_NewEntity newEntities, View view);

    Activity getActivity();

    Context getContext();
}
