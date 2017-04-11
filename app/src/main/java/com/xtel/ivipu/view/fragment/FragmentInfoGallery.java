package com.xtel.ivipu.view.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.RESP.RESP_NewEntity;
import com.xtel.ivipu.model.RESP.RESP_NewsObject;
import com.xtel.ivipu.model.entity.GalleryObj;
import com.xtel.ivipu.model.entity.NewsObj;
import com.xtel.ivipu.presenter.FragmentGalleryPresenter;
import com.xtel.ivipu.view.adapter.AdapterGallery;
import com.xtel.ivipu.view.fragment.inf.IFragmentGalleryView;
import com.xtel.ivipu.view.widget.ProgressView;
import com.xtel.ivipu.view.widget.SlideshowDialogFragment;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.sdk.commons.Constants;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/17/2017.
 */

public class FragmentInfoGallery extends BasicFragment implements IFragmentGalleryView {

    RecyclerView rcl_gallery;
    AdapterGallery adapter;
    String type;
    int id;
    private int page = 1, pagesize = 6;
    private ProgressView progressView;
    private ArrayList<GalleryObj> arraylist_gallery;
    private FragmentGalleryPresenter presenter;
    private RESP_NewEntity newEntity;
    private NewsObj newsObject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_infomation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FragmentGalleryPresenter(this);
        initProgressView(view);
        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        arraylist_gallery = new ArrayList<>();
        rcl_gallery = (RecyclerView) view.findViewById(R.id.rcl_ivip);
        rcl_gallery.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rcl_gallery.setLayoutManager(layoutManager);
        adapter = new AdapterGallery(arraylist_gallery, this);
        rcl_gallery.setAdapter(adapter);
        rcl_gallery.addOnItemTouchListener(new AdapterGallery.RecyclerTouchListener(getContext(), rcl_gallery, new AdapterGallery.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", arraylist_gallery);
                bundle.putInt("position", position);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void initProgressView(View view) {
        progressView = new ProgressView(null, view);
        progressView.initData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again), getString(R.string.loading_data), Color.parseColor("#05b589"));
        progressView.setUpWithView(rcl_gallery);

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
                arraylist_gallery.clear();
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

    @Override
    public void onLoadImageSuccess(ArrayList<GalleryObj> arrayList) {
        Log.e("arr news entity", arrayList.toString());
        if (arrayList.size() < 10) {
            adapter.setLoadMore(false);
        }
        setDataRecyclerView(arrayList);
        checkListData();
    }

    private void checkListData() {
        progressView.setRefreshing(false);

        if (arraylist_gallery.size() == 0) {
            progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again));
            progressView.show();
        } else {
            rcl_gallery.getAdapter().notifyDataSetChanged();
            progressView.hide();
        }
    }

    @Override
    public void onLoadImageError() {

    }

    @Override
    public void onNetworkDisable() {
        progressView.setRefreshing(false);
        progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_internet), getString(R.string.try_again));
        progressView.showData();
    }

    @Override
    public void onClickItem(int position, GalleryObj galleryObj, View view) {

    }

    @Override
    public void onGetNewsObjSuccess(RESP_NewsObject object) {
        newsObject = new NewsObj();
        Log.e("news obj", JsonHelper.toJson(newsObject));
        int store_id = object.getStore_id();
        if (!String.valueOf(store_id).equals("0")) {
            type = "store";
        } else {
            type = "chain";
        }
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            presenter.getGalleryFragment(id, type, page, pagesize);
        }
    }

    @Override
    public void onLoadMore() {
        page++;
        getData();
    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        super.startActivityAndFinish(clazz);
    }

    private void setDataRecyclerView(ArrayList<GalleryObj> galleryObjs) {
        arraylist_gallery.addAll(galleryObjs);
        adapter.notifyDataSetChanged();
    }

    private void getData() {
        progressView.setRefreshing(true);
        initDataGallery();
    }

    private void initDataGallery() {
        if (validData()) {
            id = newEntity.getId();
                presenter.getNewsInfo(id);
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

    @Override
    public void onResume() {
        super.onResume();
    }
}
