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
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

/**
 * @author Juan Guerra on 05/04/2017.
 */

public class CardEmisorSelected extends LinearLayoutCompat {

    private TextView txtSaldo;
    private TextView txtNombre;

    public CardEmisorSelected(Context context) {
        this(context, null);
    }

    public CardEmisorSelected(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardEmisorSelected(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView();
    }

    private void setView() {
        View child = LayoutInflater.from(getContext()).inflate(R.layout.card_emisor_selected, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0);
        addView(child, params);
        txtSaldo = (TextView)findViewById(R.id.txt_saldo);
        txtNombre = (TextView)findViewById(R.id.txt_nombre);
    }

    public void updateDatosEmisor(String nombre, String saldo) {
        this.txtNombre.setText(nombre);
        this.txtSaldo.setText(StringUtils.getCurrencyValue(saldo));
    }

    public void updateDatosEmisor(String nombre, double saldo) {
        updateDatosEmisor(nombre, String.valueOf(saldo));
    }
}
