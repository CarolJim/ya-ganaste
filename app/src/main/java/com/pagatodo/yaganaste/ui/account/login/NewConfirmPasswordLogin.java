package com.pagatodo.yaganaste.ui.account.login;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonSession;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.freja.change.presenters.ChangeNipPresenterImp;
import com.pagatodo.yaganaste.freja.reset.managers.IResetNIPView;
import com.pagatodo.yaganaste.freja.reset.presenters.ResetPinPresenter;
import com.pagatodo.yaganaste.freja.reset.presenters.ResetPinPresenterImp;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IAccountCardNIPView;
import com.pagatodo.yaganaste.interfaces.IChangeNipView;
import com.pagatodo.yaganaste.interfaces.IChangePass6;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.MainActivity;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyPassView;
import com.pagatodo.yaganaste.utils.AsignarContraseñaTextWatcher;
import com.pagatodo.yaganaste.utils.AsignarNipTextWatcher;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.BorderTitleLayout;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASOCIATE_PHONE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;
import static com.pagatodo.yaganaste.utils.Recursos.PASSWORD_CHANGE;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;
import static com.pagatodo.yaganaste.utils.StringConstants.PSW_CPR;

/**
 * Created by Armando Sandoval on 13/12/2017.
 */

public class NewConfirmPasswordLogin extends GenericFragment implements View.OnClickListener,
        ValidationForms, IAccountCardNIPView,IMyPassView, IResetNIPView,IChangePass6, IChangeNipView {
    private Preferencias prefs = App.getInstance().getPrefs();
    public static String PIN_TO_CONFIRM = "PIN_TO_CONFIRM";
    private static int PIN_LENGHT = 4;
    @BindView(R.id.borderLayout)
    BorderTitleLayout borderTitleLayout;
    @BindView(R.id.asignar_edittext)
    CustomValidationEditText edtPin;
    @BindView(R.id.btnNextAsignarPin)
    Button btnNextAsignarPin;
    @BindView(R.id.keyboard_view)
    CustomKeyboardView keyboardView;
    @BindView(R.id.progressIndicator)
    ProgressLayout progressLayout;

    @BindView(R.id.caracteristicas_contraseña_nueva)
    StyleTextView caracteristicas_contraseña_nueva;
    LinearLayout layout_control;
    TextView tv1Num;
    TextView tv2Num;
    TextView tv3Num;
    TextView tv4Num;
    TextView tv5Num;
    TextView tv6Num;
    private View rootview;
    private String nip = "";
    private String nipToConfirm = "";
    private AccountPresenterNew accountPresenter;
    ImageView imageView;
    App aplicacion;

    private ChangeNipPresenterImp changeNipPresenterImp;
    private ResetPinPresenter resetPinPresenter;
    public static NewConfirmPasswordLogin newInstance(String nip) {
        NewConfirmPasswordLogin fragmentRegister = new NewConfirmPasswordLogin();
        Bundle args = new Bundle();
        args.putString(PIN_TO_CONFIRM, nip);
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nipToConfirm = getArguments().getString(PIN_TO_CONFIRM);


        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        accountPresenter.setIView(this);
        resetPinPresenter = new ResetPinPresenterImp(false);
        resetPinPresenter.setResetNIPView(this);

        this.changeNipPresenterImp = new ChangeNipPresenterImp();
        changeNipPresenterImp.setIChangeNipView(this);
        aplicacion = new App();
        imageView = (ImageView)getActivity().findViewById(R.id.btn_back);
        //accountPresenter = new AccountPresenterNew(getActivity(),this);
    }

    @Override
    public void onResume() {
        super.onResume();
        imageView.setVisibility(VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_new_password_login, container, false);

        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnNextAsignarPin.setOnClickListener(this);
        caracteristicas_contraseña_nueva.setVisibility(View.GONE);
        keyboardView.setKeyBoard(getActivity(), R.xml.keyboard_nip);
        keyboardView.setPreviewEnabled(false);
        keyboardView.showCustomKeyboard(rootview);

        layout_control = (LinearLayout) rootview.findViewById(R.id.asignar_control_layout);

        tv1Num = (TextView) rootview.findViewById(R.id.asignar_tv1);
        tv2Num = (TextView) rootview.findViewById(R.id.asignar_tv2);
        tv3Num = (TextView) rootview.findViewById(R.id.asignar_tv3);
        tv4Num = (TextView) rootview.findViewById(R.id.asignar_tv4);
        tv5Num = (TextView) rootview.findViewById(R.id.asignar_tv5);
        tv6Num = (TextView) rootview.findViewById(R.id.asignar_tv6);
        borderTitleLayout.setTitle(getString(R.string.confirma_nueva_contraseña));
        imageView.setVisibility(View.VISIBLE);
        edtPin = (CustomValidationEditText) rootview.findViewById(R.id.asignar_edittext);
        edtPin.setMaxLength(6); // Se asigna un maximo de 4 caracteres para no tener problrmas
        edtPin.addCustomTextWatcher(new AsignarContraseñaTextWatcher(edtPin, tv1Num, tv2Num, tv3Num, tv4Num, tv5Num, tv6Num));
        edtPin.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 6) {
                    buttonIsVisible(true);
                    keyboardView.hideCustomKeyboard();
                    //validateForm();
                }
            }
        });


        layout_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonIsVisible(false);
                edtPin.requestFocus();
                keyboardView.showCustomKeyboard(v);
            }
        });

        edtPin.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                buttonIsVisible(false);
                keyboardView.showCustomKeyboard(v);
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });
        setValidationRules();
        edtPin.requestEditFocus();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNextAsignarPin:
                validateForm();
                break;
            default:
                break;
        }
    }

    /*Implementación de ValidationForms*/

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {
        getDataForm();

        if (nip.length() < PIN_LENGHT) {
            showValidationError(getString(R.string.asignar_pin));
            return;
        }

        if (!nip.equals(nipToConfirm)) {
            showValidationError(getString(R.string.confirmar_contrase));
            return;
        }

        onValidationSuccess();
    }

    private void showValidationError(Object err) {
        showValidationError(0, err);
    }

    @Override
    public void showValidationError(int id, Object error) {
        //UI.showToastShort(error.toString(), getActivity());
        UI.createSimpleCustomDialog("", error.toString(), getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {

                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
        //accountPresenter.assignNIP(nip);
        String[] pass = Utils.cipherAES(prefs.loadData(PSW_CPR), false).split("-");

        prefs.saveDataBool(PASSWORD_CHANGE,true);


        if (!RequestHeaders.getTokenauth().isEmpty()) {
            accountPresenter.changepasssixdigits(pass[0],"1Azbxcwa2"); // Realizamos el  Login

            showLoader("");
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    hideLoader();
                    if (SingletonUser.getInstance().getDataUser().isRequiereActivacionSMS()) {
                        onEventListener.onEvent(EVENT_GO_ASOCIATE_PHONE, null);//Mostramos la siguiente pantalla SMS.
                    } else {
                        onEventListener.onEvent(EVENT_GO_MAINTAB, null);
                    }
                }
            }, DELAY_MESSAGE_PROGRESS);

        }else {
            showLoader("");
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    hideLoader();
                    if (SingletonUser.getInstance().getDataUser().isRequiereActivacionSMS()) {
                        onEventListener.onEvent(EVENT_GO_ASOCIATE_PHONE, null);//Mostramos la siguiente pantalla SMS.
                    } else {
                        onEventListener.onEvent(EVENT_GO_MAINTAB, null);
                    }
                }
            }, DELAY_MESSAGE_PROGRESS);
        }

    }

    @Override
    public void getDataForm() {
        nip = edtPin.getText().toString().trim();
    }

    @Override
    public void nextScreen(String event, Object data) {
        showLoader("");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                hideLoader();
                if (SingletonUser.getInstance().getDataUser().isRequiereActivacionSMS()) {
                    onEventListener.onEvent(EVENT_GO_ASOCIATE_PHONE, null);//Mostramos la siguiente pantalla SMS.
                } else {
                    onEventListener.onEvent(EVENT_GO_MAINTAB, null);
                }
            }
        }, DELAY_MESSAGE_PROGRESS);
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
        // UI.showToastShort(error.toString(), getActivity());
        showValidationError(0, error.toString());

    }

    public boolean isCustomKeyboardVisible() {
        return false;
    }

    private void buttonIsVisible(boolean isVisible) {
        btnNextAsignarPin.setVisibility(isVisible ? VISIBLE : GONE);
    }

    public void hideKeyboard() {
        //keyboardView.hideCustomKeyboard();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void sendSuccessPassToView(String mensaje) {
        String[] pass = Utils.cipherAES(prefs.loadData(PSW_CPR), false).split("-");
        App.getInstance().getPrefs().saveData(SHA_256_FREJA, Utils.getSHA256("1Azbxcwa2"));
        if (SingletonUser.getInstance().needsReset()) {
            resetPinPresenter.doReseting(Utils.getSHA256("1Azbxcwa2"));
        } else {
           changeNipPresenterImp.doChangeNip(Utils.getSHA256(pass[0]),
                    Utils.getSHA256("1Azbxcwa2"));
        }
    }

    @Override
    public void sendErrorPassToView(String mensaje) {
        if (mensaje.contains("Contraseña")) {
            showValidationError(getString(R.string.error_service_verify_pass));
        } else {
            showValidationError(mensaje);
        }
        hideLoader();
        onEventListener.onEvent("DISABLE_BACK", false);
    }

    @Override
    public void showErrorReset(ErrorObject error) {

    }

    @Override
    public void finishReseting() {

    }

    @Override
    public void onResetingFailed() {

    }

    @Override
    public void onFrejaNipChanged() {
        endAndBack();
    }

    @Override
    public void onFrejaNipFailed() {
        SingletonUser.getInstance().setNeedsReset(true);
        resetPinPresenter.doReseting(Utils.getSHA256("1Azbxcwa2"));
    }

    @Override
    public void showErrorNip(ErrorObject error) {
        hideLoader();
    }
    private void endAndBack() {
       // editOldPassword.setText("");
       // editPassword.setText("");
       // editPasswordConfirm.setText("");
       // showDialogMesage(Recursos.MESSAGE_CHANGE_PASS);
        hideLoader();
        onEventListener.onEvent("DISABLE_BACK", false);
    }
}