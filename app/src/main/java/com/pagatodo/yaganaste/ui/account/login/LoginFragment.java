package com.pagatodo.yaganaste.ui.account.login;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.ILoginView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.AsignarNipTextWatcher;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_RECOVERY_PASS;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_STATUS;
import static com.pagatodo.yaganaste.utils.Recursos.GENERO;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_CONFIG_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_SESSION;
import static com.pagatodo.yaganaste.utils.Recursos.HUELLA_FAIL;
import static com.pagatodo.yaganaste.utils.Recursos.IS_OPERADOR;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.NAME_USER;
import static com.pagatodo.yaganaste.utils.Recursos.PASSWORD_CHANGE;
import static com.pagatodo.yaganaste.utils.Recursos.URL_PHOTO_USER;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class LoginFragment extends GenericFragment implements View.OnClickListener, ILoginView,
        ValidationForms ,AsignarNipTextWatcher.changeTxtWtxher {
    private Preferencias prefs = App.getInstance().getPrefs();

    @BindView(R.id.img_header_yg_login)
    ImageView imgHeaderLogin;

    @BindView(R.id.txtContrasena)
    StyleTextView txtContrasena;

    @BindView(R.id.lyt_img_user)
    LinearLayout lyImgUser;

    @BindView(R.id.llay_eye_pass)
    LinearLayout llay_eye_pass;

    @BindView(R.id.llypass)
    LinearLayout llypass_pass;

    @BindView(R.id.asignar_control_layout)
    LinearLayout asignar_control_layout;

    @BindView(R.id.imgLoginExistProfile)
    CircleImageView imgLoginExistProfile;

    @BindView(R.id.textNameUser)
    StyleTextView textNameUser;

    @BindView(R.id.asignar_edittext)
    EditText asignar_edittext;

    @BindView(R.id.editUserPasswordnew)
    EditText editUserPasswordnew;

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


    @BindView(R.id.text_passwordnew)
    TextInputLayout text_passwordnew;

    @BindView(R.id.btnLoginExistUser)
    StyleButton btnLogin;

    @BindView(R.id.eye_img)
    ImageView eye_img;

    @BindView(R.id.asignar_tv1)
    TextView tv1Num;
    @BindView(R.id.asignar_tv2)
    TextView tv2Num;
    @BindView(R.id.asignar_tv3)
    TextView tv3Num;
    @BindView(R.id.asignar_tv4)
    TextView tv4Num;
    @BindView(R.id.asignar_tv5)
    TextView tv5Num;
    @BindView(R.id.asignar_tv6)
    TextView tv6Num;
    Bitmap bitmapBullet;
    @BindView(R.id.txtLoginExistUserRecoverPass)
    StyleTextView txtLoginExistUserRecoverPass;

    @BindView(R.id.txtVersionApp)
    TextView txtVersionApp;

    @BindView(R.id.txtLoginOperadorOtroUsuario)
    StyleTextView txtLoginOperadorOtroUsuario;

    private View rootview;
    private AccountPresenterNew accountPresenter;

    private String username;
    private String password;
    private Preferencias preferencias;
    private boolean dialogErrorShown = false;

    private boolean passwordshow = true;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountPresenter = ((AccountActivity) Objects.requireNonNull(getActivity())).getPresenter();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        btnLogin.setOnClickListener(this);
        txtLoginExistUserRecoverPass.setOnClickListener(this);
        SpannableString ss;
        ss = new SpannableString(getString(R.string.recover_pass));
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTituloDialog)), 0, 25, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new UnderlineSpan(), 0, 25, 0);
        txtLoginExistUserRecoverPass.setText(ss);
        txtLoginOperadorOtroUsuario.setOnClickListener(this);



        asignar_edittext.addTextChangedListener(new AsignarNipTextWatcher(asignar_edittext, tv1Num, tv2Num, tv3Num, tv4Num, tv5Num, tv6Num,this));

        asignar_control_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                asignar_edittext.requestFocusFromTouch();
                InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                lManager.showSoftInput(asignar_edittext, 0);


                return false;
            }
        });

        llay_eye_pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                bitmapBullet = BitmapFactory.decodeResource(App.getContext().getResources(),
                        R.drawable.ico_asterisk_white);
                int medidaTextSize = 0;

                /**
                 * Obtenemos la resolucion de la pantalla y enviamos la medida necesaria
                 */
                DisplayMetrics metrics = App.getContext().getResources().getDisplayMetrics();
                switch (metrics.densityDpi) {
                    case DisplayMetrics.DENSITY_LOW:
                        break;
                    case DisplayMetrics.DENSITY_MEDIUM:
                        medidaTextSize = obtenerMedidas(tv1Num, 2);
                        break;
                    case DisplayMetrics.DENSITY_HIGH:
                        medidaTextSize = obtenerMedidas(tv1Num, 3);
                        break;
                    case DisplayMetrics.DENSITY_XHIGH:
                        medidaTextSize = obtenerMedidas(tv1Num, 4);
                        break;
                    case DisplayMetrics.DENSITY_XXHIGH:
                        medidaTextSize = obtenerMedidas(tv1Num, 5);
                        break;
                    case DisplayMetrics.DENSITY_XXXHIGH:
                        medidaTextSize = obtenerMedidas(tv1Num, 5);
                        break;
                    default:
                        medidaTextSize = obtenerMedidas(tv1Num, 5);
                        break;
                }

                bitmapBullet = Bitmap.createScaledBitmap(bitmapBullet, medidaTextSize, medidaTextSize, true);


                String pass = asignar_edittext.getText().toString();
                if (passwordshow) {
                    passwordshow=false;
                }else {
                    passwordshow=true;
                }
                    if (!passwordshow) {

                    if (pass.length()==1)
                        tv1Num.setText(pass.substring(0, 1));
                        if (pass.length()==2) {
                            tv1Num.setText(pass.substring(0, 1));
                            tv2Num.setText(pass.substring(1, 2));
                        }
                        if (pass.length()==3){
                            tv1Num.setText(pass.substring(0, 1));
                            tv2Num.setText(pass.substring(1, 2));
                            tv3Num.setText(pass.substring(2, 3));
                        }

                        if (pass.length()==4){
                            tv1Num.setText(pass.substring(0, 1));
                            tv2Num.setText(pass.substring(1, 2));
                            tv3Num.setText(pass.substring(2, 3));
                            tv4Num.setText(pass.substring(3, 4));
                        }

                        if (pass.length()==5){
                            tv1Num.setText(pass.substring(0, 1));
                            tv2Num.setText(pass.substring(1, 2));
                            tv3Num.setText(pass.substring(2, 3));
                            tv4Num.setText(pass.substring(3, 4));
                            tv5Num.setText(pass.substring(4, 5));
                        }
                        if (pass.length()==6)
                        {
                            tv1Num.setText(pass.substring(0, 1));
                            tv2Num.setText(pass.substring(1, 2));
                            tv3Num.setText(pass.substring(2, 3));
                            tv4Num.setText(pass.substring(3, 4));
                            tv5Num.setText(pass.substring(4, 5));
                            tv6Num.setText(pass.substring(5, 6));
                        }

                        eye_img.setImageResource(R.drawable.icon_eye_pass_blok);

                    }else {
                        SpannableStringBuilder ssb = new SpannableStringBuilder(" "); // 20
                        ssb.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        ssb.setSpan(new ImageSpan(bitmapBullet), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        if (pass.length()==1)
                        tv1Num.setText(ssb, TextView.BufferType.SPANNABLE);
                        if (pass.length()==2) {
                            tv1Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            tv2Num.setText(ssb, TextView.BufferType.SPANNABLE);
                        }
                        if (pass.length()==3) {
                            tv1Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            tv2Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            tv3Num.setText(ssb, TextView.BufferType.SPANNABLE);
                        }
                        if (pass.length()==4) {
                            tv1Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            tv2Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            tv3Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            tv4Num.setText(ssb, TextView.BufferType.SPANNABLE);
                        }
                        if (pass.length()==5){
                            tv1Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            tv2Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            tv3Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            tv4Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            tv5Num.setText(ssb, TextView.BufferType.SPANNABLE);
                        }

                        if (pass.length()==6)
                        {
                            tv1Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            tv2Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            tv3Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            tv4Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            tv5Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            tv6Num.setText(ssb, TextView.BufferType.SPANNABLE);
                        }

                        eye_img.setImageResource(R.drawable.icon_eye_pass);
                    }

                return false;
            }
        });


        if (prefs.containsData(IS_OPERADOR)) {
            txtLoginOperadorOtroUsuario.setVisibility(VISIBLE);
            txtLoginExistUserRecoverPass.setVisibility(View.INVISIBLE);
        } else {
            txtLoginOperadorOtroUsuario.setVisibility(GONE);
        }
        if ((prefs.containsData(IS_OPERADOR)) || !RequestHeaders.getTokenauth().isEmpty()) {
            text_passwordnew.setVisibility(GONE);
            llypass_pass.setVisibility(VISIBLE);
            asignar_edittext.requestFocus();
            textNameUser.setText("¡Hola " + prefs.loadData(NAME_USER) + "!");
            btnLogin.setText(getString(R.string.nextButton));
            edtUserName.setText(RequestHeaders.getUsername());
            text_email.setVisibility(GONE);
            imgHeaderLogin.setVisibility(GONE);
            textNameUser.setOnClickListener(this);
            asignar_edittext.setFocusableInTouchMode(true);
            asignar_edittext.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
            imm.showSoftInput(asignar_edittext, InputMethodManager.SHOW_IMPLICIT);
            text_passwordnew.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    text_passwordnew.setVisibility(GONE);
                    llypass_pass.setVisibility(VISIBLE);
                    txtContrasena.setVisibility(VISIBLE);
                    asignar_edittext.requestFocus();
                    return false;
                }
            });

            editUserPasswordnew.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b){
                        text_passwordnew.setVisibility(GONE);
                        llypass_pass.setVisibility(VISIBLE);
                        asignar_edittext.requestFocus();
                    }
                }
            });

        } else {
            ((AccountActivity) getActivity()).changeToolbarVisibility(true);
            lyImgUser.setVisibility(GONE);
            imgLoginExistProfile.setVisibility(GONE);
            txtSetCredentials.setVisibility(GONE);
            //edtUserPass.setHint(getString(R.string.passworddigitos));
            text_password.setHint(getString(R.string.passworddigitos));
            textNameUser.setText(getString(R.string.set_credentials_login));
            edtUserName.setText(RequestHeaders.getUsername());
            text_email.setVisibility(VISIBLE);
            edtUserName.setFocusableInTouchMode(true);
            edtUserName.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
            imm.showSoftInput(edtUserName, InputMethodManager.SHOW_IMPLICIT);
            text_passwordnew.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    text_passwordnew.setVisibility(GONE);
                    llypass_pass.setVisibility(VISIBLE);
                    txtContrasena.setVisibility(VISIBLE);
                    asignar_edittext.requestFocus();
                    return false;
                }
            });

            editUserPasswordnew.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b){
                        text_passwordnew.setVisibility(GONE);
                        llypass_pass.setVisibility(VISIBLE);
                        txtContrasena.setVisibility(VISIBLE);
                        asignar_edittext.requestFocus();
                    }
                }
            });

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
            case R.id.txtLoginOperadorOtroUsuario:
                text_email.setVisibility(VISIBLE);
                edtUserName.setText("");
                edtUserName.setFocusable(true);
                break;
            default:
                break;
        }
    }

    private void actionBtnLogin() {
        if (UtilsNet.isOnline(Objects.requireNonNull(getActivity()))) {
            validateForm();
        } else {
            UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_LONG);
        }
    }

    /**
     * Se encarga de obtener las medidas de la letra, y multiplicar por el tamaño asignado por pantalla
     *
     * @param mTextView
     * @param mInt
     * @return
     */
    public int obtenerMedidas(TextView mTextView, int mInt) {
        Paint paint = new Paint();
        paint.setTextSize(mTextView.getTextSize());
        return ((int) (-paint.ascent() + paint.descent() * mInt));
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
            asignar_edittext.clearFocus();
            asignar_edittext.setText("");
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

        asignar_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llypass_pass.setBackgroundResource(R.drawable.inputtext_active);
                } else {
                    if (asignar_edittext.toString().isEmpty()) {
                        llypass_pass.setBackgroundResource(R.drawable.inputtext_error);
                    } else {
                        llypass_pass.setBackgroundResource(R.drawable.inputtext_normal);
                    }
                }
            }
        });

        asignar_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 6) {
                    UI.hideKeyBoard(getActivity());
                    if (!UtilsNet.isOnline(getActivity())) {
                        UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_LONG);
                    } else {
                        //  Servicio para consumir usuario y contraseña
                        validateForm();
                        asignar_edittext.setText("");
                    }
                }
                text_password.setBackgroundResource(R.drawable.inputtext_active);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Asignamos el OnEditorActionListener a este CustomEditext para el efecto de consumir el servicio
        asignar_edittext.setOnEditorActionListener(new DoneOnEditorActionListener());
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
        if (!username.equals(RequestHeaders.getUsername())) {
            preferencias.saveDataBool(HAS_CONFIG_DONGLE, false);
            preferencias.saveDataInt(MODE_CONNECTION_DONGLE, QPOSService.CommunicationMode.BLUETOOTH.ordinal());
        }
        accountPresenter.login(username, password); // Realizamos el  Login
    }

    @Override
    public void getDataForm() {
        username = edtUserName.getText().toString().trim();
        password = asignar_edittext.getText().toString().trim();
    }

    @Override
    public void loginSucced() {
        App.getInstance().getPrefs().loadData(CARD_STATUS);
        SingletonUser.getInstance().setCardStatusId(null);
        Intent intentLogin = new Intent(getActivity(), TabActivity.class);
        startActivity(intentLogin);
        getActivity().finish();
    }

    private void setEnableViews(boolean isEnable) {
        edtUserName.setEnabled(isEnable);
        asignar_edittext.setEnabled(isEnable);
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
        if (preferencias.loadData(GENERO) == "H" || preferencias.loadData(GENERO) == "h") {
            Picasso.with(getContext()).load(StringUtils.procesarURLString(mUserImage))
                    .placeholder(R.mipmap.icon_user_fail).error(R.drawable.avatar_el)
                    .into(imgLoginExistProfile);
        } else if (preferencias.loadData(GENERO) == "M" || preferencias.loadData(GENERO) == "m") {
            Picasso.with(getContext()).load(StringUtils.procesarURLString(mUserImage))
                    .placeholder(R.mipmap.icon_user_fail).error(R.drawable.avatar_ella)
                    .into(imgLoginExistProfile);
        } else {
            Picasso.with(getContext()).load(StringUtils.procesarURLString(mUserImage))
                    .placeholder(R.mipmap.icon_user_fail).error(R.mipmap.icon_user_fail)
                    .into(imgLoginExistProfile);
        }

    }

    @Override
    public void changeeye() {
        eye_img.setImageResource(R.drawable.icon_eye_pass);
    }

    @Override
    public void fill() {
        UI.hideKeyBoard(getActivity());
        if (!UtilsNet.isOnline(getActivity())) {
            UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_LONG);
        } else {
            //  Servicio para consumir usuario y contraseña
            validateForm();
            asignar_edittext.setText("");
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

