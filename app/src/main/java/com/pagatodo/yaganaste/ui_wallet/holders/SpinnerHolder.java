package com.pagatodo.yaganaste.ui_wallet.holders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IEnumSpinner;
import com.pagatodo.yaganaste.ui.account.register.adapters.BussinesLineSpinnerAdapter;
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
    private BussinesLineSpinnerAdapter adapter;


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
        this.texthint.setText(spinerItem.getTextHint());
        this.adapter = spinerItem.getAdapter();
        this.spinner.setAdapter(adapter);
        if (listener != null){
            this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    listener.onClick(adapter.getItem(position));
                    if (position!=0) {
                        texthint.setVisibility(View.VISIBLE);
                        texthint.setTextColor(texthint.getContext().getResources().getColor(R.color.colorAccent));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        this.itemView.setOnFocusChangeListener((view, b) -> {
            if (b) {
                this.itemView.setBackgroundResource(R.drawable.inputtext_active);
            } else {
                this.itemView.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });
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
        private BussinesLineSpinnerAdapter adapter;

        public SpinerItem(int textHint, BussinesLineSpinnerAdapter adapter) {
            this.textHint = textHint;
            this.adapter = adapter;
        }

        public int getTextHint() {
            return textHint;
        }

        public void setTextHint(int textHint) {
            this.textHint = textHint;
        }

        public BussinesLineSpinnerAdapter getAdapter() {
            return adapter;
        }

        public void setAdapter(BussinesLineSpinnerAdapter adapter) {
            this.adapter = adapter;
        }
    }

}
