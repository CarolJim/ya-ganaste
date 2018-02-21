package com.pagatodo.yaganaste.ui.account.login;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.ILoginView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.StringConstants;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_RECOVERY_PASS;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.HUELLA_FAIL;
import static com.pagatodo.yaganaste.utils.Recursos.PASSWORD_CHANGE;
import static com.pagatodo.yaganaste.utils.Recursos.URL_PHOTO_USER;
import static com.pagatodo.yaganaste.utils.StringConstants.HAS_SESSION;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class LoginFragment extends GenericFragment implements View.OnClickListener, ILoginView,
        ValidationForms {
    private Preferencias prefs = App.getInstance().getPrefs();

    @BindView(R.id.img_header_yg_login)
    ImageView imgHeaderLogin;

    @BindView(R.id.imgLoginExistProfile)
    CircleImageView imgLoginExistProfile;

    @BindView(R.id.textNameUser)
    StyleTextView textNameUser;

    @BindView(R.id.txt_set_credentials)
    StyleTextView txtSetCredentials;

    @BindView(R.id.editUserName)
    CustomValidationEditText edtUserName;

    @BindView(R.id.editUserPassword)
    CustomValidationEditText edtUserPass;

    @BindView(R.id.btnLoginExistUser)
    StyleButton btnLogin;

    @BindView(R.id.txtLoginExistUserRecoverPass)
    StyleTextView txtLoginExistUserRecoverPass;

    @BindView(R.id.linerar_principal)
    LinearLayout linerar_principal;

    @BindView(R.id.txtVersionApp)
    TextView txtVersionApp;

    private View rootview;
    private AccountPresenterNew accountPresenter;

    private String username;
    private String password;
    private Preferencias preferencias;
    private boolean dialogErrorShown = false;

    public static LoginFragment newInstance() {
        LoginFragment fragmentRegister = new LoginFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        accountPresenter.setIView(this);
        prefs.saveDataBool(HUELLA_FAIL, true);
        this.preferencias = App.getInstance().getPrefs();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            if (accountPresenter != null) {
                accountPresenter.setIView(this);
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootview == null) {
            rootview = inflater.inflate(R.layout.fragment_login, container, false);
            initViews();
        }
        return rootview;
    }


    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        linerar_principal.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        txtLoginExistUserRecoverPass.setOnClickListener(this);
        SpannableString ss;
        ss = new SpannableString(getString(R.string.recover_pass));
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTituloDialog)), 26, 47, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtLoginExistUserRecoverPass.setText(ss);

        if (!RequestHeaders.getTokenauth().isEmpty()) {
            textNameUser.setText("¡Hola " + preferencias.loadData(StringConstants.SIMPLE_NAME) + "!");
            btnLogin.setText(getString(R.string.txt_iniciar_sesion));
            edtUserName.setText(RequestHeaders.getUsername());
            edtUserName.setVisibility(GONE);
            imgHeaderLogin.setVisibility(GONE);
            textNameUser.setOnClickListener(this);
        } else {
            ((AccountActivity) getActivity()).changeToolbarVisibility(false);
            imgLoginExistProfile.setVisibility(GONE);
            txtSetCredentials.setVisibility(GONE);
            textNameUser.setText(getString(R.string.set_credentials_login));
            edtUserName.setText(RequestHeaders.getUsername());
            edtUserName.setVisibility(VISIBLE);

        }
        setValidationRules();
        txtVersionApp.setText("Versión: " + BuildConfig.VERSION_NAME);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLoginExistUser:
                actionBtnLogin();
                break;
            case R.id.txtLoginExistUserRecoverPass:
                nextScreen(EVENT_RECOVERY_PASS, null);
                break;
            case R.id.textNameUser:
                if (BuildConfig.DEBUG) {
                    edtUserName.setText(RequestHeaders.getUsername());
                    edtUserName.setVisibility(VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    private void actionBtnLogin() {
        if (UtilsNet.isOnline(getActivity())) {
            validateForm();
        } else {
            UI.createSimpleCustomDialog("Error", getString(R.string.no_internet_access), getFragmentManager(), getFragmentTag());
        }
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
        if (!dialogErrorShown) {
            dialogErrorShown = true;
            UI.createSimpleCustomDialogNoCancel(getString(R.string.title_error),
                    error.toString(), getFragmentManager(), new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {
                            edtUserName.clearFocus();
                            edtUserPass.clearFocus();
                            edtUserPass.setText("");
                            password = "";
                            dialogErrorShown = false;
                        }

                        @Override
                        public void actionCancel(Object... params) {
                        }
                    });
        }
        setEnableViews(true);
    }

    /*Implementación de ValidationForm*/
    @Override
    public void setValidationRules() {
        edtUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edtUserName.imageViewIsGone(true);
                } else {
                    if (edtUserName.getText().isEmpty() || !edtUserName.isValidText()) {
                        edtUserName.setIsInvalid();
                    } else if (edtUserName.isValidText()) {
                        edtUserName.setIsValid();
                    }
                }
            }
        });

        edtUserName.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                edtUserName.imageViewIsGone(true);
            }
        });

        edtUserPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edtUserPass.imageViewIsGone(true);
                } else {
                    if (edtUserPass.getText().isEmpty() || !edtUserPass.isValidText()) {
                        edtUserPass.setIsInvalid();
                    } else if (edtUserPass.isValidText()) {
                        edtUserPass.setIsValid();
                    }
                }
            }
        });

        edtUserPass.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                edtUserPass.imageViewIsGone(true);
            }
        });

        // Asignamos el OnEditorActionListener a este CustomEditext para el efecto de consumir el servicio
        edtUserPass.addCustomEditorActionListener(new DoneOnEditorActionListener());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;
        String errorMsg = null;

        if (username.isEmpty()) {
            errorMsg = getString(R.string.datos_usuario_correo);
            edtUserName.setIsInvalid();
            isValid = false;
        }

        if (!edtUserName.getText().isEmpty() && !edtUserName.isValidText()) {
            errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.datos_usuario_correo) : errorMsg;
            edtUserName.setIsInvalid();
            isValid = false;
        }

        if (!RequestHeaders.getTokenauth().isEmpty()) {
            if (prefs.loadDataBoolean(PASSWORD_CHANGE, false)) {
                if (password.isEmpty() || password.length() < 6) {
                    errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.password_required) : errorMsg;
                    edtUserPass.setIsInvalid();
                    isValid = false;
                }
            } else {
                if (password.isEmpty()) {
                    errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.password_required) : errorMsg;
                    edtUserPass.setIsInvalid();
                    isValid = false;
                }
            }
        } else {
            if (password.isEmpty()) {
                errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.password_required) : errorMsg;
                edtUserPass.setIsInvalid();
                isValid = false;
            }

        }

        if (isValid) {
            onValidationSuccess();
        } else {
            showError(errorMsg);
        }

    }


    @Override
    public void showValidationError(int id, Object error) {
        UI.showToastShort(error.toString(), getActivity());
        setEnableViews(true);
    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
        setEnableViews(false);
        accountPresenter.validateVersion();
    }

    @Override
    public void getDataForm() {
        username = edtUserName.getText().trim();
        password = edtUserPass.getText().trim();
    }

    @Override
    public void loginSucced() {
        App.getInstance().getStatusId();
        SingletonUser.getInstance().setCardStatusId(null);
        Intent intentLogin = new Intent(getActivity(), TabActivity.class);
        startActivity(intentLogin);
        getActivity().finish();
    }

    @Override
    public void versionOk() {
        accountPresenter.login(username, password); // Realizamos el  Login
    }

    @Override
    public void forceUpdate() {
        setEnableViews(true);
        UI.createSimpleCustomDialog(getString(R.string.title_update),
                getString(R.string.text_update_forced), getFragmentManager(), new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse("market://details?id=" + App.getContext().getPackageName()));
                        startActivity(i);
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                }, true, true);
    }

    @Override
    public void warningUpdate() {
        setEnableViews(true);
        UI.createSimpleCustomDialog(getString(R.string.title_update),
                getString(R.string.text_update_warn), getFragmentManager(), new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse("market://details?id=" + App.getContext().getPackageName()));
                        startActivity(i);
                    }

                    @Override
                    public void actionCancel(Object... params) {
                        versionOk();
                    }
                }, true, true);
    }

    private void setEnableViews(boolean isEnable) {
        edtUserName.setEnabled(isEnable);
        edtUserPass.setEnabled(isEnable);
        btnLogin.setEnabled(isEnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        accountPresenter.setIView(this);
        if (!RequestHeaders.getTokenauth().isEmpty()) {
            edtUserName.setText(RequestHeaders.getUsername());
        }
        if (preferencias.containsData(HAS_SESSION) && !RequestHeaders.getTokenauth().isEmpty()) {
            updatePhoto();
        }
    }

    /**
     * Codigo para hacer Set en la imagen de preferencias con la foto actual
     */
    private void updatePhoto() {
        String mUserImage = preferencias.loadData(URL_PHOTO_USER);
        Glide.with(getContext()).load(StringUtils.procesarURLString(mUserImage))
                .placeholder(R.mipmap.icon_user).error(R.mipmap.icon_user)
                .dontAnimate().into(imgLoginExistProfile);
    }

    /**
     * Encargada de reaccionar al codigo de pusacion KEYCODE_ENDCALL=6 para consumir el servicio
     */
    private class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == KeyEvent.KEYCODE_ENDCALL) {
                // Toast.makeText(getContext(), "Click Enter", Toast.LENGTH_SHORT).show();
                actionBtnLogin();
            }
            return false;
        }
    }
}

