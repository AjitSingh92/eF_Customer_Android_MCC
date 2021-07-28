package com.easyfoodcustomer.utility;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.easyfoodcustomer.R;

public class DialogUtils {

    public  Dialog dialog;

    public DialogUtils(Context context) {

        dialog = new Dialog(context);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //dialog.dismiss();
            }
        });
    }


    public  void showDialog(String title)
    {
        if (dialog!=null)
        {
            dialog.setTitle(title);
            dialog.show();
        }

    }

    public  void closeDialog()
    {
        if (dialog!=null && dialog.isShowing())
        {
            dialog.dismiss();
        }
    }


}
