package com.xtel.ivipu.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;
import com.xtel.ivipu.R;
import com.xtel.ivipu.model.RESP.RESP_Checkin;
import com.xtel.ivipu.presenter.ScannerQrPresenter;
import com.xtel.ivipu.view.activity.inf.IScannerView;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.PermissionHelper;
import com.xtel.sdk.callback.DialogListener;
import com.xtel.sdk.commons.NetWorkInfo;
import com.xtel.sdk.utils.GPSTracker;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

/**
 * Created by vivhp on 2/14/2017.
 */

public class QrCheckIn extends BasicActivity implements ZXingScannerView.ResultHandler, IScannerView {

    private static final int REQUEST_PERMISSION_ALL = 2;
    double latitude_location, longitude_location;
    boolean isGPSEnabled = true;
    String session = LoginManager.getCurrentSession();
    private ZXingScannerView mZXingScannerView;
    private ScannerQrPresenter presenter;
    private String[] permission = new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private ViewGroup frameContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate", "onCreate QrCodeScanner");
        presenter = new ScannerQrPresenter(this);
        setContentView(R.layout.layout_scanner_qr_bar);
        initToolBar(R.id.scanqr_toolbar, getString(R.string.activity_checkin_content));
        initView();
        initScannerView();
        checkGPSisEnabled();

        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
            } else {
                requestPermission();
            }
        } else {

        }
    }

    private boolean checkGPS() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Call your Alert message
            isGPSEnabled = false;
        }
        return isGPSEnabled;
    }

    private void checkGPSisEnabled() {
        if (!NetWorkInfo.isOnline(this)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.TimePicker);
            dialog.setTitle("Kết nối không thành công");
            dialog.setMessage("Rất tiếc, không thể kết nối internet. Vui lòng kiểm tra kết nối Internet.");
            dialog.setPositiveButton("Cài đặt", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
            dialog.show();
        } else if (!checkGPS()) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.TimePicker);
            dialog.setTitle("GPS chưa được kích hoạt");
            dialog.setMessage("Rất tiếc, không thể xác định vị trí của bạn. Vui lòng kiểm tra dịch vụ GPS trong Cài Đặt.");
            dialog.setPositiveButton("Cài đặt", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
            dialog.show();
        } else {
            initMyLocation();
        }
    }

    private void initMyLocation() {
        GPSTracker gpsTracker = new GPSTracker(this);
        latitude_location = gpsTracker.getLatitude();
        longitude_location = gpsTracker.getLongitude();
        Log.e("my location", "Lat: " + latitude_location + ", Lng: " + longitude_location);
    }

    private void initScannerView() {
        mZXingScannerView = new ZXingScannerView(getApplicationContext()) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {
                return super.createViewFinderView(context);
            }
        };
        frameContent.addView(mZXingScannerView);
    }

    private void initView() {
        frameContent = (ViewGroup) findViewById(R.id.scanqr_content);
        if (session == null) {
            showShortToast(getString(R.string.need_login_to_check_in));
            startActivityFinish(LoginActivity.class);
        }
    }

    private void initToolBar(int id, String title) {
        Toolbar toolbar = (Toolbar) findViewById(id);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (title != null)
            actionBar.setTitle(title);
    }


    private void requestPermission() {
        PermissionHelper.checkListPermission(permission, this, REQUEST_PERMISSION_ALL);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_ALL:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean fineLocationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean coasLocationAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && fineLocationAccepted && coasLocationAccepted) {
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                    requestPermissions(new String[]{CAMERA},
//                                                            REQUEST_CAMERA);
                                                    requestPermission();
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(QrCheckIn.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean checkPermission() {
        boolean check_done = true;
        if (!(ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            check_done = false;
        } else if (!(ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            check_done = false;
        } else if (!(ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            check_done = false;
        }
        return check_done;
    }

    @Override
    public void handleResult(Result rawResult) {
        final String result = rawResult.getText();
        Log.d("QRCodeScanner", rawResult.getText());
        Log.d("QRCodeScanner", rawResult.getBarcodeFormat().toString());
        presenter.checkInStore(result, latitude_location, longitude_location);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (mZXingScannerView == null) {
                    mZXingScannerView = new ZXingScannerView(this);
                    setContentView(mZXingScannerView);
                }
                mZXingScannerView.setResultHandler(this);
                mZXingScannerView.startCamera();
            } else {
                requestPermission();
            }
        } else {
            if (mZXingScannerView == null) {
                mZXingScannerView = new ZXingScannerView(this);
                setContentView(mZXingScannerView);
            } else {
                mZXingScannerView.resumeCameraPreview(this);
            }
            mZXingScannerView.setResultHandler(this);
            mZXingScannerView.startCamera();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStartChecking() {
    }

    @Override
    public void onResumeScanner() {
        mZXingScannerView.resumeCameraPreview(QrCheckIn.this);
    }

    @Override
    public void showDialogNotification(String mes, String content) {
        showNotification(mes, content);
    }

    @Override
    public void onCheckinSuccess(RESP_Checkin respCheckin) {
        Log.e("Chekin obj", JsonHelper.toJson(respCheckin));
        String content;
        String reward;
        String shopIcon;
        if (respCheckin != null) {
            content = "Bạn đã checkin thành công tại cửa hàng " + respCheckin.getStore_name() + ", bạn nhận được: ";
            reward = String.valueOf(respCheckin.getPoint());
            shopIcon = respCheckin.getStore_logo();
            showDialogCheckIn(shopIcon, content, reward);
        }
    }

    private void showDialogCheckIn(String url_icon, String content, String reward) {
        showDialogCheckinNotification(this, url_icon, getString(R.string.action_checkin_success), content, reward, new DialogListener() {
            @Override
            public void onClicked(Object object) {
                finish();
            }

            @Override
            public void onCancel() {
                finish();
            }
        });
    }

    private void showNotification(String title, String mes) {
        showDialogNotification(title, mes, new DialogListener() {
            @Override
            public void onClicked(Object object) {
                onResumeScanner();
            }

            @Override
            public void onCancel() {
                finish();
            }
        });
    }

    @Override
    public void onCheckinError(Error error) {
        if (error != null)
            showShortToast(error.getMessage());
    }

    @Override
    public void startActivityFinish(Class clazz) {
        super.startActivityFinish(clazz);
    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onNetworkDisable() {
        WidgetHelper.getInstance().showAlertNetwork(this);
    }
}
