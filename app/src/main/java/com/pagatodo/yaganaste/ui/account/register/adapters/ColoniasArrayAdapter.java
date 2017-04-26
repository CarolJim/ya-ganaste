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
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.GiroComercio;
import com.pagatodo.yaganaste.interfaces.enums.GiroAdapterType;

import java.util.List;

/**
 * Created by Jordan on 24/03/2017.
 */

public class ColoniasArrayAdapter extends ArrayAdapter<String> {
    Context mContext;
    int mLayoutResourceId;
    List<String> mList;

    public ColoniasArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mList = objects;
        this.mLayoutResourceId = resource;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public String getItemSelected(int position){
        return mList.get(position);
    }

    private View getCustomView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View row = convertView;
        ViewHolder holder;
        String item = mList.get(position);
        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_custom_layout, parent, false);

            holder = new ViewHolder();
            holder.editText = (EditText) row.findViewById(R.id.editTextCustomSpinner);
            holder.downArrow = (ImageView)row.findViewById(R.id.imageViewCustomSpinner);
            row.setTag(holder);
        }else {
            holder = (ViewHolder) row.getTag();
        }

        if(position == 0){
            holder.editText.setHint(item);
        }else {
            holder.editText.setText(item);
        }

        return row;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       return getCustomView(position, convertView, parent);
    }

    static class ViewHolder{
        EditText editText;
        ImageView downArrow;
    }
}
