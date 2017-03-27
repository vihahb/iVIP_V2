package com.xtel.ivipu.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.RESP.RESP_NewEntity;
import com.xtel.ivipu.model.RESP.RESP_NewsObject;
import com.xtel.ivipu.model.RESP.RESP_Voucher;
import com.xtel.ivipu.model.entity.NewsObj;
import com.xtel.ivipu.model.entity.VoucherObj;
import com.xtel.ivipu.presenter.ActivityInfoPropertiesPresenter;
import com.xtel.ivipu.view.activity.IFragment;
import com.xtel.ivipu.view.activity.ListCommentActivity;
import com.xtel.ivipu.view.activity.inf.IActivityInfo;
import com.xtel.ivipu.view.widget.AppBarStateChangeListener;
import com.xtel.ivipu.view.widget.RoundImage;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.commons.NetWorkInfo;
import com.xtel.sdk.utils.SharedPreferencesUtils;

import at.blogc.android.views.ExpandableTextView;

/**
 * Created by vihahb on 1/17/2017.
 */

public class FragmentInfoProperties extends IFragment implements View.OnClickListener, IActivityInfo {
    private static final String TAG = "Info Properties";
    ActivityInfoPropertiesPresenter presenter;
    private RESP_NewEntity newEntity;
    private TextView txt_info_shop_name, txt_info_shop_view, txt_info_shop_like, txt_info_shop_rate, tv_info_shop_title;
    private TextView tv_qr_reward, tv_set_expand, tv_status, tv_Expand, tv_rate_time, tv_voucher_cocde, tv_voucher_expired_time, tv_temp_text;
    private RoundImage img_brand, img_avatar, img_avatar_background;
    private ImageView img_qr_code, img_bar_code, img_content_banner;
    private ImageView img_like, img_comment, img_share;
    private Button btn_getGiftCode, btn_rating;
    private LinearLayout inc_get_gift_code, inc_gift_code;
    private AppBarLayout appBarLayout;
    private String qr_code_voucher, barcode_voucher;
    private NewsObj newsObject;
    private ImageButton img_button_expand;
    private String avatar_user;
    private int REQUEST_COMMENT = 101;
    private RatingBar ratingBar;
    private ExpandableTextView expandableTextView;
    private double rated;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_properties, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ActivityInfoPropertiesPresenter(this);
        initView(view);
    }


    private void initView(View view) {
        appBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar);

        txt_info_shop_name = (TextView) view.findViewById(R.id.tv_info_shop_name);
        txt_info_shop_view = (TextView) view.findViewById(R.id.tv_info_shop_view);
        txt_info_shop_like = (TextView) view.findViewById(R.id.tv_info_shop_like);
//        txt_info_shop_comment = (TextView) view.findViewById(R.id.tv_info_shop_comment);
        txt_info_shop_rate = (TextView) view.findViewById(R.id.tv_info_shop_rate);
        tv_qr_reward = (TextView) view.findViewById(R.id.tv_qr_reward);
        expandableTextView = (ExpandableTextView) view.findViewById(R.id.expandableTextView);
        tv_temp_text = (TextView) view.findViewById(R.id.tv_temp_text);
        tv_info_shop_title = (TextView) view.findViewById(R.id.tv_info_shop_title);
//        tv_set_expand = (TextView) view.findViewById(R.id.tv_set_expand);
        tv_status = (TextView) view.findViewById(R.id.tv_rate_status);
        tv_rate_time = (TextView) view.findViewById(R.id.tv_rate_time);
        tv_voucher_cocde = (TextView) view.findViewById(R.id.tv_voucher_code);
        tv_voucher_expired_time = (TextView) view.findViewById(R.id.tv_voucher_expired_time);

        img_content_banner = (ImageView) view.findViewById(R.id.img_info_banner);
        img_brand = (RoundImage) view.findViewById(R.id.store_info);
        img_qr_code = (ImageView) view.findViewById(R.id.img_qr_code);
        img_qr_code.setOnClickListener(this);
        img_bar_code = (ImageView) view.findViewById(R.id.img_bar_code);
        img_bar_code.setOnClickListener(this);
        img_avatar = (RoundImage) view.findViewById(R.id.user_avatar_rate);
        img_avatar_background = (RoundImage) view.findViewById(R.id.store_info_background);
        img_like = (ImageView) view.findViewById(R.id.img_action_like);
        img_like.setOnClickListener(this);
        img_comment = (ImageView) view.findViewById(R.id.img_action_comment);
        img_comment.setOnClickListener(this);
        img_share = (ImageView) view.findViewById(R.id.img_action_share);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);


        inc_get_gift_code = (LinearLayout) view.findViewById(R.id.inc_get_gift_code);
        inc_gift_code = (LinearLayout) view.findViewById(R.id.inc_gift_code);

        btn_getGiftCode = (Button) view.findViewById(R.id.btn_get_gift_code);
        btn_getGiftCode.setOnClickListener(this);
        btn_rating = (Button) view.findViewById(R.id.btn_rating);
        btn_rating.setOnClickListener(this);
        tv_Expand = (TextView) view.findViewById(R.id.button_toggle);

        initRatingBar();
        initUnderLine();
        initAppBar();
        getDataFromFragmentShop();
        setExpandTextView();
