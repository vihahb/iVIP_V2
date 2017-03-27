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
import com.xtel.ivipu.model.entity.HistoryEntity;
import com.xtel.ivipu.presenter.FragmentProfileHistoryPresenter;
import com.xtel.ivipu.view.activity.ActivityInfoContent;
import com.xtel.ivipu.view.adapter.AdapterRecycleHistory;
import com.xtel.ivipu.view.fragment.inf.IFragmentHistoryView;
import com.xtel.ivipu.view.widget.ProgressView;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.sdk.commons.Constants;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/13/2017.
 */

public class HistoryFragment extends BasicFragment implements IFragmentHistoryView {

    RecyclerView rcl_history;
    ArrayList<RESP_NewEntity> arrayList;
    private LinearLayoutManager layoutManager;
    private ProgressView progressView;
    private int page = 1, pagesize = 4;
    AdapterRecycleHistory adapter;
    FragmentProfileHistoryPresenter presenter;
    int position = -1;
    private int REQUEST_VIEW_HISTORY = 89;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FragmentProfileHistoryPresenter(this);
        initRecycleView(view);
        initProgressView(view);
    }

    private void initRecycleView(View view) {
        arrayList = new ArrayList<>();
        rcl_history = (RecyclerView) view.findViewById(R.id.rcl_ivip);

        Log.e("arr food object ", JsonHelper.toJson(arrayList));

        rcl_history.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rcl_history.setLayoutManager(layoutManager);
        adapter = new AdapterRecycleHistory(arrayList, this);
        rcl_history.setAdapter(adapter);
    }

    private void initProgressView(View view) {
        progressView = new ProgressView(null, view);
        progressView.initData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again), getString(R.string.loading_data), Color.parseColor("#05b589"));
        progressView.setUpWithView(rcl_history);

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

    private void setDataRecyclerView(ArrayList<RESP_NewEntity> newEntities) {
        arrayList.addAll(newEntities);
        adapter.notifyDataSetChanged();
    }

    private void getData() {
        progressView.setRefreshing(true);
        initHistoryData();
    }

    private void initHistoryData() {
        presenter.getHistory(page, pagesize);
    }

    @Override
    public void onGetHistorySuccess(ArrayList arrayList) {
        Log.e("arr news entity", arrayList.toString());

        if (arrayList.size() < 4) {
            adapter.onSetLoadMore(false);
        }

        setDataRecyclerView(arrayList);

        checkListData();
    }

    private void checkListData() {
        progressView.setRefreshing(false);

        if (arrayList.size() == 0) {
            progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again));
            progressView.show();
        } else {
            rcl_history.getAdapter().notifyDataSetChanged();
            progressView.hide();
        }
    }

    @Override
    public void onGetHistoryError() {

    }

    @Override
    public void showShortToast(String mes) {
        super.showShortToast(mes);
    }

    @Override
    public void showLongToast(String mes) {

    }

    @Override
    public void onItemClick(int position, RESP_NewEntity newObjEntity, View view) {
        this.position = position;
        startActivityForResultObject(ActivityInfoContent.class, Constants.RECYCLER_MODEL, newObjEntity, REQUEST_VIEW_HISTORY);
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


}
