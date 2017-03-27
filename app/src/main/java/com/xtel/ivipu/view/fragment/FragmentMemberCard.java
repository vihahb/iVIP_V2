package com.xtel.ivipu.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.MemberObj;
import com.xtel.ivipu.presenter.FragmentNavMemberCardPresenter;
import com.xtel.ivipu.view.activity.ActivityInfoContent;
import com.xtel.ivipu.view.activity.HistoryTransactionsActivity;
import com.xtel.ivipu.view.adapter.AdapterMemberCard;
import com.xtel.ivipu.view.fragment.inf.IFragmentMemberCard;
import com.xtel.ivipu.view.widget.ProgressView;
import com.xtel.sdk.commons.Constants;

import java.util.ArrayList;

/**
 * Created by vuhavi on 07/03/2017.
 */

public class FragmentMemberCard extends BasicFragment implements IFragmentMemberCard {

    private int page = 1, pagesize = 5;
    private FragmentNavMemberCardPresenter presenter;
    private RecyclerView rcl_member_card;
    private AdapterMemberCard adapter;
    private ArrayList<MemberObj> arrayList;
    private ProgressView progressView;
    private int REQUEST_VIEW_CARD = 66;
    private int position = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nav_member_card, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FragmentNavMemberCardPresenter(this);
        initRecyclerView(view);
        initProgressView(view);
    }

    private void initRecyclerView(View view) {
        arrayList = new ArrayList<>();
        Log.e("arr member object ", arrayList.toString());
        rcl_member_card = (RecyclerView) view.findViewById(R.id.rcl_ivip);
        rcl_member_card.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcl_member_card.setLayoutManager(layoutManager);
        adapter = new AdapterMemberCard(arrayList, this);
        rcl_member_card.setAdapter(adapter);
    }


    private void initProgressView(View view) {
        progressView = new ProgressView(null, view);
        progressView.initData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again), getString(R.string.loading_data), Color.parseColor("#05b589"));
        progressView.setUpWithView(rcl_member_card);

        progressView.onLayoutClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        progressView.onRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                arrayList.clear();
                getData();
                adapter.notifyDataSetChanged();
            }
        });

        progressView.onSwipeLayoutPost(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        });
    }

    private void getData() {
        progressView.setRefreshing(true);
        initDataMemberCard();
    }

    private void initDataMemberCard() {
        presenter.getMemberCard(page, pagesize);
    }

    @Override
    public void onGetMemberCardSuccess(ArrayList<MemberObj> arrayList) {
        Log.e("arr news entity", arrayList.toString());
        if (arrayList.size() < 10) {
            adapter.onSetLoadMore(false);
        }
        this.arrayList.addAll(arrayList);
//        adapter.notifyDataSetChanged();
        checkListData();
    }

    @SuppressWarnings("CollectionAddedToSelf")
    private void setDataRecyclerView(ArrayList<MemberObj> arrayList) {
        arrayList.addAll(arrayList);
        adapter.notifyDataSetChanged();
    }

    private void checkListData() {
        progressView.setRefreshing(false);

        if (arrayList.size() == 0) {
            progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again));
            progressView.show();
        } else {
//            rcl_member_card.getAdapter().notifyDataSetChanged();
            adapter.notifyDataSetChanged();
            progressView.hide();
        }
    }

    @Override
    public void onGetMemberCardError() {

    }

    @Override
    public void showShortToast(String mes) {
        super.showShortToast(mes);
    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        super.startActivityAndFinish(clazz);
    }

    @Override
    public void onLoadMore() {
        page++;
        getData();
    }

    @Override
    public void onClickCardItem(int position, MemberObj memberObj, View view) {
        this.position = position;
        startActivityForResultObject(HistoryTransactionsActivity.class, Constants.RECYCLER_MODEL, memberObj, REQUEST_VIEW_CARD);
    }

    @Override
    public void onNetworkDisable(){
        progressView.setRefreshing(false);
        progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_internet), getString(R.string.try_again));
        progressView.showData();
    }
}
