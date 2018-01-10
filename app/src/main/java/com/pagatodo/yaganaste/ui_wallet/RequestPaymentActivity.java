package com.pagatodo.yaganaste.ui_wallet;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui_wallet.fragments.PaymentRequestFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui_wallet.fragments.SendWalletFragment.MONTO;

public class RequestPaymentActivity extends LoaderActivity implements View.OnClickListener {

    @BindView(R.id.btn_back)
    AppCompatImageView back;

    private int monto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_payment);
        monto = getIntent().getIntExtra(MONTO, 1);
        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        loadFragment(PaymentRequestFragment.newInstance("",""), R.id.container_request_payment);
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
