package com.pagatodo.yaganaste.ui.cupo.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterCupo;
import com.pagatodo.yaganaste.interfaces.enums.CupoSpinnerTypes;
import com.pagatodo.yaganaste.interfaces.enums.EstadoCivil;
import com.pagatodo.yaganaste.interfaces.enums.Hijos;
import com.pagatodo.yaganaste.interfaces.enums.Relaciones;
import com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.cupo.CupoSpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.cupo.managers.CupoActivityManager;
import com.pagatodo.yaganaste.ui.cupo.managers.CupoCuentameMasManager;
import com.pagatodo.yaganaste.ui.cupo.presenters.CuentanosMasPresenter;
import com.pagatodo.yaganaste.ui.cupo.presenters.interfaces.ICuentanosMasPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
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
    private int    idNumeroHijos;
    private String estadoCivil;
    private int    idEstadoCivil;
    private String hasCreditBank;
    private String hasCarCredit;
    private String hasTarjetaCredit;
    private String numeroTarjeta = "";

    @BindView(R.id.spEstadoCivil)
    Spinner spEstadoCivil;
    @BindView(R.id.spHijos)
    Spinner spHijos;

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

    @BindView(R.id.editNumTarjeta) CustomValidationEditText editNumTarjeta;

    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.btnNext)
    Button btnNext;

    // Errores
    @BindView(R.id.errorEstadoCivil)
    ErrorMessage errorEstadoCivil;

    @BindView(R.id.errorHijos)
    ErrorMessage errorHijos;

    @BindView(R.id.errorCreditoBancario)
    ErrorMessage errorCreditoBancario;

    @BindView(R.id.errorCreditoAutomotriz)
    ErrorMessage errorCreditoAutomotriz;

    @BindView(R.id.errorTarjetaCredito)
    ErrorMessage errorTarjetaCredito;

    @BindView(R.id.errorNumTarjeta)
    ErrorMessage errorNumTarjeta;

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

        estadoCivilAdapter = new CupoSpinnerArrayAdapter(getContext(), EstadoCivil.values(), CupoSpinnerTypes.ESTADO_CIVIL);
        hijosAdapter = new CupoSpinnerArrayAdapter(getContext(), Hijos.values(), CupoSpinnerTypes.HIJOS);

        spEstadoCivil.setAdapter(estadoCivilAdapter);
        spHijos.setAdapter(hijosAdapter);

        setValidationRules();

    }


    private void onSpinnerClick(int id) {
        hideErrorMessage(id);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                cupoActivityManager.onBtnBackPress();
                break;
            case R.id.btnNext:
                validateForm();
                //cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_REFERENCIA_FAMILIAR, null);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        cupoActivityManager.showToolBar();
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


    @Override
    public void setValidationRules() {

        spEstadoCivil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onSpinnerClick(R.id.spEstadoCivil);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                onSpinnerClick(R.id.spEstadoCivil);
            }
        });


        spHijos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onSpinnerClick(R.id.spHijos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                onSpinnerClick(R.id.spHijos);
            }
        });

        radioGroupHasCreditBank.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                hideErrorMessage(radioGroupHasCreditBank.getId());
            }
        });


        radioGroupHasCreditCar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                hideErrorMessage(radioGroupHasCreditCar.getId());
            }
        });

        radioGroupHasCreditCard.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                hideErrorMessage(radioGroupHasCreditCard.getId());

                if (!radioBtnrgHasCreditCardYes.isChecked()) {
                    editNumTarjeta.setVisibility(View.VISIBLE);
                } else {
                    editNumTarjeta.setVisibility(View.GONE);
                    errorNumTarjeta.setVisibilityImageError(false);
                }
            }
        });

    }

    @Override
    public void validateForm() {
        getDataForm();

        boolean isValid = true;

        if (estadoCivil.isEmpty() || estadoCivil.equals("")) {
            showValidationError(spEstadoCivil.getId(), getString(R.string.estado_civil_requerido));
            isValid = false;
        }

        if (numeroHijos.isEmpty() || numeroHijos.equals("")) {
            showValidationError(spHijos.getId(), getString(R.string.numero_hijos_requerido));
            isValid = false;
        }

        if (hasCreditBank == null || hasCreditBank.equals("")) {
            showValidationError(radioGroupHasCreditBank.getId(), getString(R.string.opcion_requerido));
            isValid = false;
        }

        if (hasCarCredit == null || hasCarCredit.equals("")) {
            showValidationError(radioGroupHasCreditCar.getId(), getString(R.string.opcion_requerido));
            isValid = false;
        }


        if (hasTarjetaCredit == null || hasTarjetaCredit.equals("")) {
            showValidationError(radioGroupHasCreditCard.getId(), getString(R.string.opcion_requerido));
            isValid = false;
        }

        if (!radioBtnrgHasCreditCardYes.isChecked()) {
            if (numeroTarjeta.equals("")) {
                showValidationError(editNumTarjeta.getId(), getString(R.string.numero_tarjeta_vacio));
                isValid = false;
            } else if (numeroTarjeta.length() < 4 ) {
                showValidationError(editNumTarjeta.getId(), getString(R.string.numero_tarjeta_invalido));
                isValid = false;
            }
        }

        if (isValid) {
            onValidationSuccess();
        }

    }

    @Override
    public void showValidationError(int id, Object error) {
        switch (id) {
            case R.id.spEstadoCivil:
                errorEstadoCivil.setMessageText(error.toString());
                break;
            case R.id.spHijos:
                errorHijos.setMessageText(error.toString());
                break;
            case R.id.radioGroupHasCreditBank:
                errorCreditoBancario.setMessageText(error.toString());
                errorCreditoBancario.alingCenter();
                break;
            case R.id.radioGroupHasCreditCar:
                errorCreditoAutomotriz.setMessageText(error.toString());
                errorCreditoAutomotriz.alingCenter();
                break;
            case R.id.radioGroupHasCreditCard:
                errorTarjetaCredito.setMessageText(error.toString());
                errorTarjetaCredito.alingCenter();
                break;
            case R.id.editNumTarjeta :
                errorNumTarjeta.setMessageText(error.toString());
                errorNumTarjeta.alingCenter();
                break;
        }
        UI.hideKeyBoard(getActivity());
    }

    private void hideErrorMessage(int id) {
        switch (id) {
            case R.id.spEstadoCivil:
                errorEstadoCivil.setVisibilityImageError(false);
                break;
            case R.id.spHijos:
                errorHijos.setVisibilityImageError(false);
                break;
            case R.id.radioGroupHasCreditBank:
                errorCreditoBancario.setVisibilityImageError(false);
                break;
            case R.id.radioGroupHasCreditCar:
                errorCreditoAutomotriz.setVisibilityImageError(false);
                break;
            case R.id.radioGroupHasCreditCard:
                errorTarjetaCredito.setVisibilityImageError(false);
                break;
        }
    }

    @Override
    public void onValidationSuccess() {

        /*Guardamos datos en Singleton de registro.*/
        errorEstadoCivil.setVisibilityImageError(false);
        errorHijos.setVisibilityImageError(false);
        errorCreditoBancario.setVisibilityImageError(false);
        errorCreditoAutomotriz.setVisibilityImageError(false);
        errorTarjetaCredito.setVisibilityImageError(false);
        errorNumTarjeta.setVisibilityImageError(false);

        RegisterCupo registerCupo = RegisterCupo.getInstance();
        registerCupo.setEstadoCivil(estadoCivil);
        registerCupo.setIdEstadoCivil(idEstadoCivil);
        registerCupo.setHijos(numeroHijos);
        registerCupo.setIdHijos(idNumeroHijos);
        registerCupo.setCreditoBancario(Boolean.valueOf(hasCreditBank));
        registerCupo.setCreditoAutomotriz(Boolean.valueOf(hasCarCredit));
        registerCupo.setTarjetaCreditoBancario(Boolean.valueOf(hasTarjetaCredit));
        registerCupo.setNumeroTarjeta(numeroTarjeta);

        Log.e("Estado Civil", registerCupo.getEstadoCivil() );
        Log.e("Id Estado Civil", "" + registerCupo.getIdEstadoCivil());
        Log.e("Hijos", "" + registerCupo.getHijos());
        Log.e("Id Hijos", "" + registerCupo.getIdHijos());
        Log.e("Credito Bancario", "" + registerCupo.getCreditoBancario());
        Log.e("Credito Automotriz", "" + registerCupo.getCreditoAutomotriz());
        Log.e("Tarjeta Credito", "" + registerCupo.getTarjetaCreditoBancario());
        cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_REFERENCIA_FAMILIAR, null);

    }

    @Override
    public void getDataForm() {

        hasCreditBank = radioBtnrgHasCreditBankYes.isChecked() ? "false" : radioBtnrgHasCreditBankNo.isChecked() ? "true" : "";
        hasCarCredit =  radioBtnrgHasCreditCarYes.isChecked()  ? "false" :  radioBtnrgHasCreditCarNo.isChecked() ? "true" : "";
        hasTarjetaCredit =  radioBtnrgHasCreditCardYes.isChecked()  ? "false" :  radioBtnrgHasCreditCardNo.isChecked() ? "true" : "";

        numeroTarjeta = editNumTarjeta.getText();

        Log.e("Test", hasCreditBank);

        if (spEstadoCivil.getSelectedItemPosition() != 0) {
            estadoCivil = spEstadoCivil.getSelectedItem().toString();
            idEstadoCivil = spEstadoCivil.getSelectedItemPosition();
        } else {
            estadoCivil = "";
            idEstadoCivil = 0;
        }

        if (spHijos.getSelectedItemPosition() != 0) {
            numeroHijos = spHijos.getSelectedItem().toString();
            idNumeroHijos = spHijos.getSelectedItemPosition();
        } else {
            numeroHijos = "";
            idNumeroHijos = 0;
        }


    }
}
