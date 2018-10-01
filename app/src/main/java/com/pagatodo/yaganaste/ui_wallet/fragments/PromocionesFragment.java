package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.AdapterPromociones;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementPromocion;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_DETALLE_PROMO;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_OPERADOR_DETALLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromocionesFragment extends GenericFragment implements OnRecyclerItemClickListener {

    @BindView(R.id.rv_promos)
    RecyclerView rv_promos;

    View rootview;


ArrayList<ElementPromocion> list = new ArrayList<>();

    public PromocionesFragment() {
        // Required empty public constructor
    }


    public  static  PromocionesFragment newInstance(){
        return  new PromocionesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview= inflater.inflate(R.layout.fragment_promociones, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        list.add(new ElementPromocion("$5 pesos de descuento","Vigencia 18/Oct/2018","url.com","vigente",false));
        list.add(new ElementPromocion("2 x $30","Vigencia 10/Oct/2018","url.com","vigente",true));
        list.add(new ElementPromocion("Roles 3x2","Vigencia 01/Oct/2018","url.com","vigente",true));

        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_promos.setLayoutManager(llm);
        rv_promos.setHasFixedSize(true);

        rv_promos.setAdapter(new AdapterPromociones(list,this));
    }

    @Override
    public void onRecyclerItemClick(View v, int position) {



   onEventListener.onEvent(EVENT_DETALLE_PROMO, list.get(position));

    }
}
