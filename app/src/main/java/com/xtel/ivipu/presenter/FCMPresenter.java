package com.xtel.ivipu.presenter;

import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.entity.NotifyNumberObj;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.fcm.inf.NotifyInterface;

/**
 * Created by vivhp on 3/25/2017.
 */

public class FCMPresenter {


    String session = LoginManager.getCurrentSession();
    private NotifyInterface notifyInterface;

    public FCMPresenter(NotifyInterface notifyInterface) {
        this.notifyInterface = notifyInterface;
    }

    public void getNotify() {
        if (session != null) {
            String url_notify = Constants.SERVER_IVIP + Constants.NOTIFY_NUMBER;
            HomeModel.getInstance().getNotifyNumber(url_notify, session, new ResponseHandle<NotifyNumberObj>(NotifyNumberObj.class) {
                @Override
                public void onSuccess(NotifyNumberObj obj) {
                    notifyInterface.getNotifySuccess(obj.getNotify());
                }

                @Override
                public void onError(Error error) {
                    if (error != null) {
                        int code = error.getCode();

                    }
                }
            });
        }
    }
}
