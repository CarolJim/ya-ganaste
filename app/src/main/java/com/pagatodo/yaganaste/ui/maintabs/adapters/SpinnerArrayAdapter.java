package com.pagatodo.yaganaste.ui.maintabs.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.List;

/**
 * Created by Jordan on 17/04/2017.
 */

public class SpinnerArrayAdapter extends ArrayAdapter {
    Context mContext;
    List<?> mItems;
    MovementsTab mTab;
    private static String CANTIDAD_HINT = "Importe";
    private static String TIPO_ENVIO_HINT = "Tipo de Envío";

    public SpinnerArrayAdapter(Context context, MovementsTab tab, List<?> items) {
        super(context, -1, items);
        mContext = context;
        mItems = items;
        mTab = tab;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        SpinnerArrayAdapter.DropDownHolder holder;
        Object item = mItems.get(position);
        String hint;
        String textLbl;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_layout, parent, false);

            holder = new SpinnerArrayAdapter.DropDownHolder();
            holder.txtTitle = (StyleTextView) row.findViewById(R.id.textView_spinner);

            row.setTag(holder);
        } else {
            holder = (SpinnerArrayAdapter.DropDownHolder) row.getTag();
        }

        if(mTab == MovementsTab.TAB1){
            hint = CANTIDAD_HINT;
            textLbl = "$" + item.toString() + "0";
        }else if(mTab == MovementsTab.TAB3){
            hint = TIPO_ENVIO_HINT;
            textLbl = item.toString();
        }else{
            hint = "";
            textLbl = item.toString();
        }

        if (position == 0) {
            holder.txtTitle.setText("");
            holder.txtTitle.setHint(hint);
        } else {
            holder.txtTitle.setText(textLbl);
        }

        return row;
    }


    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View row = convertView;
        SpinnerArrayAdapter.ViewHolder holder;
        Object item = mItems.get(position);
        String hint;
        String textLbl;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_custom_layout, parent, false);

            holder = new SpinnerArrayAdapter.ViewHolder();
            holder.editText = (StyleEdittext) row.findViewById(R.id.editTextCustomSpinner);

            holder.downArrow = (ImageView) row.findViewById(R.id.imageViewCustomSpinner);
            row.setTag(holder);
        } else {
            holder = (SpinnerArrayAdapter.ViewHolder) row.getTag();
        }

        holder.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.performClick();
            }
        });


        if(mTab == MovementsTab.TAB1){
            hint = CANTIDAD_HINT;
            textLbl = "$" + item.toString() + "0";
        }else if(mTab == MovementsTab.TAB3){
            hint = TIPO_ENVIO_HINT;
            textLbl = item.toString();
        }else{
            hint = "";
            textLbl = item.toString();
        }

        if (position == 0) {
            holder.editText.setHint(hint);
        } else {
            holder.editText.setText(textLbl);
        }

        return row;
    }

    static class ViewHolder {
        StyleEdittext editText;
        ImageView downArrow;
    }

    static class DropDownHolder {
        StyleTextView txtTitle;
    }
}
