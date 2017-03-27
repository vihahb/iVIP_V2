package com.xtel.ivipu.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.MemberObj;
import com.xtel.ivipu.view.fragment.inf.IFragmentMemberCard;
import com.xtel.ivipu.view.widget.RoundImage;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.utils.SharedPreferencesUtils;

import java.util.ArrayList;

/**
 * Created by vuhavi on 07/03/2017.
 */

public class AdapterMemberCard extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MemberObj> arrayList;
    private IFragmentMemberCard view;
    private boolean isLoadMore;
    private int TYPE_VIEW = 1, TYPE_LOAD = 2;

    public AdapterMemberCard(ArrayList<MemberObj> arrayList, IFragmentMemberCard view) {
        this.arrayList = arrayList;
        this.view = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEW) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_card, parent, false));
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

        if (arrayList != null && arrayList.size() > 0){
            if (holder instanceof ViewHolder){
                ViewHolder viewHolder = (ViewHolder) holder;

                final MemberObj memberObj = arrayList.get(position);

                String store_name_nonUnichar = WidgetHelper.getInstance().mapping_Char(memberObj.getStore_name());
                String user_name_nonUnichar = WidgetHelper.getInstance().mapping_Char(SharedPreferencesUtils.getInstance().getStringValue(Constants.PROFILE_FULL_NAME));

                WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_card_store_name, "Cua hang: " + store_name_nonUnichar);
                WidgetHelper.getInstance().setTextViewDate(viewHolder.tv_date_created, "Ngay khoi tao: ", memberObj.getCreate_time());
                WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_card_user_name, user_name_nonUnichar);
                WidgetHelper.getInstance().setAvatarImageURL(viewHolder.img_card_type, memberObj.getMember_card());
                WidgetHelper.getInstance().setAvatarImageURL(viewHolder.img_card_ava, memberObj.getStore_logo());
                setTypeFace(view.getContext(), viewHolder.tv_card_store_name);
                setTypeFace(view.getContext(), viewHolder.tv_card_user_name);
                setTypeFace(view.getContext(), viewHolder.tv_date_created);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.onClickCardItem(position, memberObj, v);
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

    private void setTypeFace(Context context, TextView textView){
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/halter.ttf");
        textView.setTypeface(custom_font);
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

    private class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_card_store_name, tv_card_user_name, tv_date_created;
        private ImageView img_card_type;
        private RoundImage img_card_ava;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_card_store_name = (TextView) itemView.findViewById(R.id.tv_card_store_name);
            tv_date_created = (TextView) itemView.findViewById(R.id.tv_date_created);
            tv_card_user_name = (TextView) itemView.findViewById(R.id.tv_card_user_name);

            img_card_ava = (RoundImage) itemView.findViewById(R.id.img_card_ava);
            img_card_type = (ImageView) itemView.findViewById(R.id.img_card_type);
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
