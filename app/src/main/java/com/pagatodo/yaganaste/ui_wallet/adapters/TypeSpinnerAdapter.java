package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.List;

public class TypeSpinnerAdapter extends ArrayAdapter<Integer> implements SpinnerAdapter {

    private List<Integer> listTitles;
    private LayoutInflater inflater;
    private Context context;

    public TypeSpinnerAdapter(@NonNull Context context, int resource, List<Integer> listTitles ) {
        super(context, resource);
        this.context = context;
        this.listTitles = listTitles;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return listTitles.size();
    }

    @Override
    public Integer getItem(int i) {
        return listTitles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        convertView = inflater.inflate(R.layout.spinner_layout, parent, false);
        StyleTextView styleTextView = convertView.findViewById(R.id.textView_spinner);
        styleTextView.setHint(listTitles.get(position));
        styleTextView.setHintTextColor(parent.getContext().getResources().getColor(R.color.texthint));
        styleTextView.setTextSize(14f);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        convertView = inflater.inflate(R.layout.spinner_layout, parent, false);
        StyleTextView styleTextView = convertView.findViewById(R.id.textView_spinner);
        styleTextView.setHint(listTitles.get(position));
        styleTextView.setHintTextColor(parent.getContext().getResources().getColor(R.color.colorAccent));
        styleTextView.setPadding(14,0,0,0);
        return  convertView;
    }

    public int getRes(int position){
        return listTitles.get(position);
    }
}
