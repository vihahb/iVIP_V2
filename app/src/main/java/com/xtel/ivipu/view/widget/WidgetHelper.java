package com.xtel.ivipu.view.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.Html;
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
import com.xtel.sdk.utils.PicassoImageGetter;
import com.xtel.sdk.utils.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by vivhp on 2/28/2017.
 */

public class WidgetHelper {

    private static WidgetHelper instance;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
    private final SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
    private String TAG = "Widget Helper";

    /*
    * Cấn sửa lại
    * tất cả các error của picasso thành ảnh ( không được set màu )
    * */

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

    public static void main(String[] args) {
        String test = "Hlxkgxyc&nbsp;<br><img src=\"http://124.158.5.112:9190/upload/store/files/2017/03/28/1490697533902@aVnXlthyg6.png\" alt=\"iVipBusiness\"><br>";
        String content = test.replaceAll("\\\"", "\"");
        System.out.print(content);
    }

    public void setImageURL(ImageView view, String url) {
        if (url == null || url.isEmpty())
            return;

        final String finalUrl = url.replace("https", "http").replace("9191", "9190");

        Picasso.with(MyApplication.context)
                .load(finalUrl)
                .noPlaceholder()
                .fit().centerInside()
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

    public void setTextViewDateWithHour(TextView view, String content, long milliseconds) {
        if (milliseconds == 0)
            view.setText((content + MyApplication.context.getString(R.string.updating)));
        else
            view.setText((content + convertLong2TimeWithHour(milliseconds)));
    }

    public void setTextViewNoResult(TextView view, String content) {
        view.setText(content);
    }

    public void setTextViewFromHtml(TextView view, String content) {
        view.setText(Html.fromHtml(content));
    }

    public void setTextViewFromHtmlWithImage(TextView view, String content) {
        String content_replace = content.replace("\\\"", "\"");
        view.setText(Html.fromHtml(content_replace, new PicassoImageGetter(view), null));
        Log.e(TAG, "asdasdasf " + content_replace);
    }

    public void setTextViewNoResult(TextView view, String title, String content) {
        view.setText((title + ": " + content));
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

    public void setTextViewWithResult(TextView view, String content, String result) {
        if (content == null || content.isEmpty())
            view.setText(result);
        else
            view.setText(content);
    }

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
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm dd/MM/yyyy");
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

    public void comparingTime(TextView textView, long time_get) {
//        int c_day;
//        int c_hour;
//        int c_minutes;
//        int c_month;
//        int c_year;
//        String time_now;
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        Calendar calendar = Calendar.getInstance();
//        c_minutes = calendar.get(Calendar.MINUTE);
//        c_hour = calendar.get(Calendar.HOUR_OF_DAY);
//        c_day = calendar.get(Calendar.DAY_OF_MONTH);
//        c_month = calendar.get(Calendar.MONTH);
//        c_year = calendar.get(Calendar.YEAR);
//        time_now = c_day + "/" + c_month + "/" + c_year;
//
//        //Current date
//        Date date_now = new Date();
//        try {
//            date_now = simpleDateFormat.parse(time_now);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Log.e(TAG, String.valueOf(date_now));
//
//        //Date get from server
//        Date date_get = new Date();
//        try {
//            date_get = simpleDateFormat.parse(convertLong2Time(time_get));
//            Log.e(TAG, String.valueOf(date_get));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Log.e(TAG, "Time comparing week" + TimeUtil.getInstance().betweenTime(date_get, date_now).getWeek());
//        Log.e(TAG, "Time comparing day" + TimeUtil.getInstance().betweenTime(date_get, date_now).getDay());
//        Log.e(TAG, "Time comparing hour" + TimeUtil.getInstance().betweenTime(date_get, date_now).getHours());
//        Log.e(TAG, "Time comparing minus" + TimeUtil.getInstance().betweenTime(date_get, date_now).getMinute());

        Date create_time = new Date(time_get * 1000);

        TimeUtil.TimeBetween tw = TimeUtil.getInstance().betweenTime(create_time, new Date());

        String txt = null;
        long value = 0;
        if (tw.getDay() > 7) {
            txt = String.format("%s lúc %s", sdf.format(create_time), sdf1.format(create_time));
        } else if (tw.getDay() == 7) {
            txt = "1 tuần trước";
        } else if ((value = tw.getDay()) >= 1) {
            txt = value + " ngày trước";
        } else if ((value = tw.getHours()) > 0) {
            txt = value + " giờ trước";
        } else if ((value = tw.getMinute()) > 0) {
            txt = value + " phút trước trước";
        } else {
            txt = "vừa xong";
        }
        textView.setText(txt);
    }

    public void setTimeComparingCheckin(TextView textView, long time) {
        Date create_time = new Date(time * 1000);
        TimeUtil.TimeBetween tbw = TimeUtil.getInstance().betweenTime(create_time, new Date());
        String txt = null;
        long value = 0;

        if (tbw.getDay() > 7) {
            txt = String.format("%s lúc %s", sdf.format(create_time), sdf1.format(create_time));
        } else if (tbw.getDay() == 7) {
            txt = "1 tuần trước";
        } else if ((value = tbw.getDay()) > 1) {
            txt = value + " ngày trước";
        } else if ((value = tbw.getHours()) == 1) {
            txt = "Hôm nay";
        }

        textView.setText(txt);
    }

}
