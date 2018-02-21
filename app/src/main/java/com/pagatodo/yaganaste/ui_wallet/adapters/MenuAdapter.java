package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;

import java.util.ArrayList;

import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;

/**
 * Created by icruz on 05/01/2018.
 */

public class MenuAdapter extends ArrayAdapter<String> implements CompoundButton.OnCheckedChangeListener {

    private ArrayList<OptionMenuItem> listItems;
    private final OnItemClickListener listener;
    private Context context;
    private ViewHolderMenu viewHolder;

    // View lookup cache
    private static class ViewHolderMenu {
        TextView txttitle;
        ImageView ic_item;
        View dividerList;
        AppCompatImageView rawItem;
        TextView subtitle;
        RadioGroup radioGroup;
        RadioButton radioButtonLeft;
        RadioButton radioButtonRight;
    }

    public MenuAdapter(Context context, ArrayList<OptionMenuItem> listItems, OnItemClickListener listener) {
        super(context, R.layout.menu_navegation_drawwer_adpater);
        this.context = context;
        this.listItems = listItems;
        this.listener = listener;
        this.viewHolder = new ViewHolderMenu();
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.menu_navegation_drawwer_adpater, parent, false);
        viewHolder.txttitle = convertView.findViewById(R.id.title);
        viewHolder.subtitle = convertView.findViewById(R.id.subtitle);
        viewHolder.ic_item = convertView.findViewById(R.id.ic_item);
        viewHolder.rawItem = convertView.findViewById(R.id.raw_item);
        viewHolder.dividerList = convertView.findViewById(R.id.dividerList);
        viewHolder.radioGroup = convertView.findViewById(R.id.radio_group);
        viewHolder.radioButtonLeft = convertView.findViewById(R.id.radiobutton_no);
        viewHolder.radioButtonRight = convertView.findViewById(R.id.radiobutton_si);
        convertView.setTag(viewHolder);

        viewHolder = (ViewHolderMenu) convertView.getTag();
        if (listItems.get(position).getResourceItem() != -1) {
            viewHolder.ic_item.setImageResource(listItems.get(position).getResourceItem());
        } else {
            viewHolder.ic_item.setVisibility(View.GONE);
        }

        //----- Subtitulos ----//
        if (listItems.get(position).getSubtitle() != 0) {
            viewHolder.txttitle.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
            viewHolder.subtitle.setVisibility(View.VISIBLE);
            viewHolder.subtitle.setText(listItems.get(position).getSubtitle());
        }

        if (listItems.get(position).getIndication() != null) {

            //----Toggle----//
            if (listItems.get(position).getIndication() == OptionMenuItem.INDICATION.TOGGLE) {
                viewHolder.radioGroup.setVisibility(View.VISIBLE);
                viewHolder.radioButtonLeft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    }
                });

                viewHolder.radioButtonRight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Log.d("TOGGLE RIGTH",b+"");
                    }
                });

            }
            if (listItems.get(position).getIndication() == OptionMenuItem.INDICATION.RAW) {
                viewHolder.rawItem.setVisibility(View.VISIBLE);
            }
        }

        viewHolder.txttitle.setText(this.context.getResources().getString(listItems.get(position).getResourceTitle()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(listItems.get(position));
            }
        });
        if (listItems.size() == 1){
            viewHolder.dividerList.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        super.getCount();
        return listItems.size();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        App.getInstance().getPrefs().saveDataBool(USE_FINGERPRINT, isChecked);
    }

    public interface OnItemClickListener {
        void onItemClick(OptionMenuItem optionMenuItem);
    }


    public void setStatus(boolean status){
        OptionMenuItem o = listItems.get(1);
        o.setStatusSwtich(status);
        listItems.set(1,o);
        notifyDataSetChanged();
    }

}
