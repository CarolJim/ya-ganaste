package com.pagatodo.yaganaste.ui_wallet.holders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui_wallet.adapters.TypeSpinnerAdapter;
import com.pagatodo.yaganaste.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.App.getContext;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.CLABE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TELEFONO;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.QR_CODE;

public class SpinnerHolder extends GenericHolder {

    private Spinner spinner;

    public SpinnerHolder(View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        //this.spinner = this.itemView.findViewById(R.id.spinner);
    }

    @Override
    public void bind(Object item, OnClickItemHolderListener listener) {
        TypeSpinnerAdapter dataAdapter = (TypeSpinnerAdapter) item;
        this.spinner.setAdapter(dataAdapter);

    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(this.itemView);
    }

    @Override
    public View getView() {
        return this.itemView;
    }

}
