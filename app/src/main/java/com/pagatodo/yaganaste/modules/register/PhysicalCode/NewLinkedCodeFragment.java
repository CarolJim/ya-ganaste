package com.pagatodo.yaganaste.modules.register.PhysicalCode;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.QRs;
import com.pagatodo.yaganaste.data.model.RegisterUserNew;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewLinkedCodeFragment extends GenericFragment implements TextWatcher, OnClickListener,
        TextView.OnEditorActionListener,View.OnFocusChangeListener {

    @BindView(R.id.edit_text_name_qr)
    public EditText editNameQR;
    @BindView(R.id.text_number_qr)
    public TextInputLayout textInput;
    @BindView(R.id.name_qr_text)
    public StyleTextView textNameQR;
    @BindView(R.id.button_continue)
    public StyleButton btnContinue;

    private View rootView;
    private RegActivity activity;
    private String textPlate;

    public static NewLinkedCodeFragment newInstance(String textDisplay, int restext){
        NewLinkedCodeFragment fragment = new NewLinkedCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DISPLAY",textDisplay);
        bundle.putInt("TITLE",restext);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (RegActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.new_linked_ode_fragment,container,false);
        initViews();
        return rootView;

    }

    public void initViews() {
        ButterKnife.bind(this, rootView);
        activity.showStepBar();
        RegisterUserNew registerAgent = RegisterUserNew.getInstance();
        editNameQR.setText(registerAgent.getNombreNegocio());
        textNameQR.setText(registerAgent.getNombreNegocio());

        editNameQR.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
        imm.showSoftInput(editNameQR, InputMethodManager.SHOW_IMPLICIT);


        if (getArguments() != null){
            textPlate = getArguments().getString("DISPLAY");
            assert textPlate != null;
            if (!textPlate.isEmpty()) {
                ((StyleTextView) rootView.findViewById(R.id.plate))
                        .setText(lastCode(Objects.requireNonNull(getArguments().getString("DISPLAY"))));
            } else {
                textPlate = "";
            }
        }
        rootView.findViewById(R.id.button_continue).setOnClickListener(view -> activity.getRouter().showLinkedCodes());
        editNameQR.addTextChangedListener(this);
        editNameQR.setOnEditorActionListener(this);
        editNameQR.setOnFocusChangeListener(this);
        btnContinue.setOnClickListener(this);

    }

    private String lastCode(String code){
        return code.substring(8);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        textNameQR.setText(charSequence);
        if (validate()){
            btnContinue.setBackgroundResource(R.drawable.button_yes);
        } else {
            btnContinue.setBackgroundResource(R.drawable.btn_desactive);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private boolean validate(){
        boolean isValid = true;
        if (editNameQR.getText().toString().isEmpty()){
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void onClick(View view) {
        if (validate()){
            proccessData();
        }
    }

    private void proccessData(){

        if (textPlate.isEmpty()){
            RegisterUserNew.getInstance().setqRs(new ArrayList<>());
            RegisterUserNew.getInstance().getqRs().add(new QRs(editNameQR.getText().toString().trim(),textPlate, true,""));
            activity.getRouter().showSMSAndroid();
        } else {
            RegisterUserNew.getInstance().getqRs().add(new QRs(editNameQR.getText().toString().trim(),textPlate, false,""));
            activity.getRouter().showLinkedCodes();
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if(actionId == EditorInfo.IME_ACTION_DONE){
            if (validate()){
                proccessData();
            } else {
                textInput.setBackgroundResource(R.drawable.inputtext_error);
            }
        }
        return false;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b){
            textInput.setBackgroundResource(R.drawable.inputtext_active);
        } else {
            textInput.setBackgroundResource(R.drawable.inputtext_normal);
        }
    }
}
