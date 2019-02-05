package com.pagatodo.yaganaste.modules.registerAggregator.NameQR;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

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

import com.google.android.material.textfield.TextInputLayout;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.QRs;
import com.pagatodo.yaganaste.data.model.RegisterAggregatorNew;
import com.pagatodo.yaganaste.modules.registerAggregator.AggregatorActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class NameQRFragment extends GenericFragment implements
        TextWatcher, View.OnClickListener, TextView.OnEditorActionListener,
        View.OnFocusChangeListener {
    private EditText editNameQR;
    private TextInputLayout textInput;
    private StyleTextView textNameQR;
    private StyleButton btnContinue;

    private View rootView;
    private AggregatorActivity activity;
    private String textPlate;

    public static NameQRFragment newInstance(String textDisplay, int restext) {
        NameQRFragment fragment = new NameQRFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DISPLAY", textDisplay);
        bundle.putInt("TITLE", restext);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AggregatorActivity) context;
    }

    public NameQRFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_name_qr, container, false);
        editNameQR = (EditText) rootView.findViewById(R.id.edit_text_name_qr);
        textInput = (TextInputLayout) rootView.findViewById(R.id.text_number_qr);
        textNameQR = (StyleTextView) rootView.findViewById(R.id.name_qr_text);
        btnContinue = (StyleButton) rootView.findViewById(R.id.button_continue);
        initViews();
        return rootView;

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        RegisterAggregatorNew registerAggregatorNew = RegisterAggregatorNew.getInstance();
        editNameQR.setText(registerAggregatorNew.getNombreNegocio());
        textNameQR.setText(registerAggregatorNew.getNombreNegocio());

        editNameQR.requestFocus();
        textInput.setBackgroundResource(R.drawable.inputtext_active);
        editNameQR.setSelection(editNameQR.getText().length());
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
        imm.showSoftInput(editNameQR, InputMethodManager.SHOW_IMPLICIT);


        if (getArguments() != null) {
            textPlate = getArguments().getString("DISPLAY");
            assert textPlate != null;
            if (!textPlate.isEmpty()) {
                ((StyleTextView) rootView.findViewById(R.id.plate))
                        .setText(lastCode(Objects.requireNonNull(getArguments().getString("DISPLAY"))));
            } else {
                textPlate = "";
            }
        }
        //rootView.findViewById(R.id.button_continue).setOnClickListener(view -> activity.getRouter().showLinkedCodes());
        editNameQR.addTextChangedListener(this);
        editNameQR.setOnEditorActionListener(this);
        editNameQR.setOnFocusChangeListener(this);
        btnContinue.setOnClickListener(this);
    }

    private String lastCode(String code) {
        return code.substring(8);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        textNameQR.setText(s);
        if (validate()) {
            btnContinue.setBackgroundResource(R.drawable.button_yes);
        } else {
            btnContinue.setBackgroundResource(R.drawable.btn_desactive);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private boolean validate() {
        boolean isValid = true;
        if (editNameQR.getText().toString().isEmpty()) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void onClick(View v) {
        if (validate()) {
            proccessData();
        }
    }

    private void proccessData() {
        if (textPlate.isEmpty()) {
            RegisterAggregatorNew.getInstance().setqRs(new ArrayList<>());
            RegisterAggregatorNew.getInstance().getqRs().add(new QRs(editNameQR.getText().toString().trim(), textPlate, true, ""));
            //activity.getRouter().showSMSAndroid();
        } else {
            if (!isRepit()) {
                RegisterAggregatorNew.getInstance().getqRs().add(new QRs(editNameQR.getText().toString().trim(), textPlate, false, ""));
                //activity.getRouter.showLinkedCodes();
            } else {
                //activity.onErrorValidatePlate("El QR ya fue agregado");
            }
        }
    }

    private boolean isRepit() {
        boolean isRepit = false;
        for (QRs qRs : RegisterAggregatorNew.getInstance().getqRs()) {
            if (qRs.getPlate().equalsIgnoreCase(textPlate)) {
                isRepit = true;
                break;
            }
        }
        return isRepit;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            if (validate()) {
                proccessData();
            } else {
                textInput.setBackgroundResource(R.drawable.input_text_error);
            }
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            textInput.setBackgroundResource(R.drawable.inputtext_active);
        } else {
            textInput.setBackgroundResource(R.drawable.inputtext_normal);
        }
    }
}
