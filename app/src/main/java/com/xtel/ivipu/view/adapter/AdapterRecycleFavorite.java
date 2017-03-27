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
import com.xtel.ivipu.model.entity.FavoriteEntity;
import com.xtel.ivipu.view.fragment.inf.IFragmentFavoriteView;
import com.xtel.ivipu.view.widget.WidgetHelper;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/10/2017.
 */

public class AdapterRecycleFavorite extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<RESP_NewEntity> arrayList;
    private IFragmentFavoriteView fragmentFavoriteView;
    private boolean isLoadMore = true;
    private int TYPE_VIEW = 1, TYPE_LOAD = 2;

    public AdapterRecycleFavorite(ArrayList<RESP_NewEntity> arrayList, IFragmentFavoriteView fragmentFavoriteView) {
        this.arrayList = arrayList;
        this.fragmentFavoriteView = fragmentFavoriteView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEW) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false));
        } else if (viewType == TYPE_LOAD) {
            return new ViewProgressBar(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar, parent, false));
        }
       return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (position == arrayList.size()){
            fragmentFavoriteView.onLoadMore();
        }

        if (holder instanceof ViewHolder){
            ViewHolder viewHolder = (ViewHolder) holder;

            Log.e("Arr adapter", arrayList.toString());
            final RESP_NewEntity newsEntity = arrayList.get(position);

            WidgetHelper.getInstance().setTextViewNoResult(viewHolder.txt_title, newsEntity.getTitle());
            WidgetHelper.getInstance().setTextViewNoResult(viewHolder.txt_shop_name, newsEntity.getStore_name());
            WidgetHelper.getInstance().setTextViewDate(viewHolder.txt_time, "Thời gian: ", newsEntity.getCreate_time());
            WidgetHelper.getInstance().setTextViewNoResult(viewHolder.txt_view, String.valueOf(newsEntity.getView()));
            WidgetHelper.getInstance().setTextViewNoResult(viewHolder.txt_like, String.valueOf(newsEntity.getLike()));
            WidgetHelper.getInstance().setTextViewNoResult(viewHolder.txt_comment, String.valueOf(newsEntity.getComment()));
            WidgetHelper.getInstance().setAvatarImageURL(viewHolder.img_favorite_brand, newsEntity.getLogo());
            WidgetHelper.getInstance().setAvatarImageURL(viewHolder.img_favorite_banner, newsEntity.getBanner());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentFavoriteView.onItemClick(position, newsEntity, v);
                }
            });
        } else {
            ViewProgressBar viewProgressBar = (AdapterRecycleFavorite.ViewProgressBar) holder;
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

        private ImageView img_favorite_brand, img_favorite_banner;
        private TextView txt_title, txt_shop_name, txt_time, txt_view, txt_like, txt_comment;

        ViewHolder(View itemView) {
            super(itemView);
            img_favorite_brand = (ImageView) itemView.findViewById(R.id.img_favorite_brand);
            img_favorite_banner = (ImageView) itemView.findViewById(R.id.img_favorite_banner);
            txt_title = (TextView) itemView.findViewById(R.id.tv_favorite_title);
            txt_shop_name = (TextView) itemView.findViewById(R.id.tv_favorite_shop_name);
            txt_time = (TextView) itemView.findViewById(R.id.tv_favorite_time);
            txt_view = (TextView) itemView.findViewById(R.id.tv_favorite_view);
            txt_like = (TextView) itemView.findViewById(R.id.tv_favorite_like);
            txt_comment = (TextView) itemView.findViewById(R.id.tv_favorite_comment);
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
