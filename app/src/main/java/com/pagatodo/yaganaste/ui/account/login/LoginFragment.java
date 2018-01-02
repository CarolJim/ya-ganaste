package com.pagatodo.yaganaste.ui.account.login;


import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.pagatodo.yaganaste.utils.AsignarContraseñaTextWatcher;
import com.pagatodo.yaganaste.utils.StringConstants;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_BLOCK_CARD;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_QUICK_PAYMENT;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_RECOVERY_PASS;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_SECURE_CODE;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.HUELLA_FAIL;
import static com.pagatodo.yaganaste.utils.Recursos.PASSWORD_CHANGE;
import static com.pagatodo.yaganaste.utils.Recursos.TECLADO_CUSTOM;
import static com.pagatodo.yaganaste.utils.Recursos.URL_PHOTO_USER;
import static com.pagatodo.yaganaste.utils.StringConstants.ADQUIRENTE_APPROVED;
import static com.pagatodo.yaganaste.utils.StringConstants.HAS_SESSION;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class LoginFragment extends GenericFragment implements View.OnClickListener, ILoginView,
        ValidationForms {
    private Preferencias prefs = App.getInstance().getPrefs();
    @BindView(R.id.imgLoginExistProfile)
    CircleImageView imgLoginExistProfile;

    @BindView(R.id.textNameUser)
    StyleTextView textNameUser;

    @BindView(R.id.txtBlockCard)
    StyleTextView txtBlockCard;

    @BindView(R.id.editUserName)
    CustomValidationEditText edtUserName;

    @BindView(R.id.editUserPassword)
    CustomValidationEditText edtUserPass;

    @BindView(R.id.btnLoginExistUser)
    StyleButton btnLogin;

    @BindView(R.id.txtLoginExistUserRecoverPass)
    StyleTextView txtLoginExistUserRecoverPass;

    @BindView(R.id.blockCard)
    LinearLayout blockCard;

    @BindView(R.id.accessCode)
    LinearLayout accessCode;

    @BindView(R.id.quickPayment)
    LinearLayout quickPayment;

    @BindView(R.id.customkeyboard)
    LinearLayout customkeyboard;

    @BindView(R.id.keyboard_view)
    CustomKeyboardView keyboardView;

    @BindView(R.id.linerar_principal)
    LinearLayout linerar_principal;



    LinearLayout layout_control;
    TextView tv1Num;
    TextView tv2Num;
    TextView tv3Num;
    TextView tv4Num;
    TextView tv5Num;
    TextView tv6Num;
    private String nip = "";
    private Keyboard keyboard;
    ImageView asignar_iv1;
    private static int PIN_LENGHT = 6;
    @BindView(R.id.asignar_edittext)
    CustomValidationEditText edtPin;

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
        prefs.saveDataBool(HUELLA_FAIL,true);
        prefs.saveDataBool(TECLADO_CUSTOM,false);
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
        layout_control = (LinearLayout) rootview.findViewById(R.id.asignar_control_layout_login);


        customkeyboard.setOnClickListener(this);
        linerar_principal.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        blockCard.setOnClickListener(this);
        accessCode.setOnClickListener(this);
        quickPayment.setOnClickListener(this);
        txtLoginExistUserRecoverPass.setOnClickListener(this);


        if (!RequestHeaders.getTokenauth().isEmpty()) {
            if (prefs.loadDataBoolean(PASSWORD_CHANGE,false) ){

                DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int widthp = metrics.widthPixels; // ancho absoluto en pixels
                int paramentroT = widthp / 3;
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.width = paramentroT;
                params.height = paramentroT;
                imgLoginExistProfile.setLayoutParams(params);


                Preferencias preferencias = App.getInstance().getPrefs();
                textNameUser.setText(preferencias.loadData(StringConstants.SIMPLE_NAME));
                edtUserName.setText(RequestHeaders.getUsername());
                edtUserName.setVisibility(GONE);
                textNameUser.setOnClickListener(this);
                edtUserPass.setVisibility(GONE);
                customkeyboard.setVisibility(VISIBLE);
                keyboardView.setKeyBoard(getActivity(), R.xml.keyboard_nip);
                keyboardView.setPreviewEnabled(false);
               // keyboardView.showCustomKeyboard(rootview);
                btnLogin.setVisibility(VISIBLE);
                //imageView.setVisibility(View.GONE);
                tv1Num = (TextView) rootview.findViewById(R.id.asignar_tv1);
                tv2Num = (TextView) rootview.findViewById(R.id.asignar_tv2);
                tv3Num = (TextView) rootview.findViewById(R.id.asignar_tv3);
                tv4Num = (TextView) rootview.findViewById(R.id.asignar_tv4);
                tv5Num = (TextView) rootview.findViewById(R.id.asignar_tv5);
                tv6Num = (TextView) rootview.findViewById(R.id.asignar_tv6);

                // EditTExt oculto que procesa el PIN y sirve como ancla para validacion
                // Se le asigna un TextWatcher personalizado para realizar las oepraciones
                edtPin = (CustomValidationEditText) rootview.findViewById(R.id.asignar_edittext);
                edtPin.setMaxLength(6); // Se asigna un maximo de 4 caracteres para no tener problrmas
                edtPin.addCustomTextWatcher(new AsignarContraseñaTextWatcher(edtPin, tv1Num, tv2Num, tv3Num, tv4Num, tv5Num, tv6Num));
                edtPin.addCustomTextWatcher(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().length() == 6) {
                            keyboardView.hideCustomKeyboard();
                          //  Servicio para consumir usuario y contraseña
                            validateForm();
                           edtPin.setText("");
                           edtPin.isFocused();

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });




                edtPin.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        EditText edittext = (EditText) v;
                        int inType = edittext.getInputType();       // Backup the input type
                        edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                        edittext.onTouchEvent(event);               // Call native handler
                        keyboardView.showCustomKeyboard(v);
                        edittext.setInputType(inType);              // Restore input type
                        return true; // Consume touch event
                    }
                });
           //     btnNextAsignarPin.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {validateForm()}});
                setValidationRules();
             //   keyboardView.showCustomKeyboard(rootview);
                edtPin.requestEditFocus();

            }else {
                textNameUser.setText(preferencias.loadData(StringConstants.SIMPLE_NAME));
                edtUserName.setText(RequestHeaders.getUsername());
                edtUserName.setVisibility(GONE);
                textNameUser.setOnClickListener(this);
            }

        } else {

            edtUserName.setText(RequestHeaders.getUsername());
            edtUserName.setVisibility(VISIBLE);
            textNameUser.setVisibility(GONE);

        }
        setValidationRules();

        // Mostramos los elementos de facil acceso, solo si tenemos session
        Preferencias prefs = App.getInstance().getPrefs();
        if (prefs.containsData(HAS_SESSION) && !RequestHeaders.getTokenauth().isEmpty()) {
            if (App.getInstance().isCancel()) {
                blockCard.setVisibility(GONE);
            } else {
                blockCard.setVisibility(VISIBLE);
            }
            accessCode.setVisibility(View.VISIBLE);
            boolean isAdquirente = prefs.containsData(ADQUIRENTE_APPROVED);
            if (isAdquirente) {
                quickPayment.setVisibility(View.VISIBLE);
            } else {
                quickPayment.setVisibility(View.GONE);
            }
        } else {
            blockCard.setVisibility(View.INVISIBLE);
            accessCode.setVisibility(View.INVISIBLE);
            quickPayment.setVisibility(View.INVISIBLE);
        }

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
                    textNameUser.setVisibility(GONE);
                    edtUserName.setText(RequestHeaders.getUsername());
                    edtUserName.setVisibility(VISIBLE);
                }
                break;
            case R.id.blockCard:
                nextScreen(EVENT_BLOCK_CARD, null);
                break;
            case R.id.accessCode:
                nextScreen(EVENT_SECURE_CODE, null);
                break;
            case R.id.quickPayment:
                nextScreen(EVENT_QUICK_PAYMENT, null);
                break;
            case R.id.linerar_principal:
                keyboardView.hideCustomKeyboard();
                btnLogin.setVisibility(VISIBLE);
                break;
            case R.id.customkeyboard:
                keyboardView.showCustomKeyboard(rootview);
                btnLogin.setVisibility(GONE);
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

                if (nip.isEmpty() || nip.length() < 6) {
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
        }else {
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
        if (!RequestHeaders.getTokenauth().isEmpty()) {
            if (prefs.loadDataBoolean(PASSWORD_CHANGE, false)) {
                accountPresenter.login(username, nip); // Realizamos el  Login

            }else {
                accountPresenter.login(username, password); // Realizamos el  Login
            }
        }else {
            accountPresenter.login(username, password); // Realizamos el  Login
        }
    }

    @Override
    public void getDataForm() {
        username = edtUserName.getText().trim();
        password = edtUserPass.getText().trim();
        nip = edtPin.getText().toString().trim();
    }

    @Override
    public void loginSucced() {

            App.getInstance().getStatusId();
            SingletonUser.getInstance().setCardStatusId(null);
            Intent intentLogin = new Intent(getActivity(), TabActivity.class);
            startActivity(intentLogin);
            getActivity().finish();

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
        edtPin.requestEditFocus();
        if (!RequestHeaders.getTokenauth().isEmpty()) {
            edtUserName.setText(RequestHeaders.getUsername());

        }

        if (preferencias.containsData(HAS_SESSION) && !RequestHeaders.getTokenauth().isEmpty()) {
            updatePhoto();
        }

        // Cambiamos el estado de mostrar el bloqueo de tarjeta
        // Consultamos el estado del Singleton, que tiene el estado de nuestra tarjeta
        String cardStatusId = App.getInstance().getStatusId();
        if (cardStatusId.equals("1")) {
            // Significa que la card esta bloqueada, despues de la operacion pasa a desbloqueada
            txtBlockCard.setText(getContext().getResources().getString(R.string.bloquear_tarjeta));
        } else {
            // Significa que la card esta desbloqueada, despues de la operacion pasa a bloqueada
            txtBlockCard.setText(getContext().getResources().getString(R.string.desbloquear_tarjeta));
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

