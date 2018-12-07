package com.pagatodo.yaganaste.modules.register.CorreoUsuario;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.interfaces.IUserDataRegisterView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroCorreoFragment extends GenericFragment implements View.OnClickListener, ValidationForms, IUserDataRegisterView,
        View.OnFocusChangeListener{

    private View rootview;
    @BindView(R.id.text_email)
    TextInputLayout text_email;
    @BindView(R.id.edit_email)
    EditText editMail;
    @BindView(R.id.btnNextDatosUsuario)
    StyleButton btnNextDatosUsuario;
    private Preferencias preferencias;
    private AccountPresenterNew accountPresenter;
    private boolean emailValidatedByWS = false; // Indica que el email ha sido validado por el ws.
    private boolean userExist = false; // Indica que el email ya se encuentra registrado.
    private RegActivity activityf;

    public static RegistroCorreoFragment newInstance(){
        return  new RegistroCorreoFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_registro_correo, container, false);
        initViews();
        return rootview;
    }


    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnNextDatosUsuario.setOnClickListener(this);
        setValidationRules();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnNextDatosUsuario) {
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
        //onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        //onEventListener.onEvent(EVENT_HIDE_LOADER, null);
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
                    emailValidatedByWS = false;
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

        activityf.showFragmentDatosPersonales();

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
    public void errorSessionExpired(DataSourceResult response) {

    }
}