package com.xtel.ivipu.view.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.xtel.ivipu.R;
import com.xtel.ivipu.view.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by vivhp on 2/28/2017.
 */

public class WidgetHelper {

    private static WidgetHelper instance;

    public static WidgetHelper getInstance() {
        if (instance == null) {
            instance = new WidgetHelper();
        }
        return instance;
    }

    public static void setContent2TextView(String content, TextView textView) {
        if (content != null) {
            textView.setText(content);
        }
    }

    public void setImageURL(RoundImage view, String url) {
        if (url == null || url.isEmpty())
            return;

        final String finalUrl = url.replace("https", "http").replace("9191", "9190");

        Picasso.with(MyApplication.context)
                .load(finalUrl)
                .noPlaceholder()
                .error(R.color.colorPrimary)
                .into(view, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("WidgetHelper", "load ok " + finalUrl);
                    }

                    @Override
                    public void onError() {
                        Log.e("WidgetHelper", "load error " + finalUrl);
                    }
                });
    }

    public void setImageWithBlury(final ImageView imageView, String url) {
        if (url == null || url.isEmpty())
            return;

        final String finalUrl = url.replace("https", "http").replace("9191", "9190");
        Picasso.with(MyApplication.context)
                .load(finalUrl)
                .placeholder(R.drawable.ic_action_name)
                .error(R.drawable.ic_action_name)
                .fit()
                .centerCrop()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        Blurry.with(MyApplication.context).from(bitmap).into(imageView);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    public void setSmallImageURL(ImageView view, String url) {
        if (url == null || url.isEmpty())
            return;

        final String finalUrl = url.replace("https", "http").replace("9191", "9190");

        Picasso.with(MyApplication.context)
                .load(finalUrl)
                .noPlaceholder()
                .error(R.color.colorPrimary)
                .fit()
                .centerCrop()
                .into(view, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("WidgetHelper", "load ok " + finalUrl);
                    }

                    @Override
                    public void onError() {
                        Log.e("WidgetHelper", "load error " + finalUrl);
                    }
                });
    }

    public void setAvatarImageURL(ImageView view, String url) {
        if (url == null || url.isEmpty())
            return;

        final String finalUrl = url.replace("https", "http").replace("9191", "9190");

        Picasso.with(MyApplication.context)
                .load(finalUrl)
                .noPlaceholder()
                .error(R.mipmap.ic_user)
                .fit()
                .centerCrop()
                .into(view, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("WidgetHelper", "load ok " + finalUrl);
                    }

                    @Override
                    public void onError() {
                        Log.e("WidgetHelper", "load error " + finalUrl);
                    }
                });
    }

    public void setImageResource(ImageView view, int resource) {
        view.setImageResource(resource);
    }

    public void setViewBackground(View view, int resource) {
        view.setBackgroundResource(resource);
    }

    public void setEditTextNoResult(EditText view, String content) {
        view.setText(content);
    }

    public void setEditTextWithResult(EditText view, String content, String result) {
        if (content == null || content.isEmpty())
            view.setHint(result);
        else
            view.setText(content);
    }

    public void setEditTextDate(EditText view, long milliseconds) {
        if (milliseconds != 0)
            view.setText(convertLong2Time(milliseconds));
    }

    public void setTextViewDate(TextView view, String content, long milliseconds) {
        if (milliseconds == 0)
            view.setText((content + MyApplication.context.getString(R.string.updating)));
        else
            view.setText((content + convertLong2Time(milliseconds)));
    }

    public void setTextViewNoResult(TextView view, String content) {
        view.setText(content);
    }

    public void setTextViewNoResult(TextView view, String title, String content) {
        view.setText((title + ": " + content));
    }

    public void setTextViewWithResult(TextView view, String content, String result) {
        if (content == null || content.isEmpty())
            view.setText(result);
        else
            view.setText(content);
    }


//    private String getDate(long milliseconds) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(milliseconds);
//
//        int mYear = calendar.get(Calendar.YEAR);
//        int mMonth = calendar.get(Calendar.MONTH) + 1;
//        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
//
//        String day;
//        if (mDay < 10)
//            day = "0" + mDay;
//        else
//            day = String.valueOf(mDay);
//
//        String month;
//        if (mMonth < 10)
//            month = "0" + mMonth;
//        else
//            month = String.valueOf(mMonth);
//
//        return day + "-" + month + "-" + mYear;
//    }

    public String convertLong2Time(long time) {
//        long time_set = time * 10000;
//        Date date = new Timestamp(time_set);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC+7"));
//        String formatTime = dateFormat.format(date);
        Date date = new Date(time * 1000);
        SimpleDateFormat formatTime = new SimpleDateFormat("dd-MM-yyyy");
        formatTime.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        return formatTime.format(date);
    }

    public String convertLong2TimeWithHour(long time) {
//        long time_set = time * 10000;
//        Date date = new Timestamp(time_set);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC+7"));
//        String formatTime = dateFormat.format(date);
        Date date = new Date(time * 1000);
        SimpleDateFormat formatTime = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        formatTime.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        return formatTime.format(date);
    }

    public void showAlertNetwork(Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.TimePicker);
        dialog.setTitle("Kết nối không thành công");
        dialog.setMessage("Rất tiếc, không thể kết nối internet. Vui lòng kiểm tra kết nối Internet.");
        dialog.setPositiveButton("Cài đặt", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyApplication.context.startActivity(intent);
                //get gps
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
            }
        });
        dialog.show();
    }

    public String mapping_Char(String LocalChar) {
        String uniChars = "àáảãạâầấẩẫậăằắẳẵặèéẻẽẹêềếểễệđ" + "îìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵÀÁẢÃẠÂẦẤẨẪẬĂẰẮẲ" + "ẴẶÈÉẺẼẸÊỀẾỂỄỆĐÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴÂĂĐÔƠƯ";
        String noneChars = "aaaaaaaaaaaaaaaaaeeeeeeeeeeediiiiiio" + "oooooooooooooooouuuuuuuuuuuyyyyyAAAAAAAAAAAAAA" + "AAAEEEEEEEEEEEDIIIIIOOOOOOOOOOOOOOOOOUUUUUUUUUUUYYYYYAADOOU";
        if (LocalChar == null) {
            return LocalChar;
        }
        String ret = "";
        for (int i = 0; i < LocalChar.length(); i++) {
            int pos = uniChars.indexOf(LocalChar.charAt(i));
            if (pos >= 0) {
                ret += noneChars.charAt(pos);
            } else {
                ret += LocalChar.charAt(i);
            }
        }
        return ret;
    }

    public void setUnderLine(String content, TextView textView) {
        String mystring = new String(content);
        SpannableString underLineString = new SpannableString(mystring);
        underLineString.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        textView.setText(content);
    }
}
