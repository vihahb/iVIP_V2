package com.xtel.ivipu.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.HotSaleNewsObj;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.nipservicesdk.utils.JsonHelper;

import java.util.ArrayList;

/**
 * Created by vivhp on 4/9/2017.
 */

public class AdapterNewPlugin extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<HotSaleNewsObj> arrayList;
    private int type;

    public AdapterNewPlugin(ArrayList<HotSaleNewsObj> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_hot, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            Log.e("Adapter Hot New", JsonHelper.toJson(arrayList));

            HotSaleNewsObj newsObj = arrayList.get(position);
            type = newsObj.getType();
            WidgetHelper.getInstance().setImageURL(viewHolder.img_new_banner, newsObj.getBanner());
            WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_new_title, newsObj.getTitle());
            if (type == 1) {
                WidgetHelper.getInstance().setImageResource(viewHolder.img_new_type, R.mipmap.ic_hot);
            } else if (type == 2) {
                WidgetHelper.getInstance().setImageResource(viewHolder.img_new_type, R.mipmap.ic_new);
            } else if (type == 3) {

            }
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_new_type, img_new_banner;
        private TextView tv_new_title;

        public ViewHolder(View itemView) {
            super(itemView);
            img_new_type = (ImageView) itemView.findViewById(R.id.img_new_type);
            img_new_banner = (ImageView) itemView.findViewById(R.id.img_new_banner);
            tv_new_title = (TextView) itemView.findViewById(R.id.tv_new_title);
        }
    }
}
