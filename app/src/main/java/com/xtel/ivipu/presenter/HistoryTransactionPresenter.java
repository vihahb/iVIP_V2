package com.xtel.ivipu.presenter;

import android.os.Handler;
import android.util.Log;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.RESP.RESP_ListHistoryTransaction;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.activity.inf.IHistoryTransactionActivityView;
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
 * Created by vuhavi on 11/03/2017.
 */

public class HistoryTransactionPresenter {

    private IHistoryTransactionActivityView view;
    private String TAG = "His Transaction Pre";
    private String session = LoginManager.getCurrentSession();
    public HistoryTransactionPresenter(IHistoryTransactionActivityView view) {
        this.view = view;
    }

    public void getHistoryTransaction(final int id, final int page, final int pagesize){
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
                String url_history_transaction = Constants.SERVER_IVIP + "v0.1/user/member_card/" + id + "/history?page=" + page + "&pagesize=" + pagesize;

                HomeModel.getInstance().getHistoryTransactionMemberCard(url_history_transaction, session, new ResponseHandle<RESP_ListHistoryTransaction>(RESP_ListHistoryTransaction.class) {
                    @Override
                    public void onSuccess(RESP_ListHistoryTransaction obj) {
                        view.onGetTransactionSuccess(obj.getData());
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
                                            getHistoryTransaction(id, page, pagesize);
                                        }

                                        @Override
                                        public void onError(Error error) {
                                            view.startActivityFinish(LoginActivity.class);
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
            view.startActivityFinish(LoginActivity.class);
            view.showShortToast(view.getActivity().getString(R.string.need_login_to_action));
        }
    }
}
