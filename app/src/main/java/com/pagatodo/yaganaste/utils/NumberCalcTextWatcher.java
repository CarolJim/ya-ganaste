package com.pagatodo.yaganaste.utils;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by flima on 09/03/2017.
 */

public class NumberCalcTextWatcher implements TextWatcher {

    private  EditText etMonto;
    private TextView tvMontoEntero, tvMontoDecimal;

    public NumberCalcTextWatcher(EditText editText, TextView tvMontoEntero, TextView tvMontoDecimal) {
        this.etMonto = editText;
        this.tvMontoEntero = tvMontoEntero;
        this.tvMontoDecimal = tvMontoDecimal;
    }

    @Override
    public void afterTextChanged(Editable arg0) {
        if (etMonto.getText() != null
                && !etMonto.getText().toString().equals("")) {
            String monto = String.valueOf(Utils.getDoubleValue(etMonto));
            String montos[] = monto.split("\\.");
            if (montos.length == 2) {
                if (tvMontoEntero != null)
                    tvMontoEntero.setText(montos[0]);
                if (tvMontoDecimal != null)
                    if(montos[1].toString().equals("0")){
                        tvMontoDecimal.setText("00");
                    }else {
                        tvMontoDecimal.setText(montos[1]);
                    }
                    /*if (context instanceof ObtenMasActivity)
                        ((ObtenMasActivity) context).transactionCardRequest.amount = monto.replace(",", "");*/
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {
    }
};

    /*public View.OnClickListener btnContinuarListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String montodouble = null;
            if (!etMonto.getText().toString().equals("")) {
                montodouble = etMonto.getText().toString();
            }
            if ( etMonto.getText().length() >= 1 && Utils.getDoubleValue(etMonto) != 0 ) {
                ((App) context.getApplication()).addDir(etMonto.getText().toString().replace(",", ""));
                if (onEventListener != null){
                    onEventListener.onEvent(IngresaMontoF.this);
                }
            } else {
                UI.showAlertDialogNoTitle(getString(R.string.ingresa_monto_deposito),getString(R.string.continuar), context, null).create().show();
            }
        } */
