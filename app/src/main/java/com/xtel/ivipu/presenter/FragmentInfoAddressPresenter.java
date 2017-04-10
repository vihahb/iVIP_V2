package com.xtel.ivipu.presenter;

import android.os.Handler;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.xtel.ivipu.R;
import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.RESP.RESP_Address_Arr;
import com.xtel.ivipu.model.RESP.RESP_NewsObject;
import com.xtel.ivipu.model.RESP.RESP_Router;
import com.xtel.ivipu.model.entity.Steps;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.fragment.inf.IFragmentAddressView;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ICmd;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.commons.NetWorkInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivhp on 3/1/2017.
 */

public class FragmentInfoAddressPresenter {

    private IFragmentAddressView view;
    private String session = LoginManager.getCurrentSession();

    private ICmd iCmd = new ICmd() {
        @Override
        public void execute(Object... params) {
            final double from_lat = (double) params[0];
            final double from_lng = (double) params[1];
            HomeModel.getInstance().getPolyLine(from_lat, from_lng, (double) params[2], (double) params[3], new ResponseHandle<RESP_Router>(RESP_Router.class) {
                @Override
                public void onSuccess(RESP_Router obj) {
                    if (obj != null) {
                        LatLng latLng = new LatLng(from_lat, from_lng);
                        PolylineOptions polylineOptions = getPolylineOption(obj.getRoutes().get(0).getLegs().get(0).getSteps());

                        if (polylineOptions != null)
                            view.onGetPolylineSuccess(latLng, polylineOptions);
                        else
                            view.onGetPolyLineError(new Error(-4, view.getActivity().getString(R.string.error), view.getActivity().getString(R.string.error_can_not_get_polyline)));
                    }
                }

                @Override
                public void onError(Error error) {
                    view.onGetPolyLineError(error);
                }
            });
        }
    };

    public FragmentInfoAddressPresenter(IFragmentAddressView view) {
        this.view = view;
    }

    public void getAddress(final int id_news, final String type) {

        if (!NetWorkInfo.isOnline(view.getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.onNetworkDisable();
                }
            }, 500);
            return;
        } else {
            String url_adress = Constants.SERVER_IVIP + "v0.1/address/" + id_news + "?type=" + type;
            Log.e("Get Address", url_adress);
            HomeModel.getInstance().getAddress(url_adress, session, new ResponseHandle<RESP_Address_Arr>(RESP_Address_Arr.class) {
                @Override
                public void onSuccess(RESP_Address_Arr obj) {
                    view.onGetAddressSuccess(obj.getData());
                }

                @Override
                public void onError(Error error) {
                    if (error != null) {
                        int code = error.getCode();
                        if (code == 2) {
                            CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                                @Override
                                public void onSuccess(RESP_Login success) {
                                    getAddress(id_news, type);
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

    public void getPolyLine(final double from_lat, final double from_lng, double to_lat, double to_lng) {
        iCmd.execute(from_lat, from_lng, to_lat, to_lng);
    }

    private PolylineOptions getPolylineOption(ArrayList<Steps> steps) {
        try {
            PolylineOptions polylineOptions = new PolylineOptions();

            for (int i = 0; i < steps.size(); i++) {
                List<LatLng> poly = Constants.decodePoly(steps.get(i).getPolyline().getPoints());

                for (int j = 0; j < poly.size(); j++) {
                    polylineOptions.add(poly.get(j));
                }
            }

            return polylineOptions;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
