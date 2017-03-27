package com.xtel.ivipu.view.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.HistoryTransactionObj;
import com.xtel.ivipu.model.entity.MemberObj;
import com.xtel.ivipu.presenter.HistoryTransactionPresenter;
import com.xtel.ivipu.view.activity.inf.IHistoryTransactionActivityView;
import com.xtel.ivipu.view.adapter.AdapterHistoryTransaction;
import com.xtel.ivipu.view.widget.ProgressView;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.commons.NetWorkInfo;

import java.util.ArrayList;

/**
 * Created by vuhavi on 10/03/2017.
 */

public class HistoryTransactionsActivity extends BasicActivity implements IHistoryTransactionActivityView, View.OnClickListener {

    int id_toolbar = R.id.toolbar_transaction;
    int layoutId = R.layout.activity_history_transaction;
    private RecyclerView rcl_history_transaction;
    private ProgressView progressView;
    private int page = 1, pagesize = 5;
    private ArrayList<HistoryTransactionObj> arrayListTransaction;
    private AdapterHistoryTransaction adapter;
    private LinearLayoutManager layoutManager;
    private HistoryTransactionPresenter presenter;
    private MemberObj memObj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        presenter = new HistoryTransactionPresenter(this);
        initToolbar(id_toolbar, this.getString(R.string.activity_history_transaction));
        initRecyclerView();
        initProgressView();
    }

    public void initRecyclerView(){
        rcl_history_transaction = (RecyclerView) findViewById(R.id.rcl_ivip);
        rcl_history_transaction.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rcl_history_transaction.setLayoutManager(layoutManager);

        arrayListTransaction = new ArrayList<>();
        adapter = new AdapterHistoryTransaction(arrayListTransaction, this);
        rcl_history_transaction.setAdapter(adapter);
    }

    private void initProgressView() {
        progressView = new ProgressView(getActivity(), null);
        progressView.initData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again), getString(R.string.loading_data), Color.parseColor("#05b589"));
        progressView.setUpWithView(rcl_history_transaction);

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
                arrayListTransaction.clear();
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

    private void getData() {
        progressView.setRefreshing(true);
        getDataFromMemberCard();
    }

    private boolean validData() {
        try {
            memObj = (MemberObj) getActivity().getIntent().getSerializableExtra(Constants.RECYCLER_MODEL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return memObj != null;
    }

    private void getDataFromMemberCard() {
        if (validData()) {
            checkNetWork(1);
        }
    }

    private void checkNetWork(int type) {
        Context context = getContext();
        if (!NetWorkInfo.isOnline(context)) {
            WidgetHelper.getInstance().showAlertNetwork(context);
        } else {
            if (type == 1) {
                int id = memObj.getId();
                Log.e("Id card: ", String.valueOf(id));
                initDataTransaction(id);
            }
        }
    }

    private void initDataTransaction(int id) {
        presenter.getHistoryTransaction(id, page, pagesize);
    }

    @Override
    public void onGetTransactionSuccess(ArrayList<HistoryTransactionObj> arrayList) {
        Log.e("arr transaction" +
                " entity", arrayList.toString());
        if (arrayList.size() < 10) {
            adapter.onSetLoadMore(false);
        }
        setDataRecyclerView(arrayList);
        checkListData();
    }


    private void checkListData() {
        progressView.setRefreshing(false);

        if (arrayListTransaction.size() == 0) {
            progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again));
            progressView.show();
        } else {
            rcl_history_transaction.getAdapter().notifyDataSetChanged();
            adapter.notifyDataSetChanged();
            progressView.hide();
        }
    }

    private void setDataRecyclerView(ArrayList<HistoryTransactionObj> arrayList) {
        arrayListTransaction.addAll(arrayList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetTransactionError() {

    }

    @Override
    public void showShortToast(String mes) {
        super.showShortToast(mes);
    }

    @Override
    public void startActivityFinish(Class clazz) {
        super.startActivityFinish(clazz);
    }

    @Override
    public void onLoadMore() {
        page++;
        getData();
    }

    @Override
    public void onNetworkDisable() {
        progressView.setRefreshing(false);
        progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_internet), getString(R.string.try_again));
        progressView.showData();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
