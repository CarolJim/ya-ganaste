package com.pagatodo.yaganaste.utils.customviews.yaganasteviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;

import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;

/**
 * @author Juan Guerra on 05/04/2017.
 */

public class CardEmisorSelected extends TabViewElement {

    private MontoTextView txtSaldo;
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
        txtSaldo = (MontoTextView) findViewById(R.id.txt_saldo);
        txtNombre = (TextView) findViewById(R.id.txt_nombre);
        updateData();
    }

    @Override
    public void updateData() {
        UsuarioClienteResponse userData = SingletonUser.getInstance().getDataUser().getUsuario();
        String name = userData.getNombre();
        txtNombre.setText(name.contains(" ") ? name.substring(0, userData.getNombre().indexOf(" ")) : name
                + SPACE + userData.getPrimerApellido());
        txtSaldo.setText(StringUtils.getCurrencyValue(SingletonUser.getInstance().getDatosSaldo().getSaldoEmisor()));
    }

}