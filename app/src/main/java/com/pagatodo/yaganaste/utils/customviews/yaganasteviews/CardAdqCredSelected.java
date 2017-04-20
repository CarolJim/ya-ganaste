package com.pagatodo.yaganaste.utils.customviews.yaganasteviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.SeekBarNoTouch;

/**
 * @author Juan Guerra on 05/04/2017.
 */

public class CardAdqCredSelected extends TabViewElement{

    private SeekBar seekLineaCredito;
    private TextView txtLineaCredito;

    private SeekBar seekMontoDepositar;
    private TextView txtMontoDepositar;

    private SeekBar seekSaldoDisponible;
    private TextView txtSaldoDisponible;

    public CardAdqCredSelected(Context context) {
        this(context, null);
    }

    public CardAdqCredSelected(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardAdqCredSelected(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView();
    }

    private void setView() {
        View child = LayoutInflater.from(getContext()).inflate(R.layout.card_adquirente_credito_selected, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0);
        addView(child, params);

        seekLineaCredito = (SeekBar)findViewById(R.id.seek_linea_credito);
        txtLineaCredito = (TextView)findViewById(R.id.txt_linea_credito);
        seekMontoDepositar = (SeekBar)findViewById(R.id.seek_monto_depositar);
        txtMontoDepositar = (TextView)findViewById(R.id.txt_monto_depositar);
        seekSaldoDisponible = (SeekBar)findViewById(R.id.seek_saldo_disponible);
        txtSaldoDisponible = (TextView)findViewById(R.id.txt_saldo_disponible);
    }

    public void updateSaldos(String lineaCredito, String montoDepositar, String saldoDisponible) {
        updateSaldos(StringUtils.getDoubleValue(lineaCredito),
                StringUtils.getDoubleValue(montoDepositar), StringUtils.getDoubleValue(saldoDisponible));
    }

    public void updateSaldos(double lineaCredito, double montoDepositar, double saldoDisponible) {
        double total = lineaCredito + montoDepositar + saldoDisponible;
        seekLineaCredito.setProgress(100);
        txtLineaCredito.setText(StringUtils.getCurrencyValue(lineaCredito));

        seekMontoDepositar.setProgress(100 - (int)(montoDepositar/total));
        txtMontoDepositar.setText(StringUtils.getCurrencyValue(montoDepositar));

        seekSaldoDisponible.setProgress(100 - seekMontoDepositar.getProgress());
        txtSaldoDisponible.setText(StringUtils.getCurrencyValue(saldoDisponible));
    }

    @Override
    public void updateData() {
        // TODO: 19/04/2017 Verificar como se van a actualizar estos datos
    }
}
