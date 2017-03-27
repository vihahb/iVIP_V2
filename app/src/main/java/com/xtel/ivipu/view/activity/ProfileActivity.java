package com.xtel.ivipu.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.RESP.RESP_Profile;
import com.xtel.ivipu.model.entity.UserInfo;
import com.xtel.ivipu.view.activity.inf.IProfileActivityView;
import com.xtel.ivipu.view.fragment.FavoriteFragment;
import com.xtel.ivipu.view.fragment.HistoryFragment;
import com.xtel.ivipu.view.fragment.NotifyFragment;
import com.xtel.ivipu.view.fragment.ProfileFragment;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.sdk.commons.Constants;

/**
 * Created by Vũ Hà Vi on 1/12/2016.
 */

public class ProfileActivity extends BasicActivity implements IProfileActivityView {

    BottomNavigationView bottom_nav_profile;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private Menu menu;
    private String session = LoginManager.getCurrentSession();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initToolbar();
        initBottomNav();
        getData();
    }

    private void replaceDefaultFragment() {
        replaceFragment(R.id.detail_frame, new ProfileFragment(), "PROFILE");
        renameToolbar(R.string.nav_Profile);
    }

    @SuppressWarnings("ConstantConditions")
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initBottomNav() {
        bottom_nav_profile = (BottomNavigationView) findViewById(R.id.bottom_navigation_profile);
        bottom_nav_profile.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_profile_profile:
                        replaceFragment(R.id.detail_frame, new ProfileFragment(), "PROFILE");
                        renameToolbar(R.string.nav_Profile);
                        showShortToast("Profile");
                        menu.getItem(0).setVisible(true);
                        break;
                    case R.id.nav_profile_history:
                        replaceFragment(R.id.detail_frame, new HistoryFragment(), "HISTORY");
                        renameToolbar(R.string.nav_history);
                        showShortToast("History");
                        menu.getItem(0).setVisible(false);
                        menu.getItem(1).setVisible(false);
                        break;
                    case R.id.nav_profile_favorite:
                        replaceFragment(R.id.detail_frame, new FavoriteFragment(), "FAVORITE");
                        renameToolbar(R.string.nav_favorite);
                        showShortToast("Favorite");
                        menu.getItem(0).setVisible(false);
                        menu.getItem(1).setVisible(false);
                        break;
                    case R.id.nav_profile_notify:
                        replaceFragment(R.id.detail_frame, new NotifyFragment(), "NOTIFY");
                        renameToolbar(R.string.nav_notify);
                        showShortToast("Notify");
                        menu.getItem(0).setVisible(false);
                        menu.getItem(1).setVisible(false);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_update) {
            if (session != null) {
                ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("PROFILE");
                if (fragment != null) {
                    fragment.onEnableView();
                }
                menu.getItem(0).setVisible(false);
                menu.getItem(1).setVisible(true);
            } else {
                showShortToast(getString(R.string.need_login));
            }
        } else if (id == R.id.action_update_done) {
            ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("PROFILE");
            if (fragment != null) {
                fragment.onDisableView();
                fragment.checkNetwork(this, 1);
            }
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void startActivitys(Class clazz) {
        super.startActivity(clazz);
    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        super.startActivityFinish(clazz);
    }

    @Override
    public void finishActivityBeforeStartActivity(Class clazz) {
        super.finishActivityBeforeStartActivity(clazz);
    }

    private void renameToolbar(int StringResource) {
        getSupportActionBar().setTitle(StringResource);
    }

    @Override
    public void finishActivity() {
        super.finishActivity();
    }

    @Override
    public void setProfileSuccess(UserInfo profile) {
    }

    @Override
    public void reloadProfile(RESP_Profile profile) {
    }

    @Override
    public void updateProfileSucc() {
    }

    @Override
    public void onPostPictureSuccess(String url, String server_path) {
    }

    @Override
    public void onPostPictureError(String mes) {
    }

    @Override
    public void onEnableView() {
    }

    @Override
    public void onDisableView() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_action_profile, menu);
        return true;
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

    private void getData() {
        String data = null;
        data = getIntent().getStringExtra(Constants.NOTIFY_KEY);
        if (data == null) {
            replaceDefaultFragment();
            Log.e("Data.....", "null");
        } else if (data.equals("notification")) {
            replaceNotify();
        }

    }

    private void replaceNotify() {
        if (session != null) {
            replaceFragment(R.id.detail_frame, new NotifyFragment(), "NOTIFY");
//            bottom_nav_profile.setSelectedItemId(R.id.nav_profile_notify);
            renameToolbar(R.string.nav_notify);
        } else {
//            replaceFragment(R.id.detail_frame, new NotifyFragment(), "NOTIFY");
////            bottom_nav_profile.setSelectedItemId(R.id.nav_profile_notify);
//            renameToolbar(R.string.nav_notify);
            alertLogin();
        }
    }

    private void alertLogin() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.TimePicker);
        dialog.setTitle("Thông báo");
        dialog.setMessage("Chức năng này cần phải đăng nhập để sử dụng. Bạn có muốn đăng nhập?");
        dialog.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.putExtra(Constants.DISABLE_KEY, Constants.DISABLE_KEY);
                startActivity(intent);
                finish();
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                startActivityAndFinish(HomeActivity.class);
            }
        });
        dialog.show();
    }

//    public void replaceNotify() {
////        replaceFragment(R.id.detail_frame, new NotifyFragment(), "NOTIFY");
////        renameToolbar(R.string.nav_notify);
//        tabLayout.getTabAt(3).select();
////        bottom_nav_profile.getMenu().getItem(3).setCheckable(true);
//    }
}
