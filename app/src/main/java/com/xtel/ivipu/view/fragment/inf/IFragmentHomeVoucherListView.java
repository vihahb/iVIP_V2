package com.xtel.ivipu.view.fragment.inf;

import android.app.Activity;
import android.content.Context;

import com.xtel.ivipu.model.entity.VoucherListObj;

import java.util.ArrayList;

/**
 * Created by vivhp on 4/5/2017.
 */

public interface IFragmentHomeVoucherListView {
    void onGetVoucherSuccess(ArrayList<VoucherListObj> arrayList);

    void onGetVoucherError();

    void showShortToast(String mes);

    void startActivityAndFinish(Class clazz);

    void onNetworkDisable();

    void onLoadMore();

    Activity getActivity();

    Context getContext();
}
