package com.xtel.ivipu.presenter;

import android.os.Handler;
import android.util.Log;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.RESP.RESP_MyShopCheckin;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.activity.inf.IMyShopActivity;
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
 * Created by vivhp on 2/7/2017.
 */

public class FragmentMyShopPresenter {

    private IMyShopActivity view;
    private String session = LoginManager.getCurrentSession();
    private String TAG = "MyShop Pre";
    public FragmentMyShopPresenter(IMyShopActivity view) {
        this.view = view;
    }

    public void getMyShopCheckin(final int page, final int pagesize) {

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
                String params_url = "v0.1/store?page=" + page + "&pagesize=" + pagesize;
                String url_base = Constants.SERVER_IVIP + params_url;

                HomeModel.getInstance().getMyShopCheckin(url_base, session, new ResponseHandle<RESP_MyShopCheckin>(RESP_MyShopCheckin.class) {
                    @Override
                    public void onSuccess(RESP_MyShopCheckin obj) {
                        view.onGetMyShopData(obj.getData());
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
                                            getMyShopCheckin(page, pagesize);
                                        }

                                        @Override
                                        public void onError(Error error) {
                                            if (error != null) {
                                                view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                                view.startActivityAndFinish(LoginActivity.class);
                                            }
                                        }
                                    });
                                } else {
                                    view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
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
