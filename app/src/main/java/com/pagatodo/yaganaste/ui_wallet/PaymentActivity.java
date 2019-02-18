package com.pagatodo.yaganaste.ui_wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.widget.AppCompatImageView;
import android.view.View;
import android.view.WindowManager;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui_wallet.fragments.PaymentFormFragment;
import com.pagatodo.yaganaste.utils.UI;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Constants.MESSAGE;
import static com.pagatodo.yaganaste.utils.Constants.RESULT;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_ERROR;

public class PaymentActivity extends LoaderActivity implements View.OnClickListener {

    public static final String PAYMENT_DATA = "PAYMENT_DATA";
    public static final String PAYMENT_IS_FAV = "PAYMENT_IS_FAV";
    public static final String AUTHORIZE = "AUTHORIZE";
    public static final String NEXT_VIEW = "NEXT_VIEW";
    @BindView(R.id.btn_back)
    AppCompatImageView back;

    private Comercio comercioResponse;
    private Favoritos favoritos;
    private boolean isFavorite;

    public static Intent creatIntent(Activity activity, Comercio comercio, boolean isFav){
        Intent intent = new Intent(activity, PaymentActivity.class);
        intent.putExtra(PAYMENT_DATA, comercio);
        intent.putExtra(PAYMENT_IS_FAV, isFav);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);*/
        setContentView(R.layout.activity_payment);
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
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);

        if(event.equals(AUTHORIZE)) {
            if (data instanceof Favoritos)
                loadFragment(PaymentFormFragment.newInstance((Favoritos) data), R.id.fragment_container);
            if (data instanceof Comercio)
                loadFragment(PaymentFormFragment.newInstance((Comercio) data), R.id.fragment_container);
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
