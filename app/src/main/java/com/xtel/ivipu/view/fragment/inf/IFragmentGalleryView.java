package com.xtel.ivipu.view.fragment.inf;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.xtel.ivipu.model.RESP.RESP_NewsObject;
import com.xtel.ivipu.model.entity.GalleryObj;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/27/2017.
 */

public interface IFragmentGalleryView {

    void onLoadImageSuccess(ArrayList<GalleryObj> arrayList);

    void onLoadImageError();

    void showShortToast(String mes);

    void onNetworkDisable();

    void onClickItem(int position, GalleryObj galleryObj, View view);

    void onGetNewsObjSuccess(RESP_NewsObject object);

    void onLoadMore();

    void startActivityAndFinish(Class clazz);

    Activity getActivity();

    Context getContext();
}
