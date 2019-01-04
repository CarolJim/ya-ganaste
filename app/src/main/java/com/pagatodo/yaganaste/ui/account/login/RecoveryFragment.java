package com.pagatodo.yaganaste.ui.account.login;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.interfaces.RecoveryPasswordView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_SESSION;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class RecoveryFragment extends GenericFragment implements View.OnClickListener, RecoveryPasswordView, ValidationForms {

    @BindView(R.id.img_header_recover_pass)
    ImageView imgHeaderRecover;
    @BindView(R.id.img_recover_pass_blue)
    ImageView imgRecoverBlue;
    @BindView(R.id.text_email)
    TextInputLayout lytTxtEmail;
    @BindView(R.id.edtCorreoRegistrado)
    EditText edtCorreoRegistrado;
    @BindView(R.id.btnNextRecuperar)
    StyleButton btnRecuperarContrasenia;
    @BindView(R.id.btnBackRecuperar)
    StyleButton btnBack;
    @BindView(R.id.tvCorreoRegistrado)
    StyleTextView tvCorreoRegistrado;
    @BindView(R.id.txtHeaderRecovery)
    StyleTextView txtHeaderRecovery;
    private View rootview;
    private AccountPresenterNew accountPresenter;
    Preferencias prefs;
    String userEmail;

    private String correoRegistrado = "";

    public RecoveryFragment() {

    }

    public static RecoveryFragment newInstance() {
        RecoveryFragment fragmentRegister = new RecoveryFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        accountPresenter.setIView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_recuperar_contrasenia, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!prefs.containsData(HAS_SESSION) && RequestHeaders.getTokenauth().isEmpty()) {
            ((AccountActivity) getActivity()).changeToolbarVisibility(false);
        }
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        ((AccountActivity) getActivity()).changeToolbarVisibility(true);
        btnBack.setOnClickListener(this);
        btnRecuperarContrasenia.setOnClickListener(this);
        setValidationRules();

        prefs = App.getInstance().getPrefs();
        /**
         * Dependiendo si tenemos session activa o no, tomamos diferentes caminos.
         * 1 - Trabajo con un TextView cifrado con el correo
         * 2 - Trabajo con un EditText para acomodar el nueo texto
         */
        if (prefs.containsData(HAS_SESSION) && !RequestHeaders.getTokenauth().isEmpty()) {
            imgHeaderRecover.setVisibility(View.GONE);
            edtCorreoRegistrado.setVisibility(View.GONE);
            lytTxtEmail.setVisibility(View.GONE);

            // Asignamos el texto de enlace de correo
            String mTextEnlace = getContext().getResources()
                    .getString(R.string.enviaremos_enlace_restablecer);
            txtHeaderRecovery.setText(mTextEnlace);

            // Mostramos el correo con formato
            userEmail = RequestHeaders.getUsername();
            String userEmailCifrado = StringUtils.cifrarPass(userEmail);
            tvCorreoRegistrado.setVisibility(View.VISIBLE);
            tvCorreoRegistrado.setText(userEmailCifrado);
        } else {
            ((AccountActivity) getActivity()).showImageToolbar(true);
            imgRecoverBlue.setVisibility(View.GONE);
            tvCorreoRegistrado.setVisibility(View.GONE);
            // Asignamos el texto de enlace de correo
            String mTextEnlace = getContext().getResources()
                    .getString(R.string.ingresa_correo_registrado);
            txtHeaderRecovery.setText(mTextEnlace);
            edtCorreoRegistrado.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBackRecuperar:
                backScreen(null, null);
                break;
            case R.id.btnNextRecuperar:
                validateForm();

                break;
            default:
                break;
        }
    }


    @Override
    public void nextScreen(String event, Object data) {
    }

    @Override
    public void backScreen(String event, Object data) {
        ((LoginManagerContainerFragment) getParentFragment()).onBackActions();
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
        UI.hideKeyBoard(getActivity());
        UI.showErrorSnackBar(getActivity(), error.toString(), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void hideValidationError(int id) {
    }

    /*Implementación de ValidationForm*/
    @Override
    public void setValidationRules() {

        edtCorreoRegistrado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!ValidateForm.isValidEmailAddress(edtCorreoRegistrado.getText().toString())) {
                    hideValidationError(0);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void validateForm() {
        getDataForm();

        /**
         * Dependiendo si tenemos session activa o no, tomamos diferentes caminos.
         * 1 - Trabajo con un TextView cifrado con el correo
         * 2 - Trabajo con un EditText para acomodar el nueo texto
         */
        if (prefs.containsData(HAS_SESSION) && !RequestHeaders.getTokenauth().isEmpty()) {
            onValidationSuccess();
        } else {
            if (correoRegistrado.isEmpty()) {
                showValidationError(edtCorreoRegistrado.getId(), getString(R.string.ingresa_correo_registrado_requerido));
            } else if (!ValidateForm.isValidEmailAddress(edtCorreoRegistrado.getText().toString())) {
                showValidationError(edtCorreoRegistrado.getId(), getString(R.string.datos_usuario_correo_formato));
            } else {
                onValidationSuccess();
            }
        }
    }

    @Override
    public void showValidationError(int id, Object error) {
        UI.showErrorSnackBar(getActivity(), error.toString(), Snackbar.LENGTH_LONG);
        UI.hideKeyBoard(getActivity());
        setEnableViews(true);
    }

    @Override
    public void onValidationSuccess() {
        setEnableViews(false);
        accountPresenter.recoveryPassword(correoRegistrado);
    }

    @Override
    public void getDataForm() {
        /**
         * Dependiendo si tenemos session activa o no, tomamos diferentes caminos.
         * 1 - Trabajo con un TextView cifrado con el correo
         * 2 - Trabajo con un EditText para acomodar el nueo texto
         */
        if (prefs.containsData(HAS_SESSION) && !RequestHeaders.getTokenauth().isEmpty()) {
            correoRegistrado = userEmail;
        } else {
            correoRegistrado = edtCorreoRegistrado.getText().toString().trim();
        }
    }

    @Override
    public void recoveryPasswordSuccess(String message) {
        UI.showSuccessSnackBar(getActivity(), message, Snackbar.LENGTH_LONG);
        getActivity().onBackPressed();
    }

    @Override
    public void recoveryPasswordFailed(String message) {
        UI.showErrorSnackBar(getActivity(), message, Snackbar.LENGTH_LONG);
        setEnableViews(true);
    }

    private void setEnableViews(boolean isEnable) {
        edtCorreoRegistrado.setEnabled(isEnable);
        btnBack.setEnabled(isEnable);
        btnRecuperarContrasenia.setEnabled(isEnable);
    }
}

