package com.pagatodo.yaganaste.ui.preferuser;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.AsignarNipCustomWatcher;
import com.pagatodo.yaganaste.utils.AsignarNipTextWatcher;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyChangeNip#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyChangeNip extends GenericFragment implements ValidationForms, View.OnClickListener {

    private static int PIN_LENGHT = 4;
    @BindView(R.id.keyboard_view)
    CustomKeyboardView keyboardView;
    @BindView(R.id.asignar_edittext)
    CustomValidationEditText edtPin;
    @BindView(R.id.asignar_edittext2)
    CustomValidationEditText edtPin2;
    @BindView(R.id.asignar_edittext3)
    CustomValidationEditText edtPin3;
    @BindView(R.id.asignar_nip1_change_tv1)
    StyleTextView textCustomTv1;
    @BindView(R.id.asignar_nip2_change_tv1)
    StyleTextView textCustomTv2;
    @BindView(R.id.asignar_nip3_change_tv1)
    StyleTextView textCustomTv3;
    @BindView(R.id.fragment_myemail_btn)
    StyleButton finishBtn;
    @BindView(R.id.errorOldNipMessage)
    ErrorMessage errorOldNip;
    @BindView(R.id.errorNewNipMessage)
    ErrorMessage errorNewNip;
    @BindView(R.id.errorNewNipConfirmMessage)
    ErrorMessage errorNewNipConfirm;
    TextView tv1Num;
    TextView tv2Num;
    TextView tv3Num;
    TextView tv4Num;
    TextView tv5Num;
    TextView tv6Num;
    TextView tv7Num;
    TextView tv8Num;
    TextView tv9Num;
    TextView tv10Num;
    TextView tv11Num;
    TextView tv12Num;
    private View rootview;
    LinearLayout layout_control;
    LinearLayout layout_control2;
    LinearLayout layout_control3;
    private String nip = "";
    private String nipNew = "";
    private String nipNewConfirm = "";
    Drawable backgroundGrey;
    Drawable backgroundRed;
    AsignarNipCustomWatcher asinarNipWatcher1;
    AsignarNipCustomWatcher asinarNipWatcher2;
    AsignarNipCustomWatcher asinarNipWatcher3;


    public MyChangeNip() {
    }

    public static MyChangeNip newInstance() {
        MyChangeNip fragment = new MyChangeNip();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_my_change_nip, container, false);
        initViews();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        finishBtn.setOnClickListener(this);

        backgroundGrey = getContext().getResources()
                .getDrawable(R.drawable.rounded_border_edittext);

        backgroundRed = getContext().getResources()
                .getDrawable(R.drawable.rounded_border_red_edittext);

        keyboardView.setKeyBoard(getActivity(), R.xml.keyboard_nip);
        keyboardView.setPreviewEnabled(false);

        layout_control = (LinearLayout) rootview.findViewById(R.id.asignar_nip1_contol);
        layout_control2 = (LinearLayout) rootview.findViewById(R.id.asignar_nip2_contol);
        layout_control3 = (LinearLayout) rootview.findViewById(R.id.asignar_nip3_contol);

        tv1Num = (TextView) rootview.findViewById(R.id.asignar_nip1_tv1);
        tv2Num = (TextView) rootview.findViewById(R.id.asignar_nip1_tv2);
        tv3Num = (TextView) rootview.findViewById(R.id.asignar_nip1_tv3);
        tv4Num = (TextView) rootview.findViewById(R.id.asignar_nip1_tv4);

        tv5Num = (TextView) rootview.findViewById(R.id.asignar_nip2_tv1);
        tv6Num = (TextView) rootview.findViewById(R.id.asignar_nip2_tv2);
        tv7Num = (TextView) rootview.findViewById(R.id.asignar_nip2_tv3);
        tv8Num = (TextView) rootview.findViewById(R.id.asignar_nip2_tv4);

        tv9Num = (TextView) rootview.findViewById(R.id.asignar_nip3_tv1);
        tv10Num = (TextView) rootview.findViewById(R.id.asignar_nip3_tv2);
        tv11Num = (TextView) rootview.findViewById(R.id.asignar_nip3_tv3);
        tv12Num = (TextView) rootview.findViewById(R.id.asignar_nip3_tv4);

        // EditTExt oculto que procesa el PIN y sirve como ancla para validacion
        // Se le asigna un TextWatcher personalizado para realizar las oepraciones
        edtPin = (CustomValidationEditText) rootview.findViewById(R.id.asignar_edittext);
        edtPin2 = (CustomValidationEditText) rootview.findViewById(R.id.asignar_edittext2);
        edtPin3 = (CustomValidationEditText) rootview.findViewById(R.id.asignar_edittext3);

        asinarNipWatcher1 = new AsignarNipCustomWatcher(edtPin, tv1Num, tv2Num, tv3Num,
                tv4Num, textCustomTv1);
        asinarNipWatcher2 = new AsignarNipCustomWatcher(edtPin2, tv5Num, tv6Num, tv7Num,
                tv8Num, textCustomTv2);
        asinarNipWatcher3 = new AsignarNipCustomWatcher(edtPin3, tv9Num, tv10Num, tv11Num,
                tv12Num, textCustomTv3);

        edtPin.addCustomTextWatcher(asinarNipWatcher1);
        edtPin2.addCustomTextWatcher(asinarNipWatcher2);
        edtPin3.addCustomTextWatcher(asinarNipWatcher3);

        edtPin.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 4) {
                    validateForm();
                    layout_control.setBackground(backgroundGrey);
                    keyboardView.hideCustomKeyboard();
                    finishBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtPin2.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 4) {
                    validateForm();
                    layout_control2.setBackground(backgroundGrey);
                    keyboardView.hideCustomKeyboard();
                    finishBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtPin3.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 4) {
                    validateForm();
                    layout_control3.setBackground(backgroundGrey);
                    keyboardView.hideCustomKeyboard();
                    finishBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Si ocamos el area especial del Layout abrimos el Keyboard
        layout_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtPin.requestFocus();
                layout_control.setBackground(backgroundRed);
                layout_control2.setBackground(backgroundGrey);
                layout_control3.setBackground(backgroundGrey);
                keyboardView.showCustomKeyboard(v);
                finishBtn.setVisibility(View.GONE);
            }
        });

        layout_control2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtPin2.requestFocus();
                layout_control.setBackground(backgroundGrey);
                layout_control2.setBackground(backgroundRed);
                layout_control3.setBackground(backgroundGrey);
                keyboardView.showCustomKeyboard(v);
                finishBtn.setVisibility(View.GONE);
            }
        });

        layout_control3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtPin3.requestFocus();
                layout_control.setBackground(backgroundGrey);
                layout_control2.setBackground(backgroundGrey);
                layout_control3.setBackground(backgroundRed);
                keyboardView.showCustomKeyboard(v);
                finishBtn.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {
        getDataForm();

        if (nip.length() < PIN_LENGHT) {
            showValidationError(getString(R.string.asignar_pin));
            return;
        }

        onValidationSuccess();
    }

    public boolean isCustomKeyboardVisible() {
        return keyboardView.getVisibility() == View.VISIBLE;
    }

    public void hideKeyboard() {
        keyboardView.hideCustomKeyboard();
        finishBtn.setVisibility(View.VISIBLE);
        layout_control.setBackground(backgroundGrey);
        layout_control2.setBackground(backgroundGrey);
        layout_control3.setBackground(backgroundGrey);
    }

    private void showValidationError(Object err) {
        showValidationError(0, err);
    }

    @Override
    public void showValidationError(int id, Object o) {

    }

    @Override
    public void onValidationSuccess() {

    }

    @Override
    public void getDataForm() {
        nip = edtPin.getText().toString().trim();
        nipNew = edtPin2.getText().toString().trim();
        nipNewConfirm = edtPin3.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        getDataForm();
        if (!asinarNipWatcher1.isValid()) {
            errorOldNip.setMessageText("Son necesarios 4 números");
            errorOldNip.setVisibility(View.VISIBLE);
        } else {
            errorOldNip.setVisibility(View.INVISIBLE);
        }

        if (!asinarNipWatcher2.isValid()) {
            errorNewNip.setMessageText("Son necesarios 4 números");
            errorNewNip.setVisibility(View.VISIBLE);
        } else {
            errorNewNip.setVisibility(View.INVISIBLE);
        }

        if (!asinarNipWatcher3.isValid()) {
            errorNewNipConfirm.setMessageText("Son necesarios 4 números");
            errorNewNipConfirm.setVisibility(View.VISIBLE);
        } else {
            errorNewNipConfirm.setVisibility(View.INVISIBLE);
        }

        if (asinarNipWatcher1.isValid() && asinarNipWatcher2.isValid()) {
            if (nip.equals(nipNew)) {
                errorNewNip.setMessageText("El nuevo NIP debe ser diferente del anterior");
                errorNewNip.setVisibility(View.VISIBLE);
            }else{
                errorNewNip.setVisibility(View.INVISIBLE);
            }
        }

        if (asinarNipWatcher2.isValid() && asinarNipWatcher3.isValid()) {
            if (nipNew.equals(nipNewConfirm)) {
                errorNewNipConfirm.setVisibility(View.INVISIBLE);
            }else{
                errorNewNipConfirm.setMessageText("El NIP no coincide");
                errorNewNipConfirm.setVisibility(View.VISIBLE);
            }
        }

        if (asinarNipWatcher2.isValid() && !asinarNipWatcher3.isValid()) {
            if (nipNew.equals(nipNewConfirm)) {
                errorNewNipConfirm.setVisibility(View.INVISIBLE);
            }else{
                errorNewNipConfirm.setMessageText("El NIP no coincide");
                errorNewNipConfirm.setVisibility(View.VISIBLE);
            }
        }



        // errorOldNip errorNewNip errorNewNipConfirm
    }
}
