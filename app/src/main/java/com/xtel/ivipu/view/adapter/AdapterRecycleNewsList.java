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
import com.xtel.ivipu.model.RESP.RESP_NewEntity;
import com.xtel.ivipu.view.fragment.inf.IFragmentNewsListView;
import com.xtel.ivipu.view.widget.WidgetHelper;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/16/2017.
 */

public class AdapterRecycleNewsList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //    Random random = new Random();
    private ArrayList<RESP_NewEntity> arrayList;
    private IFragmentNewsListView fragmentShopView;
    //    private ArrayList<Integer> background_alpha_item;
    private boolean isLoadMore = true;
    private int TYPE_VIEW = 1, TYPE_LOAD = 2;

    public AdapterRecycleNewsList(ArrayList<RESP_NewEntity> arrayList, IFragmentNewsListView fragmentShopView) {
        this.arrayList = arrayList;
        this.fragmentShopView = fragmentShopView;
//        background_alpha_item = new ArrayList<>();
//        background_alpha_item.add(R.drawable.item_background_1);
//        background_alpha_item.add(R.drawable.item_background_2);
//        background_alpha_item.add(R.drawable.item_background_3);
//        background_alpha_item.add(R.drawable.item_background_4);
//        background_alpha_item.add(R.drawable.item_background_5);
//        background_alpha_item.add(R.drawable.item_background_6);
//        background_alpha_item.add(R.drawable.item_background_7);
//        background_alpha_item.add(R.drawable.item_background_8);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEW) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.v2_item_rcl_news_home, parent, false));
        } else if (viewType == TYPE_LOAD) {
            return new ViewProgressBar(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar, parent, false));
        }
        return null;
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (arrayList != null && arrayList.size() != 0){
            if (position == arrayList.size()) {
                fragmentShopView.onLoadMore();
            }

            if (holder instanceof ViewHolder) {
                ViewHolder viewHolder = (ViewHolder) holder;
//                if (arrayList.get(position).getBg_position() == 0) {
//                    arrayList.get(position).setBg_position(background_alpha_item.get(random.nextInt(background_alpha_item.size())));
//                }

                final RESP_NewEntity newsEntity = arrayList.get(position);
                Log.e("Arr adapter", arrayList.toString());

                WidgetHelper.getInstance().setAvatarImageURL(viewHolder.im_news_store, newsEntity.getLogo());
                WidgetHelper.getInstance().setAvatarImageURL(viewHolder.im_news_banner, newsEntity.getBanner());
                WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_news_title, newsEntity.getTitle());
                WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_news_store_name, newsEntity.getStore_name());
                WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_news_view, String.valueOf(newsEntity.getView()));
                WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_news_like, String.valueOf(newsEntity.getLike()));
                WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_news_comment, String.valueOf(newsEntity.getComment()));
                WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_news_rates, String.valueOf(newsEntity.getRate()));
                WidgetHelper.getInstance().comparingTime(viewHolder.tv_create_time, newsEntity.getCreate_time());
                viewHolder.tv_news_store_name.setSelected(true);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragmentShopView.onItemClick(position, newsEntity, v);
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

        private ImageView im_news_store, im_news_banner;
        private TextView tv_news_title, tv_news_store_name, tv_news_rates, tv_news_view, tv_news_like, tv_news_comment, tv_create_time;

        ViewHolder(View itemView) {
            super(itemView);
            im_news_store = (ImageView) itemView.findViewById(R.id.im_news_store);
            im_news_banner = (ImageView) itemView.findViewById(R.id.im_news_banner);
            tv_news_title = (TextView) itemView.findViewById(R.id.tv_news_title);
            tv_news_store_name = (TextView) itemView.findViewById(R.id.tv_news_store_name);
//            txt_time = (TextView) itemView.findViewById(R.id.tv_history_time);
            tv_news_view = (TextView) itemView.findViewById(R.id.tv_news_view);
            tv_news_like = (TextView) itemView.findViewById(R.id.tv_news_like);
            tv_news_comment = (TextView) itemView.findViewById(R.id.tv_news_comment);
            tv_news_rates = (TextView) itemView.findViewById(R.id.tv_news_rates);
            tv_create_time = (TextView) itemView.findViewById(R.id.tv_create_time);
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
