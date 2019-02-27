package com.pagatodo.yaganaste.ui_wallet.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;

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
import android.widget.TextView;

import com.pagatodo.view_manager.buttons.ButtonContinue;
import com.pagatodo.view_manager.components.inputs.InputSecretListener;
import com.pagatodo.view_manager.components.inputs.InputSecretPass;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.sidebar.ChangePassword.NewPasswwordFragment;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IDialogSetPassword;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.Objects;

public class DialogSetPassword extends DialogFragment implements View.OnClickListener {

    private static final String TAG_TITLE = "TAG_TITLE";
    private IDialogSetPassword listener;
    private View rootView;
    private String title;

    @BindView(R.id.dialog_title)
    TextView dialogTitle;
    @BindView(R.id.input_pass)
    InputSecretPass passInput;
    @BindView(R.id.btn_continue)
    ButtonContinue btnContinue;

    public DialogSetPassword() {
    }

    public DialogSetPassword newIntance(String title) {
        DialogSetPassword dialogSetPassword = new DialogSetPassword();
        Bundle bundle = new Bundle();
        bundle.putString(TAG_TITLE,title);
        dialogSetPassword.setArguments(bundle);
        return dialogSetPassword;
    }

    public void setListener(IDialogSetPassword listener) {
        this.listener = listener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            title = getArguments().getString(TAG_TITLE);
        } else {
            title = getResources().getString(R.string.dialog_state_account);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //final Dialog dialog = super.onCreateDialog(savedInstanceState);
        //Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.dialog_iset);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_set_password,null);
        builder.setView(rootView);
        init();
        return builder.create();
    }

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_set_password, container);
        init();
        return rootView;
    }*/

    private void init(){
        ButterKnife.bind(this,rootView);

        dialogTitle.setText(title);
        btnContinue.active();
        btnContinue.setOnClickListener(this);
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
    @Override
    public void onClick(View view) {
        if (validate()){
            listener.onPasswordSet(passInput.getText());
            this.dismiss();
        }
/*        if (edtPas.getText().toString().isEmpty() || edtPas.getText().length() < 6) {
            txtInput.setBackgroundResource(R.drawable.input_text_error);
        } else {


        }
        */
    }

    @Override
    public void onStop() {
        super.onStop();
        hideKeyBoard();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        hideKeyBoard();
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
}
