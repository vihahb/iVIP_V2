package com.xtel.sdk.commons;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vũ Hà Vi on 12/27/2016.
 */

public class Constants {
    public static final String SHARED_USER_NAME = "share_user_name";
    public static final String SESSION = "session";
    public static final String CODE = "code";
    public static final String TYPE = "type";
    public static final String MESSAGE = "message";
    public static final String ERROR = "error";
    public static final String RECYCLER_MODEL = "ryc_model";

    public static final String SERVER_UPLOAD = "http://124.158.5.112:9190/upload/files";
    public static final String SERVER_IMAGE_HTTP = "http://124.158.5.112:9190/upload/store/files/";
    public static final String SERVER_IVIP = "http://124.158.5.112:9190/ivip-u/";
    public static final String GET_USER_IVIP_FULL = "v0.1/user?type=full";
    public static final String GET_USER_IVIP_SORT = "v0.1/user?type=sort";
    public static final String UPDATE_USER = "v0.1/user";
    public static final String NEWS_INFO = "v0.1/news/";
    public static final String NEWS_ACTION = "v0.1/news/action";
    public static final String NEW_ACTION_COMMENT = "v0.1/news/comment";
    public static final String CHECKIN_ACTION = "v0.1/checkin";
    public static final String RATE_ACTION = "v0.1/rate";
    public static final String GALLERY_GET = "v0.1/gallery/";
    public static final String REG_FCM_KEY = "v0.1/user/fcm";
    public static final String NOTIFY_USER = "v0.1/user/notify?";
    public static final String NOTIFY_NUMBER = "v0.1/user/notify_number";
    public static final String NOTIFY_KEY = "notification";
    public static final String NOTIFY_OBJ = "notification_obj";
    public static final String DISABLE_KEY = "disableBack";


    /**
     * Profile Infomations
     **/
    public static final String PROFILE_FULL_NAME = "profile_full_name";
    public static final String PROFILE_GENDER = "profile_gender";
    public static final String PROFILE_BIRTH_DAY = "profile_birth_day";
    public static final String PROFILE_EMAIL = "profile_email";
    public static final String PROFILE_PHONE_NUM = "profile_phone";
    public static final String PROFILE_ADDRESS = "profile_address";
    public static final String PROFILE_AVATAR = "profile_avatar";
    public static final String PROFILE_QR_CODE = "profile_qr";
    public static final String PROFILE_BAR_CODE = "profile_bar";
    public static final String PROFILE_STATUS = "profile_status";
    public static final String PROFILE_GENERAL_POINT = "profile_general_point";
    public static final String PROFILE_LEVEL = "profile_level";
    public static final String PROFILE_JOINT_DATE = "profile_joint_date";
    public static final String FCM_TOKEN_DEVICE = "fcm_token_device";

    /**
     * VARIABLE
     **/
    public static final String NEWS_ID = "news_id";


    public static String convertDate(String date) {
        String newData[] = date.split("/");
        return newData[2] + "/" + newData[1] + "/" + newData[0];
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDataTime(String dateTime) {
        try {
            DateFormat defaultFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            Date date = defaultFormat.parse(dateTime);
            SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            return newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }
}