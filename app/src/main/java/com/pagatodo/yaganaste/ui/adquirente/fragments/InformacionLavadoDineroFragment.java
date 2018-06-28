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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.room_db.entities.Paises;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CuestionarioEntity;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CobrosMensualesResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IEnumSpinner;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.OnCountrySelectedListener;
import com.pagatodo.yaganaste.interfaces.enums.SpinnerPLD;
import com.pagatodo.yaganaste.ui._controllers.BussinesActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.LegalsDialog;
import com.pagatodo.yaganaste.ui.account.register.adapters.ColoniasArrayAdapter;
import com.pagatodo.yaganaste.ui.account.register.adapters.EnumSpinnerAdapter;
import com.pagatodo.yaganaste.ui.adquirente.managers.InformationAdicionalManager;
import com.pagatodo.yaganaste.ui.adquirente.presenters.InfoAdicionalPresenter;
import com.pagatodo.yaganaste.ui.adquirente.presenters.interfaces.IinfoAdicionalPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomClickableSpan;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.interfaces.enums.SpinnerPLD.SPINNER_PLD_COBROS;
import static com.pagatodo.yaganaste.interfaces.enums.SpinnerPLD.SPINNER_PLD_DESTINO;
import static com.pagatodo.yaganaste.interfaces.enums.SpinnerPLD.SPINNER_PLD_MONTOS;
import static com.pagatodo.yaganaste.interfaces.enums.SpinnerPLD.SPINNER_PLD_ORIGEN;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_DOCUMENTS;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_ERROR;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.TERMINOS;
import static com.pagatodo.yaganaste.utils.Recursos.ADQ_PROCESS;
import static com.pagatodo.yaganaste.utils.Recursos.PREGUNTA_COBROS;
import static com.pagatodo.yaganaste.utils.Recursos.PREGUNTA_DESTINO_RECURSOS;
import static com.pagatodo.yaganaste.utils.Recursos.PREGUNTA_MONTOS;
import static com.pagatodo.yaganaste.utils.Recursos.PREGUNTA_ORIGEN_RECURSOS;

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

    @BindView(R.id.txtcobromensual)
    LinearLayout txtcobromensual;
    @BindView(R.id.txtmontomensual)
    LinearLayout txtmontomensual;
    @BindView(R.id.txtorigen)
    LinearLayout txtorigen;
    @BindView(R.id.txtdestino)
    LinearLayout txtdestino;

    @BindView(R.id.cobromensual)
    StyleTextView cobromensual;
    @BindView(R.id.montomensual)
    StyleTextView montomensual;
    @BindView(R.id.origen)
    StyleTextView origen;
    @BindView(R.id.destino)
    StyleTextView destino;

    @BindView(R.id.imgcobromensual)
    ImageView imgcobromensual;
    @BindView(R.id.imgmontomensual)
    ImageView imgmontomensual;
    @BindView(R.id.imgtorigen)
    ImageView imgtorigen;
    @BindView(R.id.imgdestino)
    ImageView imgdestino;





    private View rootView;
    private IinfoAdicionalPresenter infoAdicionalPresenter;
    //private List<String> cobrosMensual;
    //private List<String> montoMensual;
    //private List<String> origenRecursos;
    //private List<String> destinoRecursos;
    private EnumSpinnerAdapter spinnerParentescoAdapter;
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

        setClickLegales();
        infoAdicionalPresenter.setSpinner(SPINNER_PLD_COBROS);
        imgcobromensual.setOnClickListener(view -> spnCobrosMensuales.performClick());
        imgmontomensual.setOnClickListener(view -> spnMontoMensual.performClick());
        imgtorigen.setOnClickListener(view -> spnOrigenRecursos.performClick());
        imgdestino.setOnClickListener(view -> spnDestinoRecursos.performClick());


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                backScreen(BussinesActivity.EVENT_GO_BUSSINES_MONEY_LAUNDERING_BACK, null);
                break;
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

        cobromensual.setVisibility(View.VISIBLE);
        montomensual.setVisibility(View.VISIBLE);
        origen.setVisibility(View.VISIBLE);
        destino.setVisibility(View.VISIBLE);


        cobromensual.setTextColor(getResources().getColor(R.color.colorAccent));
        montomensual.setTextColor(getResources().getColor(R.color.colorAccent));
        origen.setTextColor(getResources().getColor(R.color.colorAccent));
        destino.setTextColor(getResources().getColor(R.color.colorAccent));

    }

    @Override
    public void onSubSpinnerClick() {
        hideValidationError(spnCobrosMensuales.getId());
        hideValidationError(spnMontoMensual.getId());
        hideValidationError(spnOrigenRecursos.getId());
        hideValidationError(spnDestinoRecursos.getId());


        cobromensual.setVisibility(View.VISIBLE);
        montomensual.setVisibility(View.VISIBLE);
        origen.setVisibility(View.VISIBLE);
        destino.setVisibility(View.VISIBLE);

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
        onEventListener.onEvent(event, data);
    }

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
    public void onCountrySelectedListener(Paises item) {

    }

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {
        boolean isValid = true;

        if (spnCobrosMensuales.getSelectedItemPosition() == 0) {
            //showValidationError(errorCobrosMensuales.getId(), getString(R.string.text_error_cobros_mensual));
            txtcobromensual.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }

        if (spnMontoMensual.getSelectedItemPosition() == 0) {
            // showValidationError(errorMontoMensual.getId(), getString(R.string.text_error_monto_mensual));
            txtmontomensual.setBackgroundResource(R.drawable.inputtext_error);

            isValid = false;
        }

        if (spnOrigenRecursos.getSelectedItemPosition() == 0) {
            showValidationError(errorOrigenRecursos.getId(), getString(R.string.text_error_origen_recursos));
            txtorigen.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }

        if (spnDestinoRecursos.getSelectedItemPosition() == 0) {
            showValidationError(errorDestinoRecursos.getId(), getString(R.string.text_error_destino_mensual));
            txtdestino.setBackgroundResource(R.drawable.inputtext_error);
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
                txtcobromensual.setBackgroundResource(R.drawable.inputtext_normal);
                break;
            case R.id.spnMontoMensual:
                errorMontoMensual.setVisibilityImageError(false);
                txtmontomensual.setBackgroundResource(R.drawable.inputtext_normal);
                break;
            case R.id.spnOrigenRecursos:
                errorOrigenRecursos.setVisibilityImageError(false);
                txtorigen.setBackgroundResource(R.drawable.inputtext_normal);
                break;
            case R.id.spnDestinoRecursos:
                errorDestinoRecursos.setVisibilityImageError(false);
                txtdestino.setBackgroundResource(R.drawable.inputtext_normal);
                break;
        }
    }

    @Override
    public void onValidationSuccess() {
        RegisterAgent registerAgent = RegisterAgent.getInstance();
        registerAgent.getCuestionario().add(new CuestionarioEntity(PREGUNTA_COBROS, true, spnCobrosMensuales.getSelectedItemPosition()));
        registerAgent.getCuestionario().add(new CuestionarioEntity(PREGUNTA_MONTOS, true, spnMontoMensual.getSelectedItemPosition()));
        registerAgent.getCuestionario().add(new CuestionarioEntity(PREGUNTA_ORIGEN_RECURSOS, true, spnOrigenRecursos.getSelectedItemPosition()));
        registerAgent.getCuestionario().add(new CuestionarioEntity(PREGUNTA_DESTINO_RECURSOS, true, spnDestinoRecursos.getSelectedItemPosition()));

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
    public void onSuccessCreateAgente() {
        App.getInstance().getPrefs().saveDataBool(ADQ_PROCESS, true);
        nextScreen(EVENT_GO_BUSSINES_DOCUMENTS, null);
        infoAdicionalPresenter.updateSession();
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
        ss.setSpan(span1, 50, 71, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccentTransparent)), 50, 71, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

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

    @Override
    public void onSucessSpinnerList(List<CobrosMensualesResponse> Data, SpinnerPLD sp) {
        List<String> stringList = new ArrayList<>();
        if (sp == SPINNER_PLD_COBROS) {
            stringList.add(getString(R.string.hint_cobros_mensual));
            for (int i = 0; i < Data.size(); i++) {
                stringList.add(Data.get(i).getDescripcion());
            }

            adpaterCobros = new ColoniasArrayAdapter(getContext(), R.layout.spinner_layout, stringList, this);
            spnCobrosMensuales.setAdapter(adpaterCobros);
            infoAdicionalPresenter.setSpinner(SPINNER_PLD_MONTOS);
        } else if (sp == SPINNER_PLD_MONTOS) {
            stringList.add(getString(R.string.hint_monto_mensual_estimado));
            for (int i = 0; i < Data.size(); i++) {
                stringList.add(Data.get(i).getDescripcion());
            }
            adapterMonto = new ColoniasArrayAdapter(getContext(), R.layout.spinner_layout, stringList, this);
            spnMontoMensual.setAdapter(adapterMonto);

            infoAdicionalPresenter.setSpinner(SPINNER_PLD_ORIGEN);
        } else if (sp == SPINNER_PLD_ORIGEN) {
            stringList.add(getString(R.string.hint_origen_recursos));
            for (int i = 0; i < Data.size(); i++) {
                stringList.add(Data.get(i).getDescripcion());
            }
            adpaterOrigen = new ColoniasArrayAdapter(getContext(), R.layout.spinner_layout, stringList, this);
            spnOrigenRecursos.setAdapter(adpaterOrigen);
            infoAdicionalPresenter.setSpinner(SPINNER_PLD_DESTINO);

        } else if (sp == SPINNER_PLD_DESTINO) {
            stringList.add(getString(R.string.hint_destino_recursos));
            for (int i = 0; i < Data.size(); i++) {
                stringList.add(Data.get(i).getDescripcion());
            }
            adpaterDestino = new ColoniasArrayAdapter(getContext(), R.layout.spinner_layout, stringList, this);
            spnDestinoRecursos.setAdapter(adpaterDestino);
        }
    }

    @Override
    public void onErrorSpinnerList(SpinnerPLD sp) {
        showError(getString(R.string.text_error_spinner));
    }

    @Override
    public void onSucessContryList(ArrayList<Paises> paises) {

    }

    @Override
    public void onErroContryList() {

    }
}
