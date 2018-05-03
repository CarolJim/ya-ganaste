package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUserStarbucks;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.IregisterCompleteStarbucks;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.LegalsDialog;
import com.pagatodo.yaganaste.ui.account.register.adapters.ColoniasArrayAdapter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.Iloginstarbucks;
import com.pagatodo.yaganaste.ui_wallet.presenter.RegisterCompletePresenterStarbucks;
import com.pagatodo.yaganaste.ui_wallet.presenter.RegisterPresenterStarbucks;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.CustomClickableSpan;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.AVISOSTARBUCKS;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.TERMINOS;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.TERMINOSSRABUCKS;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_TO_LOGIN_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.COMPANY_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.PHONE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.PUBLIC_STARBUCKS_KEY_RSA;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_STARBUCKS;

/**
 * Created by asandovals on 25/04/2018.
 */

public class RegisterCompleteStarbucksFragment extends GenericFragment implements View.OnClickListener, ValidationForms,IregisterCompleteStarbucks {
    private View rootView;
    @BindView(R.id.text_email)
    TextInputLayout text_email;
    @BindView(R.id.text_password)
    TextInputLayout text_password;
    @BindView(R.id.text_passwordconfirm)
    TextInputLayout text_passwordconfirm;
    @BindView(R.id.btnNextStarbucks)
    StyleButton btnNextStarbucks;
    RadioGroup radioHasCard;
    @BindView(R.id.radioBtnYes)
    RadioButton radioBtnYes;
    @BindView(R.id.radioBtnNo)
    RadioButton radioBtnNo;
    @BindView(R.id.edit_email)
    EditText editMail;
    String nombre,appaterno,apmaterno,email,cp,genero,idcolonia,colonia,calleynumero,telefono,password,passwordconfirm;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.edit_passwordconfirm)
    EditText editPasswordConfirm;

    @BindView(R.id.txtbottom)
    StyleTextView txtbottom;

    @BindView(R.id.txtbottom2)
    StyleTextView txtbottom2;



    boolean suscribe=false;
    RegisterCompletePresenterStarbucks registerCompletePresenterStarbucks;
    public static RegisterCompleteStarbucksFragment newInstance() {
        RegisterCompleteStarbucksFragment fragmentRegisterComplete = new RegisterCompleteStarbucksFragment();
        Bundle args = new Bundle();
        fragmentRegisterComplete.setArguments(args);
        return fragmentRegisterComplete;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_starbucks_registercomplete, container, false);
        initViews();
        setValidationRules();
        registerCompletePresenterStarbucks= new RegisterCompletePresenterStarbucks(getContext());
        registerCompletePresenterStarbucks.setIView(this);
        registerCompletePresenterStarbucks.datosregisterStarbucks();

        txtbottom.setOnClickListener(this);
        SpannableString ss;
        ss = new SpannableString(getString(R.string.terminos_aviso_starbucks));
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTituloDialog)), 51, 74, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new UnderlineSpan(), 0, 0, 0);
        txtbottom.setText(ss);


        txtbottom2.setOnClickListener(this);
        SpannableString s;
        s = new SpannableString(getString(R.string.aviso_de_privacidad_starbucks));
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTituloDialog)), 5, 24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new UnderlineSpan(), 0, 0, 0);
        txtbottom2.setText(s);

        return rootView;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNextStarbucks:
                validateForm();
                break;
            case R.id.txtbottom:
                LegalsDialog legalsDialog = LegalsDialog.newInstance(TERMINOSSRABUCKS);
                legalsDialog.show(getActivity().getFragmentManager(), LegalsDialog.TAG);
                break;
            case R.id.txtbottom2:
                LegalsDialog legalsDialog2 = LegalsDialog.newInstance(AVISOSTARBUCKS);
                legalsDialog2.show(getActivity().getFragmentManager(), LegalsDialog.TAG);
                break;
        }
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
                    }else if (editMail.getText().toString().isEmpty()) {
                        //    editMail.setIsInvalid();

                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        //showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo));
                        text_email.setBackgroundResource(R.drawable.inputtext_error);
                        UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_correo), Snackbar.LENGTH_SHORT);
                    } else if (!ValidateForm.isValidEmailAddress(editMail.getText().toString()) ) {

                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        text_email.setBackgroundResource(R.drawable.inputtext_error);
                        UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_correo), Snackbar.LENGTH_SHORT);
                    } else if (ValidateForm.isValidEmailAddress(editMail.getText().toString())) {
                        text_email.setBackgroundResource(R.drawable.inputtext_normal);
                    }
                }
            }
        });

        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    // hideValidationError(editPassword.getId());
                    // editPassword.imageViewIsGone(true);
                    text_password.setBackgroundResource(R.drawable.inputtext_active);
                } else {
                    if (!UtilsNet.isOnline(getActivity())) {
                        //  editPassword.setIsInvalid();
                        //  showValidationError(editPassword.getId(), getString(R.string.no_internet_access));
                    }else if (editPassword.getText().toString().isEmpty()) {
                        // editPassword.setIsInvalid();
                        // showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass));
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_pass), Snackbar.LENGTH_SHORT);
                        text_password.setBackgroundResource(R.drawable.inputtext_error);
                    }  else if (!ValidateForm.isValidPasswordsatrbucks(editPassword.getText().toString())) {
                        // hideValidationError(editPassword.getId());
                        // editPassword.setIsValid();
                        text_password.setBackgroundResource(R.drawable.inputtext_normal);
                    }
                }
            }
        });
        editPasswordConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    // hideValidationError(editPassword.getId());
                    // editPassword.imageViewIsGone(true);
                    text_passwordconfirm.setBackgroundResource(R.drawable.inputtext_active);
                } else {
                    if (!UtilsNet.isOnline(getActivity())) {
                        //  editPassword.setIsInvalid();
                        //  showValidationError(editPassword.getId(), getString(R.string.no_internet_access));
                    }else if (editPasswordConfirm.getText().toString().isEmpty()) {
                        // editPassword.setIsInvalid();
                        // showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass));
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        text_password.setBackgroundResource(R.drawable.inputtext_error);
                }else {
                        text_password.setBackgroundResource(R.drawable.inputtext_normal);
                    }
                }
            }
        });
    }
    @Override
    public void validateForm() {
      getDataForm();
      boolean isvalid=true;

      if (password.equals(passwordconfirm)) {
          if (password.isEmpty()) {
              isvalid = false;
              UI.showErrorSnackBar(getActivity(), getString(R.string.password_required), Snackbar.LENGTH_SHORT);
              text_passwordconfirm.setBackgroundResource(R.drawable.inputtext_error);
          } else if (!ValidateForm.isValidPasswordsatrbucks(editPassword.getText().toString())) {
              isvalid = false;
              UI.showErrorSnackBar(getActivity(), getString(R.string.password_incorrect_format), Snackbar.LENGTH_SHORT);
              text_password.setBackgroundResource(R.drawable.inputtext_error);
          }
      }else {
          isvalid=false;
          UI.showErrorSnackBar(getActivity(), getString(R.string.confirmar_contrase), Snackbar.LENGTH_SHORT);
          text_password.setBackgroundResource(R.drawable.inputtext_error);
      }
      if (!email.isEmpty()) {
          if (!ValidateForm.isValidEmailAddress(editMail.getText().toString())) {
              UI.showErrorSnackBar(getActivity(), getString(R.string.check_your_mail), Snackbar.LENGTH_SHORT);
              isvalid=false;
              editMail.setBackgroundResource(R.drawable.inputtext_error);
          }
      }else {
          UI.showErrorSnackBar(getActivity(), getString(R.string.txt_mail_requerid), Snackbar.LENGTH_SHORT);
          isvalid=false;
          editMail.setBackgroundResource(R.drawable.inputtext_error);
      }
      if (!radioBtnYes.isChecked()&&!radioBtnNo.isChecked()){
          UI.showErrorSnackBar(getActivity(), getString(R.string.suscribirte_starbucks), Snackbar.LENGTH_SHORT);
          isvalid=false;
      }
      if (radioBtnYes.isChecked()){
          suscribe=true;
      }
      if (isvalid){
          App.getInstance().getPrefs().saveData(SHA_256_STARBUCKS, Utils.cipherRSA(password, PUBLIC_STARBUCKS_KEY_RSA));//Contraseña Starbucks
          password=Utils.cipherRSA(password, PUBLIC_STARBUCKS_KEY_RSA);
          RegisterUserStarbucks registerUser = RegisterUserStarbucks.getInstance();
          registerUser.setContrasenia(password);
          registerUser.setEmail(email);
          registerUser.setSuscripcion(suscribe);
          registerCompletePresenterStarbucks.registerStarbucks(registerUser);
          showLoader("");
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

    }

    @Override
    public void getDataForm() {

        email= editMail.getText().toString().trim();
        password=editPassword.getText().toString().trim();
        passwordconfirm=editPasswordConfirm.getText().toString().trim();

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnNextStarbucks.setOnClickListener(this);
        SpannableString ss;
        ss = new SpannableString(getString(R.string.terminos_aviso_starbucks));
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTituloDialog)), 20, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTituloDialog)), 45, 50, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new UnderlineSpan(), 0, 0, 0);
        txtbottom.setText(ss);
    }

    @Override
    public void nextScreen(String event, Object data) {

    }

    @Override
    public void backScreen(String event, Object data) {

    }

    @Override
    public void registerstarsucced() {
        hideLoader();
        registerCompletePresenterStarbucks.login(email,password);

    }

    @Override
    public void llenardatospersona() {
        hideLoader();
        RegisterUserStarbucks registerUser = RegisterUserStarbucks.getInstance();
        nombre=(registerUser.getNombre().toString());
        appaterno=(registerUser.getPrimerApellido().toString());
        apmaterno=(registerUser.getSegundoApellido().toString());
        editMail.setText(registerUser.getEmail());
        cp=(registerUser.getCodigoPostal().toString());
        genero=registerUser.getGenero();
        idcolonia= registerUser.getIdColonia();
        colonia=registerUser.getColonia();
        calleynumero=registerUser.getCalleNumero();
        telefono= App.getInstance().getPrefs().loadData(PHONE_NUMBER);
    }


    @Override
    public void registerfail(String mensaje) {
        UI.showErrorSnackBar(getActivity(), mensaje, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void llenarcolonias(WebService ws, List<ColoniasResponse> list) {
        hideLoader();
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
        UI.showErrorSnackBar(getActivity(), error.toString(), Snackbar.LENGTH_SHORT);

    }

    @Override
    public void loginstarsucced() {
        getActivity().finish();
    }

    @Override
    public void loginfail(String mensaje) {
        nextScreen(EVENT_GO_TO_LOGIN_STARBUCKS, null);
    }
}
