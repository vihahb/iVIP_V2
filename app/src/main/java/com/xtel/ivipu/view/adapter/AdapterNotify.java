package com.xtel.ivipu.view.adapter;

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
    private ArrayList<RESP_NewEntity> arrayList;
    private IFragmentNotify view;
    private ArrayList<Integer> background_alpha_item;
    private boolean isLoadMore = true;
    private int TYPE_VIEW = 1, TYPE_LOAD = 2;

    public AdapterNotify(ArrayList<RESP_NewEntity> arrayList, IFragmentNotify view) {
        this.arrayList = arrayList;
        this.view = view;
        background_alpha_item = new ArrayList<>();
        background_alpha_item.add(R.drawable.item_list_notify_1);
        background_alpha_item.add(R.drawable.item_list_notify_2);
        background_alpha_item.add(R.drawable.item_list_notify_3);
        background_alpha_item.add(R.drawable.item_list_notify_4);
        background_alpha_item.add(R.drawable.item_list_notify_5);
        background_alpha_item.add(R.drawable.item_list_notify_6);
        background_alpha_item.add(R.drawable.item_list_notify_7);
        background_alpha_item.add(R.drawable.item_list_notify_8);
        background_alpha_item.add(R.drawable.item_list_notify_9);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEW) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcl_notify, parent, false));
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
            if (arrayList.get(position).getBg_position() == 0) {
                arrayList.get(position).setBg_position(background_alpha_item.get(random.nextInt(background_alpha_item.size())));
            }

            final RESP_NewEntity newsEntity = arrayList.get(position);
            Log.e("Arr adapter", arrayList.toString());

            WidgetHelper.getInstance().setAvatarImageURL(viewHolder.img_avatar, newsEntity.getLogo());
            WidgetHelper.getInstance().setAvatarImageURL(viewHolder.img_banner_shop, newsEntity.getBanner());
            WidgetHelper.getInstance().setTextViewNoResult(viewHolder.txt_title, newsEntity.getTitle());
            WidgetHelper.getInstance().setTextViewNoResult(viewHolder.txt_store, newsEntity.getStore_name());
            WidgetHelper.getInstance().setViewBackground(viewHolder.fr_canvas, newsEntity.getBg_position());

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

        private ImageView img_avatar, img_banner_shop;
        private TextView txt_title, txt_store;
        private View fr_canvas;

        ViewHolder(View itemView) {
            super(itemView);
            img_avatar = (ImageView) itemView.findViewById(R.id.item_notify_img_avatar);
            img_banner_shop = (ImageView) itemView.findViewById(R.id.item_notify_img_banner);
            txt_title = (TextView) itemView.findViewById(R.id.item_notify_txt_title);
            txt_store = (TextView) itemView.findViewById(R.id.item_notify_txt_store_name);
            fr_canvas = (View) itemView.findViewById(R.id.item_notify_img_background);
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
