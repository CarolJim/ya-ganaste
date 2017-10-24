package com.pagatodo.yaganaste.ui.account.register.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
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
import com.pagatodo.yaganaste.data.model.SubGiro;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.List;

/**
 * @author omarz on 30/03/2017.
 */

public class SubBussinesLineSpinnerAdapter extends ArrayAdapter<SubGiro> {

    Context context;
    int mLayoutResourceId;
    List<SubGiro> mList;
    IOnSpinnerClick spinnerClick;


    public SubBussinesLineSpinnerAdapter(@NonNull Context con, @LayoutRes int resource,
                                         @NonNull List<SubGiro> objects, IOnSpinnerClick iOnSpinnerClick) {
        super(con, resource, objects);
        this.context = con;
        this.mLayoutResourceId = resource;
        this.mList = objects;
        this.spinnerClick = iOnSpinnerClick;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public SubGiro getItemSelected(int position) {
        return mList.get(position);
    }

    public int getSubGiroId(int position) {
        return mList.get(position).getIdSubgiro();
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        SubBussinesLineSpinnerAdapter.DropDownHolder holder;
        SubGiro item = mList.get(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new SubBussinesLineSpinnerAdapter.DropDownHolder();
            holder.txtTitle = (StyleTextView) row.findViewById(R.id.textView_spinner);
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto/Roboto-Regular.ttf");
            holder.txtTitle.setTypeface(typeface);

            row.setTag(holder);
        } else {
            holder = (SubBussinesLineSpinnerAdapter.DropDownHolder) row.getTag();
        }

        if (position == 0) {
            holder.txtTitle.setText("");
            holder.txtTitle.setHint(item.getSubgiro());
        } else {
            holder.txtTitle.setText(item.getSubgiro());
        }

        return row;
    }

    public int getItemPosition(@NonNull SubGiro giroComercio) {
        SubGiro current;
        for (int position = 0; position < mList.size(); position++) {
            current = mList.get(position);
            if (giroComercio.getIdSubgiro() == current.getIdSubgiro()) {
                return position;
            }
        }
        return -1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View row = convertView;
        SubBussinesLineSpinnerAdapter.ViewHolder holder;
        SubGiro item = mList.get(position);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto/Roboto-Regular.ttf");


        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_custom_layout, parent, false);

            holder = new SubBussinesLineSpinnerAdapter.ViewHolder();
            holder.editText = (EditText) row.findViewById(R.id.editTextCustomSpinner);

            holder.editText.setTypeface(typeface);
            holder.downArrow = (ImageView) row.findViewById(R.id.imageViewCustomSpinner);
            row.setTag(holder);
        } else {
            holder = (SubBussinesLineSpinnerAdapter.ViewHolder) row.getTag();
        }

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerClick.onSpinnerClick();
                parent.performClick();
                spinnerClick.hideKeyBoard();
            }
        };

        holder.editText.setOnClickListener(onClick);
        holder.downArrow.setOnClickListener(onClick);

        holder.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.performClick();
                spinnerClick.hideKeyBoard();
            }
        });

        if (position == 0 && item.getIdSubgiro() == -1) {
            holder.editText.setHint(item.getSubgiro());
        } else {
            holder.editText.setText(item.getSubgiro());
        }
        holder.editText.setTypeface(typeface);

        return row;
    }

    static class ViewHolder {
        EditText editText;
        ImageView downArrow;
    }

    static class DropDownHolder {
        StyleTextView txtTitle;
    }

}
