package com.xtel.ivipu.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.xtel.ivipu.R;
import com.xtel.ivipu.model.RESP.RESP_Short;
import com.xtel.ivipu.model.entity.UserShort;
import com.xtel.ivipu.presenter.HomePresenter;
import com.xtel.ivipu.view.activity.inf.IHome;
import com.xtel.ivipu.view.fragment.FragmentHomeFashionMakeUp;
import com.xtel.ivipu.view.fragment.FragmentHomeFood;
import com.xtel.ivipu.view.fragment.FragmentHomeHealth;
import com.xtel.ivipu.view.fragment.FragmentHomeNewsForMe;
import com.xtel.ivipu.view.fragment.FragmentHomeNewsList;
import com.xtel.ivipu.view.fragment.FragmentHomeOtherService;
import com.xtel.ivipu.view.fragment.FragmentHomeTechnology;
import com.xtel.ivipu.view.fragment.FragmentMemberCard;
import com.xtel.ivipu.view.fragment.FragmentMyShop;
import com.xtel.ivipu.view.widget.CircleTransform;
import com.xtel.ivipu.view.widget.LinearLayoutAnimationSlideBottom;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.dialog.BadgeIcon;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by vivhp on 12/29/2016.
 */

public class HomeActivity extends BasicActivity implements NavigationView.OnNavigationItemSelectedListener, IHome, View.OnClickListener {
    HomePresenter presenter;
    Toolbar toolbar;
    LinearLayout ln_layout_transparent, ln_layout_nav_item;
    LinearLayoutAnimationSlideBottom ln_popup_item;
    BottomNavigationView nav_bottom_home;
    FrameLayout fr_home_overlay;
    LoginActivity loginActivity;
    boolean isChecked = false;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBar actionBar;
    private MenuItem mMenuItem;
    private LinearLayout mLinearLayout;
    private Context mContext;
    private int notifications;
    private String avatar;
    private String session = LoginManager.getCurrentSession();
    private Button btn_health, btn_service, btn_news_for_location;
    private boolean isShowing = false;
    private boolean item_health_selected = false;
    private boolean item_service_selected = false;
    private boolean item_news_for_me_selected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter = new HomePresenter(this);
//        presenter.onGetUserNip();
        mContext = HomeActivity.this;
        presenter.postFCMKey();
        presenter.onGetShortUser();
        presenter.onGetUserNip();
        loginActivity = new LoginActivity();
        initView();
        initNavigation();
        initNavigationWidget();
        initBottomNavigation();
        getData();
    }

    private void initNavigationWidget() {
//        View view = navigationView.getHeaderView(0);
    }

    public void initBottomNavigation() {
        nav_bottom_home = (BottomNavigationView) findViewById(R.id.bottom_navigation_item);
        initMenuSelected();
        replaceDefaultFragment();
    }

    private void initMenuSelected() {
        nav_bottom_home.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_news:
                                btn_health.setPressed(false);
                                btn_service.setPressed(false);
                                btn_news_for_location.setPressed(false);
                                btn_health.setActivated(false);
                                btn_news_for_location.setActivated(false);
                                btn_service.setActivated(false);
                                disableItem();
                                replaceFragment(R.id.home_frame, new FragmentHomeNewsList(), "NEWS");
                                renameToolbar(R.string.nav_new_list);
                                break;
                            case R.id.nav_fashion:
                                btn_health.setPressed(false);
                                btn_service.setPressed(false);
                                btn_news_for_location.setPressed(false);
                                btn_health.setActivated(false);
                                btn_news_for_location.setActivated(false);
                                btn_service.setActivated(false);
                                disableItem();
                                replaceFragment(R.id.home_frame, new FragmentHomeFashionMakeUp(), "FASHION");
                                renameToolbar(R.string.nav_fashion);
                                break;
                            case R.id.nav_food:
                                btn_health.setPressed(false);
                                btn_service.setPressed(false);
                                btn_news_for_location.setPressed(false);
                                btn_health.setActivated(false);
                                btn_news_for_location.setActivated(false);
                                btn_service.setActivated(false);
                                disableItem();
                                replaceFragment(R.id.home_frame, new FragmentHomeFood(), "FOOD");
                                renameToolbar(R.string.nav_food);
                                break;
                            case R.id.nav_technology:
                                btn_health.setPressed(false);
                                btn_service.setPressed(false);
                                btn_news_for_location.setPressed(false);
                                btn_health.setActivated(false);
                                btn_news_for_location.setActivated(false);
                                btn_service.setActivated(false);
                                disableItem();
                                replaceFragment(R.id.home_frame, new FragmentHomeTechnology(), "TECHNOLOGY");
                                renameToolbar(R.string.nav_technology);
                                break;
                            case R.id.nav_list_item:
                                isShowing = true;

                                if (isChecked) {
                                    mLinearLayout.setVisibility(View.GONE);
                                    ln_popup_item.setVisibility(View.GONE);
                                    fr_home_overlay.setVisibility(View.GONE);
                                } else {
                                    mLinearLayout.setVisibility(View.VISIBLE);
                                    ln_popup_item.setVisibility(View.VISIBLE);
                                    fr_home_overlay.setVisibility(View.VISIBLE);
                                }
                                selectedItem();
                                break;
                        }
                        return true;
                    }
                });
    }

    @SuppressLint("CutPasteId")
    private void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mLinearLayout = (LinearLayout) findViewById(R.id.ln_layout_transparent);
        ln_layout_transparent = (LinearLayout) findViewById(R.id.ln_layout_transparent);
        ln_layout_nav_item = (LinearLayout) findViewById(R.id.ln_layout_nav_item);
        ln_popup_item = (LinearLayoutAnimationSlideBottom) findViewById(R.id.ln_popup_item);
        ln_popup_item.setVisibility(View.GONE);
        ln_layout_transparent.setOnClickListener(this);
        btn_health = (Button) findViewById(R.id.btn_health);
        btn_health.setOnClickListener(this);
        btn_service = (Button) findViewById(R.id.btn_service);
        btn_service.setOnClickListener(this);
        btn_news_for_location = (Button) findViewById(R.id.btn_news_for_locations);
        btn_news_for_location.setOnClickListener(this);
        fr_home_overlay = (FrameLayout) findViewById(R.id.fr_home_overlay);
        fr_home_overlay.setOnClickListener(this);

        drawer.setOnClickListener(this);
    }

    private void disableItem() {
        isShowing = false;
        mLinearLayout.setVisibility(View.INVISIBLE);
        ln_popup_item.setVisibility(View.GONE);
        fr_home_overlay.setVisibility(View.GONE);
    }

    private void replaceHealth() {
        item_health_selected = true;
        item_news_for_me_selected = false;
        item_service_selected = false;
        btn_health.setPressed(true);
        btn_service.setPressed(false);
        btn_news_for_location.setPressed(false);
        btn_health.setActivated(true);
        btn_news_for_location.setActivated(false);
        btn_service.setActivated(false);
        disableItem();
        replaceFragment(R.id.home_frame, new FragmentHomeHealth(), "HEALTH");
        renameToolbar(R.string.nav_health);
    }

    private void replaceService() {
        item_health_selected = false;
        item_news_for_me_selected = false;
        item_service_selected = true;
        btn_health.setPressed(false);
        btn_service.setPressed(true);
        btn_news_for_location.setPressed(false);
        btn_health.setActivated(false);
        btn_news_for_location.setActivated(false);
        btn_service.setActivated(true);
        disableItem();
        replaceFragment(R.id.home_frame, new FragmentHomeOtherService(), "SERVICE");
        renameToolbar(R.string.nav_services);
    }

    private void replaceNewsForLocation() {
        item_health_selected = false;
        item_news_for_me_selected = true;
        item_service_selected = false;
        btn_health.setPressed(false);
        btn_service.setPressed(false);
        btn_news_for_location.setPressed(true);
        btn_health.setActivated(false);
        btn_news_for_location.setActivated(true);
        btn_service.setActivated(false);
        disableItem();
        replaceFragment(R.id.home_frame, new FragmentHomeNewsForMe(), "NEWS_FOR_LOCATION");
        renameToolbar(R.string.nav_news_for_me);
    }

    @SuppressWarnings("deprecation")
    private void initNavigation() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (isShowing) {
            disableItem();
        } else {
            showConfirmExitApp();
        }
    }

    private void selectedItem() {
        if (item_health_selected) {
            btn_health.setPressed(true);
            btn_service.setPressed(false);
            btn_news_for_location.setPressed(false);
        } else if (item_service_selected) {
            btn_health.setPressed(false);
            btn_service.setPressed(true);
            btn_news_for_location.setPressed(false);
        } else if (item_news_for_me_selected) {
            btn_health.setPressed(false);
            btn_service.setPressed(false);
            btn_news_for_location.setPressed(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mMenuItem = menu.findItem(R.id.action_user);
        if (avatar != null) {
//            setDrawableResource(userShort.getAvatar(), mMenuItem);
        }
        return true;
    }

    private void setImgUser2Toolbar(String avatar_url, final int notification_count) {
        final ImageView imageView = new ImageView(this);
        imageView.setVisibility(View.GONE);
        Picasso.with(getApplicationContext())
                .load(avatar_url)
                .noPlaceholder()
                .transform(new CircleTransform())
                .error(R.mipmap.ic_launcher)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        mMenuItem.setIcon(imageView.getDrawable());
                        imageView.destroyDrawingCache();
                    }

                    @Override
                    public void onError() {
                    }
                });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_user) {
            pushData();
            return true;
        } else if (id == R.id.action_qr) {
            checkInQrBar();
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkInQrBar() {
        disableItem();
        startActivty(QrCheckIn.class);
    }

    @SuppressLint("ObsoleteSdkInt")
    private void onCreteBadgeItem(int paramsInt) {
        if (Build.VERSION.SDK_INT <= 15) {
            return;
        }
        LayerDrawable layerDrawable = (LayerDrawable) mMenuItem.getIcon();
        Drawable userBadgeDrawable = layerDrawable.findDrawableByLayerId(R.id.action_user);
        BadgeIcon badgeIcon;
        if ((userBadgeDrawable != null)
                && ((userBadgeDrawable instanceof BadgeIcon))
                && (paramsInt < 0)) {
            badgeIcon = (BadgeIcon) userBadgeDrawable;
        } else {
            badgeIcon = new BadgeIcon(this);
        }
        badgeIcon.setCount(paramsInt);
        layerDrawable.mutate();
        layerDrawable.setDrawableByLayerId(R.id.action_user, badgeIcon);
        mMenuItem.setIcon(layerDrawable);
    }

    private void replaceDefaultFragment() {
        replaceFragment(R.id.home_frame, new FragmentHomeNewsList(), "NEWS");
        renameToolbar(R.string.nav_new_list);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        disableItem();
        if (id == R.id.nav_home) {
            // Handle the camera action
            nav_bottom_home.setVisibility(View.VISIBLE);
            replaceDefaultFragment();
        } else if (id == R.id.nav_my_shop) {
            if (session != null) {
                replaceMyShopFragment();
            } else {
                alertLogin();
            }
        } else if (id == R.id.nav_member_card) {
            if (session != null) {
                replaceMemberCardFragment();
            } else {
                alertLogin();
            }
        } else if (id == R.id.nav_help) {
        } else if (id == R.id.nav_privacy) {
        } else if (id == R.id.nav_faq) {
        } else if (id == R.id.nav_about) {
        } else if (id == R.id.nav_exit) {
            exitApp();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceMyShopFragment() {
        disableItem();
        nav_bottom_home.setVisibility(View.GONE);
        replaceFragment(R.id.home_frame, new FragmentMyShop(), "MYSHOP");
        renameToolbar(R.string.fragment_myshop_content);
    }

    private void replaceMemberCardFragment() {
        disableItem();
        nav_bottom_home.setVisibility(View.GONE);
        replaceFragment(R.id.home_frame, new FragmentMemberCard(), "MEMBER");
        renameToolbar(R.string.fragment_member_card_content);
    }

    @Override
    public void startActivty(Class clazz) {
        super.startActivity(clazz);
    }

    @Override
    public void startActivityFinish(Class clazz) {
        super.startActivityFinish(clazz);
    }

    @Override
    public void getShortUser(RESP_Short profile) {
        UserShort userShort = new UserShort();
        userShort.setAvatar(profile.getAvatar());
        userShort.setFullname(profile.getFullname());
        userShort.setNew_notify(profile.getNew_notify());
        notifications = userShort.getNew_notify();
        avatar = userShort.getAvatar();
        Log.e("Avatar Home", avatar);
//            setDrawableResource(userShort.getAvatar(), mMenuItem);
        setImgUser2Toolbar(avatar, notifications);
//        if (notifications != 0) {
////            onCreteBadgeItem(notifications);
////            ShortcutBadger.applyCount(getBaseContext(), notifications);
//        } else {
//            ShortcutBadger.applyCount(getBaseContext(), 0);
//        }
    }


    @Override
    public void onNetworkDisable() {
        WidgetHelper.getInstance().showAlertNetwork(this);
    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void showLongToast(String message) {
        super.showLongToast(message);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_avatar) {
            startActivity(ProfileActivity.class);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.ln_layout_transparent) {
            disableItem();
        } else if (id == R.id.btn_health) {
            replaceHealth();
        } else if (id == R.id.btn_service) {
            replaceService();
        } else if (id == R.id.btn_news_for_locations) {
            replaceNewsForLocation();
        } else if (id == R.id.fr_home_overlay) {
            disableItem();
        } else if (id == android.R.id.home) {
            disableItem();
        } else if (id == R.id.drawer_layout) {
            disableItem();
        }
    }

    private void pushData() {
        if (session != null) {
            disableItem();
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else {
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
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                loginActivity.setIsPassed(true);
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
            }
        });
        dialog.show();
    }

    private void renameToolbar(int StringResource) {
        toolbar.setTitle(StringResource);
    }

    @Override
    protected void onResume() {
        presenter.onGetShortUser();
        if (notifications != 0) {
//            onCreteBadgeItem(notifications);
            ShortcutBadger.applyCount(getBaseContext(), notifications);
        } else {
            ShortcutBadger.applyCount(getBaseContext(), 0);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (notifications != 0) {
//            onCreteBadgeItem(notifications);
            ShortcutBadger.applyCount(getBaseContext(), notifications);
        } else {
            ShortcutBadger.applyCount(getBaseContext(), 0);
        }
        super.onPause();
    }

    private void getData() {
        String data = null;
        try {
            data = getIntent().getStringExtra(Constants.NOTIFY_KEY);
            if (data.equals("news")) {
                replaceDefaultFragment();
//                replaceDefaultFragment();
            } else if (data.equals("location")) {
                replaceNewsForLocation();
                toolbar.setTitle(getString(R.string.nav_news_for_me));
                isChecked = true;
                nav_bottom_home.setSelectedItemId(R.id.nav_list_item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Log.e("Data.....", data);

        if (data == null)
            replaceDefaultFragment();
    }
}
