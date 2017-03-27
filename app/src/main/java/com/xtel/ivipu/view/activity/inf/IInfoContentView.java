package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;
import android.content.Context;

/**
 * Created by vihahb on 1/17/2017.
 */

public interface IInfoContentView {

    void onSuccess();

    void onError();

    void showShortToast(String mes);

    void showLongToast(String mes);

    Activity getActivity();

    Context getContext();

}
