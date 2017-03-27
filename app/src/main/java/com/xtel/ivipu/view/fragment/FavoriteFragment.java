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
import com.xtel.ivipu.presenter.FragmentProfileFavoritePresenter;
import com.xtel.ivipu.view.activity.ActivityInfoContent;
import com.xtel.ivipu.view.adapter.AdapterRecycleFavorite;
import com.xtel.ivipu.view.fragment.inf.IFragmentFavoriteView;
import com.xtel.ivipu.view.widget.ProgressView;
import com.xtel.sdk.commons.Constants;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/13/2017.
 */

public class FavoriteFragment extends BasicFragment implements IFragmentFavoriteView {

    RecyclerView rcl_favorite;
    ArrayList<RESP_NewEntity> arrayList;
    private ProgressView progressView;
    private int page = 1, pagesize = 5;
    AdapterRecycleFavorite adapter;
    FragmentProfileFavoritePresenter presenter;
    private int REQUEST_VIEW_FAVORITE = 87;
    private int position = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FragmentProfileFavoritePresenter(this);
        initRecycle(view);
        initProgressView(view);
    }

    private void initRecycle(View view) {
        arrayList = new ArrayList<>();
        rcl_favorite = (RecyclerView) view.findViewById(R.id.rcl_ivip);
        Log.e("arr food object ", arrayList.toString());

        rcl_favorite.setHasFixedSize(true);
        rcl_favorite.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterRecycleFavorite(arrayList, this);
        rcl_favorite.setAdapter(adapter);
    }

    private void initProgressView(View view) {
        progressView = new ProgressView(null, view);
        progressView.initData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again), getString(R.string.loading_data), Color.parseColor("#05b589"));
        progressView.setUpWithView(rcl_favorite);

        progressView.onLayoutClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
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
        initFavoriteData();
    }

    private void initFavoriteData() {
        presenter.getFavorite(page, pagesize);
    }

    @Override
    public void onGetFavoriteSuccess(ArrayList<RESP_NewEntity> arrayList) {
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
            rcl_favorite.getAdapter().notifyDataSetChanged();
            progressView.hide();
        }
    }

    @Override
    public void onGetFavoriteError() {

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

    @Override
    public void onItemClick(int position, RESP_NewEntity newEntity, View view) {
        this.position = position;
        startActivityForResultObject(ActivityInfoContent.class, Constants.RECYCLER_MODEL, newEntity, REQUEST_VIEW_FAVORITE);
    }

}
