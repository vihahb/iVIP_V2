package com.xtel.ivipu.presenter;

import android.os.Handler;

import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.RESP.RESP_ListNews;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.fragment.inf.IFragmentSuggestionView;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.commons.NetWorkInfo;

/**
 * Created by vivhp on 2/24/2017.
 */

public class FragmentSuggestionPresenter {
    private IFragmentSuggestionView view;
    private String session = LoginManager.getCurrentSession();
    public FragmentSuggestionPresenter(IFragmentSuggestionView view) {
        this.view = view;
    }

    public void getSuggestion(final int id_news, final int page, final int pagesize) {
        if (!NetWorkInfo.isOnline(view.getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.onNetworkDisable();
                }
            }, 500);
            return;
        } else {
            String url_suggestion = Constants.SERVER_IVIP + "v0.1/suggestion?id=" + id_news + "&page=" + page + "&pagesize=" + pagesize;

            HomeModel.getInstance().getNewsSuggestion(url_suggestion, session, new ResponseHandle<RESP_ListNews>(RESP_ListNews.class) {
                @Override
                public void onSuccess(RESP_ListNews obj) {
                    view.onGetSucggestionSuccess(obj.getData());
                }

                @Override
                public void onError(Error error) {
                    if (error != null) {
                        int code = error.getCode();
                        if (code == 2) {
                            CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                                @Override
                                public void onSuccess(RESP_Login success) {
                                    getSuggestion(id_news, page, pagesize);
                                }

                                @Override
                                public void onError(Error error) {
                                    view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                    view.startActivityAndFinish(LoginActivity.class);
                                }
                            });
                        }
                    }
                }
            });
        }
    }
}
