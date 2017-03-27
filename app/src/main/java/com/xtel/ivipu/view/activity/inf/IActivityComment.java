package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;
import android.content.Context;

import com.xtel.ivipu.model.entity.CommentObj;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/18/2017.
 */

public interface IActivityComment {

    void onGetCommentSuccess(ArrayList<CommentObj> arrayList);

    void onGetCommentError(String mes);

    void postCommentSuccess();

    void postCommentError();

    void onNetworkDisable();

    void startActivityAndFinish(Class clazz);

    void onLoadMore();

    void showShortToast(String mes);

    Activity getActivity();

    Context getContext();

}
