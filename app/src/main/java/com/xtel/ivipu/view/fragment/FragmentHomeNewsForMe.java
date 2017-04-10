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
import com.xtel.ivipu.presenter.FragmentNews4MePresenter;
import com.xtel.ivipu.view.activity.ActivityInfoContent;
import com.xtel.ivipu.view.adapter.AdapterRecyclerviewNews4Me;
import com.xtel.ivipu.view.fragment.inf.IFragmentNews4MeView;
import com.xtel.ivipu.view.widget.ProgressView;
import com.xtel.ivipu.view.widget.RecyclerOnScrollListener;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.sdk.commons.Constants;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/16/2017.
 */

public class FragmentHomeNewsForMe extends BasicFragment implements IFragmentNews4MeView {

    int type = 7, page = 1, pagesize = 10;
    private RecyclerView rcl_news_4_me;
    private ArrayList<RESP_NewEntity> arrayListNewsList;
    private int position = -1;
    private int REQUEST_VIEW_NEWS_LIST = 99;
    private ProgressView progressView;
    private LinearLayoutManager layoutManager;
    private BottomNavigationView nav_home;
    private AdapterRecyclerviewNews4Me adapter;
    private FragmentNews4MePresenter presenter;
    private LinearLayout ln_new_slider;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_news_4_me, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FragmentNews4MePresenter(this);
        initRecylerView(view);
        initProgressView(view);
    }

    private void initProgressView(View view) {
        progressView = new ProgressView(null, view);
        progressView.initData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again), getString(R.string.loading_data), Color.parseColor("#05b589"));
        progressView.setUpWithView(rcl_news_4_me);

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
                arrayListNewsList.clear();
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
        arrayListNewsList = new ArrayList<>();
        nav_home = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation_item);
        ln_new_slider = (LinearLayout) getActivity().findViewById(R.id.ln_new_slider);
        rcl_news_4_me = (RecyclerView) view.findViewById(R.id.rcl_ivip);
        rcl_news_4_me.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rcl_news_4_me.setLayoutManager(layoutManager);

        arrayListNewsList = new ArrayList<>();
        adapter = new AdapterRecyclerviewNews4Me(arrayListNewsList, this);
        rcl_news_4_me.setAdapter(adapter);
        rcl_news_4_me.addOnScrollListener(new RecyclerOnScrollListener(layoutManager) {
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
            }
        });
    }

    private void hideBottomNavigation() {
        WidgetHelper.getInstance().hideViewActivity(nav_home, ln_new_slider);
    }

    private void showBottomNavigation() {
        WidgetHelper.getInstance().showViewActivity(nav_home, ln_new_slider);
    }

    private void setDataRecyclerView(ArrayList<RESP_NewEntity> newEntities) {
        arrayListNewsList.addAll(newEntities);
        adapter.notifyDataSetChanged();
    }

    private void getData() {
//        progressView.hideData();
        progressView.setRefreshing(true);
        initDataNews();
    }

    private void initDataNews() {
        presenter.getNews4Me(type, page, pagesize);
    }

    @Override
    public void onLoadNews4MeSuccess(ArrayList<RESP_NewEntity> arrayList) {
        Log.e("arr news entity", arrayList.toString());
        if (arrayList.size() < 10) {
            adapter.onSetLoadMore(false);
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
            rcl_news_4_me.getAdapter().notifyDataSetChanged();
            adapter.notifyDataSetChanged();
            progressView.hide();
        }
    }


    @Override
    public void onLoadNews4MeError() {

    }

    @Override
    public void onItemClick(int position, RESP_NewEntity newEntity, View view) {
        this.position = position;
        startActivityForResultObject(ActivityInfoContent.class, Constants.RECYCLER_MODEL, newEntity, REQUEST_VIEW_NEWS_LIST);
    }

    @Override
    public void onLoadMore() {
        page++;
        getData();
    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        super.startActivityAndFinish(clazz);
    }


    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void onNetworkDisable() {
        progressView.setRefreshing(false);
        progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_internet), getString(R.string.try_again));
        progressView.showData();
    }
}
