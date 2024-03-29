package com.pagatodo.yaganaste.utils.customviews.yaganasteviews;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.utils.StringUtils;

import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

/**
 * @author Juan Guerra on 05/04/2017.
 */

public class CardEmisor extends TabViewElement {

    private TextView txtSaldoEm;

    public CardEmisor(Context context) {
        this(context, null);
    }

    public CardEmisor(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardEmisor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView();
    }

    private void setView() {
        View child = LayoutInflater.from(getContext()).inflate(R.layout.card_emisor, null);
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0);
        addView(child, params);
        txtSaldoEm = (TextView) findViewById(R.id.txt_saldo_em);
    }

    @Override
    public void updateData() {
        txtSaldoEm.setText(StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)));
    }
}