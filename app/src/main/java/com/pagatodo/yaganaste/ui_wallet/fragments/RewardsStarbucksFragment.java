package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.parser.IntegerParser;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.RequestPaymentHorizontalAdapter;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.NIVEL_ACTUAL_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.NUMERO_ESTRELLAS_FALTANTES;
import static com.pagatodo.yaganaste.utils.Recursos.SIGUIENTE_NIVEL_STARBUCKS;

/**
 * Created by asandovals on 13/04/2018.
 */

public class RewardsStarbucksFragment  extends GenericFragment {
    private View rootView;
    private Preferencias prefs = App.getInstance().getPrefs();

    @BindView(R.id.titulo_datos_usuario)
    StyleTextView titulo_datos_usuario;
    @BindView(R.id.txt_reward_subtitul)
    StyleTextView txt_reward_subtitul;


    //@BindView(R.id.edt_message_payment)
    //StyleEdittext edtMessagePayment;
    //@BindView(R.id.btn_send_payment)
    //StyleButton btnSendPayment;


    public static RewardsStarbucksFragment newInstance(){
        return new RewardsStarbucksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_starbucks_rewards, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        int estrellas_faltantes=  App.getInstance().getPrefs().loadDataInt(NUMERO_ESTRELLAS_FALTANTES);
        if (estrellas_faltantes!=1){
            titulo_datos_usuario.setText(estrellas_faltantes+" Estrellas para obtener el Nivel"+App.getInstance().getPrefs().loadData(SIGUIENTE_NIVEL_STARBUCKS));
        }else
        titulo_datos_usuario.setText(estrellas_faltantes+" Estrella para obtener el Nivel"+App.getInstance().getPrefs().loadData(SIGUIENTE_NIVEL_STARBUCKS));
        txt_reward_subtitul.setText("Nivel "+App.getInstance().getPrefs().loadData(NIVEL_ACTUAL_STARBUCKS));
    }
}
