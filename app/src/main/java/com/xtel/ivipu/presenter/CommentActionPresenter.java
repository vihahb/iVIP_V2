package com.xtel.ivipu.presenter;

import android.os.Handler;
import android.util.Log;

import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.entity.CommentActionObj;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.activity.inf.IComment;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.model.entity.RESP_None;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.commons.NetWorkInfo;

/**
 * Created by vivhp on 2/22/2017.
 */

public class CommentActionPresenter {

    private IComment view;
    private String TAG = "CommentAction Pre";
    public CommentActionPresenter(IComment view) {
        this.view = view;
    }

    public void postComment(final int id_news, final String comment_content) {

        if (!NetWorkInfo.isOnline(view.getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.onNetworkDisable();
                }
            }, 500);
            return;
        } else {
            String session = LoginManager.getCurrentSession();
            String url = Constants.SERVER_IVIP + Constants.NEW_ACTION_COMMENT;
            CommentActionObj comment = new CommentActionObj();
            comment.setNews_id(id_news);
            comment.setComment(comment_content);

            HomeModel.getInstance().postNewsComment(url, JsonHelper.toJson(comment), session, new ResponseHandle<RESP_None>(RESP_None.class) {
                @Override
                public void onSuccess(RESP_None obj) {
                    view.onPostCommentSuccess();
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
                                        postComment(id_news, comment_content);
                                    }

                                    @Override
                                    public void onError(Error error) {
                                        Log.e("Err post comment sess", String.valueOf(error.getCode()));
                                        view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                        view.startActivityAndFinish(LoginActivity.class);
                                    }
                                });
                            } else {
                                Log.e("Err post comment", String.valueOf(error.getCode()));
                                view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                            }
                        } else {
                            view.showShortToast("Co loi");
                            Log.e(TAG, "err" + JsonHelper.toJson(error));
                        }
                    }
                }
            });
        }
    }
}
