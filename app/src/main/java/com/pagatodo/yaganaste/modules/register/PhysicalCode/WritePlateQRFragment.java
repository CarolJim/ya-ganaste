package com.pagatodo.yaganaste.modules.register.PhysicalCode;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WritePlateQRFragment extends GenericFragment implements View.OnFocusChangeListener {

    private View rootView;
    private RegActivity activity;

    @BindView(R.id.edit_code_wr)
    public EditText editTextWrQr;
    @BindView(R.id.btn_continue)
    public StyleButton btnContinue;
    @BindView(R.id.text_number_qr)
    public TextInputLayout inputQr;

    public static WritePlateQRFragment newInstance(){
        return new WritePlateQRFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (RegActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.write_plateqr_fragment,container,false);
        initViews();
        return rootView;

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnContinue.setOnClickListener(view -> {
            if (validateData()){
                //activity.getRouter().showNewLinkedCode(editTextWrQr.getText().toString());
                activity.getInteractor().onValidateQr(editTextWrQr.getText().toString());
            } else {
                inputQr.setBackgroundResource(R.drawable.inputtext_error);
            }
        });
        editTextWrQr.setOnFocusChangeListener(this);
        editTextWrQr.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE){
                if (validateData()){
                    //activity.getRouter().showNewLinkedCode(editTextWrQr.getText().toString());
                    activity.getInteractor().onValidateQr(editTextWrQr.getText().toString());
                } else {
                    inputQr.setBackgroundResource(R.drawable.inputtext_error);
                }
            }
            return false;
        });

        editTextWrQr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (validateData()){
                    btnContinue.setBackgroundResource(R.drawable.button_yes);
                } else {
                    btnContinue.setBackgroundResource(R.drawable.btn_desactive);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean validateData(){
        boolean isValid = true;
        if (editTextWrQr.getText().toString().isEmpty()) {
            isValid = false;
        }
        if (editTextWrQr.getText().toString().length() < 12){
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b){
            inputQr.setBackgroundResource(R.drawable.inputtext_active);
            InputMethodManager imm = (InputMethodManager)   activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } else {
            inputQr.setBackgroundResource(R.drawable.inputtext_normal);
        }
    }
}
