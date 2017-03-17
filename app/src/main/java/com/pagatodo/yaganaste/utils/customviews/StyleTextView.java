package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.FontCache;

/**
 * Created by icruz on 21/12/2016.
 */

public class StyleTextView extends AppCompatTextView {
    private TYPE type;
    private Color color;


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
        Typeface customFont = FontCache.getTypeface("fonts/Gotham/Gotham-Medium.ttf", context);
        if(type != null){
            switch (type){
                case TITULO:
                    customFont = FontCache.getTypeface("fonts/Gotham/Gotham-Bold.ttf", context);
                    break;
                case SUBTITULO:
                    customFont = FontCache.getTypeface("fonts/Gotham/Gotham-Medium.ttf", context);
                    break;
                case DESCRIPCION:
                    customFont = FontCache.getTypeface("fonts/Gotham/Gotham-Book.ttf", context);
                    break;
                case INDICACION:
                    customFont = FontCache.getTypeface("fonts/Gotham/Gotham-Light.ttf", context);
                    break;
                case TEXTO:
                    customFont = FontCache.getTypeface("fonts/Gotham/Gotham-Medium.ttf", context);
                    break;
            }
        }
        setTypeface(customFont);
    }
    private void handlAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StyleTextView);
        String fontName = a.getString(R.styleable.StyleTextView_tipo) ;
        if (fontName != null) {
            switch (fontName){
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
            }
        }
        a.recycle();
    }
    enum TYPE {TITULO, SUBTITULO, DESCRIPCION, INDICACION, TEXTO}

}
