package com.pagatodo.yaganaste.ui_wallet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;

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
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;

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
    @BindView(R.id.layoutImageContact)
    RelativeLayout layoutImageContact;

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
                    //UI.showToastShort(getString(R.string.txt_add_success_request), getActivity());
                    dismiss();
                }
            }
        });
        layoutImageContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                getActivity().startActivityForResult(contactPickerIntent, CONTACTS_CONTRACT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONTACTS_CONTRACT && resultCode != 0)  {
            contactPicked(data);
        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor;
        String phoneNo = null;
        String nameDisplay = "";
        Uri uri = data.getData();
        cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            //get column index of the Phone Number
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            // column index of the contact name
            //int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex).replaceAll("\\s", "").replaceAll("\\+", "").replaceAll("-", "").trim();
            nameDisplay = cursor.getString(nameIndex);
            if (phoneNo.length() > 10) {
                phoneNo = phoneNo.substring(phoneNo.length() - 10);
            }
        }
        edtPhoneNumber.setText(phoneNo);

        /**
         * Validacion de nombre vacio
         */
        try {
            int oneNumbre = Integer.parseInt(nameDisplay.substring(0, 2));
            //receiverName.setText("");
        } catch (Exception e) {
            edtReceiver.setText(nameDisplay);
        }
    }
}
