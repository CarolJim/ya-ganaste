package com.pagatodo.yaganaste.ui_wallet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoRequestPayment;
import com.pagatodo.yaganaste.ui_wallet.fragments.PaymentRequestFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.ProcessRequestPaymentFragment;
import com.pagatodo.yaganaste.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui_wallet.fragments.SendWalletFragment.MONTO;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;
import static com.pagatodo.yaganaste.utils.Constants.CREDITCARD_READER_REQUEST_CODE;

public class RequestPaymentActivity extends LoaderActivity implements View.OnClickListener {

    public static final String EVENT_SOLICITAR_PAGO = "EVENT_SOLICITAR_PAGO";

    @BindView(R.id.btn_back)
    AppCompatImageView back;

    private float monto;
    private ArrayList<DtoRequestPayment> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_payment);
        monto = getIntent().getFloatExtra(MONTO, 1F);
        data = new ArrayList<>();
        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        loadFragment(PaymentRequestFragment.newInstance(monto, data), R.id.container_request_payment);
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!isLoaderShow) {
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment instanceof ProcessRequestPaymentFragment) {
                loadFragment(PaymentRequestFragment.newInstance(monto, data), R.id.container_request_payment);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onClick(View v) {
        @SuppressLint("RestrictedApi") List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList.get(0) instanceof ProcessRequestPaymentFragment) {
            loadFragment(PaymentRequestFragment.newInstance(monto, data), R.id.container_request_payment);
        } else {
            finish();
        }
    }

    public void setListRequests(ArrayList<DtoRequestPayment> data) {
        this.data = data;
        loadFragment(ProcessRequestPaymentFragment.newInstance(this.data, monto), R.id.container_request_payment);
    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        if (event.equals(EVENT_SOLICITAR_PAGO)) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONTACTS_CONTRACT || requestCode == Constants.BARCODE_READER_REQUEST_CODE
                || requestCode == CREDITCARD_READER_REQUEST_CODE) {
            getCurrentFragment().onActivityResult(requestCode, resultCode, data);
        }
    }
}
