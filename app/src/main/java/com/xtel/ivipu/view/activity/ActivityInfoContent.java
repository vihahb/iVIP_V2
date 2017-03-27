package com.xtel.ivipu.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.xtel.ivipu.R;
import com.xtel.ivipu.view.activity.inf.IInfoContentView;
import com.xtel.ivipu.view.fragment.FragmentInfoAddress;
import com.xtel.ivipu.view.fragment.FragmentInfoGallery;
import com.xtel.ivipu.view.fragment.FragmentInfoProperties;
import com.xtel.ivipu.view.fragment.FragmentInfoSuggestion;

/**
 * Created by vihahb on 1/17/2017.
 */

public class ActivityInfoContent extends BasicActivity implements View.OnClickListener, IInfoContentView {

    //    private TabLayout tabLayout;
    private FrameLayout info_frame;
    private BottomNavigationView nav_bottom_info;
    private int REQUEST_PERMISSION_LOCATION_ADDRESS = 11;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initTransition();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_content);
        initToolbars();
//        initView();
        initControl();
    }

    private void initTransition() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // inside your activity (if you did not enable transitions in your theme)
            getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
// set an enter transition
            getWindow().setEnterTransition(new Explode());
            // set an exit transition
            getWindow().setExitTransition(new Explode());
        }
    }

    private void initToolbars() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_info_content);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initControl() {

        nav_bottom_info = (BottomNavigationView) findViewById(R.id.bottom_navigation_info);
        initMenuSelected();


        replaceDefaultFragment();
    }

    private void initMenuSelected() {
        nav_bottom_info.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_info_properties:
                        renameToolbar(R.string.tab_properties);
                        replaceFragment(R.id.info_frame, new FragmentInfoProperties(), "Properties");
                        break;
                    case R.id.nav_info_address:
                        renameToolbar(R.string.tab_address);
                        replaceFragment(R.id.info_frame, new FragmentInfoAddress(), "Address");
                        break;
                    case R.id.nav_info_gallery:
                        renameToolbar(R.string.tab_gallery);
                        replaceFragment(R.id.info_frame, new FragmentInfoGallery(), "Gallery");
                        break;
                    case R.id.nav_info_suggestion:
                        renameToolbar(R.string.tab_suggestion);
                        replaceFragment(R.id.info_frame, new FragmentInfoSuggestion(), "Suggestion");
                        break;
                }
                return true;
            }
        });
    }

//    private boolean validData(){
//        try {
//            testRecycle = (TestRecycle) getIntent().getSerializableExtra(Constants.RECYCLER_MODEL);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return testRecycle != null;
//    }
//
//    private void getDataFromFragmentShop(){
//        if (validData()){
//            String object = testRecycle.toString();
//            Log.d("Object Info", object);
//            String shopName = testRecycle.getShopName();
//            String shopMember = testRecycle.getShopMenber();
//            String shopLocation = testRecycle.getShopLocation();
//            String shopComment = testRecycle.getShopComment();
//
//            txt_info_shop_name.setText(shopName);
//            txt_info_shop_member.setText(shopMember);
//            txt_info_shop_location.setText(shopLocation);
//            txt_info_shop_comment.setText(shopComment);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            supportFinishAfterTransition();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

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
    public void showLongToast(String mes) {
        super.showLongToast(mes);
    }

    @Override
    protected void showProgressBar(boolean isTouchOutside, boolean isCancel, String title, String message) {
        super.showProgressBar(isTouchOutside, isCancel, title, message);
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public void closeProgressBar() {
        super.closeProgressBar();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    private void renameToolbar(int StringResource) {
        getSupportActionBar().setTitle(StringResource);
    }

    private void replaceDefaultFragment() {
        renameToolbar(R.string.tab_properties);
        replaceFragment(R.id.info_frame, new FragmentInfoProperties(), "Properties");
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("Request permiss code activity", String.valueOf(requestCode));
        FragmentInfoAddress fragmentInfoAddress = (FragmentInfoAddress) getSupportFragmentManager().findFragmentByTag("Address");
        if (requestCode == REQUEST_PERMISSION_LOCATION_ADDRESS){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
                Log.e("Go to", "Permission granted!");
                if (fragmentInfoAddress != null)
                    fragmentInfoAddress.initGoogleMaps();
                    fragmentInfoAddress.checkNetWork(1);
            } else {
                fragmentInfoAddress.requestPermission();
            }
        }
    }
}
