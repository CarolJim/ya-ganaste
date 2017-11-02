package com.pagatodo.yaganaste.ui.adquirente.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IEnumSpinner;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.OnCountrySelectedListener;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.LegalsDialog;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.ui.adquirente.managers.InformationAdicionalManager;
import com.pagatodo.yaganaste.ui.adquirente.presenters.InfoAdicionalPresenter;
import com.pagatodo.yaganaste.ui.adquirente.presenters.interfaces.IinfoAdicionalPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomClickableSpan;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.TERMINOS;

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
    private String montoMensual, cobroMensual, origenRecursos, destinoRecursos;
    private StatesSpinnerAdapter spinnerParentescoAdapter;
    private IEnumSpinner parentesco;

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
        setClickLegales();
    }

    @Override
    public void onClick(View v) {

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

    }

    @Override
    public void onSubSpinnerClick() {

    }

    @Override
    public void hideKeyBoard() {

    }

    @Override
    public void nextScreen(String event, Object data) { onEventListener.onEvent(event, data); }

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

    }

    @Override
    public void onCountrySelectedListener(Countries item) {

    }

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {

    }

    @Override
    public void showValidationError(int id, Object o) {

    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
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

    }

    @Override
    public void onErrorCreateAgente(ErrorObject error) {

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
