package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsTabPresenter;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Jordan on 12/04/2017.
 */

public class RecargasFormFragment extends PaymentFormBaseFragment {

    @BindView(R.id.recargaNumber)
    CustomValidationEditText recargaNumber;
    @BindView(R.id.montoRecarga)
    Spinner montoRecarga;

    PaymentsTabPresenter paymentsTabPresenter;

    public static RecargasFormFragment newInstance() {
        RecargasFormFragment fragment = new RecargasFormFragment();
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
        rootview = inflater.inflate(R.layout.fragment_recarga_form, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        super.initViews();
        List<Double> montos = paymentsTabPresenter.getCarouselItem().getComercio().getListaMontos();
        montos.add(0, 0.0);
        SpinnerArrayAdapter dataAdapter = new SpinnerArrayAdapter(getContext(), MovementsTab.TAB1, montos);
        montoRecarga.setAdapter(dataAdapter);

    }

    @Override
    void continuePayment() {
        Toast.makeText(getContext(), "Realizar Pago", Toast.LENGTH_SHORT).show();
    }
}
