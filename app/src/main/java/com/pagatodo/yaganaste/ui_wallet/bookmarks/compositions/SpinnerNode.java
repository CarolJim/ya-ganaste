package com.pagatodo.yaganaste.ui_wallet.bookmarks.compositions;

import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.ui_wallet.adapters.TypeSpinnerAdapter;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.builders.Component;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.utils.customviews.AutoCompleteDropDown;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.TransferType.CLABE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TELEFONO;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.QR_CODE;

public class SpinnerNode implements Component{

    private LinearLayout container;
    private Context context;
    private Spinner spinner;
    private StyleTextView tipoEnvio;


    private List<Integer> tipoPago = new ArrayList<>();
    private TypeSpinnerAdapter adapter;

    public SpinnerNode(Context context, LinearLayout container, int res) {
        this.container = container;
        this.context = context;
        setContent(res);
    }

    @Override
    public void add(Component component) {

    }

    @Override
    public void setContent(int id) {
        this.container.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        this.container.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.spinner_holder, container, false);
        this.spinner = itemView.findViewById(R.id.spinner);
        this.tipoEnvio = itemView.findViewById(R.id.tipo_envio);
        this.adapter = getDataAdapter();
        this.spinner.setAdapter(adapter);
        this.container.addView(itemView);

    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(this.container);
    }

    public Spinner getSpinner() {
        return this.spinner;
    }

    public int getRes(int position){
        return this.adapter.getRes(position);
    }

    private TypeSpinnerAdapter getDataAdapter() {
        tipoPago.add(R.string.transfer_phone_cellphone);
        tipoPago.add(R.string.debit_card_number);
        tipoPago.add(R.string.transfer_cable);
        tipoPago.add(R.string.transfer_qr);
        return new TypeSpinnerAdapter(context, 1,tipoPago);
    }

}
