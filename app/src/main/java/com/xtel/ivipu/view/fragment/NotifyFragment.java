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
import com.xtel.ivipu.model.RESP.RESP_NewEntity;
import com.xtel.ivipu.presenter.NotifyPresenter;
import com.xtel.ivipu.view.activity.ActivityInfoContent;
import com.xtel.ivipu.view.adapter.AdapterNotify;
import com.xtel.ivipu.view.fragment.inf.IFragmentNotify;
import com.xtel.ivipu.view.widget.ProgressView;
import com.xtel.sdk.commons.Constants;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/13/2017.
 */

public class NotifyFragment extends BasicFragment implements IFragmentNotify {

    private NotifyPresenter presenter;
    private ProgressView progressView;
    private RecyclerView rcl_notify;
    private ArrayList<RESP_NewEntity> arrayListNotify;
    private int position = -1;
    private int REQUEST_VIEW_NOTIFY = 12;
    private int page = 1, pagesize = 4;
    private LinearLayoutManager layoutManager;
    private AdapterNotify adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_notify, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new NotifyPresenter(this);
        initRecylerView(view);
        initProgressView(view);
    }

    private void initProgressView(View view) {
        progressView = new ProgressView(null, view);
        progressView.initData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again), getString(R.string.loading_data), Color.parseColor("#05b589"));
        progressView.setUpWithView(rcl_notify);

        progressView.onLayoutClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                adapter.onSetLoadMore(true);
                adapter.notifyDataSetChanged();
                getData();
            }
        });

        progressView.onRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                arrayListNotify.clear();
                adapter.onSetLoadMore(true);
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

    private void initRecylerView(View view) {
        arrayListNotify = new ArrayList<>();
        rcl_notify = (RecyclerView) view.findViewById(R.id.rcl_ivip);
        rcl_notify.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rcl_notify.setLayoutManager(layoutManager);

        arrayListNotify = new ArrayList<>();
        adapter = new AdapterNotify(arrayListNotify, this);
        rcl_notify.setAdapter(adapter);
    }

    private void setDataRecyclerView(ArrayList<RESP_NewEntity> newEntities) {
        arrayListNotify.addAll(newEntities);
        adapter.notifyDataSetChanged();
    }

    private void getData() {
//        progressView.hideData();
        progressView.setRefreshing(true);
        initDataNews();
    }

    private void initDataNews() {
        presenter.getNotification(page, pagesize);
    }

    @Override
    public void onGetNotificationSuccess(ArrayList<RESP_NewEntity> arrayList) {
        Log.e("arr news entity", arrayList.toString());
        if (arrayList.size() < 4) {
            adapter.onSetLoadMore(false);
        }
        setDataRecyclerView(arrayList);
        checkListData();
    }

    private void checkListData() {
        progressView.setRefreshing(false);

        if (arrayListNotify.size() == 0) {
            progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again));
            progressView.show();
        } else {
            rcl_notify.getAdapter().notifyDataSetChanged();
            adapter.notifyDataSetChanged();
            progressView.hide();
        }
    }

    @Override
    public void onGetNotificationError() {

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
    public void onNetworkDisable() {
        progressView.setRefreshing(false);
        progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_internet), getString(R.string.try_again));
        progressView.showData();
    }

    @Override
    public void onLoadMore() {
        page++;
        getData();
    }

    @Override
    public void onItemClick(int position, RESP_NewEntity newsEntity, View v) {
        this.position = position;
        startActivityForResultObject(ActivityInfoContent.class, Constants.RECYCLER_MODEL, newsEntity, REQUEST_VIEW_NOTIFY);
    }
    
}
