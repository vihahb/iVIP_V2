package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by vihahb on 1/10/2017.
 */

public interface ILoginGroup {

    void onSuccess();

    void onError();

    void showShortToast(String mes);

    void startActivity(Class clazz);

    void startActivityForResults(Intent intent, int requestCode);

    void startActivitys(Intent intent);

    void finishActivity();

    Activity getActivity();

    Context getContext();

    void onNetworkDisable();
}
