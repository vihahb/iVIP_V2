package com.xtel.sdk.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.MessageObj;
import com.xtel.ivipu.presenter.FCMPresenter;
import com.xtel.ivipu.view.MyApplication;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.fcm.inf.NotifyInterface;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by vihahb on 1/10/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService implements NotifyInterface {

    private static final String TAG = "MyAndroidFCMService";
    private static final Context CONTEXT = MyApplication.context;
    public String PkgName = MyApplication.PACKAGE_NAME;
    FCMPresenter presenter = new FCMPresenter(this);
    int action;
    String title, body, content;
    NotificationManager notificationManager = (NotificationManager) MyApplication.context.getSystemService(Context.NOTIFICATION_SERVICE);
    int color = CONTEXT.getResources().getColor(R.color.notification_color);
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    long[] vibrate = {500, 1000};
    int notifyCount = 0, newsCount = 0, news4meCount = 0, updateCount = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        String data = JsonHelper.toJson(remoteMessage.getData());
        // Check if message contains a data payload.
        MessageObj messageObj = JsonHelper.getObject(data, MessageObj.class);
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
            title = messageObj.getTitle();
            body = messageObj.getBody();
            action = messageObj.getAction();
            content = messageObj.getContent();
            presenter.getNotify();
            checkNotify(messageObj);
            Log.e("Data fcm", JsonHelper.toJson(messageObj));
        }
//        // Check if message contains a notification payload.
//        if (remoteMessage.getData() != null){
//            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
//
//        }


