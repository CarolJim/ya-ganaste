package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.interfaces.EditTextImeBackListener;
import com.pagatodo.yaganaste.ui._controllers.AdqActivity;
import com.pagatodo.yaganaste.ui._controllers.EnvioFormularioWallet;
import com.pagatodo.yaganaste.ui._controllers.manager.DongleBatteryHome;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.ElementsWalletAdpater;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ElementView;
import com.pagatodo.yaganaste.ui_wallet.views.ItemOffsetDecoration;
import com.pagatodo.yaganaste.utils.NumberCalcTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui_wallet.interfaces.ElementView.ID_ENVIAR;
import static com.pagatodo.yaganaste.ui_wallet.interfaces.ElementView.ID_SOLICITAR;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENTS_ADQUIRENTE;

public class SendWalletFragment extends GenericFragment implements ElementsWalletAdpater.OnItemClickListener,
        EditTextImeBackListener {

    @BindView(R.id.keyboard_view)
    CustomKeyboardView keyboardView;
    @BindView(R.id.rcv_elements)
    RecyclerView rcvOpciones;
    @BindView(R.id.et_amount)
    EditText et_amount;

    @BindView(R.id.tv_monto_entero)
    StyleTextView tvMontoEntero;
    @BindView(R.id.tv_monto_decimal)
    StyleTextView tvMontoDecimal;

    private float MIN_AMOUNT = 1.0f;


    public static SendWalletFragment newInstance() {
        SendWalletFragment fragment = new SendWalletFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_wallet, container, false);
        ButterKnife.bind(this,view);
        initViews();

        return view;

    }

    @Override
    public void initViews() {
        keyboardView.setKeyBoard(getActivity(), R.xml.keyboard_nip);
        keyboardView.setPreviewEnabled(false);

        GridLayoutManager llm = new GridLayoutManager(getContext(),2);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        rcvOpciones.addItemDecoration(itemDecoration);
        rcvOpciones.setLayoutManager(llm);
        rcvOpciones.setAdapter(new ElementsWalletAdpater(getContext(),new ElementView().getListEnviar(getContext()),this));

        et_amount.addTextChangedListener(new NumberCalcTextWatcher(et_amount, tvMontoEntero, tvMontoDecimal, null));

        //keyboardView.setPreviewEnabled(false);
        // Make the custom keyboard appear
        /*et_amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    keyboardView.showCustomKeyboard(v);
                } else {
                    keyboardView.hideCustomKeyboard();
                }
            }
        });*/

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

        et_amount.requestFocus();
    }

    @Override
    public void onItemClick(ElementView elementView) {
        switch (elementView.getIdOperacion()){
            case ID_ENVIAR:
                Intent intent = new Intent(getContext(), EnvioFormularioWallet.class);
                startActivity(intent);
                break;
            case ID_SOLICITAR:
                break;

            default:
                Toast.makeText(getContext(),"Error de Operacion",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        NumberCalcTextWatcher.cleanData();
        et_amount.setText("0");
        et_amount.requestFocus();

    }

    @Override
    public void onImeBack() {
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
                String current_concept = "ENVIO";//Se agrega Concepto opcional
                if (current_mount >= MIN_AMOUNT) {
                    TransactionAdqData.getCurrentTransaction().setAmount(valueAmount);
                    TransactionAdqData.getCurrentTransaction().setDescription(current_concept);
                    //setData("", "");
                    /*NumberCalcTextWatcher.cleanData();
                    et_amount.setText("0");
                    edtConcept.setText(null);
                    mySeekBar.setProgress(0);
                    NumberCalcTextWatcher.cleanData();
*/
                    Intent intent = new Intent(getActivity(), AdqActivity.class);
                    getActivity().startActivityForResult(intent, PAYMENTS_ADQUIRENTE);
                } else showValidationError(getString(R.string.mount_be_higer));
            } catch (NumberFormatException e) {
                showValidationError(getString(R.string.mount_valid));
            }
        } else showValidationError(getString(R.string.enter_mount));

    }

    private void showValidationError(String error) {
        UI.showToast(error, getActivity());
        //mySeekBar.setProgress(0);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            et_amount.requestFocus();
        } else if (et_amount != null) {
            NumberCalcTextWatcher.cleanData();
            et_amount.setText("0");
            //edtConcept.setText(null);
        }
    }
}
