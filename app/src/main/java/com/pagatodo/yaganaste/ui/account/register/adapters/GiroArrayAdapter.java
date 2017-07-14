package com.pagatodo.yaganaste.ui.account.register.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.GiroComercio;
import com.pagatodo.yaganaste.interfaces.enums.GiroAdapterType;

import java.util.List;

/**
 * Created by Jordan on 24/03/2017.
 */

public class GiroArrayAdapter extends ArrayAdapter<GiroComercio> {
    Context mContext;
    int mLayoutResourceId;
    List<GiroComercio> mList;
    GiroAdapterType tipo;

    public GiroArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<GiroComercio> objects, GiroAdapterType tipoAdapter) {
        super(context, resource, objects);
        this.mContext = context;
        this.mList = objects;
        this.tipo = tipoAdapter;
        this.mLayoutResourceId = resource;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public GiroComercio getItemSelected(int position) {
        return mList.get(position);
    }

    public int getGiroId(int position) {
        return mList.get(position).getIdGiro();
    }

    private View getCustoView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        GiroArrayAdapter.Holder holder;
        GiroComercio item = mList.get(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new GiroArrayAdapter.Holder();
            holder.txtTitle = (TextView) row.findViewById(R.id.textView_spinner);

            row.setTag(holder);
        } else {
            holder = (GiroArrayAdapter.Holder) row.getTag();
        }

        switch (tipo) {
            case GIRO:
                holder.txtTitle.setText(item.getnGiro().trim());
                break;
            case SUBGIRO:
                holder.txtTitle.setText(item.getnSubgiro().trim());
                break;
            default:
                holder.txtTitle.setText("");
                break;
        }

        return row;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getDropDownView(position, convertView, parent);
        return getCustoView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustoView(position, convertView, parent);
    }

    static class Holder {
        TextView txtTitle;
    }
}
