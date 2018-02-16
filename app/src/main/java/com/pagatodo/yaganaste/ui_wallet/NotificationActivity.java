package com.pagatodo.yaganaste.ui_wallet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui_wallet.fragments.NotificationHistoryFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.PaymentFormFragment;

import butterknife.ButterKnife;

public class NotificationActivity extends LoaderActivity implements View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);
            loadFragment(NotificationHistoryFragment.newInstance(), R.id.fragment_container);

    }

    @Override
    public boolean requiresTimer() {
        return true;
    }
}
