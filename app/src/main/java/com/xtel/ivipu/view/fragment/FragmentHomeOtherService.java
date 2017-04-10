package com.xtel.ivipu.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.RESP.RESP_NewEntity;
import com.xtel.ivipu.presenter.FragmentAnotherServicePresenter;
import com.xtel.ivipu.view.activity.ActivityInfoContent;
import com.xtel.ivipu.view.adapter.AdapterRecyclerService;
import com.xtel.ivipu.view.fragment.inf.IFragmentAnotherServiceView;
import com.xtel.ivipu.view.widget.ProgressView;
import com.xtel.ivipu.view.widget.RecyclerOnScrollListener;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.sdk.commons.Constants;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/13/2017.
 */

public class FragmentHomeOtherService extends BasicFragment implements IFragmentAnotherServiceView {

    private int type = 6, page = 1, pagesize = 10;
    private ProgressView progressView;
    private FragmentAnotherServicePresenter presenter;
    private ArrayList<RESP_NewEntity> arraylist_service;
    private RecyclerView rcl_service;
    private AdapterRecyclerService adapter;
    private int position = -1;
    private int REQUEST_VIEW_SERVICE = 10;
    private BottomNavigationView nav_home;
    private LinearLayout ln_new_slider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_other_service, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FragmentAnotherServicePresenter(this);
        initRecyclerView(view);
        initProgressView(view);
    }

    private void initRecyclerView(View view) {
        arraylist_service = new ArrayList<>();
        nav_home = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation_item);
        ln_new_slider = (LinearLayout) getActivity().findViewById(R.id.ln_new_slider);
        Log.e("arr food object ", arraylist_service.toString());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcl_service = (RecyclerView) view.findViewById(R.id.rcl_ivip);
        rcl_service.setHasFixedSize(true);
        rcl_service.setLayoutManager(layoutManager);
        adapter = new AdapterRecyclerService(arraylist_service, this);

        rcl_service.setAdapter(adapter);
        rcl_service.addOnScrollListener(new RecyclerOnScrollListener(layoutManager) {
            @Override
            public void onScrollUp() {
                hideBottomNavigation();
            }

            @Override
            public void onScrollDown() {
                showBottomNavigation();
            }

            @Override
            public void onLoadMore() {
                showBottomNavigation();
            }
        });
    }

    private void hideBottomNavigation() {
        WidgetHelper.getInstance().hideViewActivity(nav_home, ln_new_slider);
    }

    private void showBottomNavigation() {
        WidgetHelper.getInstance().showViewActivity(nav_home, ln_new_slider);
    }

    private void initProgressView(View view) {
        progressView = new ProgressView(null, view);
        progressView.initData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again), getString(R.string.loading_data), Color.parseColor("#05b589"));
        progressView.setUpWithView(rcl_service);

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
                arraylist_service.clear();
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

    private void setDataRecyclerView(ArrayList<RESP_NewEntity> newEntities) {
        arraylist_service.addAll(newEntities);
        adapter.notifyDataSetChanged();
    }

    private void getData() {
//        progressView.hideData();
        progressView.setRefreshing(true);
        initDataNews();
    }

    private void initDataNews() {
        presenter.getService(type, page, pagesize);
    }

    private void checkListData() {
        progressView.setRefreshing(false);

        if (arraylist_service.size() == 0) {
            progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again));
            progressView.show();
        } else {
            rcl_service.getAdapter().notifyDataSetChanged();
            adapter.notifyDataSetChanged();
            progressView.hide();
        }
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
    public void getServiceSuccess(ArrayList<RESP_NewEntity> arrayList) {
        Log.e("arr news entity", arrayList.toString());
        if (arrayList.size() < 10) {
            adapter.onSetLoadMore(false);
        }
        setDataRecyclerView(arrayList);

        checkListData();
    }

    @Override
    public void getServiceError() {

    }

    @Override
    public void showLongToast(String message) {
        super.showLongToast(message);
    }

    @Override
    public void onItemClick(int position, RESP_NewEntity newObjEntity, View view) {
        this.position = position;
        startActivityForResultObject(ActivityInfoContent.class, Constants.RECYCLER_MODEL, newObjEntity, REQUEST_VIEW_SERVICE);
    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        super.startActivityAndFinish(clazz);
    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

}
