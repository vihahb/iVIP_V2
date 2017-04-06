package com.xtel.ivipu.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.CommentObj;
import com.xtel.ivipu.presenter.CommentPresenter;
import com.xtel.ivipu.view.activity.inf.IActivityComment;
import com.xtel.ivipu.view.adapter.AdapterCommentActivity;
import com.xtel.ivipu.view.widget.ProgressView;
import com.xtel.sdk.callback.DialogListener;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.commons.NetWorkInfo;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/18/2017.
 */

public class ListCommentActivity extends BasicActivity implements IActivityComment, View.OnClickListener {

    int news_id, page = 1, pagesize = 9;
    ImageButton btn_send;
    EditText edt_comment;
    private int REQUEST_ACTION_COMMENT = 91;
    private int id_toolbar = R.id.toolbar_comment;
    private ArrayList<CommentObj> arrayList;
    private RecyclerView rcl_comment;
    private ProgressView progressView;
    private AdapterCommentActivity adapter;
    private CommentPresenter presenter;
    private String st_news_id;
    private String cmtContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_comment_activity);
        initToolbar(id_toolbar, this.getString(R.string.activity_comment));
        presenter = new CommentPresenter(this);
        initView();
        initRecyclerView();
        initProgressView();
    }

    private void initView() {
        btn_send = (ImageButton) findViewById(R.id.btn_send);
        edt_comment = (EditText) findViewById(R.id.edt_comment);
        btn_send.setOnClickListener(this);
    }

    private void initRecyclerView() {
        arrayList = new ArrayList<>();
        rcl_comment = (RecyclerView) findViewById(R.id.rcl_ivip);
        rcl_comment.setHasFixedSize(true);
        rcl_comment.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterCommentActivity(getContext(), getActivity(), arrayList, this);
        rcl_comment.setAdapter(adapter);
    }

    private void initProgressView() {
        progressView = new ProgressView(this, null);
        progressView.initData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again), getString(R.string.loading_data), Color.parseColor("#05b589"));
        progressView.setUpWithView(rcl_comment);

        progressView.onLayoutClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.onSetLoadMore(true);
                adapter.notifyDataSetChanged();
                getData();
            }
        });

        progressView.onRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                arrayList.clear();
                adapter.onSetLoadMore(true);
                getData();
            }
        });

        progressView.onSwipeLayoutPost(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        });
    }

    private void setDataRecyclerView(ArrayList<CommentObj> commentObjs) {
        arrayList.addAll(commentObjs);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void onPostCommentSuccess() {
        showShortToast("Bình luận thành công!");
        page = 1;
        arrayList.clear();
        getData();
        adapter.notifyDataSetChanged();
        edt_comment.setText(null);
    }

    private void getData() {
//        progressView.hideData();
        progressView.setRefreshing(true);
        getDataFragment();
//        getDataFromComment();
    }

    private void getDataFragment() {
        if (validIdNewsl()) {
            Log.e("Status Comment", "Ok");
            initDataComment();
        }
    }

//    private void getDataFromComment(){
//        if (validComment()) {
//            Log.e("Status Comment", "Ok");
//                    page = 1;
//                    arrayList.clear();
//                    initDataComment();
//        }
//    }

    private boolean validIdNewsl() {
        Intent intent = getIntent();
        st_news_id = intent.getStringExtra(Constants.NEWS_ID);
        news_id = Integer.parseInt(st_news_id);
        Log.e("Cmt news id", String.valueOf(news_id));

        return news_id != -1;
    }

    private void initDataComment() {
        //Code presenter get data here
        checkNetWork(1);
    }


    private void checkListData() {
//        progressView.disableSwipe();
        progressView.setRefreshing(false);

        if (arrayList.size() == 0) {
            progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again));
            progressView.show();
        } else {
            rcl_comment.getAdapter().notifyDataSetChanged();
            progressView.hide();
        }
    }

    @Override
    public void onGetCommentSuccess(ArrayList<CommentObj> arrayList) {
        if (arrayList.size() < 9) {
            adapter.onSetLoadMore(false);
        }
        setDataRecyclerView(arrayList);
        checkListData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onGetCommentError(String mes) {
        Log.e("Comment activity err", mes);
        showShortToast(mes);
    }

    @Override
    public void postCommentSuccess() {

    }

    @Override
    public void postCommentError() {

    }

    @Override
    public void onNetworkDisable() {
        showMaterialDialog(true, true, "Thông báo", "Kết nối thất bai.\nVui lòng kiểm tra kết nối internet.", null, "OK", new DialogListener() {
            @Override
            public void onClicked(Object object) {
            }

            @Override
            public void onCancel() {
            }
        });
    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        super.startActivityFinish(clazz);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_comment) {
            sentNewsIdCommentAction();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sentNewsIdCommentAction() {
        startActivityForResultValue(ActionCommentActivity.class, Constants.NEWS_ID, String.valueOf(st_news_id), REQUEST_ACTION_COMMENT);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_comment_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onLoadMore() {
        page++;
        getData();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void setData2Comment() {
        checkNetWork(2);
    }

    private void postDataFromListComment() {
        if (validIdNewsl()) {
            Log.e("Status Comment", "Ok");
            validContent();
        }
    }

    private void validContent() {
        if (edt_comment.getText().length() > 0) {
            cmtContent = edt_comment.getText().toString();
            Log.e("Comment content", cmtContent);
            setData2Comment();
        } else {
            showShortToast("Vui lòng nhập nội dung bình luận");
        }
    }


    private void checkNetWork(int type) {
        final Context context = getContext();
        if (!NetWorkInfo.isOnline(context)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.TimePicker);
            dialog.setTitle("Kết nối không thành công");
            dialog.setMessage("Rất tiếc, không thể kết nối internet. Vui lòng kiểm tra kết nối Internet.");
            dialog.setPositiveButton("Cài đặt", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                }
            });
            dialog.show();
        } else {
            if (type == 1) {
                presenter.getComment(news_id, page, pagesize);
            } else if (type == 2) {
                presenter.postComment(news_id, cmtContent);
            } else if (type == 3) {

            } else if (type == 4) {

            } else if (type == 5) {

            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_send) {
            postDataFromListComment();
        }
    }
}
