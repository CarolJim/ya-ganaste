package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;

import java.util.ArrayList;

import static android.content.Context.FINGERPRINT_SERVICE;
import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;

/**
 * Created by icruz on 05/01/2018.
 */

public class MenuAdapter extends ArrayAdapter<String> implements CompoundButton.OnCheckedChangeListener {

    private ArrayList<OptionMenuItem> listItems;
    private final OnItemClickListener listener;

    // View lookup cache
    private static class ViewHolderMenu {
        TextView txttitle;
        ImageView ic_item;
        View dividerList;
        AppCompatImageView rawItem;
        SwitchCompat switchItem;
        TextView subtitle;
    }

    public MenuAdapter(Context context, ArrayList<OptionMenuItem> listItems, OnItemClickListener listener) {
        super(context, R.layout.menu_navegation_drawwer_adpater);
        this.listItems = listItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolderMenu viewHolder; // view lookup cache stored in tag

        viewHolder = new ViewHolderMenu();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.menu_navegation_drawwer_adpater, parent, false);
        viewHolder.txttitle = convertView.findViewById(R.id.title);
        viewHolder.subtitle = convertView.findViewById(R.id.subtitle);
        viewHolder.ic_item = convertView.findViewById(R.id.ic_item);
        viewHolder.rawItem = convertView.findViewById(R.id.raw_item);
        viewHolder.switchItem = convertView.findViewById(R.id.switch_item);
        viewHolder.dividerList = convertView.findViewById(R.id.dividerList);

        convertView.setTag(viewHolder);

        viewHolder = (ViewHolderMenu) convertView.getTag();
        if (listItems.get(position).getResourceItem() != -1) {
            viewHolder.ic_item.setImageResource(listItems.get(position).getResourceItem());
        } else {
            viewHolder.ic_item.setVisibility(View.GONE);
        }

        if (listItems.get(position).getIndication() != null) {
            if (listItems.get(position).getIndication() == OptionMenuItem.INDICATION.RAW) {
                viewHolder.rawItem.setVisibility(View.VISIBLE);
            }
            if (listItems.get(position).getIndication() == OptionMenuItem.INDICATION.SWITCH) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    FingerprintManager fingerprintManager = (FingerprintManager) getContext().getSystemService(FINGERPRINT_SERVICE);
                    if (fingerprintManager.isHardwareDetected()) {
                        viewHolder.switchItem.setVisibility(View.VISIBLE);
                        viewHolder.switchItem.setChecked(App.getInstance().getPrefs().loadDataBoolean(USE_FINGERPRINT, true));
                    } else {
                        viewHolder.txttitle.setVisibility(View.GONE);
                        viewHolder.ic_item.setVisibility(View.GONE);
                        viewHolder.rawItem.setVisibility(View.GONE);
                        viewHolder.switchItem.setVisibility(View.GONE);
                        viewHolder.dividerList.setVisibility(View.GONE);
                    }
                } else {
                    viewHolder.txttitle.setVisibility(View.GONE);
                    viewHolder.ic_item.setVisibility(View.GONE);
                    viewHolder.rawItem.setVisibility(View.GONE);
                    viewHolder.switchItem.setVisibility(View.GONE);
                    viewHolder.dividerList.setVisibility(View.GONE);
                }
                viewHolder.switchItem.setOnCheckedChangeListener(this);
            }
            if (listItems.get(position).getIndication() == OptionMenuItem.INDICATION.SWITCHNORMAL) {
                viewHolder.switchItem.setVisibility(View.VISIBLE);
                viewHolder.switchItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    }
                });
            } if (listItems.get(position).getSubtitle() != null && !listItems.get(position).getSubtitle().isEmpty() ) {
                viewHolder.subtitle.setVisibility(View.VISIBLE);
                viewHolder.subtitle.setText(listItems.get(position).getSubtitle());
            }

        }

        viewHolder.txttitle.setText(listItems.get(position).getTitle());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(listItems.get(position));
            }
        });
        /*if (position == listItems.size() - 1)
            viewHolder.dividerList.setVisibility(View.GONE);*/
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


}
