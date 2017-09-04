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
import com.pagatodo.yaganaste.utils.StringUtils;

import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;

/**
 * @author Juan Guerra on 05/04/2017.
 */

public class CardAdqSelected extends TabViewElement {

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

        txtNombreNegocio = (TextView) findViewById(R.id.txt_nombre_negocio);
        txtSaldoAdq = (TextView) findViewById(R.id.txt_saldo_adq);
        txtStatus = (TextView) findViewById(R.id.txt_status);

    }

    @Override
    public void updateData() {
        if (SingletonUser.getInstance().getDataUser().isEsAgente() && SingletonUser.getInstance().getDataUser().getEstatusAgente() == CRM_DOCTO_APROBADO) {
            this.txtNombreNegocio.setText(SingletonUser.getInstance().getDataUser().getUsuario().getNombreNegocio());
            double saldo = StringUtils.getDoubleValue(SingletonUser.getInstance().getDatosSaldo().getSaldoAdq());
            if (Math.abs(saldo) > 99999) {
                txtSaldoAdq.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            }

            this.txtSaldoAdq.setText(StringUtils.getCurrencyValue(saldo));
        }

    }
}