//        setExpandTextView();
    }

    private void initRatingBar() {
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 0) {
                    tv_status.setText("Trạng thái");
                } else if (rating == 1.0) {
                    tv_status.setText("Ghét");
                } else if (rating == 2.0) {
                    tv_status.setText("Không thích");
                } else if (rating == 3.0) {
                    tv_status.setText("OK");
                } else if (rating == 4.0) {
                    tv_status.setText("Thích");
                } else if (rating == 5.0) {
                    tv_status.setText("Rất thích");
                }
            }
        });
    }

    private void initAppBar() {
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateEXPANDED() {
                img_brand.setVisibility(View.VISIBLE);
                img_avatar_background.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStateIDLE() {
                img_brand.setVisibility(View.GONE);
                img_avatar_background.setVisibility(View.GONE);
            }
        });
    }

    private void setExpandTextView() {
        // set animation duration via code, but preferable in your layout files by using the animation_duration attribute
        expandableTextView.setAnimationDuration(1000L);

        // set interpolators for both expanding and collapsing animations
        expandableTextView.setInterpolator(new OvershootInterpolator());

        // or set them separately
        expandableTextView.setExpandInterpolator(new OvershootInterpolator());
        expandableTextView.setCollapseInterpolator(new OvershootInterpolator());

        // but, you can also do the checks yourself
        // toggle the ExpandableTextView
        tv_Expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                expandableTextView.toggle();
                tv_Expand.setText(expandableTextView.isExpanded() ? R.string.expand : R.string.collapse);
            }
        });

