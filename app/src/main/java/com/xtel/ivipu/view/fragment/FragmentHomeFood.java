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
import com.xtel.ivipu.presenter.FragmentFoodPresenter;
import com.xtel.ivipu.view.activity.ActivityInfoContent;
import com.xtel.ivipu.view.adapter.AdapterRecycleFood;
import com.xtel.ivipu.view.fragment.inf.IFragmentFoodView;
import com.xtel.ivipu.view.widget.ProgressView;
import com.xtel.ivipu.view.widget.RecyclerOnScrollListener;
import com.xtel.sdk.commons.Constants;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/13/2017.
 */

public class FragmentHomeFood extends BasicFragment implements IFragmentFoodView {

    int type = 3, page = 1, pagesize = 10;
    AdapterRecycleFood adapter;
    private RecyclerView rcl_food;
    private ArrayList<RESP_NewEntity> arraylist_food;
    private ProgressView progressView;
    private FragmentFoodPresenter presenter;
    private int position = -1;
    private int REQUEST_VIEW_FOOD = 92;
    private BottomNavigationView nav_home;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_food, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FragmentFoodPresenter(this);
        initRecyclerView(view);
        initProgressView(view);
    }

    private void initRecyclerView(View view) {
        arraylist_food = new ArrayList<>();

        Log.e("arr food object ", arraylist_food.toString());
        nav_home = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation_item);
        rcl_food = (RecyclerView) view.findViewById(R.id.rcl_ivip);
        rcl_food.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcl_food.setLayoutManager(layoutManager);
        adapter = new AdapterRecycleFood(arraylist_food, this);

        rcl_food.setAdapter(adapter);
        rcl_food.addOnScrollListener(new RecyclerOnScrollListener(layoutManager) {
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
        progressView.setUpWithView(rcl_food);

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
                arraylist_food.clear();
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
        arraylist_food.addAll(newEntities);
        adapter.notifyDataSetChanged();
    }

    private void getData() {
//        progressView.hideData();
        progressView.setRefreshing(true);
        initDataNews();
    }

    private void initDataNews() {
        presenter.getFood(type, page, pagesize);
    }

    @Override
    public void onGetFoodSuccess(ArrayList arrayList) {
        Log.e("arr news entity", arrayList.toString());

        if (arrayList.size() < 10) {
            adapter.onSetLoadMore(false);
        }
        setDataRecyclerView(arrayList);
        checkListData();
    }

    private void checkListData() {
        progressView.setRefreshing(false);

        if (arraylist_food.size() == 0) {
            progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again));
            progressView.show();
        } else {
            rcl_food.getAdapter().notifyDataSetChanged();
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
    public void onGetFoodError() {

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
    public void showLongToast(String message) {
        super.showLongToast(message);
    }

    @Override
    public void onItemClick(int position, RESP_NewEntity newObjEntity, View view) {
        this.position = position;
        startActivityForResultObject(ActivityInfoContent.class, Constants.RECYCLER_MODEL, newObjEntity, REQUEST_VIEW_FOOD);
    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }
}
