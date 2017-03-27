package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;
import android.content.Context;

/**
 * Created by vihahb on 1/11/2017.
 */

public interface IResetView {

    void onSuccess();

    void onError();

    void showShortToast(String mes);

    void finishActivity();


    Activity getActivity();

    Context getContext();

    void onNetworkDisable();
}
