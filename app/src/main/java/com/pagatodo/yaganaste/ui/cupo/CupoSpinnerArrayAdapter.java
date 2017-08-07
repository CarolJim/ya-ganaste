package com.pagatodo.yaganaste.ui.cupo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IEnumSpinner;
import com.pagatodo.yaganaste.interfaces.enums.CupoSpinnerTypes;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.List;

/**
 * Created by Jordan on 26/07/2017.
 */

public class CupoSpinnerArrayAdapter extends ArrayAdapter {

    private Context mContext;
    private IEnumSpinner[] mItems;
    private CupoSpinnerTypes spinnerType;

    public CupoSpinnerArrayAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.mContext = context;
    }

    public CupoSpinnerArrayAdapter(@NonNull Context context, IEnumSpinner[] items, CupoSpinnerTypes spinnerType) {
        super(context, 0, items);
        this.mContext = context;
        this.mItems = items;
        this.spinnerType = spinnerType;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    public String getItemIdString(int position) {
        return mItems[position].getId();
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        CupoSpinnerArrayAdapter.DropDownHolder holder;
        Object item = mItems[position];

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_layout, parent, false);

            holder = new CupoSpinnerArrayAdapter.DropDownHolder();
            holder.txtTitle = (StyleTextView) row.findViewById(R.id.textView_spinner);

            row.setTag(holder);
        } else {
            holder = (CupoSpinnerArrayAdapter.DropDownHolder) row.getTag();
        }

        if (position == 0) {
            holder.txtTitle.setText("");
            holder.txtTitle.setHint(mContext.getString(spinnerType.getHint()));
        } else {
            holder.txtTitle.setText(item.toString());
        }

        return row;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View row = convertView;
        CupoSpinnerArrayAdapter.ViewHolder holder;
        Object item = mItems[position];

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_custom_layout, parent, false);

            holder = new CupoSpinnerArrayAdapter.ViewHolder();
            holder.editText = (StyleEdittext) row.findViewById(R.id.editTextCustomSpinner);

            holder.downArrow = (ImageView) row.findViewById(R.id.imageViewCustomSpinner);
            row.setTag(holder);
        } else {
            holder = (CupoSpinnerArrayAdapter.ViewHolder) row.getTag();
        }

        holder.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.performClick();
            }
        });

        if (position == 0) {
            holder.editText.setHint(mContext.getString(spinnerType.getHint()));
        } else {
            holder.editText.setText(item.toString());
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