//        if (remoteMessage.getNotification() != null) {
//            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//
//            MessageObj message = JsonHelper.getObjectNoException(remoteMessage.getNotification().getBody(), MessageObj.class);
//            Log.e(TAG, String.valueOf(message));
//            if (message != null) {
//                toggleNotifications(message, 1);
//            }
////                sendNotification(message, 1);
//        }
//        Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
    }

    private void checkNotify(MessageObj messageObj) {
        switch (messageObj.getAction()) {
            case 1:
                toNotification(messageObj);
                break;
            case 2:
                toNews(messageObj);
                break;
            case 3:
                toNews4Location(messageObj);
                break;
            case 4:
                toUpdate(messageObj);
                break;
            case 5:
                toDisplay(messageObj);
                break;
            default:
                break;
        }
    }

    public void toNotification(MessageObj messageObj) {

        MessageObj noContent = new MessageObj(null, null, 1, null);

        Intent noReceive = new Intent(this, NotificationManagerAction.class);
        noReceive.putExtra(Constants.NOTIFY_OBJ, noContent);
        PendingIntent pendingIntentNoContent = PendingIntent.getActivity(this, 10, noReceive, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent = new Intent(this, NotificationManagerAction.class);
        intent.putExtra(Constants.NOTIFY_OBJ, messageObj);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 11, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap icon = BitmapFactory.decodeResource(CONTEXT.getResources(), R.mipmap.ivip_laucher);

        notifyCount++;
        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(messageObj.getTitle())
                .setContentText(messageObj.getBody())
                .setSmallIcon(R.mipmap.ivip_laucher)
                .setLargeIcon(icon)
                .setSound(defaultSoundUri)
                .setVibrate(vibrate)
                .setNumber(notifyCount)
                .addAction(R.drawable.ic_cancel, "Hủy", pendingIntentNoContent)
                .addAction(R.drawable.ic_done, "Truy cập thông báo", pendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1, notification);
    }

    public void toNews(MessageObj messageObj) {

        MessageObj noContent = new MessageObj(null, null, 2, null);

        Intent noReceive = new Intent(this, NotificationManagerAction.class);
        noReceive.putExtra(Constants.NOTIFY_OBJ, noContent);
        PendingIntent pendingIntentNoContent = PendingIntent.getActivity(this, 20, noReceive, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent = new Intent(this, NotificationManagerAction.class);
        intent.putExtra(Constants.NOTIFY_OBJ, messageObj);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 21, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap icon = BitmapFactory.decodeResource(CONTEXT.getResources(), R.mipmap.ivip_laucher);
        newsCount++;
        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(messageObj.getTitle())
                .setContentText(messageObj.getBody())
                .setSmallIcon(R.mipmap.ivip_laucher)
                .setLargeIcon(icon)
                .setSound(defaultSoundUri)
                .setVibrate(vibrate)
                .setNumber(notifyCount)
                .addAction(R.drawable.ic_cancel, "Hủy", pendingIntentNoContent)
                .addAction(R.drawable.ic_done, "Bản tin mới nhất", pendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(2, notification);
    }

    public void toNews4Location(MessageObj messageObj) {

        MessageObj noContent = new MessageObj(null, null, 3, null);

        Intent noReceive = new Intent(this, NotificationManagerAction.class);
        noReceive.putExtra(Constants.NOTIFY_OBJ, noContent);
        PendingIntent pendingIntentNoContent = PendingIntent.getActivity(this, 30, noReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap icon = BitmapFactory.decodeResource(CONTEXT.getResources(), R.mipmap.ivip_laucher);
        Intent intent = new Intent(this, NotificationManagerAction.class);
        intent.putExtra(Constants.NOTIFY_OBJ, messageObj);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 31, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        news4meCount++;
        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(messageObj.getTitle())
                .setContentText(messageObj.getBody())
                .setSmallIcon(R.mipmap.ivip_laucher)
                .setLargeIcon(icon)
                .setSound(defaultSoundUri)
                .setVibrate(vibrate)
                .setNumber(notifyCount)
                .addAction(R.drawable.ic_cancel, "Hủy", pendingIntentNoContent)
                .addAction(R.drawable.ic_done, "Bản tin gần đây", pendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(3, notification);
    }

    public void toUpdate(MessageObj messageObj) {

        MessageObj noContent = new MessageObj(null, null, 4, null);

        Intent noReceive = new Intent(this, NotificationManagerAction.class);
        noReceive.putExtra(Constants.NOTIFY_OBJ, noContent);
        PendingIntent pendingIntentNoContent = PendingIntent.getActivity(this, 40, noReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap icon = BitmapFactory.decodeResource(CONTEXT.getResources(), R.mipmap.ivip_laucher);
        Intent intent = new Intent(this, NotificationManagerAction.class);
        intent.putExtra(Constants.NOTIFY_OBJ, messageObj);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 41, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        updateCount++;
        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(messageObj.getTitle())
                .setContentText(messageObj.getBody())
                .setSmallIcon(R.mipmap.ivip_laucher)
                .setLargeIcon(icon)
                .setSound(defaultSoundUri)
                .setVibrate(vibrate)
                .setNumber(notifyCount)
                .addAction(R.drawable.ic_cancel, "Hủy", pendingIntentNoContent)
                .addAction(R.drawable.ic_update_store, "Cập nhật", pendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(4, notification);
    }

    public void toDisplay(MessageObj messageObj) {

        MessageObj noContent = new MessageObj(null, null, 5, null);

        Intent noReceive = new Intent(this, NotificationManagerAction.class);
        noReceive.putExtra(Constants.NOTIFY_OBJ, noContent);
        PendingIntent pendingIntentNoContent = PendingIntent.getActivity(this, 40, noReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap icon = BitmapFactory.decodeResource(CONTEXT.getResources(), R.mipmap.ivip_laucher);
//        Intent intent = new Intent(this, NotificationManagerAction.class);
//        intent.putExtra(Constants.NOTIFY_OBJ, messageObj);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 41, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        updateCount++;
        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(messageObj.getTitle())
                .setContentText(messageObj.getBody())
                .setSmallIcon(R.mipmap.ivip_laucher)
                .setLargeIcon(icon)
                .setSound(defaultSoundUri)
                .setVibrate(vibrate)
                .setNumber(notifyCount)
                .addAction(R.drawable.ic_cancel, "Đóng", pendingIntentNoContent)
//                .addAction(R.drawable.ic_done, "Cập nhật", pendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(5, notification);
    }

    @Override
    public void getNotifySuccess(int notify) {
        Log.e("Notify count...", String.valueOf(notify));
        ShortcutBadger.applyCount(getBaseContext(), notify);
    }
}
