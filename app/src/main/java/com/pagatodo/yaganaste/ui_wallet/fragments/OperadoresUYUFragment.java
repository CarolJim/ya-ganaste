package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.OperadoresUyUAdapter;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OperadoresUYUFragment extends GenericFragment {

    @BindView(R.id.rcv_operadore_uyu)
    RecyclerView rcvRewards;


    @BindView(R.id.titulo_nombre_negocio)
    StyleTextView titulo_nombre_negocio;


    private View rootView;

    ElementView elementView;
    List<Operadores> operadoresUYUFragments;

    public static OperadoresUYUFragment newInstance(ElementView elementView) {
        OperadoresUYUFragment fragment = new OperadoresUYUFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailsActivity.DATA, elementView);
        fragment.setArguments(args);

        return fragment;
    }

    public static OperadoresUYUFragment newInstance() {
        return new OperadoresUYUFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        elementView = (ElementView) args.getSerializable(DetailsActivity.DATA);
    }

    @Override
    public void initViews() {

        ButterKnife.bind(this, rootView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvRewards.setLayoutManager(llm);
        rcvRewards.setHasFixedSize(true);
        Operadores operadoresResponse = new Operadores();

        operadoresResponse.setIdOperador(123);
        operadoresResponse.setIdUsuario(123);
        operadoresResponse.setIdUsuarioAdquirente("123123");
        operadoresResponse.setIsAdmin(false);
        operadoresResponse.setNombreUsuario("operador@fulanito.com");
        operadoresResponse.setPetroNumero("sdkasjdoiaj");

        operadoresUYUFragments = elementView.getList();
        String nombreN = elementView.getNombreNegocio();
        titulo_nombre_negocio.setText(nombreN);
        rcvRewards.setAdapter(new OperadoresUyUAdapter(operadoresUYUFragments));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_operadores_uyu, container, false);
        initViews();
        return rootView;
    }

}
