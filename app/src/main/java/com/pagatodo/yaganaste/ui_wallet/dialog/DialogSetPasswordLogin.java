package com.pagatodo.yaganaste.ui_wallet.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.pagatodo.view_manager.components.inputs.InputSecretListener;
import com.pagatodo.view_manager.components.inputs.InputSecretPass;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IDialogSetPassword;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.Objects;

import butterknife.ButterKnife;

public class DialogSetPasswordLogin  extends DialogFragment implements View.OnClickListener {

    IDialogSetPassword listener;
    StyleButton btnAuth;
    //EditText edtPas;
    //TextInputLayout txtInput;
    StyleTextView txtTitleNotification;
    InputSecretPass passInput;

    private String title;

    public DialogSetPasswordLogin() {
    }

    public void setListener(IDialogSetPassword listener) {
        this.listener = listener;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_iset);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_set_passwordlogin, container);
        ButterKnife.bind(this, view);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        txtTitleNotification = (StyleTextView) view.findViewById(R.id.txtTitleNotification);
        btnAuth = (StyleButton) view.findViewById(R.id.btnAuthorizeDialog);
        //edtPas = (EditText) view.findViewById(R.id.editUserPasswordnew);
        //txtInput = (TextInputLayout) view.findViewById(R.id.text_passwordnew);
        passInput = (InputSecretPass) view.findViewById(R.id.input_pass);
        passInput.setRequestFocus();
        showKeyboard();
        passInput.setActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                hideKeyBoard();
                if (validate()){
                    /*activity.loadFragment(NewPasswwordFragment.newInstance(inputSecretPassCurrent
                            .getTextEdit()),R.id.container,Direction.FORDWARD,false);*/
                    hideKeyBoard();
                    listener.onPasswordSet(passInput.getText());
                    dismiss();
                }
                return true;
            }
            return false;
        });

        passInput.setInputSecretListener(new InputSecretListener() {
            @Override
            public void inputListenerFinish(View view) {
                hideKeyBoard();
                listener.onPasswordSet(passInput.getText());
                hideKeyBoard();
                dismiss();
            }

            @Override
            public void inputListenerBegin() {

            }
        });
        btnAuth.setOnClickListener(this);
        txtTitleNotification.setText(title);
        /*edtPas.setOnFocusChangeListener((view1, hasFocus) -> {
            if (hasFocus) {
                txtInput.setBackgroundResource(R.drawable.inputtext_active);
            } else if (edtPas.getText().length() < 6) {
                txtInput.setBackgroundResource(R.drawable.input_text_error);
            } else {
                txtInput.setBackgroundResource(R.drawable.inputtext_normal);
            }

        });*/

        /*edtPas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtPas.getText().length() == 6) {
//                    txtInput.setBackgroundResource(R.drawable.inputtext_active);
                    listener.onPasswordSet(edtPas.getText().toString());
                    dismiss();
                    InputMethodManager imm = (InputMethodManager)  App.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
        return view;
    }

    @Override
    public void onClick(View view) {
            this.dismiss();
    }

    private void showKeyboard(){
        InputMethodManager imm = (InputMethodManager)  App.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager) App.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(passInput
                .getInputEditText().getWindowToken(), 0);
    }

    private boolean validate(){
        boolean isValid = true;
        if (passInput.getText().length() < 6){
            isValid = false;
            passInput.isError();
            UI.showErrorSnackBar(Objects.requireNonNull(getActivity()),
                    "Por favor verifica que la contraseña sea correcta",Snackbar.LENGTH_SHORT);
        }
        return isValid;
    }
}
