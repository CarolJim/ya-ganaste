package com.pagatodo.yaganaste.modules.payments;

import androidx.appcompat.widget.AppCompatImageView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui_wallet.fragments.PaymentFormFragment;
import com.pagatodo.yaganaste.utils.UI;

import static com.pagatodo.yaganaste.utils.Constants.MESSAGE;
import static com.pagatodo.yaganaste.utils.Constants.RESULT;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_ERROR;

public class PaymentsActivity extends LoaderActivity implements View.OnClickListener {

    public static final String PAYMENT_DATA = "PAYMENT_DATA";
    public static final String PAYMENT_IS_FAV = "PAYMENT_IS_FAV";
    @BindView(R.id.btn_back)
    AppCompatImageView back;

    private Comercio comercioResponse;
    private Favoritos favoritos;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);*/
        setContentView(R.layout.activity_payments);
        if (getIntent().getExtras() != null) {
            isFavorite = getIntent().getBooleanExtra(PAYMENT_IS_FAV, false);
            if (!isFavorite) {
                comercioResponse = (Comercio) getIntent().getExtras().get(PAYMENT_DATA);
            } else {
                favoritos = (Favoritos) getIntent().getExtras().get(PAYMENT_DATA);
            }
        }
        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        if (!isFavorite) {
            loadFragment(PaymentFormFragment.newInstance(comercioResponse), R.id.fragment_container);
        } else {
            loadFragment(PaymentFormFragment.newInstance(favoritos), R.id.fragment_container);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode,final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PaymentFormFragment myFragment = (PaymentFormFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        myFragment.onActivityResult(requestCode, resultCode, data);
        //  UI.showErrorSnackBar(this, "LOL");

        // Mostramos los Snack de Error
        try {
            Bundle MBuddle = data.getExtras();
            String MMessage = MBuddle.getString(MESSAGE);
            String resultError = MBuddle.getString(RESULT);
            if (resultError.equals(RESULT_ERROR)) {
                UI.showErrorSnackBar(this, MMessage, Snackbar.LENGTH_SHORT);
            }
        } catch (Exception e) {
        }
    }
}
