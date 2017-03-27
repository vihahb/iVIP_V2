package com.xtel.sdk.fcm;

import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xtel.ivipu.model.entity.MessageObj;
import com.xtel.ivipu.view.activity.HomeActivity;
import com.xtel.ivipu.view.activity.ProfileActivity;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.sdk.commons.Constants;

/**
 * Created by vivhp on 3/24/2017.
 */

public class NotificationManagerAction extends AppCompatActivity {

    protected final String TAG = "NotifyAction";
    protected final String CH_PLAY_URL = "https://play.google.com/store/apps/details?id=";
    protected final String CH_PLAY_URI = "market://details?id=";

    protected NotificationManager notificationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Log.e(TAG, "Go to Create method....");
        getDataFCM();
    }

    protected void getDataFCM() {
        MessageObj messageObj = null;
        Log.e(TAG, "Go to GetData FCM method....");
        try {
            messageObj = (MessageObj) getIntent().getExtras().getSerializable(Constants.NOTIFY_OBJ);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (messageObj != null) {
            checMessageNotify(messageObj);
        } else
            Log.e(TAG, "Data fcm null");
    }

    private void checMessageNotify(MessageObj messageObj) {
        Log.e("Check message", JsonHelper.toJson(messageObj));

        switch (messageObj.getAction()) {
            case 1:
                goToNotify(messageObj);
                break;
            case 2:
                goToNews(messageObj);
                break;
            case 3:
                goToNews4Location(messageObj);
                break;
            case 4:
                goToCHplay(messageObj);
                break;
            case 5:
                cancelNotification(messageObj.getAction());
                break;
        }
    }

    private void cancelNotification(int id) {
        try {
            notificationManager.cancel(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    protected void goToNews(MessageObj messageObj) {
        if (messageObj.getTitle() != null || messageObj.getBody() != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(Constants.NOTIFY_KEY, "news");
            startActivity(intent);
            cancelNotification(messageObj.getAction());
        } else {
            cancelNotification(messageObj.getAction());
        }
    }

    protected void goToNotify(MessageObj messageObj) {
        if (messageObj.getTitle() != null || messageObj.getBody() != null) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra(Constants.NOTIFY_KEY, "notification");
            startActivity(intent);
            cancelNotification(messageObj.getAction());
        } else {
            cancelNotification(messageObj.getAction());
        }
    }

    protected void goToNews4Location(MessageObj messageObj) {
        if (messageObj.getTitle() != null || messageObj.getBody() != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(Constants.NOTIFY_KEY, "location");
            startActivity(intent);
            cancelNotification(messageObj.getAction());
        } else {
            cancelNotification(messageObj.getAction());
        }
    }

    protected void goToCHplay(MessageObj messageObj) {
        if (messageObj.getContent() != null) {
            String uri_app = CH_PLAY_URL + messageObj.getContent();
            Log.e("uri_app", uri_app);
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(CH_PLAY_URI + messageObj.getContent())));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(CH_PLAY_URL + messageObj.getContent())));
            }
            cancelNotification(messageObj.getAction());
        } else {
            cancelNotification(messageObj.getAction());
        }
    }
}
