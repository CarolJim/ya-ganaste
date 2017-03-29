package com.pagatodo.yaganaste.ui.account.register;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioFWSRequest;
import com.pagatodo.yaganaste.interfaces.IAccountBasicRegisterView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterOld;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.interfaces.enums.TypeLogin.LOGIN_AFTER_REGISTER;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;

/**
 * A simple {@link GenericFragment} subclass.
 */
public class RegisterBasicInfoFragment extends GenericFragment implements View.OnClickListener,IAccountBasicRegisterView,ValidationForms {

    private View rootview;
    @BindView(R.id.edtxtRegisterBasicName)
    StyleEdittext edtxtRegisterBasicName;
    @BindView(R.id.edtxtRegisterBasicLastName)
    StyleEdittext edtxtRegisterBasicLastName;
    @BindView(R.id.edtxtRegisterBasicSecondLastName)
    StyleEdittext edtxtRegisterBasicSecondLastName;
    @BindView(R.id.edtxtRegisterBasicPass)
    StyleEdittext edtxtRegisterBasicPass;
    @BindView(R.id.txtRegisterBasicPassMessage)
    StyleTextView txtRegisterBasicPassMessage;
    @BindView(R.id.btnRegisterBasicNext)
    StyleButton btnRegisterBasicNext;
    @BindView(R.id.txtRegisterBasicMail)
    StyleTextView txtRegisterBasicMail;
    @BindView(R.id.imgRegisterBasicPass)
    ImageView imgRegisterBasicPass;
    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;

    private AccountPresenterOld accountPresenter;

    private String basicName="";
    private String basicLastName="";
    private String basicSecondLastName="";
    private String basicPassword="";
    private boolean isValidPassword = false;

    public RegisterBasicInfoFragment() {
    }

    public static RegisterBasicInfoFragment newInstance() {
        RegisterBasicInfoFragment fragmentRegister = new RegisterBasicInfoFragment();
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        accountPresenter = new AccountPresenterOld(this);
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

        rootview = inflater.inflate(R.layout.fragment_register_basic_info, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnRegisterBasicNext.setOnClickListener(this);

        edtxtRegisterBasicPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 2) {
                    /*Consumimos servicio de validación de contraseña*/
                    accountPresenter.validatePasswordFormat(s.toString().trim());
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegisterBasicNext:
                actionBtnRegisterBasicNext();
                break;

            default:
                break;
        }
    }

    private void inTransaction(boolean intransac) {
        //imgloader.setVisibility(intransac ? View.VISIBLE : View.GONE);
        edtxtRegisterBasicName.setEnabled(!intransac);
        edtxtRegisterBasicLastName.setEnabled(!intransac);
        edtxtRegisterBasicSecondLastName.setEnabled(!intransac);
        edtxtRegisterBasicPass.setEnabled(!intransac);
    }

    private void actionBtnRegisterBasicNext(){
        if (UtilsNet.isOnline(getActivity())) {
            validateForm();
        }else {
            Toast.makeText(getActivity(), R.string.no_internet_access, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void validationPasswordSucces() {
        isValidPassword = true;
        txtRegisterBasicPassMessage.setText("");
        imgRegisterBasicPass.setVisibility(View.VISIBLE);
    }

    @Override
    public void validationPasswordFailed(String message) {
        isValidPassword = false;
        txtRegisterBasicPassMessage.setText(message);
        imgRegisterBasicPass.setVisibility(View.GONE);
    }

    @Override
    public void nextStepAccountFlow(String event) {
        onEventListener.onEvent(event,null);
    }

    @Override
    public void showLoader(String message) {
        progressLayout.setVisibility(View.VISIBLE);
        progressLayout.setTextMessage(message);
    }

    @Override
    public void hideLoader() {
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(Object error) {
        inTransaction(false);
        UI.showToastShort(error.toString(),getActivity());
    }

    /*Implementacion de ValidateForms*/

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {
        getDataForm();

        if(basicName.isEmpty()){
            showValidationError("Nombre Requerido, Verifique su Información");
            return;
        }

        if(basicLastName.isEmpty()){
            showValidationError("Primer Apellido Requerido, Verifique su Información");
            return;
        }

        if(!isValidPassword){
            showValidationError("Verifica tu Contraseña");
            return;
        }

        onValidationSuccess();
    }

    @Override
    public void showValidationError(Object error) {
        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationSuccess() {

        inTransaction(true);
        /*Creamos petición con los datos del usuario*/
        CrearUsuarioFWSRequest requestCrearUsuario = new CrearUsuarioFWSRequest(
                RequestHeaders.getUsername(),
                basicName,
                basicLastName,
                basicSecondLastName,
                RequestHeaders.getUsername(),
                basicPassword);

        accountPresenter.createUser(requestCrearUsuario);//Creamos usuario FWS
    }

    @Override
    public void getDataForm() {
        basicName = edtxtRegisterBasicName.getText().toString().trim();
        basicLastName= edtxtRegisterBasicLastName.getText().toString().trim();;
        basicSecondLastName= edtxtRegisterBasicSecondLastName.getText().toString().trim();;
        basicPassword= edtxtRegisterBasicPass.getText().toString().trim();;
    }

    @Override
    public void userCreatedSuccess(String message) {
        progressLayout.setTextMessage(message);
        new Handler().postDelayed(new Runnable() {
            public void run() {
               hideLoader();
               inTransaction(false);
                // Realizamos primer login después del registro.
                accountPresenter.login(LOGIN_AFTER_REGISTER,RequestHeaders.getUsername(),basicPassword);
            }
        }, DELAY_MESSAGE_PROGRESS);

    }


}

