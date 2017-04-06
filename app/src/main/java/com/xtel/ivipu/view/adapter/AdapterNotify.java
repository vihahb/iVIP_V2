package com.xtel.ivipu.view.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.RESP.RESP_NewEntity;
import com.xtel.ivipu.view.fragment.inf.IFragmentNotify;
import com.xtel.ivipu.view.widget.WidgetHelper;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by vivhp on 3/17/2017.
 */

public class AdapterNotify extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Random random = new Random();
    private String TAG = "AdapNotify";
    private ArrayList<RESP_NewEntity> arrayList;
    private IFragmentNotify view;
    private boolean isLoadMore = true;
    private int TYPE_VIEW = 1, TYPE_LOAD = 2;

    public AdapterNotify(ArrayList<RESP_NewEntity> arrayList, IFragmentNotify view) {
        this.arrayList = arrayList;
        this.view = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEW) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.v2_item_notification, parent, false));
        } else if (viewType == TYPE_LOAD) {
            return new ViewProgressBar(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position == arrayList.size()) {
            view.onLoadMore();
        }

        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;

            final RESP_NewEntity newsEntity = arrayList.get(position);
            Log.e("Arr adapter", arrayList.toString());

            String string_result = "<font color=\"#4CAD50\"><b>Bạn</font></p> nhận được thông báo từ <b>" + newsEntity.getStore_name() + "</b>";
//            Spanned spanned = Html.fromHtml(string_result, Html.FROM_HTML_OPTION_USE_CSS_COLORS);
            Log.e(TAG, string_result);
            WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_notification_title, newsEntity.getTitle());
//            WidgetHelper.getInstance().setTextViewFromHtml(viewHolder.tv_notification_store_name, string_result);
//            WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_notification_time, newsEntity.getCreate_time());
            viewHolder.tv_notification_store_name.setText(Html.fromHtml(string_result), TextView.BufferType.SPANNABLE);

            if (newsEntity.getType() == 2) {
                WidgetHelper.getInstance().setImageResource(viewHolder.im_notification_type, R.mipmap.ic_fashions);
            } else if (newsEntity.getType() == 3) {
                WidgetHelper.getInstance().setImageResource(viewHolder.im_notification_type, R.mipmap.ic_notify_food);
            } else if (newsEntity.getType() == 4) {
                WidgetHelper.getInstance().setImageResource(viewHolder.im_notification_type, R.mipmap.ic_tech);
            } else if (newsEntity.getType() == 5) {
                WidgetHelper.getInstance().setImageResource(viewHolder.im_notification_type, R.mipmap.ic_heart);
            } else if (newsEntity.getType() == 6) {
                WidgetHelper.getInstance().setImageResource(viewHolder.im_notification_type, R.mipmap.ic_other_service);
            } else {
                WidgetHelper.getInstance().setImageResource(viewHolder.im_notification_type, R.mipmap.ic_other_service);
            }
            WidgetHelper.getInstance().comparingTime(viewHolder.tv_notification_time, newsEntity.getCreate_time());
            if (newsEntity.getSeen() == 1) {
                viewHolder.im_notification_status.setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.onItemClick(position, newsEntity, v);
                }
            });
        } else {
            ViewProgressBar viewProgressBar = (ViewProgressBar) holder;
            viewProgressBar.progressBar.getIndeterminateDrawable()
                    .setColorFilter(
                            Color.WHITE,
                            android.graphics.PorterDuff.Mode.MULTIPLY
                    );
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

        private ImageView im_notification_type, im_notification_status;
        private TextView tv_notification_store_name, tv_notification_time, tv_notification_title;

        ViewHolder(View itemView) {
            super(itemView);
            im_notification_type = (ImageView) itemView.findViewById(R.id.im_notification_type);
            im_notification_status = (ImageView) itemView.findViewById(R.id.im_notification_status);
            tv_notification_store_name = (TextView) itemView.findViewById(R.id.tv_notification_store_name);
            tv_notification_time = (TextView) itemView.findViewById(R.id.tv_notification_time);
            tv_notification_title = (TextView) itemView.findViewById(R.id.tv_notification_title);
        }
    }

    private class ViewProgressBar extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        ViewProgressBar(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_progress_bar);
        }
    }
}
