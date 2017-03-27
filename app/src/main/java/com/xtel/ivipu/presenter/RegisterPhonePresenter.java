package com.xtel.ivipu.presenter;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.xtel.ivipu.R;
import com.xtel.ivipu.view.activity.inf.IRegisterPhoneView;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.callback.CallbackLisenerRegister;
import com.xtel.nipservicesdk.callback.CallbackListenerActive;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Register;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtel.sdk.commons.NetWorkInfo;

/**
 * Created by vihahb on 1/10/2017.
 */

public class RegisterPhonePresenter {

    private static final int REG_REQUEST_CODE = 199;
    private IRegisterPhoneView view;
    private CallbackManager callbackManager;

    public RegisterPhonePresenter(IRegisterPhoneView view) {
        this.view = view;
    }

    public void createCallbackManager() {
        callbackManager = CallbackManager.create(view.getActivity());
    }

    public void requestPermission(int requestCode, String[] pesmission, int[] grantResults) {
        callbackManager.onRequestPermissionsResult(requestCode, pesmission, grantResults);
    }

    public void onAccessAccountKit() {
        final Intent intent = new Intent(view.getActivity(), AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.CODE); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        configurationBuilder.setTitleType(AccountKitActivity.TitleType.APP_NAME);
        configurationBuilder.setDefaultCountryCode("VN");
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        view.startActivityForResults(intent, REG_REQUEST_CODE);
    }

    public void onActivityResultsAccountKit(
            int requestCode,
            int resultCode,
            Intent data) {
        if (requestCode == REG_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0, 10));
                    Log.d("authorization ", loginResult.getAuthorizationCode());
                    String authorization = loginResult.getAuthorizationCode();
                    activeNipAccount(authorization);
                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...
//                goToMyLoggedInActivity();
            }

            // Surface the result to your user in an appropriate way.
            view.showShortToast(toastMessage);
        }
    }

    public void activeNipAccount(String authorization_code) {
        if (!NetWorkInfo.isOnline(view.getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.onNetworkDisable();
                }
            }, 500);
            return;
        } else {
            String acc_type = "PHONE-NUMBER";
            callbackManager.activeNipAccount(authorization_code, acc_type, new CallbackListenerActive() {
                @Override
                public void onSuccess() {
                    view.showShortToast("Active success!");
                    view.finishActivitys();
                }

                @Override
                public void onError(Error error) {
                    view.showShortToast("Active error code: " + error.getCode());
                }
            });
        }
    }

    public void onRegisterPhone(String user_name, String password) {
        if (!NetWorkInfo.isOnline(view.getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.onNetworkDisable();
                }
            }, 500);
            return;
        } else {
            callbackManager.registerNipService(user_name, password, null, true, new CallbackLisenerRegister() {
                @Override
                public void onSuccess(RESP_Register register) {
                    Log.e("Success", register.getActivation_code());
                    view.showShortToast(view.getActivity().getString(R.string.action_success));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onAccessAccountKit();
                        }
                    }, 1000);
                }

                @Override
                public void onError(Error error) {
                    String code_err = String.valueOf(error.getCode());
                    int code = Integer.parseInt(code_err);
                    Log.e("Error", String.valueOf(error.getCode()));
                    Log.e("Error mess", error.toString());
                    if (!(code_err == null)) {
                        view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), code, ""));
                    }
                }
            });
        }
    }
}
