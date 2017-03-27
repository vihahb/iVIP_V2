package com.xtel.ivipu.presenter;

import android.os.Handler;
import android.util.Log;

import com.xtel.ivipu.view.activity.inf.IResetView;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.callback.CallbackListenerReset;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Reset;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtel.sdk.commons.NetWorkInfo;

/**
 * Created by vihahb on 1/11/2017.
 */

public class ResetPresenter {
    public static final String TAG = "Reset";
    CallbackManager callbackManager;
    private IResetView view;

    public ResetPresenter(IResetView view) {
        this.view = view;
    }

    public void createCallbackManager() {
        callbackManager = CallbackManager.create(view.getActivity());
    }

    public void requestPermission(int requestCode, String[] pesmission, int[] grantResults) {
        callbackManager.onRequestPermissionsResult(requestCode, pesmission, grantResults);
    }


    public void resetAccountNIP(String password, String authorization_code) {
        if (!NetWorkInfo.isOnline(view.getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.onNetworkDisable();
                }
            }, 500);
            return;
        } else {
            callbackManager.AdapterReset(null, password, authorization_code, new CallbackListenerReset() {
                @Override
                public void onSuccess(RESP_Reset reset) {
                    view.showShortToast("Thay đổi mật khẩu thành công");
                    Log.e(TAG, "New password: " + reset.getPassword());
                    view.finishActivity();
                }

                @Override
                public void onError(Error error) {
                    if (error != null) {
                        int code_err = error.getCode();
                        view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), code_err, null));
                    }
                }
            });
        }
    }

}
