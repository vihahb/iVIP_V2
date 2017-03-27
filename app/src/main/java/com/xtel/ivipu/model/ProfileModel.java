package com.xtel.ivipu.model;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.RESP_None;

/**
 * Created by vivhp on 2/8/2017.
 */

public class ProfileModel extends Model {

    public static ProfileModel instance = new ProfileModel();

    public static ProfileModel getInstance() {
        return instance;
    }

    public void getUser(String url, String session, ResponseHandle responseHandle) {
        requestServer.getApi(url, session, responseHandle);
    }

    public void onUpdateUser(String url, String jsonObject, String session, ResponseHandle<RESP_None> responseHandle) {
        requestServer.putApi(url, jsonObject, session, responseHandle);
    }
}
