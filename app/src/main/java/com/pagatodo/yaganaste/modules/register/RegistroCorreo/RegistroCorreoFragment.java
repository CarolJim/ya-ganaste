package com.pagatodo.yaganaste.modules.register.RegistroCorreo;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.interfaces.IUserDataRegisterView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.CustomPassSixDigits;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.LENGTH_LONG;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroCorreoFragment extends GenericFragment implements View.OnClickListener, ValidationForms, IUserDataRegisterView,
        View.OnFocusChangeListener, CustomPassSixDigits.OnCodeChangedListener {

    private View rootview;
    @BindView(R.id.text_email)
    TextInputLayout text_email;
    @BindView(R.id.edit_email)
    EditText editMail;
    private Preferencias preferencias;
    private AccountPresenterNew accountPresenter;
    private boolean emailValidatedByWS = false; // Indica que el email ha sido validado por el ws.
    private boolean userExist = false; // Indica que el email ya se encuentra registrado.

    @BindView(R.id.img_password)
    LinearLayout img_password;
    @BindView(R.id.customPassSixDigits)
    CustomPassSixDigits customPassSixDigits;
    @BindView(R.id.text_password)
    TextView text_password;
    @BindView(R.id.titulo_datos_usuario)
    TextView titulo_datos_usuario;
    @BindView(R.id.confirm_password)
    LinearLayout confirm_password;

    /*@BindView(R.id.customPassSixDigits)
    LinearLayout customPassSixDigits;*/
    @BindView(R.id.edit_text_aux)
    EditText edit_text_aux;

    @BindView(R.id.edit_text_aux_confirm)
    EditText edit_text_aux_confirm;


    public static RegistroCorreoFragment newInstance() {

        return new RegistroCorreoFragment();
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
        setValidationRules();
        customPassSixDigits.setListener(this);
        customPassSixDigits.setCodeChangedListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    @Override
    public void isEmailAvaliable() {
        hideLoader();
        emailValidatedByWS = true;
        userExist = false;
        text_email.setBackgroundResource(R.drawable.inputtext_normal);
    }

    @Override
    public void isEmailRegistered() {
        hideLoader();
        emailValidatedByWS = false;
        userExist = true;
        //editMail.setIsInvalid();
        text_email.setBackgroundResource(R.drawable.inputtext_error);
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

        editMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //  hideValidationError(editMail.getId());
                    //editMail.imageViewIsGone(true);
                    text_email.setBackgroundResource(R.drawable.inputtext_active);

                } else {
                    if (!UtilsNet.isOnline(getActivity())) {
                        //   editMail.setIsInvalid();
                        showValidationError(editMail.getId(), getString(R.string.no_internet_access));
                    } else if (editMail.getText().toString().isEmpty()) {
                        //    editMail.setIsInvalid();

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        //showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo));
                        text_email.setBackgroundResource(R.drawable.inputtext_error);
                        UI.showErrorSnackBar(getActivity(), getString(R.string.datos_usuario_correo), Snackbar.LENGTH_SHORT);
                    } else if (!ValidateForm.isValidEmailAddress(editMail.getText().toString().trim().toLowerCase()) && !emailValidatedByWS) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        text_email.setBackgroundResource(R.drawable.inputtext_error);
                        UI.showErrorSnackBar(getActivity(), getString(R.string.datos_usuario_correo), Snackbar.LENGTH_SHORT);
                    } else if (ValidateForm.isValidEmailAddress(editMail.getText().toString().trim().toLowerCase()) && !emailValidatedByWS) {
                        accountPresenter.validateEmail(editMail.getText().toString());
                    } else if (ValidateForm.isValidEmailAddress(editMail.getText().toString().trim().toLowerCase()) && emailValidatedByWS) {
                        text_email.setBackgroundResource(R.drawable.inputtext_normal);
                        // hideValidationError(editMail.getId());
                        //editMail.setIsValid();
                    }
                }
            }
        });

    }

    @Override
    public void validateForm() {

    }

    @Override
    public void showValidationError(int id, Object o) {

    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {

    }

    @Override
    public void getDataForm() {

    }

    @Override
    public void onCodeChanged() {
        if (edit_text_aux.getText().toString().length() == 5 &&
                edit_text_aux_confirm.getText().toString().length() == 5) {
            //UI.showErrorSnackBar(getActivity(), getString(R.string.datos_usuario_correo_existe), Snackbar.LENGTH_LONG);
            //UI.showErrorSnackBar(getActivity(), getString(R.string.datos_usuario_correo_existe), Snackbar.LENGTH_LONG);
        }
        //UI.showErrorSnackBar(getActivity(), getString(R.string.password_invalid), Snackbar.LENGTH_LONG);
    }

    @Override
    public void setVisivility() {
        img_password.setVisibility(View.VISIBLE);
        customPassSixDigits.setVisibility(View.GONE);
        text_password.setVisibility(View.GONE);
        //titulo_datos_usuario.setVisibility(View.GONE);
        confirm_password.setVisibility(View.VISIBLE);
    }
}
