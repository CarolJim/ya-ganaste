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

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EmisorResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioResponse;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;

import static com.pagatodo.yaganaste.utils.Recursos.SPACE;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

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
        UsuarioResponse userData = SingletonUser.getInstance().getDataUser().getUsuario();

        ClienteResponse clienteData= SingletonUser.getInstance().getDataUser().getCliente();





        String nombreprimerUser;
        String apellidoMostrarUser;
        if (clienteData.getPrimerApellido().isEmpty()) {
            apellidoMostrarUser = clienteData.getSegundoApellido();
        } else {
            apellidoMostrarUser = clienteData.getPrimerApellido();
        }
        nombreprimerUser = StringUtils.getFirstName(userData.getNombreUsuario());
        if (nombreprimerUser.isEmpty()) {
            nombreprimerUser = userData.getNombreUsuario();
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

        double saldo = StringUtils.getDoubleValue(App.getInstance().getPrefs().loadData(USER_BALANCE));
        if (Math.abs(saldo) >= 99999) {
            txtSaldo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        }

        txtSaldo.setText(StringUtils.getCurrencyValue(saldo));

        String statusId = SingletonUser.getInstance().getCardStatusId();

        if (SingletonUser.getInstance().getDataUser().getEmisor().getCuentas().get(0).getTarjetas().equals("")) {
            checkState("0");
        } else if (statusId != null && !statusId.isEmpty()) {
            // && statusId.equals(Recursos.ESTATUS_DE_NO_BLOQUEADA)
            checkState(statusId);

        } else {
            checkState(App.getInstance().getStatusId());
        }
    }

    private void checkState(String state) {
        switch (state) {
            case "0":
                cardYaganaste.setImageResource(R.mipmap.main_card_zoom_gray);
                break;
            case Recursos.ESTATUS_CUENTA_DESBLOQUEADA:
                cardYaganaste.setImageResource(R.mipmap.main_card_zoom_blue);
                break;
            case Recursos.ESTATUS_CUENTA_BLOQUEADA:
                cardYaganaste.setImageResource(R.mipmap.main_card_zoom_gray);
                break;
            default:
                cardYaganaste.setImageResource(R.mipmap.main_card_zoom_blue);
                break;
        }
    }
}