package com.pagatodo.yaganaste.ui_wallet.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IDialogSetPassword;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class DialogSetPassword extends DialogFragment implements View.OnClickListener {

    IDialogSetPassword listener;
    StyleButton btnAuth;
    EditText edtPas;
    TextInputLayout txtInput;
    StyleTextView txtTitleNotification;

    private String title;

    public DialogSetPassword() {
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
        View view = inflater.inflate(R.layout.dialog_set_password, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        txtTitleNotification = (StyleTextView) view.findViewById(R.id.txtTitleNotification);
        btnAuth = (StyleButton) view.findViewById(R.id.btnAuthorizeDialog);
        edtPas = (EditText) view.findViewById(R.id.edit_password);
        txtInput = (TextInputLayout) view.findViewById(R.id.text_password);
        btnAuth.setOnClickListener(this);
        //txtTitleNotification.setText(title);
        edtPas.setOnFocusChangeListener((view1, hasFocus) -> {
            if (hasFocus) {
                txtInput.setBackgroundResource(R.drawable.inputtext_active);
            } else if (edtPas.getText().length() < 6) {
                txtInput.setBackgroundResource(R.drawable.inputtext_error);
            } else {
                txtInput.setBackgroundResource(R.drawable.inputtext_normal);
            }

        });
        edtPas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtPas.getText().length() == 6) {
                    txtInput.setBackgroundResource(R.drawable.inputtext_active);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        if (edtPas.getText().toString().isEmpty() || edtPas.getText().length() < 6) {
            txtInput.setBackgroundResource(R.drawable.inputtext_error);
        } else {
            listener.onPasswordSet(edtPas.getText().toString());
            UI.hideKeyBoard(getActivity());
            this.dismiss();
        }
    }
}
