package com.pagatodo.yaganaste.modules.sidebar.ChangePassword;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;
import com.pagatodo.view_manager.buttons.ButtonContinue;
import com.pagatodo.view_manager.components.inputs.InputSecret;
import com.pagatodo.view_manager.components.inputs.InputSecretListener;
import com.pagatodo.view_manager.components.inputs.InputSecretPass;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.freja.change.presenters.ChangeNipPresenterImp;
import com.pagatodo.yaganaste.freja.reset.managers.IResetNIPView;
import com.pagatodo.yaganaste.freja.reset.presenters.ResetPinPresenter;
import com.pagatodo.yaganaste.freja.reset.presenters.ResetPinPresenterImp;
import com.pagatodo.yaganaste.interfaces.IChangeNipView;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyPassView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_PUSH;
import static com.pagatodo.yaganaste.utils.Recursos.PUBLIC_KEY_RSA;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;

public class NewPasswwordFragment extends GenericFragment implements InputSecretListener,
        IMyPassView, IResetNIPView, IChangeNipView {

    private View rootView;
    private PreferUserActivity activity;
    private PreferUserPresenter mPreferPresenter;
    private ResetPinPresenter resetPinPresenter;
    private ChangeNipPresenterImp changeNipPresenterImp;
    static String TAG_HIT = "TAG_HIT";
    private String hit;

    @BindView(R.id.input_secret_current)
    InputSecretPass inputSecretPassNew;
    @BindView(R.id.input_secret_confirm)
    InputSecretPass inputSecretPassConfirm;
    @BindView(R.id.next_btn)
    ButtonContinue nextBtn;

    public static NewPasswwordFragment newInstance(String hit){
        NewPasswwordFragment fragment = new NewPasswwordFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG_HIT,hit);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (PreferUserActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferPresenter = activity.getPreferPresenter();
        mPreferPresenter.setIView(this);
        resetPinPresenter = new ResetPinPresenterImp(false);
        resetPinPresenter.setResetNIPView(this);
        this.changeNipPresenterImp = new ChangeNipPresenterImp();
        changeNipPresenterImp.setIChangeNipView(this);
        if (getArguments() != null){
            hit = getArguments().getString(TAG_HIT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.new_passwword_fragment,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        inputSecretPassNew.setRequestFocus();
        inputSecretPassConfirm.desactive();
        showKeyboard();
        inputSecretPassNew.setActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                /*if (inputSecretPassNew.getTextEdit().length() == 6){
                    inputSecretPassConfirm.setRequestFocus();
                }*/
                if (validate()){
                    inputSecretPassConfirm.setRequestFocus();
                }
                return true;
            }
            // Return true if you have consumed the action, else false.
            return false;
        });

        inputSecretPassConfirm.setActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                if (validate()){
                    hideKeyBoard();
                    mPreferPresenter.changePassToPresenter(
                            Utils.cipherRSA(hit, PUBLIC_KEY_RSA),
                            Utils.cipherRSA(inputSecretPassNew.getTextEdit(), PUBLIC_KEY_RSA)
                    );
                }
                return true;
            }
            // Return true if you have consumed the action, else false.
            return false;
        });



        inputSecretPassNew.setInputSecretListener(new InputSecretListener() {
            @Override
            public void inputListenerFinish() {

                if (inputSecretPassConfirm.getTextEdit().length() == 6){
                    nextBtn.active();
                } else {
                    nextBtn.inactive();
                }
            }
            @Override
            public void inputListenerBegin() {
                nextBtn.inactive();
            }
        });
        inputSecretPassConfirm.setInputSecretListener(this);

    }

    private boolean validate(){
        boolean isValid = true;
        if (inputSecretPassNew.getTextEdit().length() < 6){
            isValid = false;
            inputSecretPassNew.isError();
            showError("Introdusca una contraseña valida");
        } else if (inputSecretPassConfirm.getTextEdit().length() < 6){
            isValid = false;
            inputSecretPassConfirm.isError();
            showError("Introdusca una contraseña valida");

        } else if (!inputSecretPassNew.getTextEdit().equalsIgnoreCase(inputSecretPassConfirm.getTextEdit())){
            isValid = false;
            showError("Introdusca una contraseña valida");
            inputSecretPassConfirm.isError();
        }
        return isValid;
    }

    private void showError(String msj){
        UI.showErrorSnackBar(Objects.requireNonNull(getActivity()),msj,Snackbar.LENGTH_LONG);
        hideKeyBoard();
    }



    protected void showKeyboard(){
        InputMethodManager imm = (InputMethodManager)  App.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager) App.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(inputSecretPassNew.getInputEditText().getWindowToken(), 0);
    }

    @Override
    public void sendSuccessPassToView(String mensaje) {
        App.getInstance().getPrefs().saveData(SHA_256_FREJA, Utils.getSHA256(inputSecretPassNew.getTextEdit()));
        if (SingletonUser.getInstance().needsReset()) {
            resetPinPresenter.doReseting(Utils.getSHA256(inputSecretPassNew.getTextEdit()));
        } else {
            changeNipPresenterImp.doChangeNip(Utils.getSHA256(hit),
                    Utils.getSHA256(inputSecretPassNew.getTextEdit()));
        }
    }

    @Override
    public void sendErrorPassToView(String mensaje) {
        if (mensaje.contains("Contraseña")) {
            //editActual.inputLayout.setBackgroundResource(R.drawable.input_text_error);
            showError(getString(R.string.error_service_verify_pass));
        } else {
            showError(mensaje);
        }
        hideLoader();
        onEventListener.onEvent("DISABLE_BACK", false);
    }

    @Override
    public void showLoader(String message) {
        onEventListener.onEvent("DISABLE_BACK", true);
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    public void hideLoader() {
        // progressLayout.setVisibility(GONE);
        onEventListener.onEvent("DISABLE_BACK", false);
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
    }

    //*********/
    @Override
    public void showError(Object error) {

    }

    @Override
    public void showErrorReset(ErrorObject error) {

    }

    @Override
    public void finishReseting() {
        endAndBack();
    }

    @Override
    public void onResetingFailed() {
        App.getInstance().getPrefs().saveDataBool(HAS_PUSH, false);
        endAndBack();
    }

    //*********************
    @Override
    public void onFrejaNipChanged() {
        endAndBack();
    }

    @Override
    public void onFrejaNipFailed() {
        SingletonUser.getInstance().setNeedsReset(true);
        resetPinPresenter.doReseting(Utils.getSHA256(inputSecretPassNew.getTextEdit()));
    }

    @Override
    public void showErrorNip(ErrorObject error) {
        hideLoader();
    }

    private void endAndBack() {
        /*editActual.editText.setText("");
        editNueva.editText.setText("");
        editConfir.editText.setText("");*/
        //showSnakBar(Recursos.MESSAGE_CHANGE_PASS);
        hideLoader();
        UI.showSuccessSnackBar(Objects.requireNonNull(getActivity()),"Bien",Snackbar.LENGTH_SHORT);
        //onEventListener.onEvent("PREFER_SECURITY_SUCCESS_PASS", false);
    }

    @Override
    public void onStop() {
        super.onStop();
        hideKeyBoard();
    }

    @Override
    public void inputListenerFinish() {
        if (inputSecretPassNew.getTextEdit().length() == 6){
            nextBtn.active();
        } else {
            nextBtn.inactive();
        }
    }

    @Override
    public void inputListenerBegin() {
        nextBtn.inactive();
    }
}
