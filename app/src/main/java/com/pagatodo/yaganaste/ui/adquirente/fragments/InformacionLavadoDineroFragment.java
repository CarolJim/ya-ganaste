package com.pagatodo.yaganaste.ui.adquirente.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IEnumSpinner;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.OnCountrySelectedListener;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.LegalsDialog;
import com.pagatodo.yaganaste.ui.account.register.adapters.ColoniasArrayAdapter;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.ui.adquirente.managers.InformationAdicionalManager;
import com.pagatodo.yaganaste.ui.adquirente.presenters.InfoAdicionalPresenter;
import com.pagatodo.yaganaste.ui.adquirente.presenters.interfaces.IinfoAdicionalPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomClickableSpan;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_DOCUMENTS;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_ERROR;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.TERMINOS;
import static com.pagatodo.yaganaste.utils.Recursos.ADQ_PROCESS;

/**
 * Created by Omar on 31/10/2017.
 */

public class InformacionLavadoDineroFragment extends GenericFragment implements View.OnClickListener,
        InformationAdicionalManager, IOnSpinnerClick, RadioGroup.OnCheckedChangeListener,
        OnCountrySelectedListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.spnCobrosMensuales)
    Spinner spnCobrosMensuales;
    @BindView(R.id.errorCobrosMensuales)
    ErrorMessage errorCobrosMensuales;
    @BindView(R.id.spnMontoMensual)
    Spinner spnMontoMensual;
    @BindView(R.id.errorMontoMensual)
    ErrorMessage errorMontoMensual;
    @BindView(R.id.spnOrigenRecursos)
    Spinner spnOrigenRecursos;
    @BindView(R.id.errorOrigenRecursos)
    ErrorMessage errorOrigenRecursos;
    @BindView(R.id.spnDestinoRecursos)
    Spinner spnDestinoRecursos;
    @BindView(R.id.errorDestinoRecursos)
    ErrorMessage errorDestinoRecursos;
    @BindView(R.id.txtLegales)
    StyleTextView txtLegales;
    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.btnNext)
    Button btnNext;

    private View rootView;
    private IinfoAdicionalPresenter infoAdicionalPresenter;
    private List<String> cobrosMensual;
    private List<String> montoMensual;
    private List<String> origenRecursos;
    private List<String> destinoRecursos;
    private StatesSpinnerAdapter spinnerParentescoAdapter;
    private IEnumSpinner parentesco;
    private ArrayAdapter<String> adpaterCobros, adapterMonto, adpaterOrigen, adpaterDestino;

    public static InformacionLavadoDineroFragment newInstance() {
        InformacionLavadoDineroFragment fragment = new InformacionLavadoDineroFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        infoAdicionalPresenter = new InfoAdicionalPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_informacion_lavado_dinero, container, false);
        initViews();
        setValidationRules();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        cobrosMensual = Arrays.asList(getResources().getStringArray(R.array.cobros_mensual));
        montoMensual = Arrays.asList(getResources().getStringArray(R.array.monto_mensual));
        origenRecursos = Arrays.asList(getResources().getStringArray(R.array.origen_recursos));
        destinoRecursos = Arrays.asList(getResources().getStringArray(R.array.destino_recursos));

        adpaterCobros = new ColoniasArrayAdapter(getContext(), R.layout.spinner_layout, cobrosMensual, this);
        adapterMonto = new ColoniasArrayAdapter(getContext(), R.layout.spinner_layout, montoMensual, this);
        adpaterOrigen = new ColoniasArrayAdapter(getContext(), R.layout.spinner_layout, origenRecursos, this);
        adpaterDestino = new ColoniasArrayAdapter(getContext(), R.layout.spinner_layout, destinoRecursos, this);

        spnCobrosMensuales.setAdapter(adpaterCobros);
        spnMontoMensual.setAdapter(adapterMonto);
        spnOrigenRecursos.setAdapter(adpaterOrigen);
        spnDestinoRecursos.setAdapter(adpaterDestino);
        //fillAdapter();
        setClickLegales();
    }

    private void fillAdapter() {
        montoMensual.add(getString(R.string.hint_cobros_mensual));
        adapterMonto.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNext:
                validateForm();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

    }

    @Override
    public void onSpinnerClick() {
        hideValidationError(spnCobrosMensuales.getId());
        hideValidationError(spnMontoMensual.getId());
        hideValidationError(spnOrigenRecursos.getId());
        hideValidationError(spnDestinoRecursos.getId());

        spnCobrosMensuales.requestFocus();
        spnMontoMensual.requestFocus();
        spnOrigenRecursos.requestFocus();
        spnDestinoRecursos.requestFocus();
    }

    @Override
    public void onSubSpinnerClick() {
        hideValidationError(spnCobrosMensuales.getId());
        hideValidationError(spnMontoMensual.getId());
        hideValidationError(spnOrigenRecursos.getId());
        hideValidationError(spnDestinoRecursos.getId());

        spnCobrosMensuales.requestFocus();
        spnMontoMensual.requestFocus();
        spnOrigenRecursos.requestFocus();
        spnDestinoRecursos.requestFocus();

    }

    @Override
    public void hideKeyBoard() {

    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event, data); }

    @Override
    public void backScreen(String event, Object data) {

        onEventListener.onEvent(event, data);
    }

    @Override
    public void showLoader(String message) {

        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {

        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void showError(Object error) {
        onEventListener.onEvent(EVENT_SHOW_ERROR, error);
    }

    @Override
    public void onCountrySelectedListener(Countries item) {

    }

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {
        boolean isValid = true;

        if (spnCobrosMensuales.getSelectedItemPosition() == 0) {
            showValidationError(errorCobrosMensuales.getId(),getString(R.string.text_error_cobros_mensual));
            isValid = false;
        }

        if (spnMontoMensual.getSelectedItemPosition() == 0) {
            showValidationError(errorMontoMensual.getId(),getString(R.string.text_error_monto_mensual));
            isValid = false;
        }

        if (spnOrigenRecursos.getSelectedItemPosition() == 0) {
            showValidationError(errorOrigenRecursos.getId(),getString(R.string.text_error_origen_recursos));
            isValid = false;
        }

        if (spnDestinoRecursos.getSelectedItemPosition() == 0) {
            showValidationError(errorDestinoRecursos.getId(),getString(R.string.text_error_cobros_mensual));
            isValid = false;
        }

        if (isValid) {
            onValidationSuccess();
        }
    }

    @Override
    public void showValidationError(int id, Object error) {
        switch (id) {
            case R.id.errorCobrosMensuales:
                errorCobrosMensuales.setMessageText(error.toString());
                break;
            case R.id.errorMontoMensual:
                errorMontoMensual.setMessageText(error.toString());
                break;
            case R.id.errorOrigenRecursos:
                errorOrigenRecursos.setMessageText(error.toString());
                break;
            case R.id.errorDestinoRecursos:
                errorDestinoRecursos.setMessageText(error.toString());
                break;
        }
    }

    @Override
    public void hideValidationError(int id) {
        switch (id) {
            case R.id.spnCobrosMensuales:
                errorCobrosMensuales.setVisibilityImageError(false);
                break;
            case R.id.spnMontoMensual:
                errorMontoMensual.setVisibilityImageError(false);
                break;
            case R.id.spnOrigenRecursos:
                errorOrigenRecursos.setVisibilityImageError(false);
                break;
            case R.id.spnDestinoRecursos:
                errorDestinoRecursos.setVisibilityImageError(false);
                break;
        }
    }

    @Override
    public void onValidationSuccess() {
        RegisterAgent registerAgent = RegisterAgent.getInstance();
        registerAgent.setCobrosMensual(adpaterCobros.getItem(spnCobrosMensuales.getSelectedItemPosition()));
        registerAgent.setMontoMensual(adapterMonto.getItem(spnMontoMensual.getSelectedItemPosition()));
        registerAgent.setOrigenRecursos(adpaterOrigen.getItem(spnOrigenRecursos.getSelectedItemPosition()));
        registerAgent.setDestinoRecursos(adpaterDestino.getItem(spnDestinoRecursos.getSelectedItemPosition()));

        infoAdicionalPresenter.createUsuarioAdquirente();
    }

    @Override
    public void getDataForm() {

    }

    @Override
    public void onIsMexaYesCheck() {

    }

    @Override
    public void onIsMexaNoCheck() {

    }

    @Override
    public void onHasFamiliarYesCheck() {

    }

    @Override
    public void onHasFamiliarNoCheck() {

    }

    @Override
    public void showDialogList(ArrayList<Countries> paises) {

    }

    @Override
    public void onSuccessCreateAgente() {
        //App.getInstance().getPrefs().saveDataBool(ADQ_PROCESS, true);
        nextScreen(EVENT_GO_BUSSINES_DOCUMENTS, null);
    }

    @Override
    public void onErrorCreateAgente(ErrorObject error) {
        showError(error);
    }

    private void setClickLegales() {
        SpannableString ss = new SpannableString(getString(R.string.terms_and_conditions_aditional_info));
        CustomClickableSpan span1 = new CustomClickableSpan() {
            @Override
            public void onClick(View textView) {
                boolean isOnline = Utils.isDeviceOnline();
                if (isOnline) {
                    LegalsDialog legalsDialog = LegalsDialog.newInstance(TERMINOS);
                    legalsDialog.show(getActivity().getFragmentManager(), LegalsDialog.TAG);
                } else {
                    showDialogMesage(getResources().getString(R.string.no_internet_access));
                }

            }
        };
        int startIndex = ss.toString().indexOf(getString(R.string.terminos_condiciones));
        ss.setSpan(span1, startIndex, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccentTransparent)), startIndex, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtLegales.setText(ss);
        txtLegales.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void showDialogMesage(final String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }
}
