package com.pagatodo.yaganaste.ui.cupo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.CupoSpinnerTypes;
import com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.cupo.CupoSpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.cupo.managers.CupoActivityManager;
import com.pagatodo.yaganaste.ui.cupo.managers.CupoCuentameMasManager;
import com.pagatodo.yaganaste.ui.cupo.presenters.CuentanosMasPresenter;
import com.pagatodo.yaganaste.ui.cupo.presenters.interfaces.ICuentanosMasPresenter;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 26/07/2017.
 */

public class CupoCuentanosMasFragment extends GenericFragment implements CupoCuentameMasManager {
    protected View rootview;
    private CupoActivityManager cupoActivityManager;

    private ICuentanosMasPresenter cuentanosMasPresenter;
    private CupoSpinnerArrayAdapter estadoCivilAdapter;
    private CupoSpinnerArrayAdapter hijosAdapter;

    private String numeroHijos;
    private String estadoCivil;
    private boolean hasCreditBank;
    private boolean hasCarCredit;
    private boolean hasCreaditCar;

    @BindView(R.id.spEstadoCivil)
    Spinner spEstadoCivil;
    @BindView(R.id.errorEstadoCivil)
    ErrorMessage errorEstadoCivil;
    @BindView(R.id.spHijos)
    Spinner spHijos;
    @BindView(R.id.errorHijos)
    ErrorMessage errorHijos;
    @BindView(R.id.radioGroupHasCreditBank)
    RadioGroup radioGroupHasCreditBank;
    @BindView(R.id.radioBtnrgHasCreditBankYes)
    RadioButton radioBtnrgHasCreditBankYes;
    @BindView(R.id.radioBtnrgHasCreditBankNo)
    RadioButton radioBtnrgHasCreditBankNo;
    @BindView(R.id.radioGroupHasCreditCar)
    RadioGroup radioGroupHasCreditCar;
    @BindView(R.id.radioBtnrgHasCreditCarYes)
    RadioButton radioBtnrgHasCreditCarYes;
    @BindView(R.id.radioBtnrgHasCreditCarNo)
    RadioButton radioBtnrgHasCreditCarNo;
    @BindView(R.id.radioGroupHasCreditCard)
    RadioGroup radioGroupHasCreditCard;
    @BindView(R.id.radioBtnrgHasCreditCardYes)
    RadioButton radioBtnrgHasCreditCardYes;
    @BindView(R.id.radioBtnrgHasCreditCardNo)
    RadioButton radioBtnrgHasCreditCardNo;

    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.btnNext)
    Button btnNext;

    public static CupoCuentanosMasFragment newInstance() {
        CupoCuentanosMasFragment fragment = new CupoCuentanosMasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cupoActivityManager = ((RegistryCupoActivity) getActivity()).getCupoActivityManager();
        cuentanosMasPresenter = new CuentanosMasPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_cupo_cuentanos_mas, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        List<String> estadoCivil = new ArrayList<>();
        estadoCivil.add(0, "");
        estadoCivil.add(1, "Soltero");
        estadoCivil.add(2, "Casado");
        estadoCivil.add(3, "Viudo");
        estadoCivil.add(4, "Otro");

        List<String> hijos = new ArrayList<>();
        hijos.add(0, "");
        hijos.add(1, "0");
        hijos.add(2, "1");
        hijos.add(3, "2");
        hijos.add(4, "3");
        hijos.add(5, "4");
        hijos.add(6, "Mas");

        estadoCivilAdapter = new CupoSpinnerArrayAdapter(getContext(), estadoCivil, CupoSpinnerTypes.ESTADO_CIVIL);
        hijosAdapter = new CupoSpinnerArrayAdapter(getContext(), hijos, CupoSpinnerTypes.HIJOS);

        spEstadoCivil.setAdapter(estadoCivilAdapter);
        spHijos.setAdapter(hijosAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                cupoActivityManager.onBtnBackPress();
                break;
            case R.id.btnNext:
                cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_REFERENCIA_FAMILIAR, null);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        cupoActivityManager.showToolBar();
    }

    @Override
    public void validateForms() {

    }

    @Override
    public void showError(Object error) {

    }

    @Override
    public void showLoader() {
        cupoActivityManager.callEvent(LoaderActivity.EVENT_SHOW_LOADER, null);
    }

    @Override
    public void hideLoader() {
        cupoActivityManager.callEvent(LoaderActivity.EVENT_HIDE_LOADER, null);
    }
}
