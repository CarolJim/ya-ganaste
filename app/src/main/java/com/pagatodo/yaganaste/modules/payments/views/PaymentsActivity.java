package com.pagatodo.yaganaste.modules.payments.views;

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
import com.pagatodo.yaganaste.modules.payments.mobiletopup.view.MobileTopUpFragment;
import com.pagatodo.yaganaste.modules.payments.mobiletopup.view.MobileTopUpView;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
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
