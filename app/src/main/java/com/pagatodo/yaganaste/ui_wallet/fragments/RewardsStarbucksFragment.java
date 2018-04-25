package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.room_db.entities.Rewards;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.RewardsStarbucksAdapter;
import com.pagatodo.yaganaste.ui_wallet.presenter.RewardsStarbucksPresenter;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.List;

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

public class RewardsStarbucksFragment extends GenericFragment {
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
    @BindView(R.id.rcv_rewards_starbucks)
    RecyclerView rcvRewards;

    private RewardsStarbucksPresenter presenter;
    private List<Rewards> rewardsList;

    public static RewardsStarbucksFragment newInstance() {
        return new RewardsStarbucksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RewardsStarbucksPresenter(getActivity());
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
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvRewards.setLayoutManager(llm);
        rcvRewards.setHasFixedSize(true);
        int estrellas_faltantes = prefs.loadDataInt(MISSING_STARS_NUMBER);
        num_starts_currently.setText("" + prefs.loadDataInt(STARS_NUMBER));
        if (prefs.loadDataInt(STATUS_GOLD) == 1) {
            cup_coffe.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow));
        }
        if (estrellas_faltantes != 1) {
            titulo_datos_usuario.setText(estrellas_faltantes + " Estrellas para obtener " + (prefs.loadData(ACTUAL_LEVEL_STARBUCKS).equals("Gold") ?
                    "" : "el Nivel ") + App.getInstance().getPrefs().loadData(NEXT_LEVEL_STARBUCKS));
        } else {
            titulo_datos_usuario.setText(estrellas_faltantes + " Estrella para obtener " + (prefs.loadData(ACTUAL_LEVEL_STARBUCKS).equals("Gold") ?
                    "" : "el Nivel ") + App.getInstance().getPrefs().loadData(NEXT_LEVEL_STARBUCKS));
        }
        txt_reward_subtitul.setText("Nivel " + prefs.loadData(ACTUAL_LEVEL_STARBUCKS));
        rewardsList = presenter.getMyRewards();
        rcvRewards.setAdapter(new RewardsStarbucksAdapter(rewardsList));
    }
}
