package com.xtel.ivipu.presenter;

import android.os.Handler;
import android.util.Log;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.RESP.RESP_NewsObject;
import com.xtel.ivipu.model.RESP.RESP_Voucher;
import com.xtel.ivipu.model.entity.NewsActionEntity;
import com.xtel.ivipu.model.entity.RateObject;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.activity.inf.IActivityInfo;
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
 * Created by vivhp on 1/24/2017.
 */

public class ActivityInfoPropertiesPresenter {
    String session = LoginManager.getCurrentSession();
    private IActivityInfo view;
    private String TAG = "Activity Info presenter";

    public ActivityInfoPropertiesPresenter(IActivityInfo view) {
        this.view = view;
    }

    public void getNews(int id_news) {
        if (session != null) {
            getNewsInfomation(id_news, session);
        } else {
            getNewsInfomation(id_news, null);
        }
    }

    public void getNewsInfomation(final int id_news, final String session) {
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
            Log.e("Url request info", url_news_info);
            HomeModel.getInstance().getNewsInfomation(url_news_info, session, new ResponseHandle<RESP_NewsObject>(RESP_NewsObject.class) {
                @Override
                public void onSuccess(RESP_NewsObject obj) {
                    Log.e("News obj", "data " + JsonHelper.toJson(obj));
                    view.onGetNewsObjSuccess(obj);
                }

                @Override
                public void onError(Error error) {
                    if (error != null) {
                        int code = error.getCode();
                        Log.e("err json", error.toString());
                        if (String.valueOf(code) != null) {
                            if (code == 2) {
                                CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                                    @Override
                                    public void onSuccess(RESP_Login success) {
                                        getNewsInfomation(id_news, session);
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
                        } else {
                            Log.e(TAG, "Lỗi " + JsonHelper.toJson(error));
                            view.showShortToast("Có lỗi...");
                        }
                    }
                }

            });
        }
    }

    public void onLikeAction(final int id_news) {
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
                String url_action_like = Constants.SERVER_IVIP + Constants.NEWS_ACTION;
                NewsActionEntity newsAction = new NewsActionEntity();
                newsAction.setNews_id(id_news);
                newsAction.setAction(1);

                HomeModel.getInstance().postNewsAction(url_action_like, JsonHelper.toJson(newsAction), session, new ResponseHandle<RESP_None>(RESP_None.class) {
                    @Override
                    public void onSuccess(RESP_None obj) {
                        view.onLikeSuccess();
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
                                            onLikeAction(id_news);
                                        }

                                        @Override
                                        public void onError(com.xtel.nipservicesdk.model.entity.Error error) {
                                            Log.e("err callback", JsonHelper.toJson(error));
                                            view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                            view.startActivityAndFinish(LoginActivity.class);
                                        }
                                    });
                                } else {
                                    Log.e(TAG, "Err like" + JsonHelper.toJson(error));
                                    view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                }
                            } else {
                                Log.e(TAG, "Lỗi " + JsonHelper.toJson(error));
                                view.showShortToast("Có lỗi...");
                            }
                        }
                    }
                });
            }
        } else {
            Log.e("Session", "null");
            view.showShortToast(view.getActivity().getString(R.string.need_login_to_action));
            view.startActivityAndFinish(LoginActivity.class);
            return;
        }
    }

    public void onRatesNews(final int id_news, final double rate_value) {
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
                String url_rates = Constants.SERVER_IVIP + Constants.RATE_ACTION;

                RateObject rateObject = new RateObject();
                rateObject.setNews_id(id_news);
                rateObject.setRates(rate_value);

                HomeModel.getInstance().postNewsRates(url_rates, JsonHelper.toJson(rateObject), session, new ResponseHandle<RESP_None>(RESP_None.class) {
                    @Override
                    public void onSuccess(RESP_None obj) {
                        view.showShortToast(view.getActivity().getString(R.string.rate_action_success));
                        view.onRateSuccess();
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
                                            onRatesNews(id_news, rate_value);
                                        }

                                        @Override
                                        public void onError(com.xtel.nipservicesdk.model.entity.Error error) {
                                            Log.e("err callback", JsonHelper.toJson(error));
                                            view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                            view.startActivityAndFinish(LoginActivity.class);
                                        }
                                    });
                                } else {
                                    Log.e(TAG, "rate err " + JsonHelper.toJson(error));
                                    view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                }
                            } else {
                                Log.e(TAG, "Lỗi " + JsonHelper.toJson(error));
                            }
                        }
                    }
                });
            }
        } else {
            Log.e("Session", "null");
            view.showShortToast(view.getActivity().getString(R.string.need_login_to_rate_news));
            view.startActivityAndFinish(LoginActivity.class);
            return;
        }
    }

    public void onGetVoucher(final int id_news) {
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
                String url_voucher = Constants.SERVER_IVIP + "v0.1/news/" + id_news + "/voucher";
                Log.e("Voucher url", url_voucher);

                HomeModel.getInstance().getNewsVoucher(url_voucher, session, new ResponseHandle<RESP_Voucher>(RESP_Voucher.class) {
                    @Override
                    public void onSuccess(RESP_Voucher obj) {
                        view.onGetVoucherSuccess(obj);
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
                                            onGetVoucher(id_news);
                                        }

                                        @Override
                                        public void onError(Error error) {
                                            view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                            view.startActivityAndFinish(LoginActivity.class);
                                        }
                                    });
                                } else {
                                    view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), code, null));
                                    Log.e(TAG, "Loi getVoucher" + JsonHelper.toJson(error));
                                }
                            } else {
                                Log.e(TAG, "Loi " + JsonHelper.toJson(error));
                                view.showShortToast("Co loi");
                            }
                        }
                    }
                });
            }
        } else {
            view.showShortToast(view.getActivity().getString(R.string.need_login_to_get_voucher));
            view.startActivityAndFinish(LoginActivity.class);
        }
    }

    public void showQrCode(String url_qr) {
        if (url_qr != null) {
            view.onShowQrCode(url_qr);
        }
    }

    public void showBarCode(String url_qr) {
        if (url_qr != null) {
            view.onShowBarCode(url_qr);
        }
    }
}
