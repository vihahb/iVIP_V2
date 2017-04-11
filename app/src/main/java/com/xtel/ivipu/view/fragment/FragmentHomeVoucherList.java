package com.xtel.ivipu.view.fragment;

import android.app.Activity;
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
import com.xtel.ivipu.model.entity.VoucherListObj;
import com.xtel.ivipu.presenter.FragmentVoucherListPresenter;
import com.xtel.ivipu.view.adapter.AdapterVoucherList;
import com.xtel.ivipu.view.fragment.inf.IFragmentHomeVoucherListView;
import com.xtel.ivipu.view.widget.ProgressView;

import java.util.ArrayList;

/**
 * Created by vivhp on 4/5/2017.
 */

public class FragmentHomeVoucherList extends BasicFragment implements IFragmentHomeVoucherListView {

    private RecyclerView rcl_voucher_list;
    private ProgressView progressView;
    private int page = 1, pagesize = 5;
    private ArrayList<VoucherListObj> arrayList;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterVoucherList adapter;
    private FragmentVoucherListPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.v2_home_voucher_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FragmentVoucherListPresenter(this);
        initView(view);
        initProgressView(view);
    }

    private void initView(View view) {
        rcl_voucher_list = (RecyclerView) view.findViewById(R.id.rcl_ivip);
        rcl_voucher_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rcl_voucher_list.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
        adapter = new AdapterVoucherList(arrayList, this);
        rcl_voucher_list.setAdapter(adapter);
    }

    private void initProgressView(View view) {
        progressView = new ProgressView(null, view);
        progressView.initData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again), getString(R.string.loading_data), Color.parseColor("#05b589"));
        progressView.setUpWithView(rcl_voucher_list);

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
                arrayList.clear();
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

    private void setDataRecyclerView(ArrayList<VoucherListObj> newEntities) {
        arrayList.addAll(newEntities);
        adapter.notifyDataSetChanged();
    }

    private void getData() {
        progressView.setRefreshing(true);
        initDataVoucher();
    }

    private void initDataVoucher() {
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            presenter.getVoucherList(page, pagesize);
        }
    }

    private void checkListData() {
        progressView.setRefreshing(false);

        if (arrayList.size() == 0) {
            progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again));
            progressView.show();
        } else {
            rcl_voucher_list.getAdapter().notifyDataSetChanged();
            adapter.notifyDataSetChanged();
            progressView.hide();
        }
    }

    @Override
    public void onGetVoucherSuccess(ArrayList<VoucherListObj> arrayList) {
        Log.e("arr news entity", arrayList.toString());
        if (arrayList.size() < 5) {
            adapter.onSetLoadMore(false);
        }
        setDataRecyclerView(arrayList);
        checkListData();
    }

    @Override
    public void onGetVoucherError() {

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
}
