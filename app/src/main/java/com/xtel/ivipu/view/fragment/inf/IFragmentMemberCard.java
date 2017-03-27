package com.xtel.ivipu.view.fragment.inf;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.xtel.ivipu.model.entity.MemberObj;

import java.util.ArrayList;

/**
 * Created by vuhavi on 07/03/2017.
 */

public interface IFragmentMemberCard {

    void onGetMemberCardSuccess(ArrayList<MemberObj> arrayList);
    void onGetMemberCardError();
    void onNetworkDisable();
    void showShortToast(String mes);
    void startActivityAndFinish(Class clazz);
    void onLoadMore();
    void onClickCardItem(int position, MemberObj memberObj, View view);

    Activity getActivity();
    Context getContext();
}
