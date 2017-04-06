package com.xtel.ivipu.presenter;

import android.os.Handler;
import android.util.Log;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.RESP.RESP_ListNews;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.fragment.inf.IFragmentNotify;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.commons.NetWorkInfo;

/**
 * Created by vivhp on 3/17/2017.
 */

public class NotifyPresenter {

    String session = LoginManager.getCurrentSession();
    private IFragmentNotify view;
    private String TAG = "Notify Pre";
    public NotifyPresenter(IFragmentNotify view) {
        this.view = view;
    }

    public void getNotification(final int page, final int pagesize) {

        if (session != null) {
            if (!NetWorkInfo.isOnline(view.getActivity())) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.onNetworkDisable();
                    }
                }, 500);
                return;
            } else {
                String url_notify = Constants.SERVER_IVIP + Constants.NOTIFY_USER + "page=" + page + "&pagesize=" + pagesize;
                HomeModel.getInstance().getNewsSuggestion(url_notify, session, new ResponseHandle<RESP_ListNews>(RESP_ListNews.class) {
                    @Override
                    public void onSuccess(RESP_ListNews obj) {
                        view.onGetNotificationSuccess(obj.getData());
                    }

                    @Override
                    public void onError(Error error) {
                        if (error != null) {
                            int code = error.getCode();
                            if (String.valueOf(code) != null) {
                                if (code == 2) {
                                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                                        @Override
                                        public void onSuccess(RESP_Login success) {
                                            getNotification(page, pagesize);
                                        }

                                        @Override
                                        public void onError(Error error) {
                                            view.startActivityAndFinish(LoginActivity.class);
                                            view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                        }
                                    });
                                } else {
                                    view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), code, null));
                                }
                            } else {
                                Log.e(TAG, "Err " + JsonHelper.toJson(error));
                                view.showShortToast("Co loi");
                            }
                        }
                    }
                });
            }
        } else {
            view.startActivityAndFinish(LoginActivity.class);
            view.showShortToast(view.getActivity().getString(R.string.need_login_to_action));
        }
    }
}
