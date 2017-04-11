package com.xtel.ivipu.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.presenter.CommentActionPresenter;
import com.xtel.ivipu.view.activity.inf.IComment;
import com.xtel.ivipu.view.widget.RoundImage;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.commons.NetWorkInfo;
import com.xtel.sdk.utils.SharedPreferencesUtils;

/**
 * Created by vivhp on 2/20/2017.
 */

public class ActionCommentActivity extends BasicActivity implements IComment {

    int layoutId = R.layout.action_comment_activity;
    int id_toolbar = R.id.toolbar_comment_action;
    int news_id;
    String cmtContent;
    private TextView user_name, date_reg;
    private RoundImage img_avatar;
    private EditText edt_action_comment;
    private CommentActionPresenter presenter;
    private String st_news_id;
    private int REQUEST_REFRESH = 19;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        presenter = new CommentActionPresenter(this);
        initToolbar(id_toolbar, this.getString(R.string.activity_comment));
        iniView();
    }

    private void iniView() {
        edt_action_comment = (EditText) findViewById(R.id.edt_comment);
        user_name = (TextView) findViewById(R.id.cmt_user_name);
        img_avatar = (RoundImage) findViewById(R.id.cmt_avatar);
        date_reg = (TextView) findViewById(R.id.cmt_date_reg);
        initUserData();
    }

    private void initUserData() {
        String full_name = SharedPreferencesUtils.getInstance().getStringValue(Constants.PROFILE_FULL_NAME);
        String avatar = SharedPreferencesUtils.getInstance().getStringValue(Constants.PROFILE_AVATAR);
        long number_date_reg = SharedPreferencesUtils.getInstance().getLongValue(Constants.PROFILE_JOINT_DATE);

        WidgetHelper.getInstance().setTextViewNoResult(user_name, full_name);
        WidgetHelper.getInstance().setAvatarImageURL(img_avatar, avatar);
        WidgetHelper.getInstance().setTextViewDate(date_reg, "Ngày tham gia: ", number_date_reg);
    }

    private void validContent() {
        if (edt_action_comment.getText().length() > 0) {
            cmtContent = edt_action_comment.getText().toString();
            Log.e("Comment content", cmtContent);
            setData2Comment();
        } else {
            showShortToast("Vui lòng nhập nội dung bình luận");
        }
    }

    private void checkNetWork(int type) {
        final Context context = getContext();
        if (!NetWorkInfo.isOnline(context)) {
            WidgetHelper.getInstance().showAlertNetwork(context);
        } else {
            if (type == 1) {
                presenter.postComment(news_id, cmtContent);
            } else if (type == 2) {

            }
        }
    }

    private void sendCommentDataRefesh() {
        startActivityForResultValue(ListCommentActivity.class, Constants.NEWS_ID, String.valueOf(st_news_id), REQUEST_REFRESH);
        finish();
    }

    private void postDataFromListComment() {
        if (validIdNewsl()) {
            Log.e("Status Comment", "Ok");
            validContent();
        }
    }

    private boolean validIdNewsl() {
        Intent intent = getIntent();
        st_news_id = intent.getStringExtra(Constants.NEWS_ID);
        news_id = Integer.parseInt(st_news_id);
        Log.e("Cmt news id", String.valueOf(news_id));

        return news_id != -1;
    }

    private void setData2Comment() {
        checkNetWork(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_send_comment) {
            postDataFromListComment();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onPostCommentSuccess() {
        showShortToast("Bình luận thành công!");
        sendCommentDataRefesh();
    }

    @Override
    public void onPostCommentError() {

    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        super.startActivityFinish(clazz);
    }

    @Override
    public void onNetworkDisable() {
        if (!NetWorkInfo.isOnline(this)) {
            WidgetHelper.getInstance().showAlertNetwork(getContext());
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
