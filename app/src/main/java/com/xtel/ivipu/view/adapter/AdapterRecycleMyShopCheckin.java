package com.xtel.ivipu.view.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.MyShopCheckin;
import com.xtel.ivipu.view.activity.inf.IMyShopActivity;
import com.xtel.ivipu.view.widget.RoundImage;
import com.xtel.ivipu.view.widget.WidgetHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by vivhp on 2/7/2017.
 */

public class AdapterRecycleMyShopCheckin extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MyShopCheckin> arrayList;
    private IMyShopActivity iMyShopActivity;
    private boolean isLoadMore;
    private int TYPE_VIEW = 1, TYPE_LOAD = 2;
    private Date date;
    private int day, month, year, hh, mm;

    public AdapterRecycleMyShopCheckin(ArrayList<MyShopCheckin> arrayList, IMyShopActivity iMyShopActivity) {
        this.arrayList = arrayList;
        this.iMyShopActivity = iMyShopActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEW) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_shop_item, parent, false));
        } else if (viewType == TYPE_LOAD) {
            return new ViewProgressBar(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (position == arrayList.size()) {
            iMyShopActivity.onLoadMore();
        }

        if (arrayList != null && arrayList.size() > 0) {
            if (holder instanceof ViewHolder) {
                ViewHolder viewHolder = (ViewHolder) holder;
                MyShopCheckin myShopCheckin = arrayList.get(position);
//                Log.e("MyShop Checkin", myShopCheckin.toString());
//            getTime(myShopCheckin.getCheckin_time());
                WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_store_name, myShopCheckin.getStore_name());
                WidgetHelper.getInstance().setAvatarImageURL(viewHolder.img_brand_store, myShopCheckin.getLogo());
            } else {
                ViewProgressBar viewProgressBar = (ViewProgressBar) holder;
                viewProgressBar.progressBar.getIndeterminateDrawable()
                        .setColorFilter(
                                Color.WHITE,
                                android.graphics.PorterDuff.Mode.MULTIPLY
                        );
            }
        }
    }

    @Override
    public int getItemCount() {
        if (isLoadMore && arrayList.size() > 0) {
            return arrayList.size() + 1;
        } else {
            return arrayList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == arrayList.size()) {
            return TYPE_LOAD;
        } else {
            return TYPE_VIEW;
        }
    }

    public void onSetLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private RoundImage img_brand_store;
        private TextView tv_store_name, tv_checkin_his_date, tv_checkin_his_time;

        ViewHolder(View itemView) {
            super(itemView);
            img_brand_store = (RoundImage) itemView.findViewById(R.id.img_brand_store);
            tv_store_name = (TextView) itemView.findViewById(R.id.tv_store_name);
            tv_checkin_his_date = (TextView) itemView.findViewById(R.id.tv_checkin_his_date);
            tv_checkin_his_time = (TextView) itemView.findViewById(R.id.tv_checkin_his_time);
        }

    }

    private class ViewProgressBar extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        ViewProgressBar(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_progress_bar);
        }
    }

    private void getTime(long time){
        String timer = WidgetHelper.getInstance().convertLong2TimeWithHour(time);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try {
           date = simpleDateFormat.parse(timer);
        } catch (ParseException e) {
            e.printStackTrace();
        }
         day = date.getDay();
         month = date.getMonth();
         year = date.getYear();

         hh = date.getHours();
         mm = date.getMinutes();
        Log.e("Check time", "Hour: " + hh + ", mm: " + mm + ", Day: " + day + ", month: " + month + ", year: " + year);
    }
}
