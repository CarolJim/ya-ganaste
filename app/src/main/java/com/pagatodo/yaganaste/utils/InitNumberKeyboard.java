package com.pagatodo.yaganaste.utils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pagatodo.yaganaste.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Francisco Manzo on 27/04/2017.
 */

public class InitNumberKeyboard {
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button buttonPeriod;
    private Button buttonBack;

    protected EditText etAmount;
    private DecimalFormat formatter;
    protected static String amount;

    private DecimalFormat fmt;
    private DecimalFormatSymbols fmts;

    public InitNumberKeyboard(View view) {

        amount = "";
        fmt = new DecimalFormat();
        fmts = new DecimalFormatSymbols();
        fmts.setGroupingSeparator(' ');
        fmt.setGroupingSize(3);
        fmt.setGroupingUsed(true);
        fmt.setDecimalFormatSymbols(fmts);

       /* this.button0 = (Button) view.findViewById(R.id.pass_button0);
        this.button1 = (Button) view.findViewById(R.id.pass_button1);
        this.button2 = (Button) view.findViewById(R.id.pass_button2);
        this.button3 = (Button) view.findViewById(R.id.pass_button3);
        this.button4 = (Button) view.findViewById(R.id.pass_button4);
        this.button5 = (Button) view.findViewById(R.id.pass_button5);
        this.button6 = (Button) view.findViewById(R.id.pass_button6);
        this.button7 = (Button) view.findViewById(R.id.pass_button7);
        this.button8 = (Button) view.findViewById(R.id.pass_button8);
        this.button9 = (Button) view.findViewById(R.id.pass_button9);
        this.button9 = (Button) view.findViewById(R.id.pass_button9);
        this.buttonPeriod = (Button) view.findViewById(R.id.pass_button_point);
        this.buttonBack = (Button) view.findViewById(R.id.pass_buttonback);*/

        this.etAmount = (EditText) view.findViewById(R.id.et_amount);

        this.button0.setOnClickListener(this.clickButton);
        this.button1.setOnClickListener(this.clickButton);
        this.button2.setOnClickListener(this.clickButton);
        this.button3.setOnClickListener(this.clickButton);
        this.button4.setOnClickListener(this.clickButton);
        this.button5.setOnClickListener(this.clickButton);
        this.button6.setOnClickListener(this.clickButton);
        this.button7.setOnClickListener(this.clickButton);
        this.button8.setOnClickListener(this.clickButton);
        this.button9.setOnClickListener(this.clickButton);
        this.buttonPeriod.setOnClickListener(this.clickButton);
        this.buttonBack.setOnClickListener(this.clickButton);
    }

    public String getAmount() {
        return amount;
    }

/*    public static void setAmount(String amount) {
        if (amount.contains(".")) {
            int indexOfPoint = amount.indexOf(".");
            if (amount.substring(indexOfPoint, amount.length()).equals(".00"))
                InitNumberKerboard.amount = amount.substring(0, indexOfPoint);
            else {
                if (amount.substring(amount.length() - 1, amount.length())
                        .equals("0"))
                    InitNumberKerboard.amount = amount.substring(0,
                            amount.length() - 1);
                else
                    InitNumberKerboard.amount = amount;
            }
        } else {
            InitNumberKerboard.amount = amount;
        }
    }*/

    private View.OnClickListener clickButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            functionClick(v.getId());
        }
    };

    protected void functionClick(int idView) {
        String tmpAMount = amount;

     /*   switch (idView) {
            case R.id.pass_button0:
                if (amount.length() > 0 && !amount.startsWith("0."))
                    tmpAMount = amount + "0";
                break;
            case R.id.pass_button1:
                tmpAMount = amount.concat("1");
                break;
            case R.id.pass_button2:
                tmpAMount = amount.concat("2");
                break;
            case R.id.pass_button3:
                tmpAMount = amount.concat("3");
                break;
            case R.id.pass_button4:
                tmpAMount = amount.concat("4");
                break;
            case R.id.pass_button5:
                tmpAMount = amount.concat("5");
                break;
            case R.id.pass_button6:
                tmpAMount = amount.concat("6");
                break;
            case R.id.pass_button7:
                tmpAMount = amount.concat("7");
                break;
            case R.id.pass_button8:
                tmpAMount = amount.concat("8");
                break;
            case R.id.pass_button9:
                tmpAMount = amount.concat("9");
                break;
            case R.id.pass_button_point:
                if (!amount.contains(".")) {
                    if (amount.length() == 0)
                        tmpAMount = amount.concat("0.");
                    else
                        tmpAMount = amount.concat(".");
                }
                break;

            case R.id.pass_buttonback:
                if (tmpAMount.length() > 0 && !tmpAMount.equals("")) {
                    tmpAMount = tmpAMount.substring(0, amount.length() - 1);
                }
                if (tmpAMount.length() > 0
                        && tmpAMount.substring(tmpAMount.length() - 1).equals("."))
                    tmpAMount = tmpAMount.substring(0, tmpAMount.length() - 1);
                break;
            default:
                break;
        }*/
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
            etAmount.setText(Utils.getCurrencyValue(tmp));
        }
    }

    private final static Pattern rfc2822 = Pattern.compile("^\\$?(\\d{1,5})?(\\.(\\d{1,2})?)?$");

    protected boolean validateAmount(String toCheck) {
        if (rfc2822.matcher(toCheck).matches())
            return true;
        return false;
    }

    public void reset() {
        etAmount.setText("");
        amount = "";
    }
}
