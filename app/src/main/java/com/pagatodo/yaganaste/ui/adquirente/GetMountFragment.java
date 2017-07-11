package com.pagatodo.yaganaste.ui.adquirente;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.Selection;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.interfaces.EditTextImeBackListener;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.AdqActivity;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentFormBaseFragment;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.NumberCalcTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;

public class GetMountFragment extends PaymentFormBaseFragment implements EditTextImeBackListener {

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
    private StyleTextView tvMontoEntero, tvMontoDecimal;

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
        /**
         * layout_amount capa que controla el abrir el teclado
         * tvMontoEntero TextView que contiene los enteros del elemento
         * tvMontoDecimal TextView que contiene los decimales del elemento
         * et_amount EditText oculto que captura los elementos que procesaremos, pero que no se
         * muestra en pantalla
         */
        layout_amount = (LinearLayout) rootview.findViewById(R.id.layout_amount_control);
        tvMontoEntero = (StyleTextView) rootview.findViewById(R.id.tv_monto_entero);
        tvMontoDecimal = (StyleTextView) rootview.findViewById(R.id.tv_monto_decimal);
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

        edtConcept.setOnEditTextImeBackListener(this);
        edtConcept.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtConcept.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    et_amount.requestFocus();
                    return true;
                }
                return false;
            }
        });

        et_amount.requestFocus();
    }

    private void actionCharge() {
        String valueAmount = et_amount.getText().toString().trim();

        // Limpiamos del "," que tenemos del EditText auxiliar
        int positionQuote = valueAmount.indexOf(",");
        if (positionQuote > 0) {
            String[] valueAmountArray = valueAmount.split(",");
            valueAmount = valueAmountArray[0] + valueAmountArray[1];
        }

        if (valueAmount.length() > 0 && !valueAmount.equals(getString(R.string.mount_cero))) {
            try {
                StringBuilder cashAmountBuilder = new StringBuilder(valueAmount);

                // Limpiamos del caracter $ en caso de tenerlo
                int positionMoney = valueAmount.indexOf("$");
                if (positionMoney == 0) {
                    valueAmount = cashAmountBuilder.deleteCharAt(0).toString();
                }

                float current_mount = Float.parseFloat(valueAmount);
                String current_concept = edtConcept.getText().toString().trim();//Se agrega Concepto opcional
                if (current_mount >= MIN_AMOUNT) {
                    TransactionAdqData.getCurrentTransaction().setAmount(valueAmount);
                    TransactionAdqData.getCurrentTransaction().setDescription(current_concept);
                    setData("", "");
                    mySeekBar.setProgress(0);
                    NumberCalcTextWatcher.cleanData();

                    Intent intent = new Intent(getActivity(), AdqActivity.class);
                    if (getActivity() instanceof AccountActivity) {
                        getActivity().startActivityForResult(intent, Constants.PAYMENTS_ADQUIRENTE);
                    } else {
                        startActivity(intent);
                    }

                } else showValidationError(getString(R.string.mount_be_higer));
            } catch (NumberFormatException e) {
                showValidationError(getString(R.string.mount_valid));
            }
        } else showValidationError(getString(R.string.enter_mount));

    }

    @Override
    protected void continuePayment() {
        actionCharge();
    }

    private void showValidationError(String error) {
        UI.showToast(error, getActivity());
        mySeekBar.setProgress(0);
    }

    private void setData(String amount, String concept) {
        Log.d("GetMount", "setData After Amount " + amount);
        et_amount.setText(amount);
        Selection.setSelection(et_amount.getText(), et_amount.getText().toString().length());
        if (!concept.equals("")) {
            edtConcept.setText(concept);
        }
        Log.d("GetMount", "setData Before Amount " + amount);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("GetMount", "Resume Amount " + TransactionAdqData.getCurrentTransaction().getAmount());
        setData(TransactionAdqData.getCurrentTransaction().getAmount(), TransactionAdqData.getCurrentTransaction().getDescription());
    }

    public boolean isCustomKeyboardVisible() {
        return keyboardView.getVisibility() == View.VISIBLE;
    }

    public void hideKeyboard() {
        keyboardView.hideCustomKeyboard();
    }

    @Override
    public void onImeBack() {
        et_amount.requestFocus();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            et_amount.requestFocus();
        }
    }
}

