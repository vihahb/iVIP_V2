package com.xtel.ivipu.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.MemberObj;
import com.xtel.ivipu.view.fragment.inf.IFragmentMemberCard;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.utils.SharedPreferencesUtils;

import java.util.ArrayList;

/**
 * Created by vivhp on 4/5/2017.
 */

public class AdapterCard extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean isLoadMore = true;
    private int TYPE_VIEW = 1, TYPE_LOAD = 2;
    private IFragmentMemberCard view;
    private ArrayList<MemberObj> arrayList;
    private String User_name = SharedPreferencesUtils.getInstance().getStringValue(Constants.PROFILE_FULL_NAME);

    public AdapterCard(IFragmentMemberCard view, ArrayList<MemberObj> arrayList) {
        this.view = view;
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            MemberObj memberObj = arrayList.get(position);
            Log.e("Adapter card", JsonHelper.toJson(arrayList));

            ViewHolder viewHolder = (ViewHolder) holder;
            WidgetHelper.getInstance().setAvatarImageURL(viewHolder.img_Card, memberObj.getMember_card());
            WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_user_name, User_name);
            WidgetHelper.getInstance().setTextViewDate(viewHolder.tv_create_time, "Ngày tạo: ", memberObj.getCreate_time());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_Card;
        private TextView tv_user_name, tv_create_time;

        public ViewHolder(View itemView) {
            super(itemView);
            img_Card = (ImageView) itemView.findViewById(R.id.img_card_type);
            tv_user_name = (TextView) itemView.findViewById(R.id.tv_card_user_name);
            tv_create_time = (TextView) itemView.findViewById(R.id.tv_date_created);
        }
    }
}
