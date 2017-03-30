package com.xtel.ivipu.view.activity;

import android.app.Dialog;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xtel.ivipu.R;
import com.xtel.ivipu.view.widget.WidgetHelper;

/**
 * Created by vivhp on 1/24/2017.
 */

public abstract class IActivity extends BasicActivity {
    protected void showQrCode(String url) {
        final Dialog bottomSheetDialog = new Dialog(IActivity.this, R.style.MaterialDialogSheet);
        bottomSheetDialog.setContentView(R.layout.qr_code_bottom_sheet);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        bottomSheetDialog.getWindow().setGravity(Gravity.CENTER);

        Button txt_close = (Button) bottomSheetDialog.findViewById(R.id.dialog_txt_close);
        ImageView img_qr_code = (ImageView) bottomSheetDialog.findViewById(R.id.dialog_qr_code);

        WidgetHelper.getInstance().setImageURL(img_qr_code, url);

        if (txt_close != null)
            txt_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialog.dismiss();
                }
            });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomSheetDialog.show();
            }
        }, 200);
    }
}
