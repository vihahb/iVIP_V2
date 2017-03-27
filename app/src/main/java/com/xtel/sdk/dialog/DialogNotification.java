package com.xtel.sdk.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xtel.ivipu.R;
import com.xtel.ivipu.view.widget.RoundImage;

/**
 * Created by Vũ Hà Vi on 10/26/2016.
 */

public class DialogNotification {
    private static Dialog dialog;
    private Context context;
    private Button btn_ok;

    public DialogNotification(Context context) {
        this.context = context;
    }

    //    Nếu không có giá trị thì truyền vào null
    //    VD: không có title thì truyền vào title là null
    public void showDialog(String title, String content, String button) {
        dialog = new Dialog(context, R.style.Theme_Transparent);
        dialog.setContentView(R.layout.dialog_notification);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        TextView txt_title = (TextView) dialog.findViewById(R.id.txt_dialog_notification_title);
        TextView txt_content = (TextView) dialog.findViewById(R.id.txt_dialog_notification_content);
        btn_ok = (Button) dialog.findViewById(R.id.btn_dialog_notification_ok);

        if (title == null)
            txt_title.setVisibility(View.GONE);
        else
            txt_title.setText(title);

        if (content == null)
            txt_content.setVisibility(View.GONE);
        else
            txt_content.setText(content);

        if (button == null)
            btn_ok.setVisibility(View.GONE);
        else
            btn_ok.setText(button);

        dialog.show();
    }

    public void showCheckinSuccessDialog(Context context, String url_icon, String title, String content, String reward, String button) {
        dialog = new Dialog(context, R.style.Theme_Transparent);
        dialog.setContentView(R.layout.dialog_checkin_success);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        RoundImage img_brand = (RoundImage) dialog.findViewById(R.id.img_brand_store);
        TextView txt_title = (TextView) dialog.findViewById(R.id.txt_dialog_notification_title);
        TextView txt_content = (TextView) dialog.findViewById(R.id.txt_dialog_notification_content);
        TextView txt_reward = (TextView) dialog.findViewById(R.id.txt_dialog_notification_reward);
        btn_ok = (Button) dialog.findViewById(R.id.btn_dialog_notification_ok);

        if (url_icon == null) {
            img_brand.setVisibility(View.GONE);
        } else {
            setIconFromUrl(context, url_icon, img_brand);
        }

        if (title == null)
            txt_title.setVisibility(View.GONE);
        else
            txt_title.setText(title);

        if (content == null)
            txt_content.setVisibility(View.GONE);
        else
            txt_content.setText(content);

        if (reward == null)
            txt_reward.setVisibility(View.GONE);
        else
            txt_reward.setText(reward);

        if (button == null)
            btn_ok.setVisibility(View.GONE);
        else
            btn_ok.setText(button);

        dialog.show();
    }

    private void setIconFromUrl(Context context, String url, RoundImage roundImage) {
        Picasso.with(context)
                .load(url)
                .placeholder(R.mipmap.icon_success)
                .error(R.mipmap.icon_success)
                .into(roundImage);
    }

    public void setOnButtonClicked(View.OnClickListener onClickListener) {
        btn_ok.setOnClickListener(onClickListener);
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.dismiss();
    }
}