package com.xtel.ivipu.view.fragment;

import android.app.Dialog;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.xtel.ivipu.R;

/**
 * Created by vivhp on 2/7/2017.
 */

public class IFragment extends BasicFragment {

    protected void showQrCode(String url) {
        final Dialog bottomSheetDialog = new Dialog(getContext(), R.style.MaterialDialogSheet);
        bottomSheetDialog.setContentView(R.layout.qr_code_bottom_sheet);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        bottomSheetDialog.getWindow().setGravity(Gravity.CENTER);

        Button txt_close = (Button) bottomSheetDialog.findViewById(R.id.dialog_txt_close);
        ImageView img_qr_code = (ImageView) bottomSheetDialog.findViewById(R.id.dialog_qr_code);
        url = url.replace("https", "http").replace("9191", "9190");
        Picasso.with(getContext())
                .load(url)
                .noPlaceholder()
                .error(R.mipmap.ic_error)
                .into(img_qr_code);

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
