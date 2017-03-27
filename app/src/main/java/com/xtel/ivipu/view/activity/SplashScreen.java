package com.xtel.ivipu.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xtel.ivipu.R;
import com.xtel.nipservicesdk.LoginManager;

public class SplashScreen extends BasicActivity {

    ImageView img_welcome;
    LinearLayout ln_welc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                checkSession();
                startActivityFinish(HomeActivity.class);
            }
        }, 1000);
    }
//    private void initView() {
//        img_welcome = (ImageView) findViewById(R.id.img_welc);
//        setBlury();
//    }
//
//    private void setBlury() {
//        Bitmap bitmap = ((BitmapDrawable) img_welcome.getDrawable()).getBitmap();
//        Blurry.with(MyApplication.context).from(bitmap).into(img_welcome);
//    }


    private void checkSession() {
        String session = LoginManager.getCurrentSession();
        if (session == null) {
            startActivityFinish(LoginActivity.class);
        } else {
            startActivityFinish(HomeActivity.class);
        }
    }
}
