package com.pagatodo.yaganaste.utils.customviews.yaganasteviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.StringUtils;

/**
 * @author Juan Guerra on 05/04/2017.
 */

public class CardAdqSelected extends LinearLayoutCompat {

    private TextView txtNombreNegocio;
    private TextView txtSaldoAdq;
    private TextView txtStatus;

    public CardAdqSelected(Context context) {
        this(context, null);
    }

    public CardAdqSelected(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardAdqSelected(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView();
    }

    private void setView() {
        View child = LayoutInflater.from(getContext()).inflate(R.layout.card_adquirente_selected, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0);
        addView(child, params);

        txtNombreNegocio = (TextView)findViewById(R.id.txt_nombre_negocio);
        txtSaldoAdq = (TextView)findViewById(R.id.txt_saldo_adq);
        txtStatus = (TextView)findViewById(R.id.txt_status);
    }

    public void updateDatosAdquirente (String nombreNegocio, String saldoAdq, String status) {
        this.txtNombreNegocio.setText(nombreNegocio);
        this.txtSaldoAdq.setText(StringUtils.getCurrencyValue(saldoAdq));
        this.txtStatus.setText(status);
    }

    public void updateDatosAdquirente (String nombreNegocio, double saldoAdq, String status) {
        updateDatosAdquirente(nombreNegocio, String.valueOf(saldoAdq), status);
    }
}