package com.xtel.ivipu.presenter;

import android.os.Handler;
import android.util.Log;

import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.RESP.RESP_Voucher_list;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.fragment.inf.IFragmentHomeVoucherListView;
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
 * Created by vivhp on 4/5/2017.
 */

public class FragmentVoucherListPresenter {

    String TAG = "Voucher lis pre";
    String session = LoginManager.getCurrentSession();
    private IFragmentHomeVoucherListView view;

    public FragmentVoucherListPresenter(IFragmentHomeVoucherListView view) {
        this.view = view;
    }

    public void getVoucherList(final int page, final int pagesize) {
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
                String url_voucher_user = Constants.SERVER_IVIP + "v0.1/user/vouchers?page=" + page + "&pagesize=" + pagesize;
                HomeModel.getInstance().getHistoryTransactionMemberCard(url_voucher_user, session, new ResponseHandle<RESP_Voucher_list>(RESP_Voucher_list.class) {
                    @Override
                    public void onSuccess(RESP_Voucher_list obj) {
                        view.onGetVoucherSuccess(obj.getData());
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
                                            getVoucherList(page, pagesize);
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
            view.showShortToast("Vui lòng đăng nhập để thực hiên chức năng");
            view.startActivityAndFinish(LoginActivity.class);
        }
    }
}
