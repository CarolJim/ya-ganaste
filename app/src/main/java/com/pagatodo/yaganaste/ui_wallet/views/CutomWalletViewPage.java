package com.pagatodo.yaganaste.ui_wallet.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;

import com.pagatodo.yaganaste.R;

public class CutomWalletViewPage extends LinearLayoutCompat {
    public CutomWalletViewPage(Context context) {
        super(context);
        init();
    }

    public CutomWalletViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CutomWalletViewPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.tab_em_adq, this);
    }
}
