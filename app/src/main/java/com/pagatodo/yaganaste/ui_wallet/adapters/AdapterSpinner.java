package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.interfaces.SpinnerItem;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.List;

public class AdapterSpinner extends ArrayAdapter<SpinnerItem>{

    private Context context;
    private int mLayoutResourceId;
    private List<SpinnerItem> list;

    public AdapterSpinner(@NonNull Context context, @LayoutRes int resource,
                          @NonNull List<SpinnerItem> list, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        this.context = context;
        this.mLayoutResourceId = resource;
        this.list = list;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
        View view = inflater.inflate(this.mLayoutResourceId, parent, false);
        SpinnerHolder holder = new SpinnerHolder();
        holder.textview = view.findViewById(R.id.textView_spinner);
        if (position == 0) {
            holder.textview.setText("");
            holder.textview.setHint(list.get(position).getText());
        } else {
            holder.textview.setText(list.get(position).getText());
        }

        return view;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.spinner_custom_layout, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.editText = view.findViewById(R.id.editTextCustomSpinner);
        holder.downArrow = view.findViewById(R.id.imageViewCustomSpinner);
        if (position == 0 && list.get(position).getId() == -1) {
            holder.editText.setHint(list.get(position).getText());
        } else {
            holder.editText.setText(list.get(position).getText());
        }
        return view;
    }

    static class SpinnerHolder{
        StyleTextView textview;
    }

    static class ViewHolder {
        EditText editText;
        ImageView downArrow;
    }

}
