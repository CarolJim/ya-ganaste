package com.pagatodo.yaganaste.modules.register.CorreoUsuario;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.RegisterUserNew;
import com.pagatodo.yaganaste.interfaces.IUserDataRegisterView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.AsignarNipTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroCorreoFragment extends GenericFragment implements View.OnClickListener, ValidationForms, IUserDataRegisterView,
        View.OnFocusChangeListener, AsignarNipTextWatcher.changeTxtWtxher {

    private static RegActivity activityf;
    @BindView(R.id.text_email)
    TextInputLayout text_email;
    @BindView(R.id.edit_email)
    EditText editMail;
    @BindView(R.id.btnNextDatosUsuario)
    StyleButton btnNextDatosUsuario;
    @BindView(R.id.titulo_datos_usuario)
    TextView titulo_datos_usuario;

    @BindView(R.id.text_password)
    TextInputLayout edt_psw;
    @BindView(R.id.edit_password)
    EditText edit_psw;
    @BindView(R.id.text_passwordconfirm)
    TextInputLayout edt_psw_confirm;
    @BindView(R.id.edit_passwordconfirm)
    EditText edit_psw_confirm;
    @BindView(R.id.activity_lets_start)
    LinearLayout linear_pass;


    @BindView(R.id.text_passwordnewReg)
    TextInputLayout text_passwordnew;
    @BindView(R.id.text_passwordnewRegConfirm)
    TextInputLayout text_passwordnewRegConfirm;


    @BindView(R.id.llay_eye_pass)
    LinearLayout llay_eye_pass;

    @BindView(R.id.contPass)
    LinearLayout contPass;
    @BindView(R.id.contPassConfirm)
    LinearLayout contPassConfirm;

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
    @BindView(R.id.eye_img)
    ImageView eye_img;

    @BindView(R.id.asignar_edittext)
    EditText asignar_edittext;
    @BindView(R.id.asignar_edittextConfirm)
    EditText asignar_edittextConfirm;

    private boolean passwordshow = true;

    @BindView(R.id.asignar_tv1Confirm)
    TextView tv1NumConfirm;
    @BindView(R.id.asignar_tv2Confirm)
    TextView tv2NumConfirm;
    @BindView(R.id.asignar_tv3Confirm)
    TextView tv3NumConfirm;
    @BindView(R.id.asignar_tv4Confirm)
    TextView tv4NumConfirm;
    @BindView(R.id.asignar_tv5Confirm)
    TextView tv5NumConfirm;
    @BindView(R.id.asignar_tv6Confirm)
    TextView tv6NumConfirm;
    @BindView(R.id.eye_imgConfirm)
    ImageView eye_imgConfirm;
    @BindView(R.id.asignar_control_layoutConfirm)
    LinearLayout asignar_control_layoutConfirm;
    @BindView(R.id.llypassConfirm)
    LinearLayout llypass_passConfirm;

    @BindView(R.id.asignar_control_layout)
    LinearLayout asignar_control_layout;

    @BindView(R.id.llypass)
    LinearLayout llypass_pass;

    boolean emailvalid = true;
    private View rootview;
    private Preferencias preferencias;
    private AccountPresenterNew accountPresenter;
    private boolean emailValidatedByWS = false; // Indica que el email ha sido validado por el ws.
    private boolean userExist = false; // Indica que el email ya se encuentra registrado.


    private String email = "";
    private String psd = "";
    private String psd_con = "";
    private boolean fillcode = false;
    Bitmap bitmapBullet;

    public static RegistroCorreoFragment newInstance() {
        return new RegistroCorreoFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityf = (RegActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.preferencias = App.getInstance().getPrefs();
        accountPresenter = new AccountPresenterNew(getActivity());
        accountPresenter.setIView(this);

    }

    public RegistroCorreoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_registro_correo, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        text_email.setBackgroundResource(R.drawable.inputtext_active);
        editMail.setFocusableInTouchMode(true);
        editMail.setText("");
        editMail.requestFocus();
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
        imm.showSoftInput(editMail, InputMethodManager.SHOW_IMPLICIT);
        btnNextDatosUsuario.setOnClickListener(this);
        editMail.setOnFocusChangeListener(this::onFocusChange);
        edit_psw.setOnFocusChangeListener(this::onFocusChange);
        edit_psw_confirm.setOnFocusChangeListener(this::onFocusChange);
        asignar_edittext.setFocusableInTouchMode(true);
        asignar_edittext.addTextChangedListener
                (new AsignarNipTextWatcher(asignar_edittext, tv1Num, tv2Num, tv3Num, tv4Num, tv5Num, tv6Num, this, false));

        asignar_edittextConfirm.setFocusableInTouchMode(true);
        asignar_edittextConfirm.addTextChangedListener
                (new AsignarNipTextWatcher(asignar_edittextConfirm, tv1NumConfirm, tv2NumConfirm, tv3NumConfirm, tv4NumConfirm, tv5NumConfirm, tv6NumConfirm, this, true));

        asignar_control_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                asignar_edittext.requestFocusFromTouch();
                InputMethodManager lManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                lManager.showSoftInput(asignar_edittext, 0);


                return false;
            }
        });
        asignar_control_layoutConfirm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                asignar_edittextConfirm.requestFocusFromTouch();
                InputMethodManager lManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                lManager.showSoftInput(asignar_edittextConfirm, 0);
                return false;
            }
        });

        editMail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editMail.setFocusableInTouchMode(true);
                editMail.requestFocus();
                llypass_pass.setBackgroundResource(R.drawable.inputtext_normal);
                llypass_passConfirm.setBackgroundResource(R.drawable.inputtext_normal);

                return false;
            }
        });

        llypass_passConfirm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                asignar_edittextConfirm.setFocusableInTouchMode(true);
                asignar_edittextConfirm.requestFocus();
                text_email.setBackgroundResource(R.drawable.inputtext_normal);
                llypass_pass.setBackgroundResource(R.drawable.inputtext_normal);
                llypass_passConfirm.setBackgroundResource(R.drawable.inputtext_active);

                return false;
            }
        });

        llypass_pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                asignar_edittextConfirm.setFocusableInTouchMode(true);
                asignar_edittext.requestFocus();
                llypass_pass.setBackgroundResource(R.drawable.inputtext_active);
                text_email.setBackgroundResource(R.drawable.inputtext_normal);
                llypass_passConfirm.setBackgroundResource(R.drawable.inputtext_normal);

                return false;
            }
        });

        editMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    isChecked();
                }
            }
        });
        editMail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!editMail.getText().toString().isEmpty()) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        hideKeyBoard();
                        if (asignar_edittext.getText().toString().isEmpty()) {

                        }
                        edt_psw.setVisibility(View.GONE);
                        llypass_pass.setVisibility(View.VISIBLE);
                        asignar_edittext.requestFocus();

                        llypass_pass.setBackgroundResource(R.drawable.inputtext_active);
                        asignar_edittext.requestFocus();
                    }
                    return true;
                }
                return false;
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
                        edt_psw_confirm.setVisibility(View.GONE);
                        text_passwordnewRegConfirm.setVisibility(View.GONE);
                        llypass_passConfirm.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        LinearLayout.LayoutParams margin = (LinearLayout.LayoutParams) contPassConfirm.getLayoutParams();
                        margin.leftMargin = 30;
                        margin.rightMargin = 30;
                        margin.topMargin = 20;
                        contPassConfirm.setLayoutParams(margin);
                        llypass_passConfirm.setVisibility(View.VISIBLE);
                        asignar_edittextConfirm.requestFocus();
                        llypass_passConfirm.setBackgroundResource(R.drawable.inputtext_active);
                        llypass_pass.setBackgroundResource(R.drawable.inputtext_normal);
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
                        imm.showSoftInput(asignar_edittextConfirm, InputMethodManager.SHOW_IMPLICIT);
                        isChecked();
                    }
                }

                //text_password.setBackgroundResource(R.drawable.inputtext_active);
                //edt_psw_confirm.setBackgroundResource(R.drawable.inputtext_active);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        asignar_edittextConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 6) {

                    if (asignar_edittext.getText().toString().equals(asignar_edittextConfirm.getText().toString())) {
                        text_email.setBackgroundResource(R.drawable.inputtext_normal);
                        llypass_passConfirm.setBackgroundResource(R.drawable.inputtext_normal);
                    } else {
                        llypass_pass.setVisibility(View.VISIBLE);
                        asignar_edittext.requestFocus();
                        llypass_passConfirm.setVisibility(View.GONE);
                        llypass_pass.setBackgroundResource(R.drawable.inputtext_active);
                        edt_psw_confirm.setVisibility(View.VISIBLE);
                        asignar_edittext.setText("");
                        asignar_edittextConfirm.setText("");
                        UI.showErrorSnackBar(getActivity(), getString(R.string.password_invalid), Snackbar.LENGTH_SHORT);
                        isChecked();
                    }
                    isChecked();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Asignamos el OnEditorActionListener a este CustomEditext para el efecto de consumir el servicio
        //asignar_edittext.setOnEditorActionListener(new LoginFragment.DoneOnEditorActionListener());

        editMail.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                llypass_pass.setBackgroundResource(R.drawable.inputtext_normal);
                llypass_passConfirm.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });
        setValidationRules();

    }

    public int obtenerMedidas(TextView mTextView, int mInt) {
        Paint paint = new Paint();
        paint.setTextSize(mTextView.getTextSize());
        return ((int) (-paint.ascent() + paint.descent() * mInt));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnNextDatosUsuario) {
            isChecked();
            validateForm();

        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    @Override
    public void isEmailAvaliable() {
        hideLoader();
        emailValidatedByWS = true;
        userExist = true;
        text_email.setBackgroundResource(R.drawable.inputtext_normal);
        isChecked();
    }

    @Override
    public void isEmailRegistered() {
        hideLoader();
        emailValidatedByWS = false;
        userExist = false;
        text_email.setBackgroundResource(R.drawable.input_text_error);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        UI.showErrorSnackBar(getActivity(), getString(R.string.datos_usuario_correo_existe), Snackbar.LENGTH_LONG);
    }

    @Override
    public void validationPasswordSucces() {

    }

    @Override
    public void validationPasswordFailed(String message) {

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

    @Override
    public void setValidationRules() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        editMail.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                emailValidatedByWS = false;
                text_email.setBackgroundResource(R.drawable.inputtext_active);
                editMail.setFocusableInTouchMode(true);
                editMail.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(editMail, InputMethodManager.SHOW_IMPLICIT);


                //edt_psw.setVisibility(View.VISIBLE);
            } else {
                if (!UtilsNet.isOnline(getActivity())) {
                    showValidationError(editMail.getId(), getString(R.string.no_internet_access));
                } else if (editMail.getText().toString().isEmpty()) {
                    //InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    text_email.setBackgroundResource(R.drawable.input_text_error);
                    UI.showErrorSnackBar(getActivity(), getString(R.string.datos_usuario_correo), Snackbar.LENGTH_SHORT);

                } else if (!ValidateForm.isValidEmailAddress(editMail.getText().toString().trim().toLowerCase()) && !emailValidatedByWS) {
                    //InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    //imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(btnNextDatosUsuario.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    text_email.setBackgroundResource(R.drawable.input_text_error);
                    UI.showErrorSnackBar(getActivity(), getString(R.string.datos_usuario_correo), Snackbar.LENGTH_SHORT);
                } else if (ValidateForm.isValidEmailAddress(editMail.getText().toString().trim().toLowerCase()) && !emailValidatedByWS) {
                    accountPresenter.validateEmail(editMail.getText().toString());
                } else if (ValidateForm.isValidEmailAddress(editMail.getText().toString().trim().toLowerCase()) && emailValidatedByWS) {
                    text_email.setBackgroundResource(R.drawable.inputtext_normal);
                    //text_email.setBackgroundResource(R.drawable.inputtext_active);
                }
                //isChecked();

                text_email.setBackgroundResource(R.drawable.input_text_error);

            }
            isChecked();

        });

        edit_psw.setOnFocusChangeListener((view, b) -> {
            if (b) {
                edt_psw.setVisibility(View.GONE);
                text_passwordnew.setVisibility(View.GONE);
                llypass_pass.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                llypass_pass.setVisibility(View.VISIBLE);
                llypass_pass.setBackgroundResource(R.drawable.inputtext_active);
                text_email.setBackgroundResource(R.drawable.inputtext_normal);
                llypass_passConfirm.setBackgroundResource(R.drawable.inputtext_normal);
                asignar_edittext.requestFocus();
                //InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(asignar_edittext, InputMethodManager.SHOW_IMPLICIT);

            } else {
                edt_psw.setBackgroundResource(R.drawable.inputtext_normal);
            }
            isChecked();
        });
        edit_psw_confirm.setOnFocusChangeListener((view, b) -> {
            if (b) {
                edt_psw_confirm.setVisibility(View.GONE);
                text_passwordnewRegConfirm.setVisibility(View.GONE);
                llypass_passConfirm.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                LinearLayout.LayoutParams margin = (LinearLayout.LayoutParams) contPassConfirm.getLayoutParams();
                margin.topMargin = 20;
                contPassConfirm.setLayoutParams(margin);
                llypass_passConfirm.setVisibility(View.VISIBLE);
                llypass_pass.setBackgroundResource(R.drawable.inputtext_normal);
                text_email.setBackgroundResource(R.drawable.inputtext_normal);
                llypass_passConfirm.setBackgroundResource(R.drawable.inputtext_active);
                asignar_edittextConfirm.requestFocus();
                //InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(asignar_edittextConfirm, InputMethodManager.SHOW_IMPLICIT);


            } else {
                edt_psw_confirm.setBackgroundResource(R.drawable.inputtext_normal);
            }
            isChecked();
        });

    }

    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;
        if (email.isEmpty()) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            //UI.showErrorSnackBar(getActivity(), getString(R.string.ingress_your_mail), Snackbar.LENGTH_SHORT);
            UI.showErrorSnackBar(getActivity(), getString(R.string.ingress_your_mail), Snackbar.LENGTH_SHORT);
            text_email.setBackgroundResource(R.drawable.input_text_error);
            isValid = false;
        }
        if (!ValidateForm.isValidEmailAddress(email)) {
            text_email.setBackgroundResource(R.drawable.input_text_error);
            text_email.requestFocus();
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_usuario_correo), Snackbar.LENGTH_SHORT);
            isValid = false;
        }
        if (!userExist) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            UI.showErrorSnackBar(getActivity(), getString(R.string.ingress_your_mail), Snackbar.LENGTH_SHORT);
            text_email.setBackgroundResource(R.drawable.input_text_error);
            isValid = false;
        }
        if (asignar_edittext.getText().toString().isEmpty()) {
            edt_psw.setBackgroundResource(R.drawable.input_text_error);
            isValid = false;
        }
        if (asignar_edittextConfirm.getText().toString().isEmpty()) {
            edt_psw_confirm.setBackgroundResource(R.drawable.input_text_error);
            isValid = false;
        }
        if (!asignar_edittext.getText().toString().equals(asignar_edittextConfirm.getText().toString())) {
            isValid = false;
            UI.showErrorSnackBar(getActivity(), getString(R.string.password_invalid), Snackbar.LENGTH_SHORT);
        }
        if (psd.isEmpty()) {
            UI.showErrorSnackBar(getActivity(), getString(R.string.psd), Snackbar.LENGTH_SHORT);
            edt_psw.setBackgroundResource(R.drawable.input_text_error);
            isValid = false;
        }
        if (psd_con.isEmpty()) {
            UI.showErrorSnackBar(getActivity(), getString(R.string.psd_con), Snackbar.LENGTH_SHORT);
            edt_psw_confirm.setBackgroundResource(R.drawable.input_text_error);
            isValid = false;
        }

        if (psd.length() != 6 || psd_con.length() != 6) {

            UI.showErrorSnackBar(getActivity(), getString(R.string.psd_no_six_digits), Snackbar.LENGTH_SHORT);
            isValid = false;
        }

        if (!psd_con.equals(psd)) {
            UI.showErrorSnackBar(getActivity(), getString(R.string.confirmar_contrase), Snackbar.LENGTH_SHORT);
            //edt_psw_confirm.setBackgroundResource(R.drawable.input_text_error);
            isValid = false;
        }
        if (!userExist || asignar_edittext.length() == 6 || asignar_edittextConfirm.length() == 6) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
        if (isValid) {
            setLogData();
        }
    }

    public void setLogData() {
        RegisterUserNew registerUser = RegisterUserNew.getInstance();
        registerUser.setEmail(email);
        registerUser.setContrasenia(psd);
        if (BuildConfig.DEBUG) {
            onValidationSuccess();
        }
    }

    @Override
    public void showValidationError(int id, Object o) {

    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
        activityf.showFragmentDatosPersonales();
    }

    @Override
    public void getDataForm() {
        email = editMail.getText().toString();
        //psd = customPassSixDigits.getText().toString();
        //psd_con = customPassSixDigitsConfirm.getText().toString();
        psd = asignar_edittext.getText().toString();
        psd_con = asignar_edittextConfirm.getText().toString();
    }

    public void isChecked() {
        getDataForm();
        boolean isValid = true;
        if (email == null || email.equals("") || !userExist) {
            isValid = false;
            btnNextDatosUsuario.setBackgroundResource(R.drawable.button_rounded_gray);
        }
        if (!ValidateForm.isValidEmailAddress(email)) {
            isValid = false;
            btnNextDatosUsuario.setBackgroundResource(R.drawable.button_rounded_gray);
        }
        if (asignar_edittext == null || asignar_edittext.equals("") || asignar_edittext.length() != 6) {
            isValid = false;
            btnNextDatosUsuario.setBackgroundResource(R.drawable.button_rounded_gray);
        }
        if (!asignar_edittext.getText().toString().equals(asignar_edittextConfirm.getText().toString())) {
            isValid = false;
            btnNextDatosUsuario.setBackgroundResource(R.drawable.button_rounded_gray);
        }
        if (!userExist || asignar_edittext.length() == 6 || asignar_edittextConfirm.length() == 6) {

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            //imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            imm.hideSoftInputFromWindow(btnNextDatosUsuario.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if (isValid) {
            btnNextDatosUsuario.setBackgroundResource(R.drawable.button_rounded_blue);
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
            isChecked();
            //asignar_edittext.setText("");
        }
    }

    protected void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) App.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void hideKeyBoard() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) App.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(imm).hideSoftInputFromWindow(editMail.getWindowToken(), 0);
            }
        }, 3500);
    }

    @Override
    public void onStop() {
        super.onStop();
        hideKeyBoard();
    }
}
