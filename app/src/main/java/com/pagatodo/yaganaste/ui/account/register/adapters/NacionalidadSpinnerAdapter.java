package com.pagatodo.yaganaste.ui.account.register.adapters;

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

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IEnumSpinner;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

/**
 * Created by Jordan on 28/03/2017.
 */

public class NacionalidadSpinnerAdapter extends ArrayAdapter<IEnumSpinner> {
    Context mContext;
    int mLayoutResourceId;
    IEnumSpinner[] mItems;

    public NacionalidadSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull IEnumSpinner[] objects) {
        super(context, resource, objects);
        this.mLayoutResourceId = resource;
        this.mContext = context;
        this.mItems = objects;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        NacionalidadSpinnerAdapter.DropDownHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new NacionalidadSpinnerAdapter.DropDownHolder();
            holder.txtTitle = (StyleTextView) row.findViewById(R.id.textView_spinner);

            row.setTag(holder);
        } else {
            holder = (NacionalidadSpinnerAdapter.DropDownHolder) row.getTag();
        }
        IEnumSpinner item = mItems[position];
        if(position == 0){
            holder.txtTitle.setText("");
            holder.txtTitle.setHint(item.getName());
        }else {
            holder.txtTitle.setText(item.getName());
        }

        return row;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View row = convertView;
        NacionalidadSpinnerAdapter.ViewHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_custom_layout, parent, false);

            holder = new NacionalidadSpinnerAdapter.ViewHolder();
            holder.editText = (EditText) row.findViewById(R.id.editTextCustomSpinner);
            holder.downArrow = (ImageView)row.findViewById(R.id.imageViewCustomSpinner);
            row.setTag(holder);
        }else {
            holder = (NacionalidadSpinnerAdapter.ViewHolder) row.getTag();
        }

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.performClick();
            }
        });

        IEnumSpinner item = mItems[position];
        if(position == 0){
            holder.editText.setHint(item.getName());
        }else {
            holder.editText.setText(item.getName());
        }

        return row;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

    public String getItemIdString(int position){
        return mItems[position].getId();
    }

    static class DropDownHolder {
        StyleTextView txtTitle;
    }

    public String getItemName(int position){
        return mItems[position].getName();
    }

    static class ViewHolder{
        EditText editText;
        ImageView downArrow;
    }

    public int getPositionItemByName(String name){
        for(int position = 0 ; position< mItems.length;position++){
            if(mItems[position].getName().equals(name))
                return position;
        }
        return 0;
    }

}
