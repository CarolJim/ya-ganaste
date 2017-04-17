package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.ButterKnife;

/**
 * Created by Jordan on 12/04/2017.
 */

public class RecargasFormFragment extends PaymentFormBaseFragment {

    public static RecargasFormFragment newInstance(){
        RecargasFormFragment fragment = new RecargasFormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_recarga_form, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    void continuePayment() {
        Toast.makeText(getContext(), "Realizar Pago", Toast.LENGTH_SHORT).show();
    }
}
