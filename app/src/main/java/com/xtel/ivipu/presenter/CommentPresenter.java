package com.xtel.ivipu.presenter;

import android.os.Handler;

import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.RESP.RESP_Get_Comment_data;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.activity.inf.IActivityComment;
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
 * Created by vivhp on 2/18/2017.
 */

public class CommentPresenter {

    String session = LoginManager.getCurrentSession();
    private IActivityComment view;

    public CommentPresenter(IActivityComment view) {
        this.view = view;
    }

    public void getComment(final int id_news, final int page, final int pagesize) {
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
                String url = Constants.SERVER_IVIP + "v0.1/news/" + id_news + "/comment?page=" + page + "&pagesize=" + pagesize;
                HomeModel.getInstance().getNewsComment(url, session, new ResponseHandle<RESP_Get_Comment_data>(RESP_Get_Comment_data.class) {
                    @Override
                    public void onSuccess(RESP_Get_Comment_data obj) {
                        view.onGetCommentSuccess(obj.getData());
                    }

                    @Override
                    public void onError(Error error) {
                        if (error != null) {
                            int code = error.getCode();
                            if (code == 2) {
                                CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                                    @Override
                                    public void onSuccess(RESP_Login success) {
                                        getComment(id_news, page, pagesize);
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
                                view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), code, null));
                            }
                        }
                    }
                });
            }
        } else {
            view.showShortToast("Vui lòng đăng nhập để xem và bình luận bản tin.");
            view.startActivityAndFinish(LoginActivity.class);
        }
    }

}
