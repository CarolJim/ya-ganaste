package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Font;
import com.pagatodo.yaganaste.utils.FontCache;

/**
 * Created by Jordan on 31/03/2017.
 */

public class CustomFontTextView extends AppCompatTextView {

    public CustomFontTextView(Context context, Font font) {
        super(context);
        this.isInEditMode();
        FontCache.initStyle(getContext(), font, this);
    }

    public CustomFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.isInEditMode();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomFontTextView, 0, 0);
        Font font = getFont(typedArray.getInt(R.styleable.CustomFontTextView_font_type, 0));
        FontCache.initStyle(context, font, this);
        typedArray.recycle();
    }

    private Font getFont(int type) {
        switch (type) {
            case 0:
                return Font.ANGELINA;
            case 1:
                return Font.GLYPHICONS;
            case 2:
                return Font.SOURCESANSPRO_LIGHT;
            case 3:
                return Font.SOURCESANSPRO_REGULAR;
            case 4:
                return Font.SOURCESANSPRO_SEMIBOLD;
            case 5:
                return Font.DK_BUPKIS;
            default:
                return Font.ANGELINA;
        }
    }
}
