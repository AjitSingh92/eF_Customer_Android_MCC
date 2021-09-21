package com.easyfoodcustomer.utility;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.easyfoodcustomer.R;

import java.util.ArrayList;
import java.util.List;


public class MyDialogUtils {

    /**
     * get progress dialog
     *
     * @param context current application context
     * @return dialog
     */
    private static List<Dialog> dialogList = new ArrayList<>();




    public static void showAlertDialog(Context context, String message, final OnDialogClickListener onDialogClickListener) {
        dismissAll();
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_alert_dialog);
        TextView tvMessaage = dialog.findViewById(R.id.tvMessage);
        tvMessaage.setText(message);
        if (dialog.getWindow() != null) {
            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
            layoutParams.dimAmount = 0.7f;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.getWindow().setAttributes(layoutParams);
            dialog.getWindow().setWindowAnimations(R.style.AnimationCenterPopUp);
            dialogList.add(dialog);
            dialog.show();
            dialog.findViewById(R.id.btnOkay).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (onDialogClickListener != null)
                        onDialogClickListener.onPositiveClick();
                }
            });
            dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }

    public static void showAlertDialog(Context context, String message) {
        dismissAll();
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_alert_dialog);
        TextView tvMessaage = dialog.findViewById(R.id.tvMessage);
        tvMessaage.setText(message);

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.dimAmount = 0.7f;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setWindowAnimations(R.style.AnimationCenterPopUp);
        dialogList.add(dialog);
        dialog.show();
        dialog.findViewById(R.id.btnOkay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnCancel).setVisibility(View.GONE);
    }

    public static void showAlertDialogWithSingleButton(Context context, String message, final OnDialogClickListener onDialogClickListener) {
        dismissAll();
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_alert_dialog);
        TextView tvMessaage = dialog.findViewById(R.id.tvMessage);
        tvMessaage.setText(message);
        dialog.findViewById(R.id.btnCancel).setVisibility(View.GONE);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.dimAmount = 0.7f;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setWindowAnimations(R.style.AnimationCenterPopUp);
        dialogList.add(dialog);
        dialog.show();
        dialog.findViewById(R.id.btnOkay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (onDialogClickListener != null)
                    onDialogClickListener.onPositiveClick();
            }
        });
    }

    public static void dismiss(Dialog dialog) {
        dialogList.remove(dialog);
        dialog.dismiss();
    }


    public static void dismissAll() {
        for (Dialog dialog : dialogList) {
            dialog.dismiss();
        }
        dialogList.clear();
    }

}
