package com.pagatodo.yaganaste.ui.adquirente.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.Space;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.interfaces.IEnumSpinner;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.OnCountrySelectedListener;
import com.pagatodo.yaganaste.interfaces.enums.Parentescos;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.LegalsDialog;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.ui.adquirente.managers.InformationAdicionalManager;
import com.pagatodo.yaganaste.ui.adquirente.presenters.InfoAdicionalPresenter;
import com.pagatodo.yaganaste.ui.adquirente.presenters.interfaces.IinfoAdicionalPresenter;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CountriesDialogFragment;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.TERMINOS;

/**
 * Created by Jordan on 03/08/2017.
 */

public class InformacionAdicionalFragment extends GenericFragment implements View.OnClickListener,
        InformationAdicionalManager, IOnSpinnerClick, RadioGroup.OnCheckedChangeListener,
        OnCountrySelectedListener, AdapterView.OnItemSelectedListener, INavigationView {

    @BindView(R.id.layoutEresMexa)
    LinearLayout layoutEresMexa;
    @BindView(R.id.errorRadioEresMexaMessage)
    ErrorMessage errorRadioEresMexaMessage;
    @BindView(R.id.radioEresMexa)
    RadioGroup radioEresMexa;
    @BindView(R.id.radioBtnEresMexaNo)
    RadioButton radioBtnEresMexaNo;
    @BindView(R.id.radioBtnEresMexaYes)
    RadioButton radioBtnEresMexaYes;
    @BindView(R.id.layoutPais)
    LinearLayout layoutPais;
    @BindView(R.id.editCountry)
    CustomValidationEditText editCountry;
    @BindView(R.id.errorCountryMessage)
    ErrorMessage errorCountryMessage;
    @BindView(R.id.errorRadioPublicServantMessage)
    ErrorMessage errorRadioPublicServantMessage;
    @BindView(R.id.radioPublicServant)
    RadioGroup radioPublicServant;
    @BindView(R.id.radioBtnPublicServantNo)
    RadioButton radioBtnPublicServantNo;
    @BindView(R.id.radioBtnPublicServantYes)
    RadioButton radioBtnPublicServantYes;
    @BindView(R.id.spaceFamiliar)
    Space spaceFamiliar;
    @BindView(R.id.layoutFamiliarCargoPublico)
    LinearLayout layoutFamiliarCargoPublico;
    @BindView(R.id.spParentesco)
    Spinner spParentesco;
    @BindView(R.id.errorParentescoMessage)
    ErrorMessage errorParentescoMessage;
    @BindView(R.id.editCargo)
    CustomValidationEditText editCargo;
    @BindView(R.id.errorCargoMessage)
    ErrorMessage errorCargoMessage;
    @BindView(R.id.txtLegales)
    StyleTextView txtLegales;
    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.btnNext)
    Button btnNext;

    private View rootView;
    private IinfoAdicionalPresenter infoAdicionalPresenter;
    private boolean isExtranjero;
    private boolean isMexaNaturalizado;
    private boolean hasFamiliaCargoPublico;
    private String cargo;
    private Countries paisNacimiento;
    private StatesSpinnerAdapter spinnerParentescoAdapter;
    private IEnumSpinner parentesco;

    public static InformacionAdicionalFragment newInstance() {
        InformacionAdicionalFragment fragment = new InformacionAdicionalFragment();
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
        rootView = inflater.inflate(R.layout.fragment_informacion_adicional, container, false);
        initViews();
        setValidationRules();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        isExtranjero = SingletonUser.getInstance().getDataUser().getUsuario().isExtranjero();

        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        if (isExtranjero) {
            layoutEresMexa.setVisibility(View.VISIBLE);
            spaceFamiliar.setVisibility(View.VISIBLE);
            radioBtnEresMexaYes.setChecked(true);
            editCountry.setFullOnClickListener(this);
            editCountry.setOnClickListener(this);
        }
        radioBtnPublicServantNo.setChecked(true);

        radioEresMexa.setOnCheckedChangeListener(this);
        radioPublicServant.setOnCheckedChangeListener(this);

        editCargo.getEditText().setImeOptions(EditorInfo.IME_ACTION_DONE);
        editCargo.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    editCargo.clearFocus();
                    UI.hideKeyBoard(getActivity());
                    return true;
                }
                return false;
            }
        });
        setClickLegales();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnNext:
                validateForm();
                break;
            case R.id.editCountry:
                onCountryClick();
                break;
            case R.id.editTextCustom:
                onCountryClick();
                break;
            case R.id.imageViewValidation:
                onCountryClick();
                break;
        }
    }

    private void onCountryClick() {
        infoAdicionalPresenter.getPaisesList();
        hideValidationError(R.id.editCountry);
    }

    @Override
    public void onSpinnerClick() {
        hideValidationError(R.id.spParentesco);
    }

    @Override
    public void setValidationRules() {
        editCargo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(R.id.editCargo);
                    editCargo.imageViewIsGone(true);
                } else {
                    if (TextUtils.isEmpty(editCargo.getText())) {
                        showValidationError(R.id.editCargo, getString(R.string.error_cargo));
                        editCargo.setIsInvalid();
                    } else {
                        hideValidationError(R.id.editCargo);
                        editCargo.setIsValid();
                    }
                }
            }
        });

        editCargo.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(R.id.editCargo);
                editCargo.imageViewIsGone(true);
            }
        });
    }

    @Override
    public void validateForm() {
        getDataForm();
        editCargo.clearFocus();


        boolean isValid = true;
        if (isExtranjero && !isMexaNaturalizado) {
            if (paisNacimiento == null) {
                showValidationError(R.id.editCountry, getString(R.string.datos_personal_pais));
                isValid = false;
            }
        }

        if (hasFamiliaCargoPublico) {
            if (parentesco == null || parentesco == Parentescos.PARENTESCO) {
                showValidationError(R.id.spParentesco, getString(R.string.error_parentesco));
                isValid = false;
            }

            if (TextUtils.isEmpty(cargo)) {
                showValidationError(R.id.editCargo, getString(R.string.error_cargo));
                isValid = false;
            }
        }

        if (isValid) {
            onValidationSuccess();
        }
    }

    @Override
    public void showValidationError(int id, Object o) {
        switch (id) {
            case R.id.editCountry:
                errorCountryMessage.setMessageText(o.toString());
                break;
            case R.id.spParentesco:
                errorParentescoMessage.setMessageText(o.toString());
                break;
            case R.id.editCargo:
                errorCargoMessage.setMessageText(o.toString());
                break;
        }
        UI.hideKeyBoard(getActivity());
    }

    @Override
    public void hideValidationError(int id) {
        switch (id) {
            case R.id.editCountry:
                errorCountryMessage.setVisibilityImageError(false);
                break;
            case R.id.spParentesco:
                errorParentescoMessage.setVisibilityImageError(false);
                break;
            case R.id.editCargo:
                errorCargoMessage.setVisibilityImageError(false);
                break;
        }
    }

    @Override
    public void onValidationSuccess() {
        Toast.makeText(getContext(), "Validation OK", Toast.LENGTH_LONG).show();
    }

    @Override
    public void getDataForm() {
        isMexaNaturalizado = radioBtnEresMexaYes.isChecked();
        hasFamiliaCargoPublico = radioBtnPublicServantYes.isChecked();

        if (hasFamiliaCargoPublico) {
            cargo = editCargo.getText();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radioBtnPublicServantNo:
                onHasFamiliarNoCheck();
                break;
            case R.id.radioBtnPublicServantYes:
                onHasFamiliarYesCheck();
                break;
            case R.id.radioBtnEresMexaNo:
                onIsMexaNoCheck();
                break;
            case R.id.radioBtnEresMexaYes:
                onIsMexaYesCheck();
                break;
        }
    }

    @Override
    public void onIsMexaYesCheck() {
        layoutPais.setVisibility(View.GONE);
        editCountry.setText("");
        editCountry.imageViewIsGone(false);
        editCountry.setDrawableImage(R.drawable.menu_canvas);

        paisNacimiento = null;
    }

    @Override
    public void onIsMexaNoCheck() {
        layoutPais.setVisibility(View.VISIBLE);
        editCountry.setText("");
        editCountry.imageViewIsGone(false);
        editCountry.setDrawableImage(R.drawable.menu_canvas);
    }

    @Override
    public void onHasFamiliarYesCheck() {
        layoutFamiliarCargoPublico.setVisibility(View.VISIBLE);
        if (spinnerParentescoAdapter == null) {
            spinnerParentescoAdapter = new StatesSpinnerAdapter(getContext(),
                    R.layout.spinner_layout, Parentescos.values(), this);
            spParentesco.setAdapter(spinnerParentescoAdapter);
            spParentesco.setOnItemSelectedListener(this);
        } else {
            spParentesco.setSelection(0);
        }

        cargo = "";
        editCargo.setText("");
        editCargo.imageViewIsGone(true);
    }

    @Override
    public void onHasFamiliarNoCheck() {
        layoutFamiliarCargoPublico.setVisibility(View.GONE);

        cargo = "";
        editCargo.setText("");
        editCargo.imageViewIsGone(true);

        if (spinnerParentescoAdapter != null) {
            spParentesco.setSelection(0);
        }

        parentesco = null;
    }

    @Override
    public void showDialogList(ArrayList<Countries> paises) {
        CountriesDialogFragment dialogFragment = CountriesDialogFragment.newInstance(paises);
        dialogFragment.setOnCountrySelectedListener(this);
        dialogFragment.show(getChildFragmentManager(), "FragmentDialog");
    }

    @Override
    public void onCountrySelectedListener(Countries item) {
        paisNacimiento = item;
        editCountry.setText(item.getPais());
        editCountry.setIsValid();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parentesco = spinnerParentescoAdapter.getItem(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void nextScreen(String event, Object data) {

    }

    @Override
    public void backScreen(String event, Object data) {

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

    private void setClickLegales() {
        SpannableString ss = new SpannableString(getString(R.string.terms_and_conditions_aditional_info));
        ClickableSpan span1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                LegalsDialog legalsDialog = LegalsDialog.newInstance(TERMINOS);
                legalsDialog.show(getActivity().getFragmentManager(), LegalsDialog.TAG);
            }
        };

        int startIndex = ss.toString().indexOf(getString(R.string.terminos_condiciones));
        ss.setSpan(span1, startIndex, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccentTransparent)), startIndex, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtLegales.setText(ss);
        txtLegales.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
