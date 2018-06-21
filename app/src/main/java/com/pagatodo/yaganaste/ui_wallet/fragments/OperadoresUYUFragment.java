package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.OperadoresUyUAdapter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IGetOperators;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.ui_wallet.presenter.GetOperatorsPresenter;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_OPERADOR_DETALLE;
import static com.pagatodo.yaganaste.utils.Recursos.AGENTE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.ID_COMERCIOADQ;
import static com.pagatodo.yaganaste.utils.Recursos.LAST_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.SPACE;

/**
 * A simple {@link Fragment} subclass.
 */
public class OperadoresUYUFragment extends GenericFragment implements OnRecyclerItemClickListener, IGetOperators {

    @BindView(R.id.rcv_operadore_uyu)
    RecyclerView rcvRewards;
    @BindView(R.id.titulo_nombre_negocio)
    StyleTextView titulo_nombre_negocio;
    String nombreN;

    private View rootView;
    ElementView elementView;
    List<Operadores> operadoresUYUFragments;
    GetOperatorsPresenter getOperatorsPresenter;

    public static OperadoresUYUFragment newInstance(ElementView elementView) {
        OperadoresUYUFragment fragment = new OperadoresUYUFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailsActivity.DATA, elementView);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        elementView = (ElementView) args.getSerializable(DetailsActivity.DATA);
        Preferencias prefs = App.getInstance().getPrefs();
        prefs.saveData(AGENTE_NUMBER, elementView.getNumeroAgente());
        prefs.saveData(ID_COMERCIOADQ, elementView.getIdComercio());
        getOperatorsPresenter = new GetOperatorsPresenter(getContext(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getOperatorsPresenter.getListOperators();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvRewards.setLayoutManager(llm);
        rcvRewards.setHasFixedSize(true);
        String nombreN = elementView.getNombreNegocio();
        titulo_nombre_negocio.setText(nombreN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_operadores_uyu, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void onRecyclerItemClick(View v, int position) {
        Operadores operadoresResponse;
        operadoresResponse = operadoresUYUFragments.get(position + 1);
        onEventListener.onEvent(EVENT_OPERADOR_DETALLE, operadoresResponse);
    }

    @Override
    public void succedGetOperador(List<Operadores> operadores) {
        operadoresUYUFragments = operadores;
        rcvRewards.setAdapter(new OperadoresUyUAdapter(operadoresUYUFragments, getActivity(), this));
    }

    @Override
    public void failGetOperador(String mensaje) {

    }

    @Override
    public void nextScreen(String event, Object data) {

    }

    @Override
    public void backScreen(String event, Object data) {

    }

    @Override
    public void showLoader(String message) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void showError(Object error) {

    }
}
