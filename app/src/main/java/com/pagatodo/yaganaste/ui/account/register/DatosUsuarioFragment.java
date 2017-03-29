package com.pagatodo.yaganaste.ui.account.register;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.interfaces.IUserDataRegisterView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Validations;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_PERSONAL_DATA;

/**
 * A simple {@link GenericFragment} subclass.
 */
public class DatosUsuarioFragment extends GenericFragment implements View.OnClickListener,ValidationForms, IUserDataRegisterView {

    private static int MIN_LENGHT_VALIDATION_PASS = 2;
    private View rootview;
    @BindView(R.id.edtitEmail)
    EditText editMail;
    @BindView(R.id.edtitConfirmEmail)
    EditText edidConfirmEmail;
    @BindView(R.id.editPassword)
    EditText editPassword;
    @BindView(R.id.editPasswordConfirmation)
    EditText editPasswordConfirm;
    @BindView(R.id.txtRegisterBasicPassMessage)
    StyleTextView txtRegisterBasicPassMessage;
    @BindView(R.id.btnNextDatosUsuario)
    Button btnNextDatosUsuario;
    private String email = "";
    private String emailConfirmation = "";
    private String password = "";
    private String passwordConfirmation = "";
    private boolean isValidPassword = false;
    private AccountPresenterNew accountPresenter;

    public DatosUsuarioFragment() {
    }

    public static DatosUsuarioFragment newInstance() {
        DatosUsuarioFragment fragmentRegister = new DatosUsuarioFragment();
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
        accountPresenter = new AccountPresenterNew(this);
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
        rootview = inflater.inflate(R.layout.fragment_datos_usuario, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnNextDatosUsuario.setOnClickListener(this);
        setCurrentData();// Seteamos datos si hay registro en proceso.
        setValidationRules();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNextDatosUsuario:
                validateForm();
                break;
            default:
                break;
        }
    }

    /*Implementación de ValidationForms*/

    @Override
    public void setValidationRules() {
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > MIN_LENGHT_VALIDATION_PASS) {
                    /*Consumimos servicio de validación de contraseña*/
                    accountPresenter.validatePasswordFormat(s.toString().trim());
                }
            }
        });
    }

    @Override
    public void validateForm() {
        getDataForm();
        if (email.replaceAll("\\s", "").isEmpty()) {
            showValidationError(getString(R.string.datos_usuario_correo));
            return;
        }

        if(!Validations.isMail(email)){
            showValidationError(getString(R.string.datos_usuario_correo_formato));
            return;
        }

        if(emailConfirmation.isEmpty()){
            showValidationError(getString(R.string.datos_usuario_correo_confirma));
            return;
        }

        if(!emailConfirmation.equals(email)){
            showValidationError(getString(R.string.datos_usuario_correo_no_coinciden));
            return;
        }

        if(password.isEmpty()){
            showValidationError(getString(R.string.datos_usuario_pass));
            return;
        }

        if(passwordConfirmation.isEmpty()){
            showValidationError(getString(R.string.datos_usuario_pass_confirm));
            return;
        }

        if(!passwordConfirmation.equals(password)){
            showValidationError(getString(R.string.datos_usuario_pass_no_coinciden));
            return;
        }

        if(!isValidPassword){
            showValidationError(getString(R.string.datos_usuario_pass_formato));
            return;
        }

        onValidationSuccess();
    }

    @Override
    public void showValidationError(Object error) {
        UI.showToastShort(error.toString(),getActivity());
    }

    @Override
    public void onValidationSuccess() {
        /*Guardamos datos en Singleton de registro.*/
        RegisterUser registerUser = RegisterUser.getInstance();
        registerUser.setEmail(email);
        registerUser.setContrasenia(password);
        nextStepRegister(EVENT_PERSONAL_DATA,null);//Mostramos la siguiente pantalla de registro.
    }

    @Override
    public void getDataForm() {
        email = editMail.getText().toString().trim();
        emailConfirmation = edidConfirmEmail.getText().toString().trim();
        password = editPassword.getText().toString().trim();
        passwordConfirmation = editPasswordConfirm.getText().toString().trim();
    }

    private void setCurrentData(){
        RegisterUser registerUser = RegisterUser.getInstance();
        editMail.setText(registerUser.getEmail());
        edidConfirmEmail.setText(registerUser.getEmail());
        editPassword.setText(registerUser.getContrasenia());
        editPasswordConfirm.setText(registerUser.getContrasenia());
        isValidPassword = true;
    }

    @Override
    public void nextStepRegister(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void backStepRegister(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void showLoader(String message) {
    }

    @Override
    public void hideLoader() {
    }

    @Override
    public void showError(Object error) {
        UI.showToastShort(error.toString(),getActivity());
    }

    @Override
    public void validationPasswordSucces() {//Mostrar imagen en edtText
        isValidPassword = true;
        txtRegisterBasicPassMessage.setText("");
    }

    @Override
    public void validationPasswordFailed(String message) {//Mostrar imagen en edtText
        isValidPassword = false;
        txtRegisterBasicPassMessage.setText(message);
    }
}