//        int lineCount = expandableTextView.getTe;
//        Log.e("Line Count....", String.valueOf(lineCount));


    }


    private void initUnderLine() {
        String qr_reward = new String(getActivity().getResources().getString(R.string.qr_reward));
        SpannableString content = new SpannableString(qr_reward);
        content.setSpan(new UnderlineSpan(), 0, qr_reward.length(), 0);
        tv_qr_reward.setText(content);
        tv_qr_reward.setOnClickListener(this);
    }

    private void initShowQrCode() {
        presenter.showQrCode(qr_code_voucher);
    }

    private void initBarCode() {
        presenter.showBarCode(barcode_voucher);
    }

    private boolean validData() {
        try {
            newEntity = (RESP_NewEntity) getActivity().getIntent().getSerializableExtra(Constants.RECYCLER_MODEL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newEntity != null;
    }

    private void getDataFromFragmentShop() {
        if (validData()) {
            checkNetWork(1);
        }
    }

    private void setupData2View(NewsObj newsObj) {
        VoucherObj voucherObj = new VoucherObj();
        voucherObj = newsObj.getVoucher();
        String shopName = newsObj.getStore_name();
        String shopView = String.valueOf(newsObj.getView());
        String shopLike = String.valueOf(newsObj.getLike());
        String shopComment = String.valueOf(newsObj.getComment());
        String shopRate = String.valueOf(newsObj.getRate());
        String shopDescription = newsObj.getDescription();
        String shopTitle = newsObj.getTitle();
        int sales = newsObj.getSales();
        int favorite_check = newsObj.getFavorite();
        double current_rate = newsObj.getCurrent_rate();
        Log.e("Curent rate value", String.valueOf(current_rate));

        if (String.valueOf(current_rate) != "0.0") {

            /** Rate Time **/
            long rate_time = newsObj.getRate_time();
            WidgetHelper.getInstance().setTextViewDate(tv_rate_time, "Đã đánh giá vào ", rate_time);
            tv_rate_time.setVisibility(View.VISIBLE);
            tv_status.setVisibility(View.GONE);

            /** Rate Value **/
            current_rate = newsObj.getCurrent_rate();
            float rated_get = Float.parseFloat(String.valueOf(current_rate));
            int val = Math.round(rated_get);
            ratingBar.setEnabled(false);
            ratingBar.setRating(rated_get);
            btn_rating.setVisibility(View.GONE);
        }

        WidgetHelper.getInstance().setTextViewNoResult(txt_info_shop_name, shopName);
        WidgetHelper.getInstance().setTextViewNoResult(txt_info_shop_view, shopView);
        WidgetHelper.getInstance().setTextViewNoResult(txt_info_shop_like, shopLike);
        WidgetHelper.getInstance().setTextViewNoResult(txt_info_shop_rate, shopRate);
        WidgetHelper.getInstance().setTextViewNoResult(tv_temp_text, shopDescription);
        WidgetHelper.getInstance().setTextViewNoResult(tv_info_shop_title, shopTitle);

        String banner = newsObj.getBanner();
        String brand = newsObj.getLogo();
        if (favorite_check != 0) {
            WidgetHelper.getInstance().setImageResource(img_like, R.mipmap.ic_action_liked);
        } else {
            WidgetHelper.getInstance().setImageResource(img_like, R.mipmap.ic_action_not_like);
        }

        if (sales != 0) {
            Log.e("Voucher obj", JsonHelper.toJson(voucherObj));
            if (voucherObj != null) {
                setVoucher(voucherObj);
            } else {
                inc_get_gift_code.setVisibility(View.VISIBLE);
            }
        } else {
            inc_get_gift_code.setVisibility(View.GONE);
        }

        WidgetHelper.getInstance().setAvatarImageURL(img_brand, brand);
        WidgetHelper.getInstance().setAvatarImageURL(img_content_banner, banner);


        expandableTextView.setText(tv_temp_text.getText().toString());
        int count_tv = tv_temp_text.getLineCount();
        Log.e("Count Textview", String.valueOf(count_tv));
        tv_Expand.setVisibility(View.INVISIBLE);
        if (count_tv > 3) {
            tv_temp_text.setVisibility(View.GONE);
            tv_Expand.setVisibility(View.VISIBLE);
        }

    }

    private void setVoucher(VoucherObj voucherObj) {
        inc_get_gift_code.setVisibility(View.GONE);
        inc_gift_code.setVisibility(View.VISIBLE);
        String bar_code = voucherObj.getBar_code();
        String qr_code = voucherObj.getQr_code();
        this.barcode_voucher = bar_code;
        this.qr_code_voucher = qr_code;
        String code = voucherObj.getCode();
        long expired_time = voucherObj.getExpired_time();
        WidgetHelper.getInstance().setAvatarImageURL(img_qr_code, qr_code_voucher);
        WidgetHelper.getInstance().setAvatarImageURL(img_bar_code, barcode_voucher);
        WidgetHelper.getInstance().setTextViewNoResult(tv_voucher_cocde, code);
        WidgetHelper.getInstance().setTextViewDate(tv_voucher_expired_time, "", expired_time);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_get_gift_code) {
            checkNetWork(6);
        } else if (id == R.id.tv_qr_reward) {
            inc_get_gift_code.setVisibility(View.VISIBLE);
            inc_gift_code.setVisibility(View.GONE);
        } else if (id == R.id.img_qr_code) {
            checkNetWork(4);
        } else if (id == R.id.img_bar_code) {
            checkNetWork(5);
        } else if (id == R.id.img_action_like) {
            checkNetWork(3);
        } else if (id == R.id.img_action_comment) {
            commentOnclick();
        } else if (id == R.id.btn_rating) {
            ratingAction();
        }
    }

    private void ratingAction() {
        float rating_value = ratingBar.getRating();
        rated = Double.parseDouble(String.valueOf(rating_value));
        Log.e("Rating value", String.valueOf(rated));
        checkNetWork(2);
    }

    @Override
    public void onShowQrCode(String url) {
        if (NetWorkInfo.isOnline(getContext())) {
            showQrCode(url);
        } else {
            showShortToast(getString(R.string.no_connection));
        }
    }

    @Override
    public void onShowBarCode(String url_bar_code) {
        if (NetWorkInfo.isOnline(getContext())) {
            showQrCode(url_bar_code);
        } else {
            showShortToast("Không có kết nối internet");
        }
    }

    @Override
    public void onNetworkDisable() {
    }

    @Override
    public void showShortToast(String mes) {
        super.showShortToast(mes);
    }

    @Override
    public void onGetNewsObjSuccess(RESP_NewsObject resp_newsObject) {
        newsObject = new NewsObj();
        Log.e("news obj", JsonHelper.toJson(newsObject));
        Log.e("Store id", String.valueOf(newsObject.getStore_id()));
        Log.e("Chain id", String.valueOf(newsObject.getChain_store_id()));
        fillData(resp_newsObject);
        setupData2View(newsObject);
        avatar_user = SharedPreferencesUtils.getInstance().getStringValue(Constants.PROFILE_AVATAR);
        WidgetHelper.getInstance().setAvatarImageURL(img_avatar, avatar_user);
    }

    @Override
    public void onGetVoucherSuccess(RESP_Voucher voucher) {
        Log.e("Voucher Obj", JsonHelper.toJson(voucher));
        inc_get_gift_code.setVisibility(View.GONE);
        inc_gift_code.setVisibility(View.VISIBLE);
        String bar_code = voucher.getBar_code();
        String qr_code = voucher.getQr_code();
        this.barcode_voucher = bar_code;
        this.qr_code_voucher = qr_code;
        String code = voucher.getCode();
        long expired_time = voucher.getExpired_time();

        WidgetHelper.getInstance().setTextViewNoResult(tv_voucher_cocde, code);
        WidgetHelper.getInstance().setTextViewDate(tv_voucher_expired_time, "", expired_time);
        WidgetHelper.getInstance().setAvatarImageURL(img_qr_code, qr_code_voucher);
        WidgetHelper.getInstance().setAvatarImageURL(img_bar_code, barcode_voucher);
    }

    @Override
    public void onLikeSuccess() {
        int id = newEntity.getId();
        presenter.getNews(id);
    }

    @Override
    public void onRateSuccess() {
        int rated_set = Integer.parseInt(String.valueOf(rated));
        int val = Math.round(rated_set);
        Log.e("Value rate set", String.valueOf(val));
        ratingBar.setEnabled(false);
        ratingBar.setRating(rated_set);
        ratingBar.setNumStars(val);
//        tv_status.setText("Đã đánh giá");
        WidgetHelper.getInstance().setTextViewNoResult(tv_status, "", "Đã đánh giá");
        btn_rating.setVisibility(View.GONE);
    }

    private void fillData(RESP_NewsObject resp_newsObject) {
        newsObject.setId(resp_newsObject.getId());
        newsObject.setBanner(resp_newsObject.getBanner());
        newsObject.setLogo(resp_newsObject.getLogo());
        newsObject.setStore_name(resp_newsObject.getStore_name());
        newsObject.setLike(resp_newsObject.getLike());
        newsObject.setComment(resp_newsObject.getComment());
        newsObject.setRate(resp_newsObject.getRate());
        newsObject.setView(resp_newsObject.getView());
        newsObject.setCreate_time(resp_newsObject.getCreate_time());
        newsObject.setTitle(resp_newsObject.getTitle());
        newsObject.setDescription(resp_newsObject.getDescription());
        newsObject.setSales(resp_newsObject.getSales());
        newsObject.setVoucher(resp_newsObject.getVoucher());
        newsObject.setStore_id(resp_newsObject.getStore_id());
        newsObject.setChain_store_id(resp_newsObject.getChain_store_id());
        newsObject.setFavorite(resp_newsObject.getFavorite());
        newsObject.setCurrent_rate(resp_newsObject.getCurrent_rate());
        newsObject.setRate_time(resp_newsObject.getRate_time());
    }

    private void checkNetWork(int type) {
        final Context context = getContext();
        if (!NetWorkInfo.isOnline(context)) {
            WidgetHelper.getInstance().showAlertNetwork(context);
        } else {
            if (type == 1) {
                int id = newEntity.getId();
                presenter.getNews(id);
            } else if (type == 2) {
                onRateAction(rated);
            } else if (type == 3) {
                onActionLike();
            } else if (type == 4) {
                initShowQrCode();
            } else if (type == 5) {
                initBarCode();
            } else if (type == 6) {
                int id = newEntity.getId();
                presenter.onGetVoucher(id);
            }
        }
    }

    private void onActionLike() {
        int id = newEntity.getId();
        presenter.onLikeAction(id);
    }

    private void onRateAction(double value_rated) {
        int id = newEntity.getId();
        presenter.onRatesNews(id, value_rated);
    }

    private void commentOnclick() {
        int id = newEntity.getId();
        startActivityForResultValue(ListCommentActivity.class, Constants.NEWS_ID, String.valueOf(id), REQUEST_COMMENT);
    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        super.startActivityAndFinish(clazz);
    }
}
