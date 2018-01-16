package com.pagatodo.yaganaste.ui_wallet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoRequestPayment;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IAddRequestPayment;
import com.pagatodo.yaganaste.utils.PhoneTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class DialogAddRequestPayment extends DialogFragment {

    View rootView;
    @BindView(R.id.receiverName)
    StyleEdittext edtReceiver;
    @BindView(R.id.errorNameRequest)
    ErrorMessage errorName;
    @BindView(R.id.cardNumber)
    StyleEdittext edtPhoneNumber;
    @BindView(R.id.errorPhoneRequest)
    ErrorMessage errorPhone;
    @BindView(R.id.btn_add_request)
    StyleButton btnAddRequest;

    IAddRequestPayment addRequestPaymentListener;

    public static DialogAddRequestPayment newInstance() {
        DialogAddRequestPayment dialogFragment = new DialogAddRequestPayment();
        /*Bundle args = new Bundle();
        args.putSerializable("backUpResponse", null);
        dialogFragment.setArguments(args);*/
        return dialogFragment;
    }

    public void setAddRequestPaymentListener(IAddRequestPayment addRequestPaymentListener) {
        this.addRequestPaymentListener = addRequestPaymentListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_iset);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_add_request_payment, null);
        initViews();
        return rootView;
    }

    private void initViews() {
        ButterKnife.bind(this, rootView);
        edtPhoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtPhoneNumber.setSingleLine();
        edtPhoneNumber.addTextChangedListener(new PhoneTextWatcher(edtPhoneNumber));
        edtReceiver.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                errorName.setVisibility(GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btnAddRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtReceiver.getText().toString().trim().equals("")) {
                    errorName.setVisibility(View.VISIBLE);
                    errorName.setMessageText(getString(R.string.txt_error_name_receiver));
                } else if (edtPhoneNumber.getText().toString().trim().equals("")) {
                    errorPhone.setMessageText(getString(R.string.txt_error_phone_receiver));
                } else {
                    errorName.setVisibility(View.VISIBLE);
                    errorPhone.setMessageText("");
                    DtoRequestPayment dtoRequestPayment = new DtoRequestPayment(0, edtReceiver.getText().toString(),
                            edtPhoneNumber.getText().toString().replaceAll(" ", ""), "#00A1E1", "");
                    addRequestPaymentListener.onAddNewRequest(dtoRequestPayment);
                    UI.showToastShort(getString(R.string.txt_add_success_request), getActivity());
                    dismiss();
                }
            }
        });
    }
}
