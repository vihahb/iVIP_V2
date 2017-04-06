package com.xtel.ivipu.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.presenter.LoginGroupPresenter;
import com.xtel.ivipu.view.activity.inf.ILoginGroup;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.sdk.callback.DialogListener;

/**
 * Created by vihahb on 1/10/2017.
 */

public class LoginGroupActivity extends BasicActivity implements ILoginGroup, View.OnClickListener {

    private EditText edt_user, edt_pass;
    private Button btn_login;
    private TextView tv_register, tv_reset, tv_active;


    private LoginGroupPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v2_login_phone);
        presenter = new LoginGroupPresenter(this);
        presenter.createCallbackManager();
//        initToolbars();
        initView();
    }

    private void initView() {
        edt_user = (EditText) findViewById(R.id.edt_login_phone);
        edt_pass = (EditText) findViewById(R.id.edt_login_password);
        btn_login = (Button) findViewById(R.id.btn_login_tonip);

//        tv_register = (TextView) findViewById(R.id.tv_signup);
        tv_active = (TextView) findViewById(R.id.tv_re_active);
        tv_reset = (TextView) findViewById(R.id.tv_reset);

//        tv_register.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
        tv_active.setOnClickListener(this);

        btn_login.setOnClickListener(this);
        setUnderLine();
    }

    private void setUnderLine() {
        WidgetHelper.getInstance().setUnderLine("Kích hoạt tài khoản?", tv_active);
        WidgetHelper.getInstance().setUnderLine("Quên mật khẩu?", tv_reset);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.requestPermission(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResultAccountKit(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void showShortToast(String mes) {
        super.showShortToast(mes);
    }

    @Override
    public void startActivity(Class clazz) {
        super.startActivity(clazz);
    }

    @Override
    public void startActivityForResults(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivitys(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void finishActivity() {
        super.finishActivity();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
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
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_login_tonip) {
            onLoginToNIP();
        } else if (id == R.id.tv_reset) {
            onReset();
        } else if (id == R.id.tv_re_active) {
            onReActive();
        }
//        else if (id == R.id.tv_signup) {
//            onSignup();
//        }
    }

    private void onReActive() {
        startActivity(ReactiveAccountActivity.class);
    }

    private void onReset() {
        presenter.onLoginAccountKit();
    }

    private void onLoginToNIP() {
        String user = edt_user.getText().toString();
        String pass = edt_pass.getText().toString();

        presenter.onLoginNip(user, pass, true);
    }
}
