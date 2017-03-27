package com.xtel.ivipu.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.RESP.RESP_Checkin;
import com.xtel.ivipu.model.entity.CheckInEntity;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.activity.inf.IScannerView;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtel.sdk.commons.Constants;

/**
 * Created by vivhp on 2/15/2017.
 */

public class ScannerQrPresenter {

    private IScannerView view;

    public ScannerQrPresenter(IScannerView view) {
        this.view = view;
    }

    public void checkInStore(final String store_code, final double location_lat, final double location_lng) {
        String session = LoginManager.getCurrentSession();
        String utl_checkin = Constants.SERVER_IVIP + Constants.CHECKIN_ACTION;
        CheckInEntity checkInEntity = new CheckInEntity();
        checkInEntity.setStore_code(store_code);
        checkInEntity.setLocation_lat(location_lat);
        checkInEntity.setLocation_lng(location_lng);

        HomeModel.getInstance().postChekinAction(
                utl_checkin,
                JsonHelper.toJson(checkInEntity),
                session,
                new ResponseHandle<RESP_Checkin>(RESP_Checkin.class) {
                    @Override
                    public void onSuccess(RESP_Checkin obj) {
                        Log.e("Checkin presenter obj", JsonHelper.toJson(obj));
                        view.onCheckinSuccess(obj);
                    }

                    @SuppressLint("LongLogTag")
                    @Override
                    public void onError(Error error) {
                        int code = error.getCode();
                        if (code == 2) {
                            CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                                @Override
                                public void onSuccess(RESP_Login success) {
                                    checkInStore(store_code, location_lat, location_lng);
                                }

                                @Override
                                public void onError(Error error) {
                                    Log.e("Code er get new sess", String.valueOf(error.getCode()));
                                    view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                    view.startActivityFinish(LoginActivity.class);
                                }
                            });
                        } else if (code == 301) {
                            Log.e("Code er store not existed", String.valueOf(error.getCode()));
                            showNotification("Thông báo", "Cửa hàng không tồn tại trên hệ thống.\nVui lòng thử lại");
                        } else {
                            Log.e("Code er get new sess", String.valueOf(error.getCode()));
                            view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                        }
                        }
                    }
        );
    }

    private void showNotification(String title, String mes) {
        view.showDialogNotification(title, mes);
    }
}
