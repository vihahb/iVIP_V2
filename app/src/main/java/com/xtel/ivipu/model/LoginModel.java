package com.xtel.ivipu.model;

import com.xtel.nipservicesdk.callback.ResponseHandle;

/**
 * Created by vivhp on 1/28/2017.
 */

public class LoginModel extends Model {

    public static LoginModel instance = new LoginModel();

    public static LoginModel getInstance() {
        return instance;
    }

    public void getUser(String url, String session, ResponseHandle responseHandle) {
        requestServer.getApi(url, session, responseHandle);
    }

    public void postFCMKey(String url, String object, String session, ResponseHandle responseHandle) {
        requestServer.postApi(url, object, session, responseHandle);
    }
}
