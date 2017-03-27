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
import com.xtel.ivipu.model.entity.MyShopCheckin;
import com.xtel.ivipu.presenter.FragmentMyShopPresenter;
import com.xtel.ivipu.view.activity.inf.IMyShopActivity;
import com.xtel.ivipu.view.adapter.AdapterCheckinHistory;
import com.xtel.ivipu.view.widget.ProgressView;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/7/2017.
 */

public class FragmentMyShop extends BasicFragment implements IMyShopActivity {

    public RecyclerView rcl_my_shop;
    AdapterCheckinHistory adapter;
    private ArrayList<MyShopCheckin> arr;
    private FragmentMyShopPresenter presenter;
    private ProgressView progressView;
    private int page = 1, pagesize = 10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_shop_checkin, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FragmentMyShopPresenter(this);
        initView(view);
        initProgressView(view);
    }

    private void initView(View view) {
        arr = new ArrayList<>();
        adapter = new AdapterCheckinHistory(arr, this);
        rcl_my_shop = (RecyclerView) view.findViewById(R.id.rcl_ivip);
        rcl_my_shop.setHasFixedSize(true);
        rcl_my_shop.setLayoutManager(new LinearLayoutManager(getContext()));
        rcl_my_shop.setAdapter(adapter);
    }

    private void initProgressView(View view) {
        progressView = new ProgressView(null, view);
        progressView.initData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again), getString(R.string.loading_data), Color.parseColor("#05b589"));
        progressView.setUpWithView(rcl_my_shop);

        progressView.onLayoutClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                adapter.onSetLoadMore(true);
                adapter.notifyChange();
                getData();
            }
        });

        progressView.onRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                arr.clear();
                adapter.notifyChange();
                adapter.onSetLoadMore(true);
                getData();
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
//        progressView.hideData();
        progressView.setRefreshing(true);
        adapter.onSetLoadMore(true);
        inirDataCheckIn();
    }

    private void setDataRecyclerView(ArrayList<MyShopCheckin> newsCheckinsArrayList) {
        arr.addAll(newsCheckinsArrayList);
        adapter.notifyChange();
    }

    private void inirDataCheckIn() {
        presenter.getMyShopCheckin(page, pagesize);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onNetworkDisable() {
        progressView.setRefreshing(false);
        progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_internet), getString(R.string.try_again));
        progressView.showData();
    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        super.startActivityAndFinish(clazz);
    }

    @Override
    public void onGetMyShopData(ArrayList<MyShopCheckin> arrayList) {
        Log.e("arr Shop entity", arrayList.toString());
        if (arrayList.size() < 10) {
            adapter.onSetLoadMore(false);
        }
        setDataRecyclerView(arrayList);
        checkListData();
    }

    private void checkListData() {
//        progressView.disableSwipe();
        progressView.setRefreshing(false);
        if (arr.size() == 0) {
            progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again));
            progressView.show();
        } else {
            adapter.notifyChange();
            progressView.hide();
        }
    }

    @Override
    public void onItemClick(int position, MyShopCheckin myShopCheckin, View view) {

    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void onLoadMore() {
        page++;
        getData();
    }
}
