package com.xtel.ivipu.view.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.MyShopCheckin;
import com.xtel.ivipu.view.activity.inf.IMyShopActivity;
import com.xtel.ivipu.view.widget.RoundImage;
import com.xtel.ivipu.view.widget.WidgetHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vuhavi on 07/03/2017.
 */

public class AdapterCheckinHistory extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean isLoadMore;
    private int TYPE_VIEW = 1;
    private int TYPE_HEADER= 3;
    private int TYPE_LOAD = 2;
    ArrayList<MyShopCheckin> arrayList;
    IMyShopActivity view;
    private Date date;
    private int day, month, year, hh, mm;

    public AdapterCheckinHistory(ArrayList<MyShopCheckin> arrayList, IMyShopActivity view) {
        this.arrayList = arrayList;
        this.view = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEW) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_checkin, parent, false));
        } else if (viewType == TYPE_HEADER){
            return new ViewHolderHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false));
        } else if (viewType == TYPE_LOAD) {
            return new ViewProgressBar(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (arrayList != null && arrayList.size() > 0) {
            if (holder instanceof ViewHolder) {
                ViewHolder viewHolder = (ViewHolder) holder;

                MyShopCheckin myShopCheckin = arrayList.get(position);

                long checkin_time = myShopCheckin.getCheckin_time();
                if (checkin_time != 0) {
                    getTime(checkin_time);
                    String dat_of_checkin = day + "/" + month + "/" + year;
                    String time_of_checkin = hh + ":" + mm;
//                    WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_checkin_his_date, dat_of_checkin);
                    WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_checkin_his_time, time_of_checkin);
                }
                WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_store_name, myShopCheckin.getStore_name());
//                WidgetHelper.getInstance().setAvatarImageURL(viewHolder.img_brand_store, myShopCheckin.getLogo());
            } else if (holder instanceof ViewHolderHeader) {
                ViewHolderHeader holderHeader = (ViewHolderHeader) holder;
                WidgetHelper.getInstance().setTextViewNoResult(holderHeader.tv_checkin_date, arrayList.get(position).getDate());
            }
            else if (holder instanceof ViewProgressBar) {
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
    public int getItemViewType(int position) {
        if (position == arrayList.size()) {
            return TYPE_LOAD;
        } else if (arrayList.get(position).isTitle()) {
            return TYPE_HEADER;
        } else
            return TYPE_VIEW;
    }

    @Override
    public int getItemCount() {
        if (isLoadMore && arrayList.size() > 0) {
            return arrayList.size() + 1;
        } else {
            return arrayList.size();
        }
    }

    public void onSetLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private RoundImage img_brand_store;
        private TextView tv_store_name, tv_checkin_his_time;

        ViewHolder(View itemView) {
            super(itemView);
//            img_brand_store = (RoundImage) itemView.findViewById(R.id.img_brand_store);
            tv_store_name = (TextView) itemView.findViewById(R.id.tv_item_store_name);
            tv_checkin_his_time = (TextView) itemView.findViewById(R.id.tv_item_checkin_his_time);
        }

    }

    private class ViewHolderHeader extends RecyclerView.ViewHolder {

        private TextView tv_checkin_date;

        ViewHolderHeader(View itemView) {
            super(itemView);
            tv_checkin_date = (TextView) itemView.findViewById(R.id.tv_group_by_date);
        }

    }

    private class ViewProgressBar extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        ViewProgressBar(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_progress_bar);
        }
    }

    public void notifyChange() {
        new SortHistory().execute();
    }

    private class SortHistory extends AsyncTask<Void,Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            if (arrayList.size() == 0) return null;
            for (int i = arrayList.size() - 1; i >= 0; i--) {
                if (arrayList.get(i).getStore_name() == null) arrayList.remove(i);
            }
            for (int i = arrayList.size() - 1; i >= 0; i--) {
                arrayList.get(i).setDate(WidgetHelper.getInstance().convertLong2Time((arrayList.get(i).getCheckin_time())));
            }
            for (int i = arrayList.size() - 1; i > 0; i--) {
                if (!arrayList.get(i).getDate().equals(arrayList.get((i - 1)).getDate())) {
                    arrayList.add(i, new MyShopCheckin(true, arrayList.get(i).getDate()));
                }
            }

            MyShopCheckin history = arrayList.get(0);
            arrayList.add(0, new MyShopCheckin(true, history.getDate()));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notifyDataSetChanged();
        }

    }

    private void getTime(long time) {
        date = new Date();
        String timer = WidgetHelper.getInstance().convertLong2TimeWithHour(time);
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            date = dateFormat.parse(timer);
            calendar.setTime(date);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH) + 1;
            year = calendar.get(Calendar.YEAR);

            hh = calendar.get(Calendar.HOUR_OF_DAY);
            mm = calendar.get(Calendar.MINUTE);
            Log.e("Check time", "Hour: " + hh + ", mm: " + mm + ", Day: " + day + ", month: " + month + ", year: " + year);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("Get time exception", e.toString());
        }
    }
}
