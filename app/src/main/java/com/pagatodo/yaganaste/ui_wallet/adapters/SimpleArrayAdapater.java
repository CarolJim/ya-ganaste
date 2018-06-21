package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

public class SimpleArrayAdapater extends ArrayAdapter<String> {

    private Context context;
    private List<String> arrayList;


    public SimpleArrayAdapater(@NonNull Context context, int resource, List<String> arrayList) {
        super(context, resource);
        this.context = context;
        this.arrayList = arrayList;
    }

    public void setArrayList(List<String> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }


    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.spinner_layout, parent, false);
        StyleTextView styleTextView = row.findViewById(R.id.textView_spinner);

        styleTextView.setText(arrayList.get(position));
        return row;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull final ViewGroup parent) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        @SuppressLint("ViewHolder") View row = inflater.inflate(R.layout.spinner_custom_layout, parent, false);
        StyleEdittext editText = row.findViewById(R.id.editTextCustomSpinner);
        editText.setText(this.arrayList.get(position));
        editText.setTextColor(context.getResources().getColor(R.color.textColorAlternative));

        return row;
    }

}
