package com.pagatodo.yaganaste.ui.maintabs.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.List;



public class SpinnerArrayAdapter extends ArrayAdapter {
    private static String CANTIDAD_HINT = App.getContext().getResources().getString(R.string.details_monto);
    private static String TIPO_ENVIO_HINT = "Tipo de envío";
    Context mContext;
    List<?> mItems;
    int paymentType;

    public SpinnerArrayAdapter(Context context, int paymentType, List<?> items) {
        super(context, -1, items);
        mContext = context;
        mItems = items;
        this.paymentType = paymentType;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }


    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
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
        if (paymentType == Constants.PAYMENT_ENVIOS) {
            hint = TIPO_ENVIO_HINT;
            textLbl = item.toString();
        }
        if (paymentType == Constants.PAYMENT_RECARGAS) {
            hint = CANTIDAD_HINT;
            textLbl = "$" + item.toString() + "0";
        } else if (paymentType == Constants.PAYMENT_SERVICIOS) {
            hint = TIPO_ENVIO_HINT;
            textLbl = item.toString();
        } else {
            hint = TIPO_ENVIO_HINT;
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


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull final ViewGroup parent) {
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

        holder.editText.setOnClickListener(v -> parent.performClick());


        if (paymentType == Constants.PAYMENT_RECARGAS) {
            hint = CANTIDAD_HINT;
            textLbl = "$" + item.toString() + "0";
        } else if (paymentType == Constants.PAYMENT_SERVICIOS) {
            hint = TIPO_ENVIO_HINT;
            textLbl = item.toString();
        }else if (paymentType == Constants.PAYMENT_ENVIOS) {
            hint = TIPO_ENVIO_HINT;
            textLbl = item.toString();
        } else {
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
