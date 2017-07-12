package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.CustomCharacterSpan;

/**
 * Created by flima on 19/04/2017.
 */

public class MontoTextView extends StyleTextView {

    private static final int DECIMALS = 2; // cantidad de decimales
    private int decimalsLenght = DECIMALS;
    private boolean symbolSmaller = false;
    private boolean DotBottom = true;
    private String value = "";

    public MontoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.MontoTextView,
                    0, 0);

            try {

                decimalsLenght = typedArray.getInt(R.styleable.MontoTextView_decimalLenght, DECIMALS);
                if (decimalsLenght < 2)
                    decimalsLenght = DECIMALS;

                symbolSmaller = typedArray.getBoolean(R.styleable.MontoTextView_symbolSmaller, true);
                DotBottom = typedArray.getBoolean(R.styleable.MontoTextView_dotBottom, true);

            } catch (Exception e) {
                Log.e(context.getPackageName(), "Error loading attributes:" + e.getMessage());
            } finally {
                typedArray.recycle();
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!getText().toString().startsWith("$"))
            setText(String.format("$%s", getText())); // Agregamos símbolo $

        if (!getText().toString().contains(".")) {
            setText(String.format("%s.00", getText()));// Agregamos decimales por default
        }

        int index = getText().toString().indexOf(".");
        int lenght = getText().toString().length();
        SpannableString text = new SpannableString(getText().toString());

        if (index > 0) {
            if (decimalsLenght > (lenght - (index + 1)))
                decimalsLenght = (lenght - (index + 1)); // Obtenemos máximo de decimales que contiene el string

            // Truncamos decimales
            if ((lenght - 1) - (index + decimalsLenght) > 0) {
                value = text.toString();
                value = value.substring(0, (index + decimalsLenght + 1));
                text = new SpannableString(value);
                index = value.indexOf(".");
                lenght = value.length();
            }


             /*Cambiamos tamaño del símbolo $*/
            if (symbolSmaller) {
                /*Centramos símbolo de $*/
                text.setSpan(new CustomCharacterSpan(0.3),
                        0, 1,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            /*Seteamos elevación de decimales*/
                text.setSpan(new CustomCharacterSpan(0.8),
                        DotBottom ? index + 1 : index, lenght,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            /*Seteamos escala de font de decimales*/
                text.setSpan(new RelativeSizeSpan(0.8f),
                        DotBottom ? index + 1 : index, lenght,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            /*Seteamos escala de font de enteros*/
                text.setSpan(new RelativeSizeSpan(2f),
                        0,
                        lenght - (decimalsLenght + 1)/*omitimos el punto decimal*/,
                        0); // aumentamos el tamaño

                text.setSpan(new RelativeSizeSpan(0.6f), 0, 1, 0); // aumentamos el tamaño del simbolo $
            } else {
               /*Seteamos elevación de decimales*/
                text.setSpan(new CustomCharacterSpan(0.8),
                        DotBottom ? index + 1 : index, lenght,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            /*Seteamos escala de font de decimales*/
                text.setSpan(new RelativeSizeSpan(0.8f),
                        DotBottom ? index + 1 : index, lenght,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            /*Seteamos escala de font de enteros*/
                text.setSpan(new RelativeSizeSpan(2f),
                        0,
                        lenght - (decimalsLenght + 1)/*omitimos el punto decimal*/,
                        0); // aumentamos el tamaño
            }
        }


        setText(text, TextView.BufferType.SPANNABLE);

    }

    @Override
    public void setText(CharSequence text, BufferType type) {

        super.setText(text, type);
    }

    public int getDecimalsLenght() {
        return decimalsLenght;
    }

    public void setDecimalsLenght(int decimalsLenght) {
        this.decimalsLenght = decimalsLenght;
    }

    public boolean isSymbolSmaller() {
        return symbolSmaller;
    }

    public void setSymbolSmaller(boolean symbolSmaller) {
        this.symbolSmaller = symbolSmaller;
    }

    public boolean isDotBottom() {
        return DotBottom;
    }

    public void setDotBottom(boolean dotBottom) {
        DotBottom = dotBottom;
    }

}
