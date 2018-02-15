package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.FontCache;

/**
 * Created by Jordan on 06/07/2017.
 */

public class YaGanasteCard extends AppCompatImageView {

    private static int MAX_LENGTH_STRING = 25;
    private String cardName;
    private String cardNumber;
    private String cardDate;
    private boolean isMasked;

    public YaGanasteCard(Context context) {
        super(context);
        initView(context, null);
    }

    public YaGanasteCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public YaGanasteCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                    R.styleable.YaGanasteCard,
                    0, 0);
            setCardName(typedArray.getString(R.styleable.YaGanasteCard_name));
            setCardNumber(typedArray.getString(R.styleable.YaGanasteCard_numberCard));
            setCardDate(typedArray.getString(R.styleable.YaGanasteCard_expirationDate));
            setMasked(typedArray.getBoolean(R.styleable.YaGanasteCard_isMasked, false));
        }
        setImageResource(R.drawable.tarjeta_yg);
        //setAdjustViewBounds(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        TextPaint textPaint = new TextPaint();
        Typeface typeface = FontCache.getTypeface("fonts/roboto/Roboto-Regular.ttf", getContext());
        textPaint.setColor(Color.WHITE);
        textPaint.setTypeface(typeface);

        float heigth = canvas.getHeight();
        float width = canvas.getWidth();
        textPaint.setTextSize(heigth * 0.115f);

        if (cardNumber != null && !cardNumber.isEmpty()) {
            textPaint.setTextSize(heigth * 0.115f);
            if (isMasked) {
                cardNumber = "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
            }
            canvas.drawText(cardNumber, width * 0.07f, heigth * 0.6f, textPaint);
        }

        if (cardName != null && !cardName.isEmpty()) {
            textPaint.setTextSize(heigth * 0.095f);
            canvas.drawText(cardName, width * 0.07f, heigth * 0.9f, textPaint);
        }

        if (cardDate != null && !cardDate.isEmpty()) {
            textPaint.setTextSize(heigth * 0.095f);
            canvas.drawText(cardDate, width * 0.14f, heigth * 0.75f, textPaint);
        }


    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        if (cardName != null && !cardName.isEmpty() && cardName.length() > MAX_LENGTH_STRING) {
            int lastSpace = cardName.lastIndexOf(" ");
            if (lastSpace > -1) {
                cardName = cardName.substring(0, lastSpace);
                if (cardName.length() > MAX_LENGTH_STRING) {
                    lastSpace = cardName.lastIndexOf(" ");
                    cardName = cardName.substring(0, lastSpace);
                }
            } else {
                cardName = cardName.substring(0, MAX_LENGTH_STRING);
            }
        }
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        if (cardNumber != null && !cardNumber.isEmpty()) {
            cardNumber = cardNumber.trim().replaceAll(" ", "");
            if (cardNumber.length() >= 16) {
                cardNumber = cardNumber.substring(0, 16);
            }

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < cardNumber.length(); i++) {
                if (i % 4 == 0 && i != 0) {
                    result.append(" ");
                }
                result.append(cardNumber.charAt(i));
            }
            this.cardNumber = result.toString();
        }
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }

    public boolean isMasked() {
        return isMasked;
    }

    public void setMasked(boolean masked) {
        isMasked = masked;
    }
}
