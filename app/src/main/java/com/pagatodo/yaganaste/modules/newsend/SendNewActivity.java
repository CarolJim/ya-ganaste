package com.pagatodo.yaganaste.modules.newsend;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.data.QrItems;
import com.pagatodo.yaganaste.modules.emisor.PaymentToQR.operations.QrOperationActivity;
import com.pagatodo.yaganaste.modules.emisor.PaymentToQR.operations.QrOperationsRouter;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;

public class SendNewActivity extends LoaderActivity implements  SendNewContracts.Presenter , SendNewContracts.Listener  {
    private  static  final String TAG_ID_FRAGMENT = "TAG_ID_FRAGMENT";
    public static final int ID_ALL_FAVO = 101;

    SendNewRouter router ;
    int idFragment;

    public static Intent createIntent(Activity activity, int tag){
        Intent intent = new Intent(activity, QrOperationActivity.class);
        intent.putExtra(TAG_ID_FRAGMENT,tag);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new);
        router = new SendNewRouter(this);
        if (getIntent().getExtras() != null){
            idFragment = getIntent().getExtras().getInt(TAG_ID_FRAGMENT);
        }
        initViews();
    }

    @Override
    public boolean requiresTimer() {
        return false;
    }
    @Override
    public void initViews() {
        switch (idFragment) {
            case ID_ALL_FAVO:
                router.showAllFavorites(Direction.FORDWARD);
                break;

        }



    }
}
