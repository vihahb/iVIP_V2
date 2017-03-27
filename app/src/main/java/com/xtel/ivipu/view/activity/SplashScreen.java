package com.xtel.ivipu.view.activity;

import android.os.Bundle;
import android.os.Handler;

import com.xtel.ivipu.R;
import com.xtel.nipservicesdk.LoginManager;

public class SplashScreen extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                checkSession();
                startActivityFinish(HomeActivity.class);
            }
        }, 1000);
    }

    private void checkSession() {
        String session = LoginManager.getCurrentSession();
        if (session == null) {
            startActivityFinish(LoginActivity.class);
        } else {
            startActivityFinish(HomeActivity.class);
        }
    }
}
