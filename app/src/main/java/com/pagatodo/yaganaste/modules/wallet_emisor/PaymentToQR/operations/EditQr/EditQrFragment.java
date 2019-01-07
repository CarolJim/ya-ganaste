package com.pagatodo.yaganaste.modules.wallet_emisor.PaymentToQR.operations.EditQr;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.data.QrItems;
import com.pagatodo.yaganaste.modules.wallet_emisor.PaymentToQR.operations.QrOperationActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditQrFragment extends GenericFragment implements View.OnClickListener,
        EditQrContracts.Listener, TextWatcher, View.OnFocusChangeListener, TextView.OnEditorActionListener {


    private static String TAG_QR_ITEM = "TAG_QR_ITEM";
    private QrOperationActivity activity;
    private View rootView;
    private QrItems item;
    private EditQrInteractor interactor;

    @BindView(R.id.txt_name_qr)
    StyleTextView namQR;
    @BindView(R.id.text_name_qr)
    TextInputLayout inputNameQr;
    @BindView(R.id.edit_code_qr)
    EditText editCodeQr;
    @BindView(R.id.btn_continue_edit_qr)
    StyleButton btnContinue;

    public static EditQrFragment newInstance(QrItems item){
        EditQrFragment fragment = new EditQrFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_QR_ITEM,item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null){
            item = (QrItems) getArguments().getSerializable(TAG_QR_ITEM);
            assert item != null;
        }
        interactor = new EditQrInteractor(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (QrOperationActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.edit_qr_fragment,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        if (item != null){
            namQR.setText(item.getQrUser().getAlias());
            editCodeQr.setText(item.getQrUser().getAlias());
            editCodeQr.addTextChangedListener(this);
            editCodeQr.setOnFocusChangeListener(this);
            editCodeQr.requestFocus();
            editCodeQr.setOnEditorActionListener(this);
            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity())
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm)
                    .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
            imm.showSoftInput(editCodeQr, InputMethodManager.SHOW_IMPLICIT);
        }
        btnContinue.setOnClickListener(this);
        /*
        editText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, keyEvent ->
    if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent?.keyCode == KeyEvent.KEYCODE_ENTER) {
        userEnteredQuery(editText.text.toString())
        return@OnEditorActionListener true
    }

    false
})
         */
    }

    @Override
    public void onClick(View view) {
        if (!editCodeQr.getText().toString().isEmpty()) {
            item.getQrUser().setAlias(editCodeQr.getText().toString());
            interactor.onEditQr(item);
            this.activity.showLoader("");
        }
    }

    @Override
    public void onSuccessEdit() {
        this.activity.hideLoader();
        this.activity.finish();
    }

    @Override
    public void onErrorEdit(String msjError) {
        this.activity.hideLoader();
        UI.showErrorSnackBar(activity, msjError, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() > 0) {
            namQR.setText(charSequence);
            btnContinue.setEnabled(true);
            btnContinue.setBackgroundResource(R.drawable.button_yes);
        } else {
            btnContinue.setEnabled(false);
            btnContinue.setBackgroundResource(R.drawable.btn_desactive);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b){
            inputNameQr.setBackgroundResource(R.drawable.inputtext_active);
        } else {
            inputNameQr.setBackgroundResource(R.drawable.inputtext_normal);
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if (!editCodeQr.getText().toString().isEmpty()) {
                item.getQrUser().setAlias(editCodeQr.getText().toString());
                interactor.onEditQr(item);
                this.activity.showLoader("");
            }
        }
        return true;
    }
}
