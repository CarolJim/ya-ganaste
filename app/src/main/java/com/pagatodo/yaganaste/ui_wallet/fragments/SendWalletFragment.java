package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.EditTextImeBackListener;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.EnvioFormularioWallet;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.RequestPaymentActivity;
import com.pagatodo.yaganaste.ui_wallet.adapters.ElementsWalletAdapter;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.ui_wallet.views.ItemOffsetDecoration;
import com.pagatodo.yaganaste.utils.NumberCalcTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.ID_ENVIAR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.ID_SOLICITAR;

public class SendWalletFragment extends GenericFragment implements ElementsWalletAdapter.OnItemClickListener,
        EditTextImeBackListener {

    public static final String MONTO = "Monto";
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
    @BindView(R.id.saldoDisponible)
    MontoTextView saldoDisponible;

    private float MIN_AMOUNT = 1.0f, current_mount;
    Double monto;
    Payments payments;

    public static SendWalletFragment newInstance(Payments payments) {
        SendWalletFragment fragment = new SendWalletFragment();
        Bundle args = new Bundle();
        args.putSerializable("payments", payments);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        payments = (Payments) getArguments().getSerializable("payments");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_wallet, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;

    }

    @Override
    public void initViews() {
        keyboardView.setKeyBoard(getActivity(), R.xml.keyboard_nip);
        keyboardView.setPreviewEnabled(false);
        SingletonUser dataUser = SingletonUser.getInstance();
        saldoDisponible.setText("" + Utils.getCurrencyValue(dataUser.getDatosSaldo().getSaldoEmisor()));
        GridLayoutManager llm = new GridLayoutManager(getContext(), 2);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        rcvOpciones.addItemDecoration(itemDecoration);
        rcvOpciones.setLayoutManager(llm);
        //rcvOpciones.setAdapter(new ElementsWalletAdapter(getContext(), new ElementView().getListEnviar(getContext()), this));
        ElementsWalletAdapter elementsWalletAdapter = new ElementsWalletAdapter(getContext(), this, ElementView.getListEnviar(getContext()));
        rcvOpciones.setAdapter(elementsWalletAdapter);
        //elementsWalletAdapter.setList(ElementView.getListEnviar(getContext()));
        elementsWalletAdapter.notifyDataSetChanged();
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
        switch (elementView.getIdOperacion()) {
            case ID_ENVIAR:
                //  Integer valueAmount = Parseet_amount.getText().toString();
                if (actionCharge()) {
                    if (!UtilsNet.isOnline(getActivity())) {
                        UI.createSimpleCustomDialog("Error", getString(R.string.no_internet_access), getActivity().getSupportFragmentManager(), getFragmentTag());
                    } else {
                        Intent intent = new Intent(getContext(), EnvioFormularioWallet.class);
                        intent.putExtra(MONTO, monto);
                        startActivity(intent);
                        current_mount = 0;
                        monto = 00.00;
                    }
                }
                break;
            case ID_SOLICITAR:
                if (actionCharge()) {
                    Intent intent = new Intent(getContext(), RequestPaymentActivity.class);
                    intent.putExtra(MONTO, current_mount);
                    startActivity(intent);
                }
                break;
            default:
                Toast.makeText(getContext(), "Error de Operacion", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        NumberCalcTextWatcher.cleanData();

        et_amount.requestFocus();
        SingletonUser dataUser = SingletonUser.getInstance();
        saldoDisponible.setText("" + Utils.getCurrencyValue(dataUser.getDatosSaldo().getSaldoEmisor()));
        tvMontoEntero.setText("00");
        tvMontoDecimal.setText("00");
        et_amount.setText("$00.00");

    }

    @Override
    public void onImeBack() {
        et_amount.requestFocus();
    }

    private boolean actionCharge() {
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
                monto = Double.parseDouble(valueAmount);
                current_mount = Float.parseFloat(valueAmount);
                if (current_mount >= MIN_AMOUNT) {
                    return true;
                } else showValidationError(getString(R.string.new_body_envios_importe_error));
            } catch (NumberFormatException e) {
                showValidationError(getString(R.string.mount_valid));
            }
        } else showValidationError(getString(R.string.enter_mount));
        return false;
    }

    private void showValidationError(String error) {
        UI.createSimpleCustomDialog("Error", error, getActivity().getSupportFragmentManager(), getFragmentTag());
        //mySeekBar.setProgress(0);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            et_amount.requestFocus();
        } else if (et_amount != null) {
            NumberCalcTextWatcher.cleanData();

            //edtConcept.setText(null);
        }
    }
}
