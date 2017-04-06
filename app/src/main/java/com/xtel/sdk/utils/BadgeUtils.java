package com.xtel.sdk.utils;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;

import com.xtel.sdk.dialog.BadgetItemCount;

/**
 * Created by vihahb on 1/13/2017.
 */

public class BadgeUtils {

    public static void setBadgeCount(Context context, LayerDrawable icon, int count) {
        BadgetItemCount badgetItemCount;

        //Reuse drawable if possible
//        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
//        if (reuse != null && reuse instanceof BadgetItemCount) {
//            badgetItemCount = (BadgetItemCount) reuse;
//        } else {
//            badgetItemCount = new BadgetItemCount(context);
//        }
//
//        badgetItemCount.setCount(count);
//        icon.mutate();
//        icon.setDrawableByLayerId(R.id.ic_badge, badgetItemCount);
    }

}
