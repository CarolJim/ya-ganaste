package com.pagatodo.yaganaste.utils;
import android.app.ProgressDialog;
import android.content.Context;
/**
 * Created by flima on 21/02/2017.
 */

public class ProgressIndicator {

    public static void showProgress(Context c,String title,String msg,ProgressDialog mProgressDialog) {

        mProgressDialog = new ProgressDialog(c);
        mProgressDialog.setMessage("Loading");
        mProgressDialog.show(c, title, msg);
    }

    public static void dismissProgress(ProgressDialog mProgressDialog) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
