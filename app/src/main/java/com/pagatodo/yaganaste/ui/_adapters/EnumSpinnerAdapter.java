package com.pagatodo.yaganaste.ui._adapters;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IEnumSpinner;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

/**
 * Created by Jordan on 23/03/2017.
 */

public class EnumSpinnerAdapter extends ArrayAdapter<IEnumSpinner> {
    Context mContext;
    int mLayoutResourceId;
    IEnumSpinner[] mItems;

    public EnumSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull IEnumSpinner[] objects) {
        super(context, resource, objects);
        this.mLayoutResourceId = resource;
        this.mContext = context;
        this.mItems = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        Holder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new Holder();
            holder.txtTitle = (StyleTextView) row.findViewById(R.id.textView_spinner);

            row.setTag(holder);
        } else {
            holder = (Holder) row.getTag();
        }

        IEnumSpinner item = mItems[position];
        holder.txtTitle.setText(item.getName());
        return row;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /*public String getItemIdString(int position) {
        return mItems[position].getId();
    }*/

    /*public int getPositionItemByName(String name) {
        for (int position = 0; position < mItems.length; position++) {
            if (mItems[position].getName().equals(name))
                return position;
        }
        return 0;
    }*/

    static class Holder {
        StyleTextView txtTitle;
    }
}
