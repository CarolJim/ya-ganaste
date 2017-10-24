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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Giros;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.List;

/**
 * @author Jordan on 30/03/2017.
 */

public class BussinesLineSpinnerAdapter extends ArrayAdapter<Giros> {

    Context context;
    int mLayoutResourceId;
    List<Giros> mList;
    IOnSpinnerClick spinnerClick;


    public BussinesLineSpinnerAdapter(@NonNull Context con, @LayoutRes int resource,
                                      @NonNull List<Giros> objects, IOnSpinnerClick iOnSpinnerClick) {
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

    public Giros getItemSelected(int position) {
        return mList.get(position);
    }

    public int getGiroId(int position) {
        return mList.get(position).getIdGiro();
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        BussinesLineSpinnerAdapter.DropDownHolder holder;
        Giros item = mList.get(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new BussinesLineSpinnerAdapter.DropDownHolder();
            holder.txtTitle = (StyleTextView) row.findViewById(R.id.textView_spinner);
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto/Roboto-Regular.ttf");
            holder.txtTitle.setTypeface(typeface);

            row.setTag(holder);
        } else {
            holder = (BussinesLineSpinnerAdapter.DropDownHolder) row.getTag();
        }

        if (position == 0) {
            holder.txtTitle.setText("");
            holder.txtTitle.setHint(item.getGiro());
        } else {
            holder.txtTitle.setText(item.getGiro());
        }

        return row;
    }

    public int getItemPosition(@NonNull Giros giroComercio) {
        Giros current;
        for (int position = 0; position < mList.size(); position++) {
            current = mList.get(position);
            if (giroComercio.getIdGiro() == current.getIdGiro()) {
                return position;
            }
        }
        return -1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View row = convertView;
        BussinesLineSpinnerAdapter.ViewHolder holder;
        Giros item = mList.get(position);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto/Roboto-Regular.ttf");


        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_custom_layout, parent, false);

            holder = new BussinesLineSpinnerAdapter.ViewHolder();
            holder.editText = (EditText) row.findViewById(R.id.editTextCustomSpinner);
            holder.editText.setTypeface(typeface);
            holder.downArrow = (ImageView) row.findViewById(R.id.imageViewCustomSpinner);

            row.setTag(holder);
        } else {
            holder = (BussinesLineSpinnerAdapter.ViewHolder) row.getTag();
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

        if (position == 0 && item.getIdGiro() == -1) {
            holder.editText.setHint(item.getGiro());
        } else {
            holder.editText.setText(item.getGiro());
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
