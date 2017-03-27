package com.xtel.ivipu.view.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.HistoryTransactionObj;
import com.xtel.ivipu.view.activity.inf.IHistoryTransactionActivityView;
import com.xtel.ivipu.view.widget.WidgetHelper;

import java.util.ArrayList;

/**
 * Created by vuhavi on 11/03/2017.
 */

public class AdapterHistoryTransaction extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<HistoryTransactionObj> arrayList;
    private IHistoryTransactionActivityView view;
    private boolean isLoadMore;
    private int TYPE_VIEW = 1, TYPE_LOAD = 2;

    public AdapterHistoryTransaction(ArrayList<HistoryTransactionObj> arrayList, IHistoryTransactionActivityView view) {
        this.arrayList = arrayList;
        this.view = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEW) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_transaction, parent, false));
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

        if (arrayList != null && arrayList.size() > 0){
            if (holder instanceof ViewHolder){

            ViewHolder viewHolder = (ViewHolder) holder;
                HistoryTransactionObj transactionObj = arrayList.get(position);
                WidgetHelper.getInstance().setTextViewDate(viewHolder.tv_action_time, "Ngày: ", transactionObj.getAction_time());
                viewHolder.tv_action_des.setText(Html.fromHtml(transactionObj.getAction_desc()));
            } else {
                ViewProgressBar viewProgressBar = (ViewProgressBar) holder;
                viewProgressBar.progressBar.getIndeterminateDrawable().setColorFilter(
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
        if (isLoadMore && arrayList.size() > 0){
            return arrayList.size() + 1;
        } else {
            return arrayList.size();
        }
    }

    public void onSetLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    private class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_action_time, tv_action_des;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_action_des = (TextView) itemView.findViewById(R.id.tv_action_des);
            tv_action_time = (TextView) itemView.findViewById(R.id.tv_action_time);

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
