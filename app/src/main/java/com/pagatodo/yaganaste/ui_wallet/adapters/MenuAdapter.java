package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;

import java.util.ArrayList;

/**
 * Created by icruz on 05/01/2018.
 */

public class MenuAdapter extends ArrayAdapter<String>{

    ArrayList<Integer> listIcItem;
    ArrayList<String> listTitle;
    // View lookup cache
    private static class ViewHolderMenu {
        TextView txttitle;
        ImageView ic_item;
        View dividerList;
        LinearLayout itemMenu;
    }

    public MenuAdapter(Context context){
        super(context, R.layout.menu_navegation_drawwer_adpater);
        listIcItem = new ArrayList<>();
        listIcItem.add(R.mipmap.ic_seguridad);
        listIcItem.add(R.mipmap.ic_chat);
        listIcItem.add(R.mipmap.ic_ajustes);
        listIcItem.add(R.mipmap.ic_acerca);
        listTitle = new ArrayList<>();
        listTitle.add(context.getResources().getString(R.string.navigation_drawer_menu_seguridad));
        listTitle.add(context.getResources().getString(R.string.navigation_drawer_menu_chat));
        listTitle.add(context.getResources().getString(R.string.navigation_drawer_menu_ajustes));
        listTitle.add(context.getResources().getString(R.string.navigation_drawer_menu_acerca));

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolderMenu viewHolder; // view lookup cache stored in tag


            viewHolder = new ViewHolderMenu();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.menu_navegation_drawwer_adpater, parent, false);
            viewHolder.txttitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.ic_item = (ImageView) convertView.findViewById(R.id.ic_item);
            viewHolder.dividerList =  convertView.findViewById(R.id.dividerList);

            convertView.setTag(viewHolder);

            viewHolder = (ViewHolderMenu) convertView.getTag();





        viewHolder.txttitle.setText(listTitle.get(position));
        viewHolder.ic_item.setImageResource(listIcItem.get(position));
        if (position == 3)
        viewHolder.dividerList.setVisibility(View.GONE);

        return convertView;

    }

    @Override
    public int getCount() {
        super.getCount();
        return 4;
    }
}
