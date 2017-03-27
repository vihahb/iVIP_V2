package com.xtel.ivipu.view.fragment.inf;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.xtel.ivipu.model.RESP.RESP_NewEntity;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/24/2017.
 */

public interface IFragmentSuggestionView {

    void onGetSucggestionSuccess(ArrayList<RESP_NewEntity> arrayList);

    void onGetSuggestionError();

    void showShortToast(String mes);

    void startActivityAndFinish(Class clazz);

    void onNetworkDisable();

    void onLoadMore();

    void onItemClick(int position, RESP_NewEntity newEntity, View view);

    Activity getActivity();

    Context getContext();
}
