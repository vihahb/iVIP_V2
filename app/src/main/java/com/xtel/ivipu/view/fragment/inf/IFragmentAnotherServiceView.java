package com.xtel.ivipu.view.fragment.inf;

import android.app.Activity;
import android.view.View;

import com.xtel.ivipu.model.RESP.RESP_NewEntity;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/22/2017.
 */

public interface IFragmentAnotherServiceView {

    void onLoadMore();

    void onNetworkDisable();

    void getServiceSuccess(ArrayList<RESP_NewEntity> arrayList);

    void getServiceError();

    void startActivityAndFinish(Class clazz);

    void showShortToast(String mes);

    void onItemClick(int position, RESP_NewEntity newObjEntity, View view);

    Activity getActivity();
}
