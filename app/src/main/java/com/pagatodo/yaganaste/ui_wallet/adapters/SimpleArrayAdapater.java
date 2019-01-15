package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Giros;
import com.pagatodo.yaganaste.ui.account.register.adapters.BussinesLineSpinnerAdapter;
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
        View row = convertView;
        DropDownHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_layout, parent, false);

            holder = new DropDownHolder();
            holder.txtTitle = (StyleTextView) row.findViewById(R.id.textView_spinner);
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto/Roboto-Regular.ttf");
            holder.txtTitle.setTypeface(typeface);

            row.setTag(holder);
        } else {
            holder = (DropDownHolder) row.getTag();
        }

        if (position == 0) {
            holder.txtTitle.setText("");
            holder.txtTitle.setHint(arrayList.get(position));
        } else {
            holder.txtTitle.setText(arrayList.get(position));
        }

        return row;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull final ViewGroup parent) {

        View row = convertView;
        ViewHolder holder;
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto/Roboto-Regular.ttf");


        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_custom_layout, parent, false);

            holder = new ViewHolder();
            holder.editText = row.findViewById(R.id.editTextCustomSpinner);
            holder.editText.setTypeface(typeface);
            holder.downArrow = row.findViewById(R.id.imageViewCustomSpinner);
            holder.downArrow.setVisibility(View.VISIBLE);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        View.OnClickListener onClick = v -> {
            //if (spinnerClick != null) {
                //spinnerClick.onSpinnerClick();
                //spinnerClick.hideKeyBoard();
            //}
            parent.performClick();

        };

        holder.editText.setOnClickListener(onClick);
        holder.downArrow.setOnClickListener(onClick);
        holder.editText.setOnClickListener(v -> {
            parent.performClick();
            /*if (spinnerClick != null) {
                spinnerClick.hideKeyBoard();
            }*/
        });

        if (position == 0) {
            holder.editText.setHint(arrayList.get(position));
        } else {
            holder.editText.setText(arrayList.get(position));
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
