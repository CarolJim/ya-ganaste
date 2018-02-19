package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.ui_wallet.pojos.TextData;

import java.util.ArrayList;

/**
 * Created by icruz on 13/02/2018.
 */

public class TextDataAdapter extends ArrayAdapter<String> {

    private ArrayList<TextData> listItems;

    // View lookup cache
    private static class ViewHolderTextData {
        TextView titleLeft;
        TextView titleRight;
    }

    public TextDataAdapter(Context context, ArrayList<TextData> listItems) {
        super(context, R.layout.adapter_text_data);
        this.listItems = listItems;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            ViewHolderTextData viewHolder;
            viewHolder = new ViewHolderTextData();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adapter_text_data, parent, false);

        viewHolder.titleLeft = convertView.findViewById(R.id.text_data_left);
        viewHolder.titleRight = convertView.findViewById(R.id.text_data_right);

        viewHolder.titleLeft.setText(listItems.get(position).getTitleLeft());
        viewHolder.titleRight.setText(listItems.get(position).getTitleRight());

        return convertView;
    }

    @Override
    public int getCount() {
        super.getCount();
        return listItems.size();
    }
}