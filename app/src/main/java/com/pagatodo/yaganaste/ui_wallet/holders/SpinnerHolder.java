package com.pagatodo.yaganaste.ui_wallet.holders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui.account.register.adapters.EnumSpinnerAdapter;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui_wallet.adapters.TypeSpinnerAdapter;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.App.getContext;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.CLABE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TELEFONO;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.QR_CODE;

public class SpinnerHolder extends GenericHolder {

    private StyleTextView texthint;
    private Spinner spinner;

    public SpinnerHolder(View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        this.texthint = this.itemView.findViewById(R.id.text_hint);
        this.spinner = this.itemView.findViewById(R.id.spinner);
    }

    @Override
    public void bind(Object item, OnClickItemHolderListener listener) {
        SpinerItem spinerItem = (SpinerItem) item;
        //this.texthint.setText(spinerItem.getTextHint());
        this.spinner.setAdapter(spinerItem.getAdapter());

    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(this.itemView);
    }

    @Override
    public View getView() {
        return this.itemView;
    }

    public static class SpinerItem{
        private int textHint;
        private StatesSpinnerAdapter adapter;

        public SpinerItem(int textHint, StatesSpinnerAdapter adapter) {
            this.textHint = textHint;
            this.adapter = adapter;
        }

        public int getTextHint() {
            return textHint;
        }

        public void setTextHint(int textHint) {
            this.textHint = textHint;
        }

        public StatesSpinnerAdapter getAdapter() {
            return adapter;
        }

        public void setAdapter(StatesSpinnerAdapter adapter) {
            this.adapter = adapter;
        }
    }

}
