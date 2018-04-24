package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Recursos.ACTUAL_LEVEL_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.MISSING_STARS_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.NEXT_LEVEL_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.STARS_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_GOLD;

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

    @BindView(R.id.num_starts_currently)
    StyleTextView num_starts_currently;
    @BindView(R.id.cup_coffee)
    ImageView cup_coffe;




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
        int estrellas_faltantes=  prefs.loadDataInt(MISSING_STARS_NUMBER);
        String start=(prefs.loadData(STARS_NUMBER));
        num_starts_currently.setText(""+start);

        if (prefs.loadDataInt(STATUS_GOLD)==1){
            cup_coffe.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow));
        }

        if (estrellas_faltantes!=1){
            titulo_datos_usuario.setText(estrellas_faltantes+" Estrellas para obtener el Nivel"+App.getInstance().getPrefs().loadData(NEXT_LEVEL_STARBUCKS));
        }else
        titulo_datos_usuario.setText(estrellas_faltantes+" Estrella para obtener el Nivel"+App.getInstance().getPrefs().loadData(NEXT_LEVEL_STARBUCKS));
        txt_reward_subtitul.setText("Nivel "+prefs.loadData(ACTUAL_LEVEL_STARBUCKS));
    }
}
