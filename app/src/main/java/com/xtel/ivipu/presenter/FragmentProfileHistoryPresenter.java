package com.xtel.ivipu.presenter;

import android.os.Handler;
import android.util.Log;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.RESP.RESP_ListNews;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.fragment.inf.IFragmentHistoryView;
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
 * Created by vuhavi on 03/03/2017.
 */

public class FragmentProfileHistoryPresenter {

    private IFragmentHistoryView view;
    private String TAG = "History Pre";
    private String session = LoginManager.getCurrentSession();
    public FragmentProfileHistoryPresenter(IFragmentHistoryView view) {
        this.view = view;
    }

    public void getHistory(final int page, final int pagesize){

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
                String url_history = Constants.SERVER_IVIP + "v0.1/user/history?page=" + page + "&pagesize=" + pagesize;
                HomeModel.getInstance().getHistory(url_history, session, new ResponseHandle<RESP_ListNews>(RESP_ListNews.class) {
                    @Override
                    public void onSuccess(RESP_ListNews obj) {
                        view.onGetHistorySuccess(obj.getData());
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
                                            getHistory(page, pagesize);
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
                                Log.e(TAG, "err " + JsonHelper.toJson(error));
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
