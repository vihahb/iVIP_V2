package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;
import android.content.Context;

/**
 * Created by vihahb on 1/10/2017.
 */

public interface ILoginView {

    void onSuccess();

    void onEror();

    void showShortToast(String mes);

    void startActivitys(Class clazz);

    void startActivityAndFinish(Class clazz);

    Activity getActivity();

    Context getContext();

    void onNetworkDisable();
}
