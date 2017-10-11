package com.pagatodo.yaganaste.utils.customviews.yaganasteviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;

import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;

/**
 * @author Juan Guerra on 05/04/2017.
 */

public class CardEmisorSelected extends TabViewElement {

    private MontoTextView txtSaldo;
    private TextView txtNombre;
    private ImageView cardYaganaste;

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
        cardYaganaste = (ImageView) findViewById(R.id.imgYaGanasteCard);
        updateData();
    }

    @Override
    public void updateData() {
        UsuarioClienteResponse userData = SingletonUser.getInstance().getDataUser().getUsuario();

        String nombreprimerUser;
        String apellidoMostrarUser;
        if (userData.getPrimerApellido().isEmpty()){
        apellidoMostrarUser=userData.getSegundoApellido();
        }else {
            apellidoMostrarUser=userData.getPrimerApellido();
        }
        nombreprimerUser= StringUtils.getFirstName(userData.getNombre());
        if (nombreprimerUser.isEmpty()){
            nombreprimerUser=userData.getNombre();
        }

        txtNombre.setText(nombreprimerUser.concat(SPACE.concat(apellidoMostrarUser)));
       /*
        txtNombre.setText(
               userData.getNombre() + " " +
               userData.getPrimerApellido() + "\n" +
                       userData.getSegundoApellido()
        );

        */
//        txtNombre.setText(
//                StringUtils.getFirstName(userData.getNombre())
//                        .concat(SPACE).concat(userData.getSegundoApellido() +
//                        "\n" + userData.getSegundoApellido())
//        );

        double saldo = StringUtils.getDoubleValue(SingletonUser.getInstance().getDatosSaldo().getSaldoEmisor());
        if (Math.abs(saldo) >= 99999) {
            txtSaldo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        }

        txtSaldo.setText(StringUtils.getCurrencyValue(saldo));

        String statusId = SingletonUser.getInstance().getCardStatusId();
        if (statusId != null && statusId.equals(Recursos.ESTATUS_DE_NO_BLOQUEADA)) {
            cardYaganaste.setImageResource(R.mipmap.main_card_zoom_blue);
        } else {
            cardYaganaste.setImageResource(R.mipmap.logo_ya_ganaste);
        }
    }

}