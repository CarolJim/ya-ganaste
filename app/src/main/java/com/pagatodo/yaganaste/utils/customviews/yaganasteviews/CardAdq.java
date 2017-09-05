package com.pagatodo.yaganaste.utils.customviews.yaganasteviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;

import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;

/**
 * @author Juan Guerra on 05/04/2017.
 */

public class CardAdq extends TabViewElement {

    private MontoTextView saldoAdq;
    private TextView txtInvite;

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

        saldoAdq = (MontoTextView) findViewById(R.id.txt_saldo_adq);
        txtInvite = (TextView) findViewById(R.id.txt_invite);
    }

    @Override
    public void updateData() {
        if (SingletonUser.getInstance().getDataUser().isEsAgente() && SingletonUser.getInstance().getDataUser().getEstatusAgente() == CRM_DOCTO_APROBADO) {
            txtInvite.setVisibility(GONE);
            saldoAdq.setVisibility(VISIBLE);
            double saldo;
            if (SingletonUser.getInstance().getDataUser().getIdEstatus() == IdEstatus.CUPO.getId()) {
                saldo = StringUtils.getDoubleValue(SingletonUser.getInstance().getDatosCupo().getSaldoDisponible());
            } else {
                saldo = StringUtils.getDoubleValue(SingletonUser.getInstance().getDatosSaldo().getSaldoAdq());
            }

            if (Math.abs(saldo) >= 1000000) {
                saldoAdq.setTextSize(TypedValue.COMPLEX_UNIT_SP, 6);
            }
            saldoAdq.setText(StringUtils.getCurrencyValue(saldo));
        }
    }

}