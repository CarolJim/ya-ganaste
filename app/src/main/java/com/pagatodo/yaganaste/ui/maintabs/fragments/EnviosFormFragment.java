package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsTabPresenter;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Jordan on 12/04/2017.
 */

public class EnviosFormFragment extends PaymentFormBaseFragment{

    @BindView(R.id.tipoEnvio)
    Spinner tipoEnvio;
    @BindView(R.id.cardNumner)
    CustomValidationEditText cardNumner;

    PaymentsTabPresenter paymentsTabPresenter;

    public static EnviosFormFragment newInstance() {
        EnviosFormFragment fragment = new EnviosFormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            paymentsTabPresenter = ((PaymentsTabFragment) getParentFragment()).getPresenter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_envios_form, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        super.initViews();

        List<String> tipoPago = new ArrayList<>();

        tipoPago.add(0, "");
        tipoPago.add(1, "Número de Teléfono");
        tipoPago.add(2, "Número de Tarjeta");

        if(paymentsTabPresenter.getCarouselItem().getComercio().getIdComercio() != 8609){
            tipoPago.add(3, "Cuenta CABLE");
        }
        SpinnerArrayAdapter dataAdapter = new SpinnerArrayAdapter(getContext(), MovementsTab.TAB3, tipoPago);
        tipoEnvio.setAdapter(dataAdapter);
        tipoEnvio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        cardNumner.setHintText("Número de Teléfono");
                        cardNumner.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        cardNumner.setHintText("Número de Tarjeta");
                        cardNumner.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        cardNumner.setHintText("Cuenta CABLE");
                        cardNumner.setVisibility(View.VISIBLE);
                        break;
                    default:
                        cardNumner.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    void continuePayment() {
        super.continuePayment();
    }

}
