package com.xtel.ivipu.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.MemberObj;
import com.xtel.ivipu.presenter.FragmentNavMemberCardPresenter;
import com.xtel.ivipu.view.activity.HistoryTransactionsActivity;
import com.xtel.ivipu.view.adapter.AdapterCard;
import com.xtel.ivipu.view.fragment.inf.IFragmentMemberCard;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.sdk.commons.Constants;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;

/**
 * Created by vuhavi on 07/03/2017.
 */

public class FragmentMemberCard extends BasicFragment implements IFragmentMemberCard, View.OnClickListener, DiscreteScrollView.ScrollListener {

    DiscreteScrollView scrollView;
    AdapterCard pagerAdapter;
    long date_create;
    int id_Card;
    Activity activity = getActivity();
    //    PagerContainer mContainer;
//    ViewPager viewPager;
    private int page = 1, pagesize = 5;
    private FragmentNavMemberCardPresenter presenter;
    //    private RecyclerView rcl_member_card;
    private ArrayList<MemberObj> cardArraylist;
    //    private ProgressView progressView;
    private int REQUEST_VIEW_CARD = 66;
    private int position = -1;
    private MemberObj memberObj;
    private String store_name;
    private int total_point;
    private int current_point;
    private TextView tv_store_name, tv_level, tv_current_point, tv_date_create, tv_card_total_point, tv_action_view_his, tv_store;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.v2_fragment_member_card, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FragmentNavMemberCardPresenter(this);
//        initRecyclerView(view);
//        initProgressView(view);
//        initializeViews(view);
        initView(view);
        getData();
    }

    private void initView(View view) {

        tv_store_name = (TextView) view.findViewById(R.id.tv_card_store_name);
        tv_level = (TextView) view.findViewById(R.id.tv_card_level);
        tv_card_total_point = (TextView) view.findViewById(R.id.tv_card_total_point);
        tv_current_point = (TextView) view.findViewById(R.id.tv_card_current_point);
        tv_date_create = (TextView) view.findViewById(R.id.tv_date);
        tv_action_view_his = (TextView) view.findViewById(R.id.tv_action_view_his);
        tv_store = (TextView) view.findViewById(R.id.tv_store);
        tv_action_view_his.setOnClickListener(this);

        cardArraylist = new ArrayList<>();
        scrollView = (DiscreteScrollView) view.findViewById(R.id.picker);
        pagerAdapter = new AdapterCard(this, cardArraylist);
        scrollView.setAdapter(pagerAdapter);
    }

    private void getData() {
//        progressView.setRefreshing(true);
        initDataMemberCard();
    }

    private void initDataMemberCard() {
            presenter.getMemberCard(page, pagesize);
    }

    @Override
    public void onGetMemberCardSuccess(final ArrayList<MemberObj> arrayList) {
        Log.e("arr news member", arrayList.toString());
//        if (cardArraylist.size() < 10) {
//            adapter.onSetLoadMore(false);
//        }
        cardArraylist.addAll(arrayList);
        pagerAdapter.notifyDataSetChanged();
//        scrollView.setOffscreenItems(this.cardArraylist.size());
//        adapter.notifyDataSetChanged();
//        checkListData();
        onStateChangeListener();
    }

    private void onStateChangeListener() {
        scrollView.getCurrentItem();
        scrollView.scrollToPosition(0);
        scrollView.smoothScrollToPosition(0);
        scrollView.setHasFixedSize(true);
        scrollView.setItemAnimator(new DefaultItemAnimator());
        scrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .setMaxScale(1.05f)
                .setPivotX(Pivot.X.CENTER)
                .setPivotY(Pivot.Y.CENTER)
                .build());
        memberObj = new MemberObj();
        scrollView.setOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@NonNull RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                memberObj = cardArraylist.get(adapterPosition);

                Log.e("Position scroll", String.valueOf(adapterPosition));
                Log.e("Obj member", JsonHelper.toJson(memberObj));

                store_name = memberObj.getStore_name();
                current_point = memberObj.getCurrent_point();
                total_point = memberObj.getTotal_point();
                date_create = memberObj.getCreate_time();

                id_Card = cardArraylist.get(adapterPosition).getId();

                WidgetHelper.getInstance().setTextViewNoResult(tv_store_name, store_name);
                WidgetHelper.getInstance().setTextViewNoResult(tv_store, store_name);
                WidgetHelper.getInstance().setTextViewNoResult(tv_level, "Khách hàng thân thiết");
                WidgetHelper.getInstance().setTextViewNoResult(tv_current_point, String.valueOf(current_point));
                WidgetHelper.getInstance().setTextViewNoResult(tv_card_total_point, total_point + " điểm");
                WidgetHelper.getInstance().setTextViewDate(tv_date_create, "", date_create);
            }
        });

    }

    @Override
    public void onGetMemberCardError() {

    }

    @Override
    public void onNetworkDisable() {

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
    public void onLoadMore() {

    }

    @Override
    public void onClickCardItem(int position, MemberObj memberObj, View view) {
        this.position = position;
        startActivityForResultObject(HistoryTransactionsActivity.class, Constants.RECYCLER_MODEL, memberObj, REQUEST_VIEW_CARD);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_action_view_his) {
            onClickCardItem(id_Card, memberObj, v);
        }
    }

    @Override
    public void onScroll(float scrollPosition, @NonNull RecyclerView.ViewHolder currentHolder, @NonNull RecyclerView.ViewHolder newCurrent) {

    }

//    @Override
//    public void onNetworkDisable(){
//        progressView.setRefreshing(false);
//        progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_internet), getString(R.string.try_again));
//        progressView.showData();
//    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("Resume", "Da resume");
        onStateChangeListener();
    }
}
