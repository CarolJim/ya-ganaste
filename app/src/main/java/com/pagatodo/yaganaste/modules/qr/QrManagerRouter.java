package com.pagatodo.yaganaste.modules.qr;

import android.app.Activity;

import com.pagatodo.yaganaste.modules.data.QrItem;
import com.pagatodo.yaganaste.modules.data.QrItems;
import com.pagatodo.yaganaste.modules.qr.operations.QrOperationActivity;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;

import static com.pagatodo.yaganaste.modules.qr.operations.QrOperationActivity.ID_DETAIL_QR;

public class QrManagerRouter implements QrManagerContracts.Router {

    private TabActivity activity;

    QrManagerRouter(TabActivity activity) {
        this.activity = activity;
    }

    @Override
    public void showOperation(int idFragment) {
        this.activity.startActivity(QrOperationActivity.createIntent(activity,idFragment));
    }

    @Override
    public void showOperationDetail(QrItems item) {
        this.activity.startActivity(QrOperationActivity.createIntent(activity,item,ID_DETAIL_QR));
    }
}
