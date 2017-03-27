package com.xtel.ivipu.presenter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.xtel.ivipu.model.LoginModel;
import com.xtel.ivipu.model.ProfileModel;
import com.xtel.ivipu.model.RESP.RESP_Profile;
import com.xtel.ivipu.model.entity.UserInfo;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.activity.inf.IProfileActivityView;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtel.sdk.callback.RequestWithStringListener;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.commons.NetWorkInfo;
import com.xtel.sdk.utils.SharedPreferencesUtils;
import com.xtel.sdk.utils.Task;

/**
 * Created by vihahb on 1/13/2017.
 */

public class ProfilePresenter {

    public static final String TAG = "Profile_presenter";
    String session = LoginManager.getCurrentSession();
    private IProfileActivityView view;
    public ProfilePresenter(IProfileActivityView view) {
        this.view = view;
    }


//    public void setProfile() {
//        String rank = "Diamond";
//        String date_reg = "2017/17/01";
//        int score = 100;
//        String f_name = "Vi";
//        String l_name = "Vũ Hà";
//        int gender = 3;
//        long birthday = 838425617000l;
//        String phone = "01673378303";
//        String address = "Hòa Bình - Việt Nam";
//        String ava = "https://scontent.xx.fbcdn.net/v/t1.0-1/p200x200/13528706_849870591811691_1547349153482224738_n.jpg?oh=02ba740bab49c1a839995abad5f0802d&oe=59483152";
//        String email = "vihahb@icloud.com";
//        profile = new Profile();
//        profile.setScore(score);
//        profile.setRank(rank);
//        profile.setDate_reg(date_reg);
//        profile.setFirst_name(f_name);
//        profile.setLast_name(l_name);
//        profile.setBirth_day(birthday);
//        profile.setPhone(phone);
//        profile.setGender(gender);
//        profile.setAddress(address);
//        profile.setAvatar(ava);
//        profile.setEmail(email);
//        view.setProfileSuccess(profile);
//    }

    public void getProfileData() {
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
                String full_name = SharedPreferencesUtils.getInstance().getStringValue(Constants.PROFILE_FULL_NAME);
                int gender = SharedPreferencesUtils.getInstance().getIntValue(Constants.PROFILE_GENDER);
                long birth_day = SharedPreferencesUtils.getInstance().getLongValue(Constants.PROFILE_BIRTH_DAY);
                String email = SharedPreferencesUtils.getInstance().getStringValue(Constants.PROFILE_EMAIL);
                String phone_number = SharedPreferencesUtils.getInstance().getStringValue(Constants.PROFILE_PHONE_NUM);
                String address = SharedPreferencesUtils.getInstance().getStringValue(Constants.PROFILE_ADDRESS);
                String avatar = SharedPreferencesUtils.getInstance().getStringValue(Constants.PROFILE_AVATAR);
                String qr_code = SharedPreferencesUtils.getInstance().getStringValue(Constants.PROFILE_QR_CODE);
                String bar_code = SharedPreferencesUtils.getInstance().getStringValue(Constants.PROFILE_BAR_CODE);
                int status = SharedPreferencesUtils.getInstance().getIntValue(Constants.PROFILE_STATUS);
                int general_point = SharedPreferencesUtils.getInstance().getIntValue(Constants.PROFILE_GENERAL_POINT);
                String level = SharedPreferencesUtils.getInstance().getStringValue(Constants.PROFILE_LEVEL);
                long joint_date = SharedPreferencesUtils.getInstance().getLongValue(Constants.PROFILE_JOINT_DATE);

                Log.e("Profile full name", full_name);

                UserInfo userInfo = new UserInfo();

                userInfo.setFullname(full_name);
                userInfo.setGender(gender);
                userInfo.setBirthday(birth_day);
                userInfo.setEmail(email);
                userInfo.setPhonenumber(phone_number);
                userInfo.setAddress(address);
                userInfo.setAvatar(avatar);
                userInfo.setQr_code(qr_code);
                userInfo.setBar_code(bar_code);
                userInfo.setStatus(status);
                userInfo.setGeneral_point(general_point);
                userInfo.setLevel(level);
                userInfo.setJoin_date(joint_date);

                view.setProfileSuccess(userInfo);
            }
        } else {
            Log.e("Session", "session null");
            return;
        }
    }

    public void reloadDataProfile() {
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
                String session = LoginManager.getCurrentSession();
                Log.e(TAG + "ses", session);
                String url_profile = Constants.SERVER_IVIP + Constants.GET_USER_IVIP_FULL;
                Log.e(TAG + "url", url_profile);
                LoginModel.getInstance().getUser(url_profile, session, new ResponseHandle<RESP_Profile>(RESP_Profile.class) {
                    @Override
                    public void onSuccess(RESP_Profile obj) {
                        Log.d(TAG + "succ", JsonHelper.toJson(obj));
                        view.reloadProfile(obj);
                    }

                    @Override
                    public void onError(com.xtel.nipservicesdk.model.entity.Error error) {
                        Log.e(TAG + "err", error.getMessage());
                        if (error.getCode() == 2) {
                            CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                                @Override
                                public void onSuccess(RESP_Login success) {
                                    reloadDataProfile();
                                }

                                @Override
                                public void onError(com.xtel.nipservicesdk.model.entity.Error error) {
                                    view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                    view.startActivityAndFinish(LoginActivity.class);
                                }
                            });
                        }
                        view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                    }
                });
            }
        } else {
            return;
        }
    }

    public void setData(final UserInfo profile_object) {
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
                final String session = LoginManager.getCurrentSession();
                String url = Constants.SERVER_IVIP + Constants.UPDATE_USER;
                Log.e("Project user", JsonHelper.toJson(profile_object));
                ProfileModel.getInstance().onUpdateUser(url, JsonHelper.toJson(profile_object), session, new ResponseHandle<com.xtel.nipservicesdk.model.entity.RESP_None>(com.xtel.nipservicesdk.model.entity.RESP_None.class) {
                    @Override
                    public void onSuccess(com.xtel.nipservicesdk.model.entity.RESP_None obj) {
                        Log.e("respond success", "ádasdaweawe");
                        view.updateProfileSucc();
                    }

                    @Override
                    public void onError(com.xtel.nipservicesdk.model.entity.Error error) {
                        Log.e("respond err", "ádasdaweaweád");
                        if (error.getCode() == 2) {
                            CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                                @Override
                                public void onSuccess(RESP_Login success) {
                                    setData(profile_object);
                                }

                                @Override
                                public void onError(com.xtel.nipservicesdk.model.entity.Error error) {
                                    view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                    view.startActivityAndFinish(LoginActivity.class);
                                }
                            });
                        } else {
                            view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                        }
                    }

                });
                reloadDataProfile();
            }
        } else {
            return;
        }
    }

    public void postImage(Bitmap bitmap) {
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
                new Task.ConvertImage(view.getActivity(), true, new RequestWithStringListener() {
                    @Override
                    public void onSuccess(String url, String server_path) {
                        Log.e("Url", url);
                        view.onPostPictureSuccess(url, server_path);
                    }

                    @Override
                    public void onError() {
                        view.onPostPictureError("Xảy ra lỗi. Vui lòng thử lại");
                    }
                }).execute(bitmap);
            }
        } else {
            return;
        }
    }
}
