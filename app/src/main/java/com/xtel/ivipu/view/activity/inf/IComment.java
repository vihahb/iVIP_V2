package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;
import android.content.Context;

/**
 * Created by vivhp on 2/22/2017.
 */

public interface IComment {

    void onPostCommentSuccess();

    void onPostCommentError();

    void showShortToast(String mes);

    void startActivityAndFinish(Class clazz);

    void onNetworkDisable();

    Activity getActivity();

    Context getContext();
}
