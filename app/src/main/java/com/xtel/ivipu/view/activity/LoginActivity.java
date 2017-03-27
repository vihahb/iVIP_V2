package com.xtel.ivipu.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.presenter.LoginPresenter;
import com.xtel.ivipu.view.activity.inf.ILoginView;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.sdk.commons.Constants;

/**
 * Created by vihahb on 1/10/2017.
 */

public class LoginActivity extends BasicActivity implements ILoginView, View.OnClickListener {

    Button btn_login_facebook, btn_login_account_kit;
    TextView tv_Signup;
    ImageView img_login_phone, img_login_facebook;
    LoginPresenter presenter;
    boolean isDisable = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this);
        presenter.createCallBackManager();
        presenter.createNipCallbackManager();
        initView();
        getData();
    }

    private void initView() {
        btn_login_facebook = (Button) findViewById(R.id.btn_facebook_login);
        btn_login_account_kit = (Button) findViewById(R.id.btn_phone_login);

        img_login_phone = (ImageView) findViewById(R.id.img_login_phone);
        img_login_facebook = (ImageView) findViewById(R.id.img_login_facebook);

//        tv_Signup = (TextView) findViewById(R.id.tv_signup);

        btn_login_facebook.setOnClickListener(this);
        btn_login_account_kit.setOnClickListener(this);
        img_login_phone.setOnClickListener(this);
        img_login_facebook.setOnClickListener(this);
//        tv_Signup.setOnClickListener(this);
    }


    @Override
    public void onSuccess() {

    }

    @Override
    public void onEror() {

    }

    @Override
    public void showShortToast(String mess) {
        super.showShortToast(mess);
    }

    @Override
    public void startActivitys(Class clazz) {
        super.startActivity(clazz);
    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        super.startActivityFinish(clazz);
    }

    public void finishActivity() {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onNetworkDisable() {
        WidgetHelper.getInstance().showAlertNetwork(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_facebook_login) {
            onLoginFacebook();
        } else if (id == R.id.btn_phone_login) {
            onLoginGroup();
        } else if (id == R.id.img_login_phone) {
            onLoginGroup();
        } else if (id == R.id.img_login_facebook) {
            onLoginFacebook();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onResultFacebookLogin(requestCode, resultCode, data);
    }

    private void onLoginGroup() {
        startActivity(LoginGroupActivity.class);
    }

    private void onLoginFacebook() {
        presenter.onLoginFacebook();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.onRequestCallbackPermission(requestCode, permissions, grantResults);
    }

    public void setIsPassed(boolean isPassed) {
        isDisable = isPassed;
    }

    @Override
    public void onBackPressed() {
        if (isDisable) {
            startActivityFinish(HomeActivity.class);
        } else {
            super.onBackPressed();
        }
    }

    public void getData() {
        String data = null;
        data = getIntent().getStringExtra(Constants.DISABLE_KEY);
        if (data == null) {
            isDisable = false;
            Log.e("Data.....", "null");
        } else if (data.equals(Constants.DISABLE_KEY)) {
            Log.e("Data.....", "Not null");
            isDisable = true;
        }
    }
}
