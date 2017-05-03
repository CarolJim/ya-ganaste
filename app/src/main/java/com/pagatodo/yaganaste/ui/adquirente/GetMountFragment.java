package com.pagatodo.yaganaste.ui.adquirente;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.ui._controllers.AdqActivity;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentFormBaseFragment;
import com.pagatodo.yaganaste.utils.NumberCalcTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;

import butterknife.BindView;

public class GetMountFragment extends PaymentFormBaseFragment {

    @BindView(R.id.et_amount)
    EditText et_amount;
    @BindView(R.id.edtConcept)
    StyleEdittext edtConcept;
    @BindView(R.id.keyboard_view)
    CustomKeyboardView keyboardView;
    private String amount = "";
    private String concept = "";
    LinearLayout layout_amount;

    private float MIN_AMOUNT = 1.0f;

    private EditText etMonto;
    private TextView tvMontoEntero, tvMontoDecimal;

    public GetMountFragment() {
        // Required empty public constructor
    }

    public static GetMountFragment newInstance() {
        return new GetMountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_monto, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        super.initViews();
        layout_amount = (LinearLayout) rootview.findViewById(R.id.layout_amount_control);
        tvMontoEntero = (TextView) rootview.findViewById(R.id.tv_monto_entero);
        tvMontoDecimal = (TextView) rootview.findViewById(R.id.tv_monto_decimal);
        et_amount.addTextChangedListener(new NumberCalcTextWatcher(et_amount, tvMontoEntero, tvMontoDecimal));
        keyboardView.setKeyBoard(getActivity(), R.xml.keyboard_nip);
        keyboardView.setPreviewEnabled(false);
      /*  et_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 4) {
                    keyboardView.hideCustomKeyboard();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });*/

        // Make the custom keyboard appear
        et_amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    keyboardView.showCustomKeyboard(v);
                } else {
                    keyboardView.hideCustomKeyboard();
                }
            }
        });

        /**
         * Agregamos el Listener al layout que contiene todos los elementos, esto es para que se
         * abra el teclado custom
         */
        layout_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_amount.requestFocus();
                keyboardView.showCustomKeyboard(v);
            }
        });

        et_amount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                keyboardView.showCustomKeyboard(v);
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });

    }

    private void actionCharge() {
        String valueAmount = et_amount.getText().toString().trim();
        if (valueAmount.length() > 0 && !valueAmount.equals("$0.00")) {
            try {
                StringBuilder cashAmountBuilder = new StringBuilder(valueAmount);
                valueAmount = cashAmountBuilder.deleteCharAt(0).toString();
                float current_mount = Float.parseFloat(valueAmount);
                String current_concept = edtConcept.getText().toString().trim();//Se agrega Concepto opcional
                if (current_mount >= MIN_AMOUNT) {
                    TransactionAdqData.getCurrentTransaction().setAmount(String.format("%s",current_mount));
                    TransactionAdqData.getCurrentTransaction().setDescription(current_concept);
                    Intent intent = new Intent(getActivity(), AdqActivity.class);
                    startActivity(intent);
                    setData("","");
                    mySeekBar.setProgress(0);
                } else showValidationError("El monto tiene que ser mayor");
            } catch (NumberFormatException e) {
                showValidationError("Ingresa un monto válido.");
            }
        } else showValidationError("Por favor ingrese un monto.");

    }

    @Override
    protected void continuePayment() {
        actionCharge();
    }

    private void showValidationError(String error){
        UI.showToast(error, getActivity());
        mySeekBar.setProgress(0);
    }

    private void setData(String amount,String concept){
        et_amount.setText(amount);
        edtConcept.setText(concept);
    }

    @Override
    public void onResume(){
        super.onResume();
        setData(TransactionAdqData.getCurrentTransaction().getAmount(),TransactionAdqData.getCurrentTransaction().getDescription());
    }
}

