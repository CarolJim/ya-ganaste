package com.pagatodo.view_manager.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.view_manager.R;

import java.text.NumberFormat;
import java.util.Locale;

import androidx.annotation.Nullable;

public class CurrencyFormat extends LinearLayout {

    private TextView unitsText;
    private TextView decimalsText;

    public CurrencyFormat(Context context) {
        super(context);
        init();
    }

    public CurrencyFormat(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurrencyFormat(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View viewItem = inflater.inflate(R.layout.currency_format,this,false);
        unitsText = viewItem.findViewById(R.id.units);
        decimalsText = viewItem.findViewById(R.id.decimals);
        setMoney("3.0");
        this.addView(viewItem);
    }

    public void setMoney(String money){

        double amount;
        try {
            amount = Double.parseDouble(money);
        } catch (NumberFormatException e){
            amount = 0.00d;
        }

        Locale locale = new Locale("en", "US");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        //System.out.println(currencyFormatter.format(amount));
        String textAmount = currencyFormatter.format(amount);
        String textSplit[] = textAmount.split("\\.");
        if (textSplit.length > 0){
            unitsText.setText(textSplit[0]);
            decimalsText.setText(textSplit[1]);
        }
    }
}
