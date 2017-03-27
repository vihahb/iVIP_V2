package com.xtel.ivipu.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xtel.ivipu.R;
import com.xtel.ivipu.presenter.ReactivePresenter;
import com.xtel.ivipu.view.activity.inf.IReactiveAccount;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.nipservicesdk.model.entity.Error;

/**
 * Created by vihahb on 1/12/2017.
 */

public class ReactiveAccountActivity extends BasicActivity implements IReactiveAccount, View.OnClickListener {

    private EditText edt_input_phone;
    private Button btnReactive;

    private ReactivePresenter presenter;

    private String phone_number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reactive);
        presenter = new ReactivePresenter(this);
        presenter.createCallbackManager();
        initToolbars();
        initView();
    }

    private void initView() {
        edt_input_phone = (EditText) findViewById(R.id.edt_input_phone);
        btnReactive = (Button) findViewById(R.id.btn_reactive);
        btnReactive.setOnClickListener(this);
    }

    /**
     * Step 1
     **/
    private void checkDataInput() {
        if (ValidData()) {
            if (!checkPhone()) {
                String mes = "Sai dinh dang so dien thoai.";
                showShortToast(mes);
            } else {
                String mes = "Phone number: " + phone_number;
                phone_number = edt_input_phone.getText().toString();
                showShortToast(mes);
                presenter.onReactiveAccount(phone_number);
            }
        }
    }

    /**
     * Step 2
     **/
    private boolean ValidData() {
        String mes;
        phone_number = edt_input_phone.getText().toString();
        if (phone_number.isEmpty()) {
            mes = "So dien thoai khong duoc de trong";
            showShortToast(mes);
            return false;
        } else {
            mes = "OK, PHONE";
            showShortToast(mes);
            return true;
        }

    }

    /**
     * Step 3
     * if true
     * return step 1 request
     **/
    private boolean checkPhone() {
        return !(phone_number.length() < 10 || phone_number.length() > 11);

    }

    private void initToolbars() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reactive);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.onRequestCallbackPermission(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResultAccountKit(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finishActivitys();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLongToast(String message) {
        super.showLongToast(message);
    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(Error error) {

    }

    @Override
    public void startActivitys(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void finishActivitys() {
        super.finishActivity();
    }

    @Override
    public void startActivityForResults(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
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
        WidgetHelper.getInstance().showAlertNetwork(getContext());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_reactive) {
            checkDataInput();
        }
    }
}
