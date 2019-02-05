package com.pagatodo.yaganaste.modules.register.CodigoVinculados;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.data.QrItem;
import com.pagatodo.yaganaste.modules.holders.QrHolder;
import com.pagatodo.yaganaste.modules.patterns.OnHolderListener;

import java.util.ArrayList;

public class QrItemAdapters extends RecyclerView.Adapter<QrHolder> {

    private ArrayList<QrItem> qrItems;
    private OnHolderListener<QrItem> listener;

    public QrItemAdapters(OnHolderListener<QrItem> listener) {
        this.qrItems = new ArrayList<>();
        this.listener = listener;
    }

    public void setQrItems(ArrayList<QrItem> qrItems) {
        this.qrItems = qrItems;
        notifyDataSetChanged();
    }

    @Override
    public QrHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_qr,parent,false);
        return new QrHolder(view);
    }

    @Override
    public void onBindViewHolder(QrHolder holder, int position) {
        holder.bind(qrItems.get(position),this.listener);
    }

    @Override
    public int getItemCount() {
        return qrItems.size();
    }
}
