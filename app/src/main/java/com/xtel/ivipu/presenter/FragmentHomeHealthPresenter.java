package com.xtel.ivipu.presenter;

import android.os.Handler;
import android.util.Log;

import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.RESP.RESP_ListNews;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.fragment.inf.IFragmentHomeHealth;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.commons.NetWorkInfo;

/**
 * Created by vuhavi on 06/03/2017.
 */

public class FragmentHomeHealthPresenter {

    private IFragmentHomeHealth view;
//    private String session = LoginManager.getCurrentSession();
private String TAG = "Health";
    public FragmentHomeHealthPresenter(IFragmentHomeHealth view) {
        this.view = view;
    }

    public void getHealtNews(final int type, final int page, final int pagesize){

        if (!NetWorkInfo.isOnline(view.getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.onNetworkDisable();
                }
            }, 500);
            return;
        } else {
            String url_health = Constants.SERVER_IVIP + "v0.1/news?type=" + type + "&page=" + page + "&pagesize=" + pagesize;

//            Log.e("Session", session);
            Log.e("Url request arr news", url_health);
            HomeModel.getInstance().getNews(url_health, null, new ResponseHandle<RESP_ListNews>(RESP_ListNews.class) {
                @Override
                public void onSuccess(RESP_ListNews obj) {
                    Log.e("obj news shop", JsonHelper.toJson(obj));
                    view.onGetNewsListSuccess(obj.getData());
                }

                @Override
                public void onError(com.xtel.nipservicesdk.model.entity.Error error) {
                    int code = error.getCode();
                    if (String.valueOf(code) != null) {
                        if (code == 2) {
                            CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                                @Override
                                public void onSuccess(RESP_Login success) {
                                    getHealtNews(type, page, pagesize);
                                }

                                @Override
                                public void onError(com.xtel.nipservicesdk.model.entity.Error error) {
                                    view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                    view.startActivityAndFinish(LoginActivity.class);
                                }
                            });
                        } else {
                            view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), code, null));
                            Log.e("Code err shop", String.valueOf(code));
                        }
                    } else {
                        Log.e(TAG, "Err " + JsonHelper.toJson(error));
                        view.showShortToast("Co loi");
                    }
                }
            });

        }
    }
}
