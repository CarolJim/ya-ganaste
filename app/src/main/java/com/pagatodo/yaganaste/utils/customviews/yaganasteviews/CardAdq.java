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

public class CardAdq extends LinearLayoutCompat {

    private TextView saldoAdq;
    private TextView txtInvite;
    private boolean isAdquirente;

    public CardAdq(Context context) {
        this(context, null);
    }

    public CardAdq(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardAdq(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView();
    }

    private void setView() {
        View child = LayoutInflater.from(getContext()).inflate(R.layout.invite_adq, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0);
        addView(child, params);

        saldoAdq = (TextView) findViewById(R.id.txt_saldo_adq);
        txtInvite = (TextView) findViewById(R.id.txt_invite);
        isAdquirente = true;
    }

    public void updateSaldo(String saldo) {
        saldoAdq.setVisibility(VISIBLE);
        saldoAdq.setText(StringUtils.getCurrencyValue(saldo));
    }

    public void updateSaldo(double saldo) {
        txtInvite.setVisibility(GONE);
        updateSaldo(String.valueOf(saldo));
        isAdquirente = true;
    }

    public boolean isAdquirente() {
        return this.isAdquirente;
    }
}
