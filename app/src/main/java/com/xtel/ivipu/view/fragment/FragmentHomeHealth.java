package com.xtel.ivipu.view.fragment;

import android.app.Activity;
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
import com.xtel.ivipu.presenter.FragmentHomeHealthPresenter;
import com.xtel.ivipu.view.activity.ActivityInfoContent;
import com.xtel.ivipu.view.adapter.AdapterRecycleHealth;
import com.xtel.ivipu.view.fragment.inf.IFragmentHomeHealth;
import com.xtel.ivipu.view.widget.ProgressView;
import com.xtel.ivipu.view.widget.RecyclerOnScrollListener;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.sdk.commons.Constants;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/16/2017.
 */

public class FragmentHomeHealth extends BasicFragment implements IFragmentHomeHealth{

    private FragmentHomeHealthPresenter presenter;
    private ProgressView progressView;
    private RecyclerView rcl_healt;
    private int type = 5, page = 1, pagesize = 10;
    private AdapterRecycleHealth adapter;
    private ArrayList<RESP_NewEntity> arrayListHealth;
    private int position = -1;
    private int REQUEST_VIEW_NEWS_HEALTH = 99;
    private RecyclerView.LayoutManager layoutManager;
    private BottomNavigationView nav_home;
    private LinearLayout ln_new_slider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FragmentHomeHealthPresenter(this);
        initRecylerView(view);
        initProgressView(view);
    }

    @Override
    public void onGetNewsListSuccess(ArrayList<RESP_NewEntity> arrayList) {
        Log.e("arr news entity", arrayList.toString());
        if (arrayList.size() < 10) {
            adapter.onSetLoadMore(false);
        }
        setDataRecyclerView(arrayList);
        checkListData();
    }

    @Override
    public void onGetNewsListErr() {

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
    public void showShortToast(String mes) {
        super.showShortToast(mes);
    }

    @Override
    public void showLongToast(String mes) {
        super.showLongToast(mes);
    }

    @Override
    public void onItemClick(int position, RESP_NewEntity testRecycle, View view) {
        this.position = position;
        startActivityForResultObject(ActivityInfoContent.class, Constants.RECYCLER_MODEL, testRecycle, REQUEST_VIEW_NEWS_HEALTH);
    }

    @Override
    public void onNetworkDisable() {
        progressView.setRefreshing(false);
        progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_internet), getString(R.string.try_again));
        progressView.showData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initProgressView(View view) {
        progressView = new ProgressView(null, view);
        progressView.initData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again), getString(R.string.loading_data), Color.parseColor("#05b589"));
        progressView.setUpWithView(rcl_healt);

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
                arrayListHealth.clear();
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
        nav_home = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation_item);
        ln_new_slider = (LinearLayout) getActivity().findViewById(R.id.ln_new_slider);
        rcl_healt = (RecyclerView) view.findViewById(R.id.rcl_ivip);
        rcl_healt.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rcl_healt.setLayoutManager(layoutManager);

        arrayListHealth = new ArrayList<>();
        adapter = new AdapterRecycleHealth(arrayListHealth, this);
        rcl_healt.setAdapter(adapter);
        rcl_healt.addOnScrollListener(new RecyclerOnScrollListener((LinearLayoutManager) layoutManager) {
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
        arrayListHealth.addAll(newEntities);
        adapter.notifyDataSetChanged();
    }

    private void getData() {
        progressView.setRefreshing(true);
        initDataNews();
    }

    private void initDataNews() {
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            presenter.getHealtNews(type, page, pagesize);
        }
    }

    private void checkListData() {
        progressView.setRefreshing(false);

        if (arrayListHealth.size() == 0) {
            progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again));
            progressView.show();
        } else {
            rcl_healt.getAdapter().notifyDataSetChanged();
            adapter.notifyDataSetChanged();
            progressView.hide();
        }
    }
}
