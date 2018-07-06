package com.pagatodo.yaganaste.utils;

import android.inputmethodservice.KeyboardView;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by flima on 09/03/2017.
 * Update Francisco Manzo
 * Encarga de hacer las operaciones para la calculadora, y mostrar lo resultados en los dos
 * TextView que mostramos en pantalla.
 * Se tienen las validaciones para no tener problemas al momento de ir poco a poco escribiendo el
 * numero, esto es porque cada vez que se actualiza el EditText entra en el ciclo de afterTextChanged
 */

public class NumberCalcTextWatcher implements TextWatcher {

    private TextChange listener;
    private final static Pattern rfc2822 = Pattern.compile("^\\$?(\\d{1,5})?(\\.(\\d{1,2})?)?$");
    protected static String amount = "";
    String tmpAMount, strAmountEditText;
    private EditText etMonto;
    EditText edtConcept;
    private TextView tvMontoEntero, tvMontoDecimal;
    private DecimalFormat formatter;
    private DecimalFormat fmt;
    private DecimalFormatSymbols fmts;
    private String oldData;
    private String newData;
    private String TAG = getClass().getSimpleName();

    public NumberCalcTextWatcher(EditText edtMount, TextView tvMontoEntero, TextView tvMontoDecimal, @Nullable EditText edtConcept, TextChange listener) {
        this.etMonto = edtMount;
        this.tvMontoEntero = tvMontoEntero;
        this.tvMontoDecimal = tvMontoDecimal;
        this.edtConcept = edtConcept;
        this.listener = listener;
    }

    public static void cleanData() {
        amount = "";
        // Reiniciamos el control de CodeKey para cuando cargamos de nuevo el fragment
        // GetAmountFragment, tengamos un inicio de $0.00
        CustomKeyboardView.setCodeKey(0);
    }

    @Override
    public void afterTextChanged(Editable arg0) {
        // Log.d(TAG, "NumberCalc " + etMonto.getText().toString());

        /**
         * Iniciamos la vista con un $0.00
         */
        if ((CustomKeyboardView.getCodeKey() == 0)) {
            etMonto.setText("$0.00");
            Selection.setSelection(etMonto.getText(), "$0.00".length());
            CustomKeyboardView.setCodeKey(99);
        }

        /**
         * Con codigo 99 realizamos el proceso de mostrar el formato correcto
         */
        if (CustomKeyboardView.getCodeKey() == 99) {
            // Detectar las pulsaciones de cada tecla y mostrarlas.
            if (etMonto.getText() != null
                    && !etMonto.getText().toString().equals("")) {
                //String monto = String.valueOf(Utils.getDoubleValue(etMonto));
                String monto = etMonto.getText().toString().replace("$", "").replace("%", "")
                        .replace("&nbsp;", "");
                String montos[] = monto.split("\\.");

                if (montos.length == 2) {
                    if (tvMontoEntero != null)
                        tvMontoEntero.setText(montos[0]);
                    if (tvMontoEntero.getText().equals("0")) {
                        tvMontoEntero.setText("0");
                    }
                    if (tvMontoDecimal != null)
                        if (montos[1].toString().equals("0")) {
                            tvMontoDecimal.setText("00");
                        } else {
                            tvMontoDecimal.setText(montos[1]);
                        }
                }
            }
        } else {
            //Si es diferente a 99, mostramos el formato y hacemos Set CodeKey
            CustomKeyboardView.setCodeKey(99);
            etMonto.setText(StringUtils.getCurrencyValue(strAmountEditText));
            if (!etMonto.toString().equals("") && strAmountEditText != null) {
                Selection.setSelection(etMonto.getText(), strAmountEditText.toString().length());
            }
        }
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
        if (!(CustomKeyboardView.getCodeKey() == 99)) {
            tmpAMount = amount;

            if (!s.toString().equals("")) {
                //  Log.d(TAG, "NumberCalcKeycode " + CustomKeyboardView.getCodeKey());
                int codeKey = CustomKeyboardView.getCodeKey();

                StringBuilder keyData = new StringBuilder();
                keyData.append(s.toString().charAt(s.length() - 1));

                switch (codeKey) {
                    case 7:
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
                    case 29:
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
                        if (tmpAMount.length() > 0 && tmpAMount.substring(tmpAMount.length() - 1).equals("."))
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
                    //  Log.d(TAG, "NumberCalc " + keyData + " tmp " + Utils.getCurrencyValue(tmp));
                    strAmountEditText = StringUtils.getCurrencyValue(tmp);
                    CustomKeyboardView.setCodeKey(99);
                    etMonto.setText(StringUtils.getCurrencyValue(tmp));
                    Selection.setSelection(etMonto.getText(), StringUtils.getCurrencyValue(tmp).toString().length());
                    if (listener != null) {
                        listener.onChangeTextListener(etMonto.getText().toString());
                    }
                    // Guardamos la cantidad en el modelo para recuperar en caso de perdida
                    //TransactionAdqData.getCurrentTransaction().setAmount(Utils.getCurrencyValue(strAmountEditText));

                    // Hacemos un set vacio en el concepto al reiniciarse la cantidad
                    if (tmp.equals("0.00")) {
                        tvMontoEntero.setText("0");
                        if (edtConcept != null) {
                            edtConcept.setText("");
                        }
                    }
                }
            }
        }
    }

    protected boolean validateAmount(String toCheck) {
        if (rfc2822.matcher(toCheck).matches())
            return true;
        return false;
    }

    public interface TextChange {
        void onChangeTextListener(String text);
    }
}