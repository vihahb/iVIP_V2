package com.xtel.ivipu.view.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.VoucherListObj;
import com.xtel.ivipu.view.fragment.inf.IFragmentHomeVoucherListView;
import com.xtel.ivipu.view.widget.WidgetHelper;

import java.util.ArrayList;

/**
 * Created by vivhp on 4/5/2017.
 */

public class AdapterVoucherList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private IFragmentHomeVoucherListView view;
    private ArrayList<VoucherListObj> arrayList;
    private boolean isLoadMore = false;
    private int TYPE_VIEW = 1, TYPE_LOAD = 2;
    private int state;

    public AdapterVoucherList(ArrayList<VoucherListObj> arrayList, IFragmentHomeVoucherListView view) {
        this.arrayList = arrayList;
        this.view = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEW) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.v2_item_voucher, parent, false));
        } else if (viewType == TYPE_LOAD) {
            return new ViewProgressBar(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == arrayList.size()) {
            view.onLoadMore();
        }

        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;

            VoucherListObj obj = arrayList.get(position);
            Log.e("Arr voucher adapter", arrayList.toString());
            state = obj.getState();
            if (state == 0) {
                WidgetHelper.getInstance().setViewBackground(viewHolder.ln_state, R.color.color_unused);
            } else if (state == 1) {
                WidgetHelper.getInstance().setViewBackground(viewHolder.ln_state, R.color.color_used);
            } else if (state == 2) {
                WidgetHelper.getInstance().setViewBackground(viewHolder.ln_state, R.color.color_expired);
            }

            WidgetHelper.getInstance().setAvatarImageURL(viewHolder.img_voucher_banner, obj.getBanner());
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

        private ImageView img_voucher_banner;
        private LinearLayout ln_state;

        public ViewHolder(View itemView) {
            super(itemView);
            img_voucher_banner = (ImageView) itemView.findViewById(R.id.img_voucher_banner);
            ln_state = (LinearLayout) itemView.findViewById(R.id.ln_state);
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
