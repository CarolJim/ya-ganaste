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
import android.widget.Filter;
import android.widget.Filterable;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Paises;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Jordan on 28/03/2017.
 */

public class NacionalidadSpinnerAdapter extends ArrayAdapter<Paises> implements Filterable {
    private Context mContext;
    private int mLayoutResourceId;
    private List<Paises> orgPaisesList;
    private List<Paises> paisesList;
    private Filter countriesFilter;

    public NacionalidadSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Paises> countries) {
        super(context, resource, countries);
        this.mLayoutResourceId = resource;
        this.mContext = context;
        this.orgPaisesList = countries;
        this.paisesList = countries;
    }


    public Paises getItem(int position) {
        return paisesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return paisesList.get(position).hashCode();
    }

    @Override
    public int getCount() {
        return paisesList.size();
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        DropDownHolder holder;
        Paises item = paisesList.get(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new DropDownHolder();
            holder.txtTitle = (StyleTextView) row.findViewById(R.id.textView_spinner);

            row.setTag(holder);
        } else {
            holder = (DropDownHolder) row.getTag();
        }

        holder.txtTitle.setText(item.getPais());

        return row;
    }

    public void resetData() {
        paisesList = orgPaisesList;
    }


    public String getItemIdString(int position) {
        return paisesList.get(position).getIdPais();
    }

    public String getItemName(int position) {
        return paisesList.get(position).getPais();
    }

    public int getPositionItemByName(String name) {
        for (int position = 0; position < paisesList.size(); position++) {
            if (paisesList.get(position).getPais().equals(name))
                return position;
        }
        return -1;
    }

    static class DropDownHolder {
        StyleTextView txtTitle;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (countriesFilter == null) {
            countriesFilter = new CountriesFilter();

        }
        return countriesFilter;
    }

    private class CountriesFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = orgPaisesList;
                results.count = orgPaisesList.size();
            } else {
                List<Paises> nPaisesList = new ArrayList<>();
                for (Paises country : orgPaisesList) {
                    if (removeDiacriticalMarks(country.getPais()).toUpperCase().contains(removeDiacriticalMarks(constraint.toString()).toUpperCase())) {
                        nPaisesList.add(country);
                    }
                }

                Collections.sort(nPaisesList, new Comparator<Paises>() {
                    @Override
                    public int compare(Paises paises, Paises t1) {
                        return paises.getPais().compareTo(t1.getPais());
                    }
                });


                results.count = nPaisesList.size();
                results.values = nPaisesList;

                /**
                 * Si no tenenmos resultados, agregamos al leyenda de No existen Resultados
                 */
                if (results.count == 0) {
                    String noExisten = mContext.getResources()
                            .getString(R.string.without_items);
                    nPaisesList.add(new Paises(
                                    999,
                                    noExisten,
                                    "999"
                            )
                    );
                    results.count = nPaisesList.size();
                    results.values = nPaisesList;
                }
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                paisesList = new ArrayList<>();
                notifyDataSetChanged();
            } else {
                paisesList = (List<Paises>) results.values;
                notifyDataSetChanged();
            }
        }

        private String removeDiacriticalMarks(String string) {
            return Normalizer.normalize(string, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        }
    }
}
