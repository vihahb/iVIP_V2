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

public interface IFragmentFavoriteView {
    void onGetFavoriteSuccess(ArrayList<RESP_NewEntity> arrayList);

    void onGetFavoriteError();

    void showShortToast(String mes);

    void showLongToast(String mes);

    void startActivityAndFinish(Class clazz);

    void onNetworkDisable();

    void onLoadMore();

    void onItemClick(int position, RESP_NewEntity newEntity, View view);

    Activity getActivity();
    Context getContext();
}
