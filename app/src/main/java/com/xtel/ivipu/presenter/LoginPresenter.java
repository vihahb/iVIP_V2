package com.xtel.ivipu.presenter;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.xtel.ivipu.view.activity.HomeActivity;
import com.xtel.ivipu.view.activity.inf.ILoginView;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.commons.Constants;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.SharedUtils;
import com.xtel.sdk.commons.NetWorkInfo;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by vihahb on 1/10/2017.
 */

public class LoginPresenter {


    String mess = "";
    String tokenFacebook;
    String TAG = "Login presenter ";
    private ILoginView view;
    private CallbackManager callbackManager;
    private com.xtel.nipservicesdk.CallbackManager nipCallbackManager;
    private AccessTokenTracker tokenTracker;
    private AccessToken accessToken;

    public LoginPresenter(ILoginView view) {
        this.view = view;
    }

    public void createNipCallbackManager() {
        nipCallbackManager = com.xtel.nipservicesdk.CallbackManager.create(view.getActivity());
    }

    public void onRequestCallbackPermission(int requestCode, String[] permission, int[] grantResult) {
        nipCallbackManager.onRequestPermissionsResult(requestCode, permission, grantResult);
    }

    public void createCallBackManager() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mess = "Success" + loginResult.getAccessToken().getUserId();
                view.showShortToast(mess);
                tokenFacebook = loginResult.getAccessToken().getToken();
                onLoginNipAccFb(tokenFacebook);
            }

            @Override
            public void onCancel() {
                mess = "Login Cancel";
                view.showShortToast(mess);
            }

            @Override
            public void onError(FacebookException error) {
                mess = "Login Error: " + error.getMessage();
                view.showShortToast(mess);
            }
        });
    }

    public void onLoginFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(view.getActivity(),
                Arrays.asList("public_profile", "email", "user_birthday"));
    }

    public void onResultFacebookLogin(int requestCode, int resultCode, Intent data) {
        if (!NetWorkInfo.isOnline(view.getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.onNetworkDisable();
                }
            }, 500);
            return;
        } else {
            if (callbackManager.onActivityResult(requestCode, resultCode, data))
                return;
        }
    }

    public void checkAccessToken() {
        tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                //Đã đăng nhập, lưu token lại và làm việc sau đăng nhập
                accessToken = currentAccessToken;
            }
        };
        //If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();
        Log.d("Get Access Token:", String.valueOf(AccessToken.getCurrentAccessToken().getToken()));
    }

    private void onLoginNipAccFb(String token) {
        if (!NetWorkInfo.isOnline(view.getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.onNetworkDisable();
                }
            }, 500);
            return;
        } else {
            nipCallbackManager.LoginFaceook(token, new CallbacListener() {
                @Override
                public void onSuccess(RESP_Login success) {
                    view.showShortToast("Login Success");
                    String sesion = success.getSession();
                    Log.d(TAG + "session", sesion);
                    SharedUtils.getInstance().putStringValue(Constants.SESSION, sesion);
                    view.startActivityAndFinish(HomeActivity.class);
                }

                @Override
                public void onError(Error error) {
                    view.showShortToast("Login Exception. Error code: " + error.getCode());
                }
            });
        }
    }

    private String convertLong2Time(long time) {
        Date date = new Date(time * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC+7"));
        String formatTime = dateFormat.format(date);
        return formatTime;
    }

    private void checkTime(long login_time, long expired_time) {
        String time = "login: " + convertLong2Time(login_time) + ", Expired: " + convertLong2Time(expired_time);
        Log.v("Time login 2 SV: ", time);
    }
}
