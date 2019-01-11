package com.pagatodo.yaganaste.modules.emisor.GeneratePIN;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.view_manager.buttons.ButtonContinue;
import com.pagatodo.view_manager.components.InputSecret;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.modules.emisor.WalletMainActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.utils.keyboard.UiKeyBoard;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneratePINFragment extends SupportFragment implements InputSecret.InputSecretListener,
        GeneratePINContracts.Listener,View.OnClickListener{

    private View rootView;
    private WalletMainActivity activity;
    private GeneratePINInteractor interactor;

    @BindView(R.id.input_secret)
    InputSecret inputSecretNew;
    @BindView(R.id.input_secret_confirm)
    InputSecret inputSecretConfirm;
    @BindView(R.id.btn_continue_active_card)
    ButtonContinue btnContinue;

    public static GeneratePINFragment newInstance(){
        return new GeneratePINFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (WalletMainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.interactor = new GeneratePINInteractor(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.generate_pin_fragment,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        //editNipNew.setOnFocusChangeListener(this);
        //editNipConfirm.setOnFocusChangeListener(this);
        //editNipNew.requestFocus();
        btnContinue.setOnClickListener(this);
        btnContinue.inactive();
        inputSecretNew.resquestFoucus();
        UiKeyBoard.showKeyboard();
        inputSecretNew.setInputSecretListener(this);
        inputSecretNew.setActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                if (validate()){
                    //this.activity.getRouter().onShowGeneratePIN();
                    //hideKeyboard();
                    inputSecretConfirm.resquestFoucus();
                    /*this.interactor.onActiveCard(SingletonUser.getInstance().getDataUser().getEmisor()
                            .getCuentas().get(0).getTarjetas().get(0).getNumero().trim());*/
                    //interactor.validateCard(editNumberCard.getText().toString().trim());
                    //interactor.onActiveCard("1234567890123456");
                }
                return true;
            }
            // Return true if you have consumed the action, else false.
            return false;
        });
        inputSecretConfirm.setInputSecretListener(new InputSecret.InputSecretListener() {
            @Override
            public void inputListener() {
                btnContinue.active();
            }
        });
        inputSecretConfirm.setActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                if (validate()){
                    //this.activity.getRouter().onShowGeneratePIN();
                    hideKeyboard();
                    //inputSecretConfirm.resquestFoucus();
                /*this.interactor.onActiveCard(SingletonUser.getInstance().getDataUser().getEmisor()
                        .getCuentas().get(0).getTarjetas().get(0).getNumero().trim());*/
                    //interactor.validateCard(editNumberCard.getText().toString().trim());
                    interactor.onActiveCard("1234567890123456");
                }
                return true;
            }
            // Return true if you have consumed the action, else false.
            return false;
        });

    }

    private boolean validate(){
        hideKeyboard();
        if (inputSecretNew.getView().toString().length() < 4){
            activity.showError("Los datos no son correctos, favor de introducirlos nuevamente");
            //this.activity.showError();

            return false;
        } else{
            return true;
        }
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)Objects.requireNonNull(getContext()).getSystemService(
                Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(inputSecretNew.getView().getWindowToken(), 0);
    }
    @Override
    public void onStop() {
        super.onStop();
        //UiKeyBoard.hideKeyboard(editNipNew);
        hideKeyboard();
    }

    @Override
    public void inputListener() {
        inputSecretConfirm.setVisibility(View.VISIBLE);
        //linearConfirm.setVisibility(View.VISIBLE);
        inputSecretConfirm.requestFocus();
    }

    @Override
    public void onClick(View view) {
        this.interactor.onActiveCard(SingletonUser.getInstance().getDataUser().getEmisor()
                .getCuentas().get(0).getTarjetas().get(0).getNumero().trim());
    }

    @Override
    public void onShowLoading() {
        activity.showLoad();
    }

    @Override
    public void onHideLoading() {
        activity.hideLoad();
    }

    @Override
    public void onSucces() {

    }

    @Override
    public void onFail(String error) {
        activity.showError(error);
    }
}
