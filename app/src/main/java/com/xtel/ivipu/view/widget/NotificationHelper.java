package com.xtel.ivipu.view.widget;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.xtel.ivipu.R;
import com.xtel.ivipu.view.MyApplication;
import com.xtel.ivipu.view.activity.HomeActivity;
import com.xtel.ivipu.view.activity.ProfileActivity;

/**
 * Created by vivhp on 3/21/2017.
 */

public class NotificationHelper {
    public static NotificationHelper instance;
    public Context context = MyApplication.context;
    public String PkgName = MyApplication.PACKAGE_NAME;
    NotificationManager notificationManager = (NotificationManager) MyApplication.context.getSystemService(Context.NOTIFICATION_SERVICE);
    int color = context.getResources().getColor(R.color.notification_color);
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    long[] vibrate = {500, 1000};
    int notifyCount = 0, newsCount = 0, news4meCount = 0, updateCount = 0;

    public static NotificationHelper getIntance() {
        if (instance == null) {
            instance = new NotificationHelper();
        }

        return instance;
    }

    public void toNotification(String title, String body) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("notification", "notification");
        notifyCount++;
        Log.e("Pkg_name notify", PkgName);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(color)
                .setSound(defaultSoundUri)
                .setVibrate(vibrate)
                .setNumber(notifyCount)
                .addAction(R.drawable.ic_no_send, "Mở thông báo", pendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1, notification);
    }

    public void toNews(String title, String body) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("notification", "news");
        newsCount++;
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(color)
                .setSound(defaultSoundUri)
                .setVibrate(vibrate)
                .setNumber(newsCount)
                .addAction(R.drawable.ic_no_send, "Bản tin mới nhất", pendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(2, notification);
    }

    public void toNewsForLocation(String title, String body) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("notification", "location");
        news4meCount++;
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(color)
                .setSound(defaultSoundUri)
                .setVibrate(vibrate)
                .setNumber(news4meCount)
                .addAction(R.drawable.ic_no_send, "Bản tin xung quanh", pendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(3, notification);
    }

    public void toUpdate(String title, String body, String content) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            intent.setData(Uri.parse("market://details?id=" + content));
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PkgName)));
        } catch (android.content.ActivityNotFoundException anfe) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + PkgName)));
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + content));
        }
        Log.e("Pkg_name notify", PkgName);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(color)
                .setSound(defaultSoundUri)
                .setVibrate(vibrate)
                .addAction(R.drawable.ic_no_send, "Cập nhật", pendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(4, notification);
    }

    public void toNotifyDefault(String title, String body) {
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(color)
                .setSound(defaultSoundUri)
                .setVibrate(vibrate)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(4, notification);
    }

}
