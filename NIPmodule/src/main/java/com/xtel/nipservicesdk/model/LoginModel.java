package com.xtel.nipservicesdk.model;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.commons.Constants;
import com.xtel.nipservicesdk.model.entity.ActiveNip;
import com.xtel.nipservicesdk.model.entity.AuthenNip;
import com.xtel.nipservicesdk.model.entity.AuthenNipModel;
import com.xtel.nipservicesdk.model.entity.LoginNipModel;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.model.entity.RESP_Reactive;
import com.xtel.nipservicesdk.model.entity.RESP_Register;
import com.xtel.nipservicesdk.model.entity.RESP_Reset;
import com.xtel.nipservicesdk.model.entity.ReactiveNip;
import com.xtel.nipservicesdk.model.entity.RegisterModel;
import com.xtel.nipservicesdk.model.entity.ResetEntity;
import com.xtel.nipservicesdk.utils.DeviceInfo;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.SharedUtils;

/**
 * Created by Lê Công Long Vũ on 12/28/2016
 */

public class LoginModel extends BasicModel {
    private static LoginModel instance = new LoginModel();
    String url_facebook = Constants.URL_NIP + Constants.API_FACEBOOK;
    String url_account_kit = Constants.URL_NIP + Constants.API_ACCOUNT_KIT;
    String url_sesion_authenticate = Constants.URL_NIP + Constants.API_SESSION_AUTHENTICATE;
    String url_reg_nip_acc = Constants.URL_NIP + Constants.API_REGISTER_NIP;
    String url_reset_password = Constants.URL_NIP + Constants.API_RESET_ACC_NIP;
    String url_login = Constants.URL_NIP + Constants.API_LOGIN_ACC_NIP;

    public static LoginModel getInstance() {
        return instance;
    }

    public void postFacebookData2Server(String service_code, String token_key, ResponseHandle<RESP_Login> responseHandle) {
        AuthenNipModel facebookModel = new AuthenNipModel();
        facebookModel.setAccess_token_key(token_key);
        facebookModel.setService_code(service_code);
        facebookModel.setDevInfo(DeviceInfo.getDeviceObject());

        requestServer.postApi(url_facebook, JsonHelper.toJson(facebookModel), null, responseHandle);
    }

    public void postAccountKitData2Server(String service_code, String authorization_code, ResponseHandle<RESP_Login> responseHandle) {
        AuthenNipModel accountKitModel = new AuthenNipModel();
        accountKitModel.setAuthorization_code(authorization_code);
        accountKitModel.setService_code(service_code);
        accountKitModel.setDevInfo(DeviceInfo.getDeviceObject());
        requestServer.postApi(url_account_kit, JsonHelper.toJson(accountKitModel), null, responseHandle);
    }


    public void registerAccountNip(String user_name, String password, String email, boolean isPhone, String service_code, ResponseHandle<RESP_Register> responseHandle) {
        RegisterModel registerNipModel = new RegisterModel();
        registerNipModel.setUsername(user_name);
        registerNipModel.setPassword(password);
        registerNipModel.setEmail(email);
        registerNipModel.setService_code(service_code);
        if (!isPhone) {
            registerNipModel.setAccountType("EMAIL");
            registerNipModel.setSendEmail(0);
        } else {
            registerNipModel.setSendEmail(1);
            registerNipModel.setAccountType("PHONE-NUMBER");
        }
//        Log.e("Object reg", JsonHelper.toJson(registerNipModel));
        requestServer.postApi(url_reg_nip_acc, JsonHelper.toJson(registerNipModel), null, responseHandle);
    }

    public void loginNipServices(String user_name, String password, boolean isPhone, String service_code, ResponseHandle<RESP_Login> responseHandle) {
        LoginNipModel loginNipModel = new LoginNipModel();
        loginNipModel.setUsername(user_name);
        loginNipModel.setPassword(password);
        loginNipModel.setService_code(service_code);
        if (isPhone) {
            loginNipModel.setAccountType("PHONE-NUMBER");
        } else {
            loginNipModel.setAccountType("EMAIL");
        }
        loginNipModel.setDevInfo(DeviceInfo.getDeviceObject());
        Log.e("login nip_1", JsonHelper.toJson(loginNipModel));
        String request = JsonHelper.toJson(loginNipModel);
        requestServer.postApi(url_login, JsonHelper.toJson(loginNipModel), null, responseHandle);
    }

    public void resetPassworf(String email, String password, String service_code, boolean isPhone, String authorization_code, ResponseHandle<RESP_Reset> responseHandle) {
        ResetEntity resetEntity = new ResetEntity();

        resetEntity.setService_code(service_code);

        if (isPhone) {
//            resetEntity.setUsename(email);
            resetEntity.setSendMail(0);
            resetEntity.setAccountType("PHONE-NUMBER");
            resetEntity.setPassword(password);
            resetEntity.setAuthorization_code(authorization_code);
        } else {
            resetEntity.setEmail(email);
            resetEntity.setSendMail(1);
            resetEntity.setAccountType("EMAIL");
        }

        requestServer.putApi(url_reset_password, JsonHelper.toJson(resetEntity), null, responseHandle);
    }

    public void reactiveNipAccoint(String user_name, String service_code, boolean isPhone, ResponseHandle<RESP_Reactive> responseHandle) {
        String url_reactive = Constants.URL_NIP + Constants.API_RE_ACTIVE_ACC_NIP;

        ReactiveNip reactiveNip = new ReactiveNip();
        reactiveNip.setUsername(user_name);
        reactiveNip.setService_code(service_code);

        if (isPhone) {
            reactiveNip.setSendMail(1);
            reactiveNip.setAccountType("PHONE-NUMBER");
        } else {
            reactiveNip.setSendMail(0);
            reactiveNip.setAccountType("EMAIL");
        }

        requestServer.putApi(url_reactive, JsonHelper.toJson(reactiveNip), null, responseHandle);
    }


    public String getSessiong() {
        return SharedUtils.getInstance().getStringValue(Constants.SESSION);
    }

    public void getNewSession(String service_code, ResponseHandle<RESP_Login> responseHandle) {
        AuthenNip authenNip = new AuthenNip();
        authenNip.setAuthenticationid(SharedUtils.getInstance().getStringValue(Constants.USER_AUTH_ID));
        authenNip.setService_code(service_code);
        authenNip.setDevInfo(DeviceInfo.getDeviceObject());

        String url_authen = Constants.URL_NIP + Constants.API_SESSION_AUTHENTICATE;
        requestServer.postApi(url_authen, JsonHelper.toJson(authenNip), null, responseHandle);
    }

    public void activeAccount(String authorization_code, String service_code, String accountType, ResponseHandle responseHandle) {
        String url_active = Constants.URL_NIP + Constants.API_ACTIVE_ACCOUNT;
        ActiveNip activeNip = new ActiveNip();
        activeNip.setAuthorization_code(authorization_code);
        activeNip.setService_code(service_code);
        activeNip.setActivation_code(SharedUtils.getInstance().getStringValue(Constants.USER_ACTIVATION_CODE));
        activeNip.setAccountType(accountType);

        requestServer.postApi(url_active, JsonHelper.toJson(activeNip), null, responseHandle);
    }

    public String getServiceCode(Activity activity) {
        try {
            ApplicationInfo ai = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            return bundle.getString(Constants.META_DATA_NAME);
        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void logout() {
        SharedUtils.getInstance().clearData();
    }
}
