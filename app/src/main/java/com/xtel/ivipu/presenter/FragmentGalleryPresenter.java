package com.xtel.ivipu.presenter;

import android.os.Handler;
import android.util.Log;

import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.RESP.RESP_Gallery;
import com.xtel.ivipu.model.RESP.RESP_NewsObject;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.fragment.inf.IFragmentGalleryView;
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
 * Created by vivhp on 2/27/2017.
 */

public class FragmentGalleryPresenter {

    private IFragmentGalleryView view;
    private String TAG = "Gallery Pre";
    private String session = LoginManager.getCurrentSession();

    public FragmentGalleryPresenter(IFragmentGalleryView view) {
        this.view = view;
    }

    public void getGalleryFragment(final int news_id, final String type, final int page, final int pagesize) {

        if (!NetWorkInfo.isOnline(view.getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.onNetworkDisable();
                }
            }, 500);
            return;
        } else {
            String url_gallery = Constants.SERVER_IVIP + Constants.GALLERY_GET + news_id + "?type=" + type + "&page=" + page + "&pagesize=" + pagesize;
            Log.e("Url gallery", url_gallery);

            HomeModel.getInstance().getGalleryArray(url_gallery, session, new ResponseHandle<RESP_Gallery>(RESP_Gallery.class) {
                @Override
                public void onSuccess(RESP_Gallery obj) {
                    view.onLoadImageSuccess(obj.getData());
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
                                        getGalleryFragment(news_id, type, page, pagesize);
                                    }

                                    @Override
                                    public void onError(Error error) {
                                        view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                        view.startActivityAndFinish(LoginActivity.class);
                                    }
                                });
                            } else {
                                view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), code, null));
                                Log.e(TAG, "Err " + error.getCode());
                            }
                        } else {
                            Log.e(TAG, "Err " + JsonHelper.toJson(error));
                            view.showShortToast("Co loi");
                        }
                    } else {
                        view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                    }
                }
            });
        }
    }

    public void getNewsInfo(final int id_news) {

        if (!NetWorkInfo.isOnline(view.getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.onNetworkDisable();
                }
            }, 500);
            return;
        } else {
            String url_news_info = Constants.SERVER_IVIP + Constants.NEWS_INFO + id_news;
            HomeModel.getInstance().getNewsInfomation(url_news_info, session, new ResponseHandle<RESP_NewsObject>(RESP_NewsObject.class) {
                @Override
                public void onSuccess(RESP_NewsObject obj) {
                    Log.e("News obj", "data " + JsonHelper.toJson(obj));
                    view.onGetNewsObjSuccess(obj);
                }

                @Override
                public void onError(com.xtel.nipservicesdk.model.entity.Error error) {
                    if (error != null) {
                        int code = error.getCode();
                        Log.e("err json", error.toString());
                        if (code == 2) {
                            CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                                @Override
                                public void onSuccess(RESP_Login success) {
                                    getNewsInfo(id_news);
                                }

                                @Override
                                public void onError(com.xtel.nipservicesdk.model.entity.Error error) {
                                    Log.e("err callback", JsonHelper.toJson(error));
                                    view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                    view.startActivityAndFinish(LoginActivity.class);
                                }
                            });
                        } else {
                            view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                        }
                    }
                }

            });
        }
    }
}
