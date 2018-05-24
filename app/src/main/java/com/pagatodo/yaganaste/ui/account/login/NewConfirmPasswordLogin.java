package com.pagatodo.yaganaste.ui.account.login;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.freja.change.presenters.ChangeNipPresenterImp;
import com.pagatodo.yaganaste.freja.reset.managers.IResetNIPView;
import com.pagatodo.yaganaste.freja.reset.presenters.ResetPinPresenter;
import com.pagatodo.yaganaste.freja.reset.presenters.ResetPinPresenterImp;
import com.pagatodo.yaganaste.interfaces.IChangeNipView;
import com.pagatodo.yaganaste.interfaces.IChangePass6;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyPassView;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASOCIATE_PHONE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;
import static com.pagatodo.yaganaste.utils.Recursos.PASSWORD_CHANGE;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;
import static com.pagatodo.yaganaste.utils.Recursos.PSW_CPR;

/**
 * Created by Armando Sandoval on 13/12/2017.
 */

public class NewConfirmPasswordLogin extends GenericFragment implements View.OnClickListener,
        ValidationForms, IMyPassView, IResetNIPView, IChangePass6, IChangeNipView {
    private Preferencias prefs = App.getInstance().getPrefs();
    private static int PIN_LENGHT = 4;
    @BindView(R.id.txt_password)
    TextInputLayout txtPassword;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.txt_password_confirm)
    TextInputLayout txtPasswordConfirm;
    @BindView(R.id.edt_password_confirm)
    EditText edtPasswordConfirm;
    @BindView(R.id.progressIndicator)
    ProgressLayout progressLayout;
    @BindView(R.id.btnNextAsignarPin)
    StyleButton btnNextAsignarPin;

    @BindView(R.id.stt_1)
    StyleTextView titulo;

    @BindView(R.id.titulo_datos_usuario)
    StyleTextView titulo_datos_usuario;








    private View rootview;
    private String nip = "";
    private String nipToConfirm = "";
    private AccountPresenterNew accountPresenter;
    App aplicacion;

    private ChangeNipPresenterImp changeNipPresenterImp;
    private ResetPinPresenter resetPinPresenter;

    public static NewConfirmPasswordLogin newInstance() {
        NewConfirmPasswordLogin fragmentRegister = new NewConfirmPasswordLogin();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        accountPresenter.setIView(this);
        resetPinPresenter = new ResetPinPresenterImp(false);
        resetPinPresenter.setResetNIPView(this);

        this.changeNipPresenterImp = new ChangeNipPresenterImp();
        changeNipPresenterImp.setIChangeNipView(this);
        aplicacion = new App();
        //accountPresenter = new AccountPresenterNew(getActivity(),this);
    }

    @Override
    public void onResume() {
        super.onResume();
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
        setValidationRules();

        if (!prefs.loadDataBoolean(PASSWORD_CHANGE, false)) {
            titulo.setText(getResources().getString(R.string.cambia_contraseda));
            titulo_datos_usuario.setVisibility(View.GONE);
        }
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
            showValidationError(getString(R.string.error_1_new_password));
            return;
        }

        if (!nip.equals(nipToConfirm)) {
            showValidationError(getString(R.string.confirmar_contrase));
            return;
        }

        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            onValidationSuccess();
        } else {
            showValidationError(getResources().getString(R.string.no_internet_access));
        }
    }

    private void showValidationError(Object err) {
        showValidationError(0, err);
    }

    @Override
    public void showValidationError(int id, Object error) {
        //UI.showToastShort(error.toString(), getActivity());
        UI.showErrorSnackBar(getActivity(), error.toString(), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
        //accountPresenter.assignNIP(nip);
        String[] pass = Utils.cipherAES(prefs.loadData(PSW_CPR), false).split("-");
        accountPresenter.changepasssixdigits(pass[0], nip); // Realizamos el  Login
    }

    @Override
    public void getDataForm() {
        nip = edtPassword.getText().toString().trim();
        nipToConfirm = edtPasswordConfirm.getText().toString().trim();
    }

    @Override
    public void nextScreen(String event, Object data) {
        showLoader("");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                hideLoader();
                if (SingletonUser.getInstance().getDataUser().getControl().getRequiereActivacionSMS()) {
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
        showValidationError(0, error);
    }

    public boolean isCustomKeyboardVisible() {
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void sendSuccessPassToView(String mensaje) {
        String[] pass = Utils.cipherAES(prefs.loadData(PSW_CPR), false).split("-");
        App.getInstance().getPrefs().saveData(SHA_256_FREJA, Utils.getSHA256(nip));
        App.getInstance().getPrefs().saveDataBool(PASSWORD_CHANGE, true);
        if (SingletonUser.getInstance().needsReset()) {
            resetPinPresenter.doReseting(Utils.getSHA256(nip));
        } else {
            changeNipPresenterImp.doChangeNip(Utils.getSHA256(pass[0]),
                    Utils.getSHA256(nip));
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
        endAndBack();
    }

    @Override
    public void onFrejaNipChanged() {
        endAndBack();
    }

    @Override
    public void onFrejaNipFailed() {
        SingletonUser.getInstance().setNeedsReset(true);
        resetPinPresenter.doReseting(Utils.getSHA256(nip));
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
        if (SingletonUser.getInstance().getDataUser().getControl().getRequiereActivacionSMS()) {
            onEventListener.onEvent(EVENT_GO_ASOCIATE_PHONE, null);//Mostramos la siguiente pantalla SMS.
        } else {
            onEventListener.onEvent(EVENT_GO_MAINTAB, null);
        }
    }
}