package com.xtel.ivipu.presenter;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.xtel.ivipu.view.activity.inf.IReactiveAccount;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.callback.CallbackListenerActive;
import com.xtel.nipservicesdk.callback.CallbackListenerReactive;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Reactive;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtel.sdk.commons.NetWorkInfo;

/**
 * Created by vihahb on 1/12/2017.
 */

public class ReactivePresenter {

    private static final int REACTIVE_REQUEST_CODE = 91;
    String activation_code;
    private IReactiveAccount view;
    private CallbackManager callbackManager;

    public ReactivePresenter(IReactiveAccount view) {
        this.view = view;
    }

    public void createCallbackManager() {
        callbackManager = CallbackManager.create(view.getActivity());
    }

    public void onRequestCallbackPermission(int requestCode, String[] permissions, int[] grantResults) {
        callbackManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onAccessAccountKitReactive() {
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
        view.startActivityForResults(intent, REACTIVE_REQUEST_CODE);
    }

    public void onActivityResultAccountKit(final int requestCode,
                                           final int resultCode,
                                           final Intent data) {
        if (requestCode == REACTIVE_REQUEST_CODE) { // confirm that this response matches your request
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
                    forceActiveAccount(authorization);
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

    public void onReactiveAccount(String phone_number) {
        if (!NetWorkInfo.isOnline(view.getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.onNetworkDisable();
                }
            }, 500);
            return;
        } else {
            callbackManager.reactiveNipAccount(phone_number, true, new CallbackListenerReactive() {
                @Override
                public void onSuccess(RESP_Reactive reactive) {
                    activation_code = reactive.getActivation_code();
                    Log.e("activation code", activation_code);
                    onAccessAccountKitReactive();
                }

                @Override
                public void onError(Error error) {
                    String err_code = String.valueOf(error.getCode());
                    int code = Integer.parseInt(err_code);
                    if (!(err_code == null)) {
                        view.showLongToast(parseMessage(code));
                    }
                }
            });
        }
    }


    public void forceActiveAccount(String authorization_code) {
        String account_type = "PHONE-NUMBER";
        callbackManager.activeNipAccount(authorization_code, account_type, new CallbackListenerActive() {
            @Override
            public void onSuccess() {
                view.showShortToast("Reactive success!");
                view.finishActivitys();
            }

            @Override
            public void onError(Error error) {
                Log.e("reactive code", String.valueOf(error.getCode()));
                view.showShortToast("Reactive error code: " + error.getCode());
            }
        });
    }

    private String parseMessage(int code) {
        String mess = JsonParse.getCodeMessage(view.getActivity(), code, "");
        return mess;
    }
}
