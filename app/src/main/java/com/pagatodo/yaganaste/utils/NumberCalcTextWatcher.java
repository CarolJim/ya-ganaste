package com.pagatodo.yaganaste.utils;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by flima on 09/03/2017.
 */

public class NumberCalcTextWatcher implements TextWatcher {

    private EditText etMonto;
    private TextView tvMontoEntero, tvMontoDecimal;

    private DecimalFormat formatter;
    protected static String amount = "";

    private DecimalFormat fmt;
    private DecimalFormatSymbols fmts;

    private String oldData;
    private String newData;

    String tmpAMount, testString;
    private String TAG = getClass().getSimpleName();

    public NumberCalcTextWatcher(EditText editText, TextView tvMontoEntero, TextView tvMontoDecimal) {
        this.etMonto = editText;
        this.tvMontoEntero = tvMontoEntero;
        this.tvMontoDecimal = tvMontoDecimal;
    }

    @Override
    public void afterTextChanged(Editable arg0) {
        Log.d(TAG, "NumberCalc " + etMonto.getText().toString());

        // Detectar las pulsaciones de cada tecla y mostrarlas.
        if (etMonto.getText() != null
                && !etMonto.getText().toString().equals("")) {
            String monto = String.valueOf(Utils.getDoubleValue(testString));
            String montos[] = monto.split("\\.");
            if (montos.length == 2) {
                if (tvMontoEntero != null)
                    tvMontoEntero.setText(montos[0]);
                if (tvMontoDecimal != null)
                    if (montos[1].toString().equals("0")) {
                        tvMontoDecimal.setText("00");
                    } else {
                        tvMontoDecimal.setText(montos[1]);
                    }
            }
        }
        /* if (etMonto.getText() != null
                && !etMonto.getText().toString().equals("")) {
            String monto = String.valueOf(Utils.getDoubleValue(etMonto));
            String montos[] = monto.split("\\.");
            if (montos.length == 2) {
                if (tvMontoEntero != null)
                    tvMontoEntero.setText(montos[0]);
                if (tvMontoDecimal != null)
                    if (montos[1].toString().equals("0")) {
                        tvMontoDecimal.setText("00");
                    } else {
                        tvMontoDecimal.setText(montos[1]);
                    }
                    *//*if (context instanceof ObtenMasActivity)
                        ((ObtenMasActivity) context).transactionCardRequest.amount = monto.replace(",", "");*//*
            }
        }*/
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        fmt = new DecimalFormat();
        fmts = new DecimalFormatSymbols();
        fmts.setGroupingSeparator(' ');
        fmt.setGroupingSize(3);
        fmt.setGroupingUsed(true);
        fmt.setDecimalFormatSymbols(fmts);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {
        tmpAMount = amount;

        if (!s.toString().equals("")) {
            Log.d(TAG, "NumberCalcKeycode " + CustomKeyboardView.getCodeKey());
            int codeKey = CustomKeyboardView.getCodeKey();

            StringBuilder keyData = new StringBuilder();
            keyData.append(s.toString().charAt(s.length() - 1));

            switch (codeKey) {
                case 144:
                    if (amount.length() > 0 && !amount.startsWith("0."))
                        tmpAMount = amount + "0";
                    break;
                case 8:
                    tmpAMount = amount.concat("1");
                    break;
                case 9:
                    tmpAMount = amount.concat("2");
                    break;
                case 10:
                    tmpAMount = amount.concat("3");
                    break;
                case 11:
                    tmpAMount = amount.concat("4");
                    break;
                case 12:
                    tmpAMount = amount.concat("5");
                    break;
                case 13:
                    tmpAMount = amount.concat("6");
                    break;
                case 14:
                    tmpAMount = amount.concat("7");
                    break;
                case 15:
                    tmpAMount = amount.concat("8");
                    break;
                case 16:
                    tmpAMount = amount.concat("9");
                    break;
                case 158:
                    if (!amount.contains(".")) {
                        if (amount.length() == 0)
                            tmpAMount = amount.concat("0.");
                        else
                            tmpAMount = amount.concat(".");
                    }
                    break;

                case 67:
                    if (tmpAMount.length() > 0 && !tmpAMount.equals("")) {
                        tmpAMount = tmpAMount.substring(0, amount.length() - 1);
                    }
                    if (tmpAMount.length() > 0
                            && tmpAMount.substring(tmpAMount.length() - 1).equals("."))
                        tmpAMount = tmpAMount.substring(0, tmpAMount.length() - 1);
                    break;
                default:
                    break;
            }

            if (validateAmount(tmpAMount) == true) {
                amount = tmpAMount;
                String tmp;
                if (amount.equals(""))
                    tmp = String.format(Locale.US, "%.2f", 0.00);
                else
                //tmp = String.format(Locale.US, "%.2f", Float.valueOf(amount));
                {
                    formatter = new DecimalFormat("#,##0.00");
                    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                    dfs.setDecimalSeparator('.');
                    dfs.setGroupingSeparator(',');
                    formatter.setDecimalFormatSymbols(dfs);
                    tmp = formatter.format(Double.parseDouble(amount));
                }
                Log.d(TAG, "NumberCalc " + keyData + " tmp " + Utils.getCurrencyValue(tmp));
                testString = Utils.getCurrencyValue(tmp);
                // etMonto.setText(Utils.getCurrencyValue(tmp));
                // Selection.setSelection(etMonto.getText(), Utils.getCurrencyValue(tmp).toString().length());
            }
        }
    }

    private final static Pattern rfc2822 = Pattern.compile("^\\$?(\\d{1,5})?(\\.(\\d{1,2})?)?$");

    protected boolean validateAmount(String toCheck) {
        if (rfc2822.matcher(toCheck).matches())
            return true;
        return false;
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
