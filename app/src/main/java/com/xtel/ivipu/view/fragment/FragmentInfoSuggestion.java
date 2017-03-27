package com.xtel.ivipu.view.fragment;

import android.content.Context;
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
import com.xtel.ivipu.presenter.FragmentSuggestionPresenter;
import com.xtel.ivipu.view.activity.ActivityInfoContent;
import com.xtel.ivipu.view.adapter.AdapterRecyclerViewSuggestion;
import com.xtel.ivipu.view.fragment.inf.IFragmentSuggestionView;
import com.xtel.ivipu.view.widget.ProgressView;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.commons.NetWorkInfo;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/17/2017.
 */

public class FragmentInfoSuggestion extends BasicFragment implements IFragmentSuggestionView {

    int id_news, page = 1, pagesize = 3;
    private FragmentSuggestionPresenter presenter;
    private RecyclerView rcl_suggestion;
    private ArrayList<RESP_NewEntity> arrayListNewsList;
    private int position = -1;
    private int REQUEST_VIEW_SUGGESTION_LIST = 99;
    private ProgressView progressView;
    private AdapterRecyclerViewSuggestion adapter;
    private RESP_NewEntity newEntity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_suggestion, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FragmentSuggestionPresenter(this);
        initProgressView(view);
        initRecylerView(view);
//        getDataFromNews();
    }

    private void getDataFromNews() {
        if (validData()) {
            getData();
        }
    }

    private boolean validData() {
        try {
            newEntity = (RESP_NewEntity) getActivity().getIntent().getSerializableExtra(Constants.RECYCLER_MODEL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newEntity != null;
    }

    private void initProgressView(View view) {
        progressView = new ProgressView(null, view);
        progressView.initData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again), getString(R.string.loading_data), Color.parseColor("#05b589"));
        progressView.setUpWithView(rcl_suggestion);

        progressView.onLayoutClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.onSetLoadMore(true);
                adapter.notifyDataSetChanged();
                getData();
            }
        });

        progressView.onRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                arrayListNewsList.clear();
                adapter.onSetLoadMore(true);
                adapter.notifyDataSetChanged();
                getData();
            }
        });

        progressView.onSwipeLayoutPost(new Runnable() {
            @Override
            public void run() {
//                getData();
                getDataFromNews();
            }
        });
    }

    private void initRecylerView(View view) {
        rcl_suggestion = (RecyclerView) view.findViewById(R.id.rcl_ivip);
        rcl_suggestion.setHasFixedSize(true);
        rcl_suggestion.setLayoutManager(new LinearLayoutManager(getContext()));

        arrayListNewsList = new ArrayList<>();
        adapter = new AdapterRecyclerViewSuggestion(arrayListNewsList, this);
        rcl_suggestion.setAdapter(adapter);
    }

    private void setDataRecyclerView(ArrayList<RESP_NewEntity> newEntities) {
        arrayListNewsList.addAll(newEntities);
        adapter.notifyDataSetChanged();
    }

    private void getData() {
        progressView.setRefreshing(true);
        initDataSuggestion();
    }

    private void initDataSuggestion() {
        checkNetWork(1);
    }

    @Override
    public void onGetSucggestionSuccess(ArrayList<RESP_NewEntity> arrayList) {
        Log.e("arr news entity", arrayList.toString());
        if (arrayList.size() < 3) {
            adapter.onSetLoadMore(false);
            adapter.notifyDataSetChanged();
        }
        setDataRecyclerView(arrayList);

        checkListData();
    }

    private void checkListData() {
        progressView.setRefreshing(false);

        if (arrayListNewsList.size() == 0) {
            progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again));
            progressView.show();
        } else {
            rcl_suggestion.getAdapter().notifyDataSetChanged();
            progressView.hide();
        }
    }

    @Override
    public void onGetSuggestionError() {

    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
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
    public void onNetworkDisable() {
        progressView.setRefreshing(false);
        progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_internet), getString(R.string.try_again));
        progressView.showData();
    }

    @Override
    public void onItemClick(int position, RESP_NewEntity newEntity, View view) {
        startActivityForResultObject(ActivityInfoContent.class, Constants.RECYCLER_MODEL, newEntity, REQUEST_VIEW_SUGGESTION_LIST);
    }

    private void checkNetWork(int type) {
        final Context context = getContext();
        if (!NetWorkInfo.isOnline(context)) {
            WidgetHelper.getInstance().showAlertNetwork(context);
        } else {
            if (type == 1) {
                int id = newEntity.getId();
                presenter.getSuggestion(id, page, pagesize);
            } else if (type == 2) {

            }
        }
    }
}
