package com.pagatodo.yaganaste.ui.account.login;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
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
import com.pagatodo.yaganaste.utils.StringConstants;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_RECOVERY_PASS;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.GENERO;
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

    @BindView(R.id.lyt_img_user)
    LinearLayout lyImgUser;

    @BindView(R.id.imgLoginExistProfile)
    CircleImageView imgLoginExistProfile;

    @BindView(R.id.textNameUser)
    StyleTextView textNameUser;

    @BindView(R.id.txt_set_credentials)
    StyleTextView txtSetCredentials;

    @BindView(R.id.editUserName)
    EditText edtUserName;

    @BindView(R.id.editUserPassword)
    EditText edtUserPass;

    @BindView(R.id.text_email)
    TextInputLayout text_email;

    @BindView(R.id.text_password)
    TextInputLayout text_password;

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
        ss.setSpan(new UnderlineSpan(), 26, 47, 0);
        txtLoginExistUserRecoverPass.setText(ss);

        if (!RequestHeaders.getTokenauth().isEmpty()) {
            textNameUser.setText("¡Hola " + prefs.loadData(StringConstants.NAME_USER) + "!");
            btnLogin.setText(getString(R.string.txt_iniciar_sesion));
            edtUserName.setText(RequestHeaders.getUsername());
            text_email.setVisibility(GONE);
            imgHeaderLogin.setVisibility(GONE);
            edtUserPass.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            textNameUser.setOnClickListener(this);
        } else {
            ((AccountActivity) getActivity()).changeToolbarVisibility(false);
            lyImgUser.setVisibility(GONE);
            imgLoginExistProfile.setVisibility(GONE);
            txtSetCredentials.setVisibility(GONE);
            textNameUser.setText(getString(R.string.set_credentials_login));
            edtUserName.setText(RequestHeaders.getUsername());
            text_email.setVisibility(VISIBLE);

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
                    text_email.setVisibility(VISIBLE);
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
            UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_LONG);
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
            dialogErrorShown = false;
            UI.showErrorSnackBar(getActivity(), error.toString(), Snackbar.LENGTH_LONG);
            edtUserName.clearFocus();
            edtUserPass.clearFocus();
            edtUserPass.setText("");
            password = "";
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
                    text_email.setBackgroundResource(R.drawable.inputtext_active);
                } else {
                    if (edtUserName.getText().toString().isEmpty() || !ValidateForm.isValidEmailAddress(edtUserName.getText().toString())) {
                        text_email.setBackgroundResource(R.drawable.inputtext_error);
                    } else if (ValidateForm.isValidEmailAddress(edtUserName.getText().toString())) {
                        text_email.setBackgroundResource(R.drawable.inputtext_normal);
                    }
                }
            }
        });

        edtUserPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    text_password.setBackgroundResource(R.drawable.inputtext_active);
                } else {
                    if (edtUserPass.toString().isEmpty()) {
                        text_password.setBackgroundResource(R.drawable.inputtext_error);
                    } else {
                        text_password.setBackgroundResource(R.drawable.inputtext_normal);
                    }
                }
            }
        });

        edtUserPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (prefs.loadDataBoolean(PASSWORD_CHANGE, false)) {
                    if (charSequence.toString().length() == 6) {
                        UI.hideKeyBoard(getActivity());
                        if (!UtilsNet.isOnline(getActivity())) {
                            UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_LONG);
                        } else {
                            //  Servicio para consumir usuario y contraseña
                            validateForm();
                            edtUserPass.setText("");
                        }
                    }
                }
                text_password.setBackgroundResource(R.drawable.inputtext_active);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Asignamos el OnEditorActionListener a este CustomEditext para el efecto de consumir el servicio
        edtUserPass.setOnEditorActionListener(new DoneOnEditorActionListener());
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
            text_email.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }

        if (!edtUserName.getText().toString().isEmpty() && !ValidateForm.isValidEmailAddress(edtUserName.getText().toString())) {
            errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.datos_usuario_correo) : errorMsg;
            text_email.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }

        if (!RequestHeaders.getTokenauth().isEmpty()) {
            if (prefs.loadDataBoolean(PASSWORD_CHANGE, false)) {
                if (password.isEmpty() || password.length() < 6) {
                    errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.password_required) : errorMsg;
                    text_password.setBackgroundResource(R.drawable.inputtext_error);
                    isValid = false;
                }
            } else {
                if (password.isEmpty()) {
                    errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.password_required) : errorMsg;
                    text_password.setBackgroundResource(R.drawable.inputtext_error);
                    isValid = false;
                }
            }
        } else {
            if (password.isEmpty()) {
                errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.password_required) : errorMsg;
                text_password.setBackgroundResource(R.drawable.inputtext_error);
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
        //versionOk();
    }

    @Override
    public void getDataForm() {
        username = edtUserName.getText().toString().trim();
        password = edtUserPass.getText().toString().trim();
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
        if (preferencias.loadData(GENERO)=="H"||preferencias.loadData(GENERO)=="h") {
            Picasso.with(getContext()).load(StringUtils.procesarURLString(mUserImage))
                    .placeholder(R.mipmap.icon_user).error(R.drawable.avatar_el)
                    .into(imgLoginExistProfile);
        }else if (preferencias.loadData(GENERO)=="M"||preferencias.loadData(GENERO)=="m"){
            Picasso.with(getContext()).load(StringUtils.procesarURLString(mUserImage))
                    .placeholder(R.mipmap.icon_user).error(R.drawable.avatar_ella)
                    .into(imgLoginExistProfile);
        }else {
            Picasso.with(getContext()).load(StringUtils.procesarURLString(mUserImage))
                    .placeholder(R.mipmap.icon_user).error(R.mipmap.icon_user)
                    .into(imgLoginExistProfile);
        }

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

