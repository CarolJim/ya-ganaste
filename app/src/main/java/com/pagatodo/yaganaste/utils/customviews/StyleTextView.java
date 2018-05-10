package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.FontCache;

public class StyleTextView extends AppCompatTextView {
    private TYPE type;



    public StyleTextView(Context context) {
        super(context);
        applyFont(context);
    }

    public StyleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        handlAttrs(context, attrs);
        applyFont(context);
    }

    public StyleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handlAttrs(context, attrs);
        applyFont(context);

    }

    private void applyFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/roboto/Roboto-Regular.ttf", context);
        if (type != null) {
            switch (type) {
                case TITULO:
                    customFont = FontCache.getTypeface("fonts/roboto/Roboto-Bold.ttf", context);
                    break;
                case SUBTITULO:
                    customFont = FontCache.getTypeface("fonts/roboto/Roboto-Medium.ttf", context);
                    break;
                case DESCRIPCION:
                    customFont = FontCache.getTypeface("fonts/roboto/Roboto-Light.ttf", context);
                    break;
                case INDICACION:
                    customFont = FontCache.getTypeface("fonts/roboto/Roboto-Thin.ttf", context);
                    break;
                case TEXTO:
                    customFont = FontCache.getTypeface("fonts/roboto/Roboto-Regular.ttf", context);
                    break;
                case INPUTTEXT:
                    customFont = FontCache.getTypeface("fonts/roboto/Roboto-Medium.ttf", context);
                    break;
                case INPUTDESCRIPTION:
                    customFont = FontCache.getTypeface("fonts/roboto/Roboto-Light.ttf", context);
                    break;
                case ITALIC:
                    customFont = FontCache.getTypeface("fonts/roboto/Roboto-Italic.ttf", context);
                    break;
            }
        }
        setTypeface(customFont);
    }

    private void handlAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StyleTextView);
        String fontName = a.getString(R.styleable.StyleTextView_tipo);
        if (fontName != null) {
            switch (fontName) {
                case "0":
                    type = TYPE.TITULO;
                    break;
                case "1":
                    type = TYPE.SUBTITULO;
                    break;
                case "2":
                    type = TYPE.DESCRIPCION;
                    break;
                case "3":
                    type = TYPE.INDICACION;
                    break;
                case "4":
                    type = TYPE.TEXTO;
                    break;
                case "5":
                    type = TYPE.INPUTTEXT;
                    break;
                case "6":
                    type = TYPE.INPUTDESCRIPTION;
                    break;
                case "7":
                    type = TYPE.ITALIC;
                    break;
            }
        }
        a.recycle();
    }

    enum TYPE {TITULO, SUBTITULO, DESCRIPCION, INDICACION, TEXTO, INPUTTEXT, INPUTDESCRIPTION, ITALIC}

}
