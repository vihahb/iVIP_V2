package com.xtel.ivipu.view.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xtel.ivipu.R;
import com.xtel.sdk.callback.DialogListener;

import java.io.Serializable;

/**
 * Created by vivhp on 12/29/2016.
 */

public class BasicFragment extends Fragment {
    private ProgressDialog progressDialog;
    private Dialog dialog;

    public BasicFragment() {
    }

    protected void showLongToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    protected void showShortToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showProgressBar(boolean isTouchOutside, boolean isCancel, String title, String message) {
        progressDialog = new ProgressDialog(getContext(), R.style.AppCompatAlertDialogStyle);
        progressDialog.setCanceledOnTouchOutside(isTouchOutside);
        progressDialog.setCancelable(isCancel);

        if (title != null)
            progressDialog.setTitle(title);
        if (message != null)
            progressDialog.setMessage(message);

        progressDialog.show();
    }

    protected void closeProgressBar() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    protected void showMaterialDialog(boolean isTouchOutside, boolean isCancelable, String title, String message,
                                      String negative, String positive, final DialogListener dialogListener) {
        dialog = new Dialog(getContext(), R.style.Theme_Transparent);
        dialog.setContentView(R.layout.dialog_material);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(isTouchOutside);
        dialog.setCanceledOnTouchOutside(isCancelable);

        TextView txt_title = (TextView) dialog.findViewById(R.id.dialog_txt_title);
        TextView txt_message = (TextView) dialog.findViewById(R.id.dialog_txt_message);
        Button btn_negative = (Button) dialog.findViewById(R.id.dialog_btn_negative);
        Button btn_positive = (Button) dialog.findViewById(R.id.dialog_btn_positive);

        if (title == null)
            txt_title.setVisibility(View.GONE);
        else
            txt_title.setText(title);

        if (message == null)
            txt_message.setVisibility(View.GONE);
        else
            txt_message.setText(message);

        if (negative == null)
            btn_negative.setVisibility(View.GONE);
        else
            btn_negative.setText(negative);

        if (positive == null)
            btn_positive.setVisibility(View.GONE);
        else
            btn_positive.setText(positive);

        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialogListener.onClicked(null);
            }
        });

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialogListener.onCancel();
            }
        });

        if (dialog != null)
            dialog.show();
    }

    public void closeDialog() {
        if (dialog != null)
            dialog.dismiss();
    }

    protected void startActivity(Class clazz) {
        getActivity().startActivity(new Intent(getActivity(), clazz));
    }

    protected void startActivity(Class clazz, String key, Object object, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtra(key, (Serializable) object);
        getActivity().startActivity(intent);
    }

    protected void startActivityForResultValue(Class clazz, String key, String value_int, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtra(key, value_int);
        getActivity().startActivityForResult(intent, requestCode);
    }

    protected void startActivityForResultObject(Class clazz, String key, Object object, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtra(key, (Serializable) object);
        getActivity().startActivityForResult(intent, requestCode);
    }

    protected void startActivityForResultWithTransition(Class clazz, String key, Object object, int transition_name, View view, int id, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtra(key, (Serializable) object);
        String transitionName = getString(transition_name);
        View viewStart = view.findViewById(id);
        ActivityOptionsCompat activityCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        viewStart,
                        transitionName);
        ActivityCompat.startActivityForResult(getActivity(), intent, requestCode, activityCompat.toBundle());
    }

    protected void startActivityAndFinish(Class clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    protected void finishActivity() {
        getActivity().finish();
    }

    protected void finishActivityBeforeStartActivity(Class clazz) {
        getActivity().finishAffinity();
        getActivity().startActivity(new Intent(getActivity(), clazz));
    }
}
