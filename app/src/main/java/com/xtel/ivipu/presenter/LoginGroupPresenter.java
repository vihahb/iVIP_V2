package com.xtel.ivipu.presenter;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.xtel.ivipu.view.activity.HomeActivity;
import com.xtel.ivipu.view.activity.ResetActivity;
import com.xtel.ivipu.view.activity.inf.ILoginGroup;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.commons.Constants;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtel.nipservicesdk.utils.SharedUtils;
import com.xtel.sdk.commons.NetWorkInfo;

/**
 * Created by vihahb on 1/10/2017.
 */

public class LoginGroupPresenter {

    public static int APP_REQUEST_CODE = 100;

    private ILoginGroup view;

    private String TAG = "Login Group ";

    private CallbackManager callbackManager;

    public LoginGroupPresenter(ILoginGroup view) {
        this.view = view;
    }

    public void createCallbackManager() {
        callbackManager = CallbackManager.create(view.getActivity());
    }

    public void requestPermission(int requestCode, String[] pesmission, int[] grantResults) {
        callbackManager.onRequestPermissionsResult(requestCode, pesmission, grantResults);
    }

    public void onLoginNip(String user, String pass, boolean isPhone) {
        if (!NetWorkInfo.isOnline(view.getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.onNetworkDisable();
                }
            }, 500);
            return;
        } else {
            callbackManager.LoginNipAcc(user, pass, isPhone, new CallbacListener() {
                @Override
                public void onSuccess(RESP_Login success) {
                    Log.e("Login Success", success.getAuthenticationid());
                    view.showShortToast("Success");
                    String sesion = success.getSession();
                    Log.d(TAG + "session", sesion);
                    SharedUtils.getInstance().putStringValue(Constants.SESSION, sesion);
                    view.startActivity(HomeActivity.class);
                    view.finishActivity();
                }

                @Override
                public void onError(Error error) {
                    Log.e("Login err", JsonHelper.toJson(error));
                    String code_err = String.valueOf(error.getCode());
                    int code = Integer.parseInt(code_err);
                    if (!(code_err == null)) {
                        view.showShortToast(parseMessage(code));
                    }
                }
            });
        }
    }

    public void onLoginAccountKit() {
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
        view.startActivityForResults(intent, APP_REQUEST_CODE);
    }

    public void onActivityResultAccountKit(final int requestCode,
                                           final int resultCode,
                                           final Intent data) {
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
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
                    Intent intent = new Intent(view.getActivity(), ResetActivity.class);
                    intent.putExtra("authorization", authorization);
                    view.startActivitys(intent);
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

    private String parseMessage(int code) {
        String mess = JsonParse.getCodeMessage(view.getActivity(), code, "");
        return mess;
    }
}
