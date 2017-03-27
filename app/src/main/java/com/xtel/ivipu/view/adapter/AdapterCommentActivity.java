package com.xtel.ivipu.view.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.xtel.ivipu.model.entity.CommentObj;
import com.xtel.ivipu.view.activity.inf.IActivityComment;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.nipservicesdk.utils.JsonHelper;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/18/2017.
 */

public class AdapterCommentActivity extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<CommentObj> arrayList;
    private IActivityComment view;
    private boolean isLoadMore = true;
    private int TYPE_VIEW = 1, TYPE_LOAD = 2;

    public AdapterCommentActivity(Context context, Activity activity, ArrayList<CommentObj> arrayList, IActivityComment view) {
        this.context = context;
        this.activity = activity;
        this.arrayList = arrayList;
        this.view = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEW) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false));
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
            final CommentObj commentObj = arrayList.get(position);
            Log.e("Arr adapter", JsonHelper.toJson(arrayList));
            ViewHolder viewHolder = (ViewHolder) holder;
            WidgetHelper.getInstance().setTextViewNoResult(viewHolder.txt_user_name, commentObj.getFullname());
            WidgetHelper.getInstance().setTextViewNoResult(viewHolder.txt_comment_content, "Nội dung: ", commentObj.getComment());
            WidgetHelper.getInstance().setTextViewDate(viewHolder.txt_date_time, "Đã bình luận vào: ", commentObj.getComment_time());
            WidgetHelper.getInstance().setAvatarImageURL(viewHolder.img_avatar, commentObj.getAvatar());
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

        private TextView txt_user_name, txt_date_time, txt_comment_content;
        private ImageView img_avatar, img_action_comment;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_user_name = (TextView) itemView.findViewById(R.id.txt_user_name);
            txt_comment_content = (TextView) itemView.findViewById(R.id.txt_user_comment);
            txt_date_time = (TextView) itemView.findViewById(R.id.txt_date_time_comment);

            img_avatar = (ImageView) itemView.findViewById(R.id.ic_user_avatar_comment);
//            img_action_comment = (ImageView) itemView.findViewById(R.id.ic_comment);
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
