package com.pagatodo.yaganaste.modules.qr.operations;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;

public class QrOperationActivity extends LoaderActivity {

    private static final String TAG_ID_FRAGMENT = "TAG_ID_FRAGMENT";
    public static final int ID_ADD_QR = 101;
    public static final int ID_GENERATE_NEW_QR = 102;
    public static final int ID_MY_SALES_QR = 103;
    private int idFragment;
    private QrOperationsRouter router;

    public static Intent createIntent(Activity activity, int tag){
        Intent intent = new Intent(activity,QrOperationActivity.class);
        intent.putExtra(TAG_ID_FRAGMENT,tag);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        router = new QrOperationsRouter(this);
        if (getIntent().getExtras() != null){
            idFragment = getIntent().getExtras().getInt(TAG_ID_FRAGMENT);
        }
        initView();
    }

    private void initView(){
        switch (idFragment){
            case ID_ADD_QR:
                router.showScanQR(Direction.NONE);
                break;
            case ID_GENERATE_NEW_QR:
                router.showGenerateQR(Direction.NONE);
                break;
            case ID_MY_SALES_QR:
                router.showMyMovementsQR(Direction.NONE);
                break;
        }
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }
}
