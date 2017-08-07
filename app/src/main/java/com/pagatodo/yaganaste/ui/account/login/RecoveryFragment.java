package com.pagatodo.yaganaste.ui.account.login;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.interfaces.RecoveryPasswordView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;
import static com.pagatodo.yaganaste.utils.StringConstants.HAS_SESSION;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class RecoveryFragment extends GenericFragment implements View.OnClickListener, RecoveryPasswordView, ValidationForms {

    @BindView(R.id.edtCorreoRegistrado)
    CustomValidationEditText edtCorreoRegistrado;
    @BindView(R.id.btnNextRecuperar)
    StyleButton btnRecuperarContrasenia;
    @BindView(R.id.btnBackRecuperar)
    StyleButton btnBack;
    @BindView(R.id.errorMessage)
    ErrorMessage errorMessageView;
    @BindView(R.id.progressIndicator)
    ProgressLayout progressLayout;
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
    public void initViews() {
        ButterKnife.bind(this, rootview);
        errorMessageView.setVisibilityImageError(false);
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
            edtCorreoRegistrado.setVisibility(View.GONE);

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
        //onEventListener.onEvent(event, data);
    }

    @Override
    public void backScreen(String event, Object data) {
        //onEventListener.onEvent(event, data);
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
        errorMessageView.setMessageText(error.toString());
        UI.hideKeyBoard(getActivity());
    }

    @Override
    public void hideValidationError(int id) {
        errorMessageView.setVisibilityImageError(false);
    }

    /*Implementación de ValidationForm*/
    @Override
    public void setValidationRules() {

        edtCorreoRegistrado.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!edtCorreoRegistrado.isValidText()) {
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
                edtCorreoRegistrado.setIsInvalid();
            } else if (!edtCorreoRegistrado.isValidText()) {
                showValidationError(edtCorreoRegistrado.getId(), getString(R.string.datos_usuario_correo_formato));
                edtCorreoRegistrado.setIsInvalid();
            } else {
                onValidationSuccess();
            }
        }


    }

    @Override
    public void showValidationError(int id, Object error) {
        errorMessageView.setMessageText(error.toString());
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
            correoRegistrado = edtCorreoRegistrado.getText().trim();
        }
    }


    @Override
    public void recoveryPasswordSuccess(String message) {
        UI.showToastShort(message, getActivity());
        new Handler().postDelayed(new Runnable() {
            public void run() {
                backScreen(null, null);
            }
        }, DELAY_MESSAGE_PROGRESS);
        setEnableViews(true);
    }

    @Override
    public void recoveryPasswordFailed(String message) {
        UI.showToastShort(message, getActivity());
        setEnableViews(true);
    }

    private void setEnableViews(boolean isEnable) {
        edtCorreoRegistrado.setEnabled(isEnable);
        btnBack.setEnabled(isEnable);
        btnRecuperarContrasenia.setEnabled(isEnable);
    }
}

