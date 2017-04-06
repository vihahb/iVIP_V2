package com.xtel.ivipu.presenter;

import android.os.Handler;
import android.util.Log;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.RESP.RESP_ListMember;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.fragment.inf.IFragmentMemberCard;
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
 * Created by vuhavi on 07/03/2017.
 */

public class FragmentNavMemberCardPresenter {

    private IFragmentMemberCard view;
    private String TAG = "MemberCard Pre";
    private String session = LoginManager.getCurrentSession();
    public FragmentNavMemberCardPresenter(IFragmentMemberCard view) {
        this.view = view;
    }

    public void getMemberCard(final int page, final int pagesize){
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
                String url_member_card = Constants.SERVER_IVIP + "v0.1/user/member_card?page=" + page + "&pagesize=" + pagesize;
                HomeModel.getInstance().getMemberCard(url_member_card, session, new ResponseHandle<RESP_ListMember>(RESP_ListMember.class) {
                    @Override
                    public void onSuccess(RESP_ListMember obj) {
                        view.onGetMemberCardSuccess(obj.getData());
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
                                            getMemberCard(page, pagesize);
                                        }

                                        @Override
                                        public void onError(Error error) {
                                            view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                            view.startActivityAndFinish(LoginActivity.class);
                                        }
                                    });
                                } else {
                                    view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), code, null));
                                }

                            } else {
                                Log.e(TAG, "Err " + JsonHelper.toJson(error));
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
