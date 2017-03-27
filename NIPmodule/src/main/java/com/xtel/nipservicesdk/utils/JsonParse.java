package com.xtel.nipservicesdk.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.xtel.nipservicesdk.R;
import com.xtel.nipservicesdk.commons.Constants;
import com.xtel.nipservicesdk.model.entity.Error;

import org.json.JSONObject;

/**
 * Created by Vũ Hà Vi on 01/01/2017
 */

public class JsonParse {

    public static boolean checkJsonObject(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Error checkError(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject error = jsonObject.getJSONObject(Constants.ERROR);

            Error errorModel = new Error();
            errorModel.setCode(error.getInt(Constants.CODE));
            errorModel.setType(error.getString(Constants.TYPE));
            errorModel.setMessage(error.getString(Constants.MESSAGE));

            return errorModel;
        } catch (Exception e) {
            Log.e("parse_error", e.toString());
            e.printStackTrace();
        }

        return null;
    }

    public static void getCodeError(Context activity, View view, int code, String content) {
        if (code == 3) {
            if (view != null)
                Toast.makeText(activity, activity.getString(R.string.error_code_3), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(activity, activity.getString(R.string.error_code_3), Toast.LENGTH_SHORT).show();
        } else if (code == 4) {
            if (view != null)
                Toast.makeText(activity, activity.getString(R.string.error_code_3), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(activity, activity.getString(R.string.error_code_4), Toast.LENGTH_SHORT).show();
        }
    }

    public static String getCodeMessage(Activity activity, int code, String content) {

        if (code == 2) {
            return activity.getString(R.string.error_session_invalid);
        } else if (code == 1) {
            return activity.getString(R.string.error_input_invalid);
        } else if (code == 4) {
            return activity.getString(R.string.error_system);
        } else if (code == 401) {
            return activity.getString(R.string.error_activation_failed);
        } else if (code == 100) {
            return activity.getString(R.string.error_service_not_valid);
        } else if (code == 101) {
            return activity.getString(R.string.error_service_in_valid);
        } else if (code == 102) {
            return activity.getString(R.string.error_email_already);
        } else if (code == 103) {
            return activity.getString(R.string.error_user_name_already);
        } else if (code == 104) {
            return activity.getString(R.string.error_activation_wrong);
        } else if (code == 105) {
            return activity.getString(R.string.error_account_already_activated);
        } else if (code == 106) {
            return activity.getString(R.string.error_url_invalid);
        } else if (code == 108) {
            return activity.getString(R.string.error_user_invalid);
        } else if (code == 109) {
            return activity.getString(R.string.error_account_invalid);
        } else if (code == 110) {
            return activity.getString(R.string.error_service_not_support_device);
        } else if (code == 111) {
            return activity.getString(R.string.error_user_or_password_wrong);
        } else if (code == 112) {
            return activity.getString(R.string.error_account_not_active);
        } else if (code == 113) {
            return activity.getString(R.string.error_authentication_id_wrong);
        } else if (code == 114) {
            return activity.getString(R.string.error_authentication_id_invalid);
        } else if (code == 117) {
            return activity.getString(R.string.error_facebook_access_token_invalid);
        } else if (code == 118) {
            return activity.getString(R.string.error_phone_number_wrong);
        } else if (code == 201) {
            return activity.getString(R.string.error_rate_not_exists);
        } else if (code == 202) {
            return activity.getString(R.string.error_rate_already_rated);
        } else if (code == 301) {
            return activity.getString(R.string.error_checkin_store_not_existed);
        } else if (code == 401) {
            return activity.getString(R.string.error_voucher_not_provider);
        } else if (code == 402) {
            return activity.getString(R.string.error_voucher_already_exists);
        } else if (code == 403) {
            return activity.getString(R.string.error_voucher_not_yet);
        } else if (code == 404) {
            return activity.getString(R.string.error_voucher_expired_time);
        } else if (code == 405) {
            return activity.getString(R.string.error_voucher_not_available);
        } else if (code == 406) {
            return activity.getString(R.string.error_voucher_not_rewards);
        } else {
            return content;
        }

    }
}