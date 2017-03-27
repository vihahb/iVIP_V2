package com.xtel.ivipu.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xtel.ivipu.R;
import com.xtel.ivipu.presenter.ResetPresenter;
import com.xtel.ivipu.view.activity.inf.IResetView;
import com.xtel.ivipu.view.widget.WidgetHelper;

/**
 * Created by vihahb on 1/11/2017.
 */

public class ResetActivity extends BasicActivity implements IResetView, View.OnClickListener {
    private ResetPresenter presenter;
    private EditText edt_new_pasword;
    private Button btn_change_pasword;

    private String password;
    private String authorization_code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        presenter = new ResetPresenter(this);
        presenter.createCallbackManager();

        initToolbars();
        initView();
        getDataFromAccountKit();
    }

    private void initView() {
        edt_new_pasword = (EditText) findViewById(R.id.edt_new_pasword);
        btn_change_pasword = (Button) findViewById(R.id.btn_change_password);

        btn_change_pasword.setOnClickListener(this);
    }

    private void initToolbars() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reset);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private boolean validData() {
        password = edt_new_pasword.getText().toString();
        if (password.isEmpty()) {
            showShortToast("Vui long nhap mat khau");
            return false;
        } else
            return true;
    }

    private void getData() {
        if (validData()) {
            password = edt_new_pasword.getText().toString();
            presenter.resetAccountNIP(password, authorization_code);
        }
    }

    public void getDataFromAccountKit() {
        Intent intent = getIntent();
        authorization_code = intent.getStringExtra("authorization");
        Log.e("ma code", authorization_code);
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
    }

    @Override
    public void finishActivity() {
        super.finishActivity();
    }

    @Override
    public void showShortToast(String mes) {
        super.showShortToast(mes);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

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
        WidgetHelper.getInstance().showAlertNetwork(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_change_password) {
            onResetPassword();
        }
    }

    private void onResetPassword() {
        getData();
    }
}
