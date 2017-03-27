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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.RESP.RESP_NewEntity;
import com.xtel.ivipu.presenter.FragmentNewsFashionPresenter;
import com.xtel.ivipu.view.activity.ActivityInfoContent;
import com.xtel.ivipu.view.adapter.AdapterRecyclerFashion;
import com.xtel.ivipu.view.fragment.inf.IFragmentFashionView;
import com.xtel.ivipu.view.widget.ProgressView;
import com.xtel.ivipu.view.widget.RecyclerOnScrollListener;
import com.xtel.sdk.commons.Constants;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/13/2017.
 */

public class FragmentHomeFashionMakeUp extends BasicFragment implements IFragmentFashionView {

    private RecyclerView rcl_fashion;
    private ArrayList<RESP_NewEntity> arrayList_fashion;

    private int position = -1;
    private int REQUEST_VIEW_MOVIE = 91;
    private ProgressView progressView;
    private AdapterRecyclerFashion adapter;
    private FragmentNewsFashionPresenter presenter;
    private int type = 2, page = 1, pagesize = 10;
    private BottomNavigationView nav_home;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_fashion, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FragmentNewsFashionPresenter(this);
        initView(view);
        initProgressView(view);
    }

    private void initView(View view) {
        nav_home = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation_item);
        rcl_fashion = (RecyclerView) view.findViewById(R.id.rcl_ivip);
        rcl_fashion.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcl_fashion.setLayoutManager(layoutManager);
        arrayList_fashion = new ArrayList<>();

        adapter = new AdapterRecyclerFashion(arrayList_fashion, this);
        rcl_fashion.setAdapter(adapter);
        rcl_fashion.addOnScrollListener(new RecyclerOnScrollListener(layoutManager) {
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
        nav_home.animate().translationY(nav_home.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
        nav_home.setVisibility(View.GONE);
    }

    private void showBottomNavigation() {
        nav_home.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        nav_home.setVisibility(View.VISIBLE);
    }

    private void initProgressView(View view) {
        progressView = new ProgressView(null, view);
        progressView.initData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again), getString(R.string.loading_data), Color.parseColor("#05b589"));
        progressView.setUpWithView(rcl_fashion);

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
                arrayList_fashion.clear();
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
        initDataNews();
    }

    private void initDataNews() {
        presenter.getNewsList(type, page, pagesize);
    }

    private void checkListData() {
//        progressView.disableSwipe();
        progressView.setRefreshing(false);

        if (arrayList_fashion.size() == 0) {
            progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again));
            progressView.show();
        } else {
            rcl_fashion.getAdapter().notifyDataSetChanged();
            adapter.notifyDataSetChanged();
            progressView.hide();
        }
    }

    private void setDataRecyclerView(ArrayList<RESP_NewEntity> newEntities) {
        arrayList_fashion.addAll(newEntities);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore() {
        page++;
        getData();
    }

    @Override
    public void showLongToast(String message) {
        super.showLongToast(message);
    }

    @Override
    public void onNetworkDisable() {
        progressView.setRefreshing(false);
        progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_internet), getString(R.string.try_again));
        progressView.showData();
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
    public void onGetNewsListError() {

    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        super.startActivityAndFinish(clazz);
    }

    @Override
    public void onItemClick(int position, RESP_NewEntity newEntities, View view) {
        this.position = position;
        startActivityForResultObject(ActivityInfoContent.class, Constants.RECYCLER_MODEL, newEntities, REQUEST_VIEW_MOVIE);
    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

}
