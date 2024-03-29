package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.SeekBar;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.model.SingletonSession;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsTabPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Constants.BACK_FROM_PAYMENTS;

/**
 * Created by Jordan on 12/04/2017.
 */

public abstract class PaymentFormBaseFragment extends GenericFragment implements SeekBar.OnSeekBarChangeListener {
    protected View rootview;
    @BindView(R.id.myseek)
    protected SeekBar mySeekBar;
    boolean isValid = false;
    Payments payment;
    IPaymentsTabPresenter paymentsTabPresenter;
    Comercio comercioItem;
    Favoritos favoriteItem;
    String errorText;
    String referencia;
    Double monto;
    String concepto;
    MovementsTab tab;
    int current_tab;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar.getProgress() >= 85) {
            seekBar.setProgress(100);
            continuePayment();
        } else if (seekBar.getProgress() < 85) {
            seekBar.setProgress(0);
        }
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        mySeekBar.setOnSeekBarChangeListener(this);

    }

    protected abstract void continuePayment();

    protected void sendPayment() {
        Intent intent = new Intent(getContext(), PaymentsProcessingActivity.class);
        intent.putExtra("pagoItem", payment);
        intent.putExtra("TAB", current_tab);
        SingletonSession.getInstance().setFinish(false);//No cerramos la aplicación
        getActivity().startActivityForResult(intent, BACK_FROM_PAYMENTS);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void setSeekBarProgress(int progress) {
        mySeekBar.setProgress(progress);
    }
}
