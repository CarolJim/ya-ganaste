package com.pagatodo.yaganaste.utils.customviews.carousel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francisco Manzo on 07/08/2017.
 * Adapter Custom para poder trabajar con el filtro personalizado
 */

public class CustomAdapterPagos extends ArrayAdapter<String> {

    ArrayList<String> mList = new ArrayList<>();
    ArrayList<String> mListFiltered = new ArrayList<>();
    Context mContext;
    int layoutResourceId;
    boolean isFavorite;

    public CustomAdapterPagos(Context context, int layoutResourceId, ArrayList<String> mList, boolean isFavorite) {
        super(context, layoutResourceId, mList);
        this.mContext = context;
        this.layoutResourceId = layoutResourceId;
        this.mList = mList;
        this.mListFiltered = mList;
        this.isFavorite = isFavorite;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //  return super.getView(position, convertView, parent);
        ViewHolder viewHolder = null; // view lookup cache stored in tag

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pagos_textview, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.txtTypePagos = (TextView) convertView.findViewById(R.id.txtItemPagos);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtTypePagos.setText("" + mListFiltered.get(position).toString());
        return convertView;
    }

    public int getCount() {
        return mListFiltered.size();//note the change
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return mListFiltered.get(position);
    }

    // View lookup cache
    public static class ViewHolder {
        TextView txtTypePagos;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    //Si no existe el termino de busqueda o viene nulo
                    results.count = mList.size();
                    results.values = mList;
                } else {
                    //Si existen conlindancion en la cadena de busqueda las agregamos a la lista
                    List<String> resultsData = new ArrayList<>();
                    String searchStr = constraint.toString().toUpperCase();
                    for (String s : mList) {
                        if (s.toUpperCase().contains(searchStr)) {
                            resultsData.add(s);
                        }
                        results.count = resultsData.size();
                        results.values = resultsData;
                    }

                    /**
                     * Si no tenenmos resultados, agregamos al leyenda de No existen Resultados
                     */
                    if (results.count == 0) {
                        resultsData.add(!isFavorite ? mContext.getResources().getString(R.string.no_companias_busqueda) :
                                mContext.getResources().getString(R.string.no_favoritos_busqueda));
                        results.count = resultsData.size();
                        results.values = resultsData;
                    }
                }
                return results;
            }

            /**
             * Actualiza de forma automatica el fuiltro para mostrar los resultados
             * @param constraint
             * @param results
             */
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListFiltered = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
