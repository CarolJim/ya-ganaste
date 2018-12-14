package com.pagatodo.yaganaste.modules.qr;

import android.app.Activity;

import com.pagatodo.yaganaste.modules.qr.operations.QrOperationActivity;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;

public class QrManagerRouter implements QrManagerContracts.Router {

    private TabActivity activity;

    public QrManagerRouter(TabActivity activity) {
        this.activity = activity;
    }

    @Override
    public void showOperation(int idFragment) {
        this.activity.startActivity(QrOperationActivity.createIntent(activity,idFragment));
    }
}
