package com.pagatodo.yaganaste.ui.maintabs.fragments.deposits;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DetailsAdquirenteFragment;

public class CompartirReciboFragment extends GenericFragment {

    private DataMovimientoAdq dataMovimientoAdq;

    public CompartirReciboFragment() {
        // Required empty public constructor
    }

    public static CompartirReciboFragment newInstance(DataMovimientoAdq dataMovimientoAdq) {
        CompartirReciboFragment fragment = new CompartirReciboFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailsActivity.DATA, dataMovimientoAdq);
        fragment.setArguments(args);
        return fragment;
    }

    public static GenericFragment newInstance() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            dataMovimientoAdq = (DataMovimientoAdq) args.getSerializable(DetailsActivity.DATA);
        } else {
            throw new IllegalCallException(DetailsAdquirenteFragment.class.getSimpleName() + "must be called by newInstance factory method");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compartir_recibo, container, false);
    }

    @Override
    public void initViews() {

    }
}
