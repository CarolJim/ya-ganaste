package com.pagatodo.yaganaste.ui.account.register.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IEnumSpinner;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoStates;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.List;

/**
 * Created by Jordan on 28/03/2017.
 */

public class StatesSpinnerAdapter extends ArrayAdapter<DtoStates> {
    Context mContext;
    int mLayoutResourceId;
    List<DtoStates> mItems;
    private IOnSpinnerClick spinnerClick;

    public StatesSpinnerAdapter(@NonNull Context context,
                                @LayoutRes int resource, @NonNull List<DtoStates> mItems,
                                IOnSpinnerClick iOnSpinnerClick) {
        super(context, resource, mItems);
        this.mLayoutResourceId = resource;
        this.mContext = context;
        this.mItems = mItems;
        this.spinnerClick = iOnSpinnerClick;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View row = convertView;
        StatesSpinnerAdapter.DropDownHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new StatesSpinnerAdapter.DropDownHolder();
            holder.txtTitle = (StyleTextView) row.findViewById(R.id.textView_spinner);

            row.setTag(holder);
        } else {
            holder = (StatesSpinnerAdapter.DropDownHolder) row.getTag();
        }
        DtoStates item = mItems.get(position);
        if (position == 0) {
            holder.txtTitle.setText("");
            holder.txtTitle.setHint(item.Nombre);
            holder.txtTitle.setHintTextColor(mContext.getResources().getColor(R.color.hint_color));
        } else {
            holder.txtTitle.setText(item.Nombre);
        }
        return row;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View row = convertView;
        StatesSpinnerAdapter.ViewHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_custom_layout, parent, false);

            holder = new StatesSpinnerAdapter.ViewHolder();
            holder.editText = (EditText) row.findViewById(R.id.editTextCustomSpinner);
            if (position == 0) {
                holder.editText.setTextColor(App.getContext().getResources().getColor(R.color.hint_color));
            } else {
                holder.editText.setTextColor(App.getContext().getResources().getColor(R.color.grayColor));
            }
            holder.downArrow = (ImageView) row.findViewById(R.id.imageViewCustomSpinner);
            holder.laySpinnerCustom = (LinearLayout) row.findViewById(R.id.laySpinnerCustom);
            row.setTag(holder);
        } else {
            holder = (StatesSpinnerAdapter.ViewHolder) row.getTag();
        }

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerClick.onSpinnerClick();
                parent.performClick();
            }
        };

        holder.laySpinnerCustom.setOnClickListener(onClick);

        holder.editText.setOnClickListener(onClick);

        holder.downArrow.setOnClickListener(onClick);

        DtoStates item = mItems.get(position);
        if (position == 0) {
            holder.editText.setHint(item.Nombre);
        } else {
            holder.editText.setText(item.Nombre);
        }

        return row;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    public String getItemName(int position) {
        return mItems.get(position).Nombre;
    }

    static class DropDownHolder {
        StyleTextView txtTitle;
    }

    static class ViewHolder {
        EditText editText;
        ImageView downArrow;
        LinearLayout laySpinnerCustom;
    }
}
