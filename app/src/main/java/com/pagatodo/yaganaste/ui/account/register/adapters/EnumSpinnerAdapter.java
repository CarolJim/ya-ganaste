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
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

/**
 * Created by Jordan on 28/03/2017.
 */

public class EnumSpinnerAdapter extends ArrayAdapter<IEnumSpinner> {
    Context mContext;
    int mLayoutResourceId;
    IEnumSpinner[] mItems;
    private IOnSpinnerClick spinnerClick;

    public EnumSpinnerAdapter(@NonNull Context context,
                              @LayoutRes int resource, @NonNull IEnumSpinner[] objects,
                              IOnSpinnerClick iOnSpinnerClick) {
        super(context, resource, objects);
        this.mLayoutResourceId = resource;
        this.mContext = context;
        this.mItems = objects;
        this.spinnerClick = iOnSpinnerClick;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View row = convertView;
        EnumSpinnerAdapter.DropDownHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new EnumSpinnerAdapter.DropDownHolder();
            holder.txtTitle = (StyleTextView) row.findViewById(R.id.textView_spinner);

            row.setTag(holder);
        } else {
            holder = (EnumSpinnerAdapter.DropDownHolder) row.getTag();
        }
        IEnumSpinner item = mItems[position];
        if (position == 0) {
            holder.txtTitle.setText("");
            holder.txtTitle.setHint(item.getName());
            holder.txtTitle.setHintTextColor(mContext.getResources().getColor(R.color.hint_color));
        } else {
            holder.txtTitle.setText(item.getName());
        }

        return row;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View row = convertView;
        EnumSpinnerAdapter.ViewHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_custom_layout, parent, false);

            holder = new EnumSpinnerAdapter.ViewHolder();
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
            holder = (EnumSpinnerAdapter.ViewHolder) row.getTag();
        }


        if (spinnerClick != null && parent != null) {
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
        }

        IEnumSpinner item = mItems[position];
        if (position == 0) {
            holder.editText.setHint(item.getName());
        } else {
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

    /*public String getItemIdString(int position) {
        return mItems[position].getId();
    }*/

    public String getItemName(int position) {
        return mItems[position].getName();
    }

    /*public int getPositionItemByName(String name) {
        for (int position = 0; position < mItems.length; position++) {
            if (mItems[position].getName().equals(name))
                return position;
        }
        return 0;
    }*/

    static class DropDownHolder {
        StyleTextView txtTitle;
    }

    static class ViewHolder {
        EditText editText;
        ImageView downArrow;
        LinearLayout laySpinnerCustom;
    }

}
