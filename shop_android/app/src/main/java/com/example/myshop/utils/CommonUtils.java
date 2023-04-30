package com.example.myshop.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.example.myshop.R;

public class CommonUtils {
    private static final String TAG = CommonUtils.class.getSimpleName();
    static ProgressDialog progressDialog;
    private static Context context;

    public static void setContext(Context context) {
        CommonUtils.context=context;
    }
    public static ProgressDialog showLoading() {
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if(progressDialog.getWindow()!=null)
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
    public static void hideLoading() {
        if(progressDialog==null)
            return;
        progressDialog.dismiss();
    }
}
