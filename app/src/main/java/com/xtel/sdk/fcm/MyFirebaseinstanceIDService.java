package com.xtel.sdk.fcm;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.utils.SharedPreferencesUtils;

/**
 * Created by vihahb on 1/10/2017.
 */

public class MyFirebaseinstanceIDService extends FirebaseInstanceIdService {


    private static final String TAG = "MyFirebaseInstanceIDService";

    @SuppressLint("LongLogTag")
    public void onTokenRefresh() {
        //Get update token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "FCM Device Token: " + refreshedToken);
        SharedPreferencesUtils.getInstance().putStringValue(Constants.FCM_TOKEN_DEVICE, refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        //Implement this method if you want to store the token on your server
    }
}
