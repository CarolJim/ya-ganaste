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
import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 28/03/2017.
 */

public class NacionalidadSpinnerAdapter extends ArrayAdapter<Countries> implements Filterable {
    private Context mContext;
    private int mLayoutResourceId;
    private List<Countries> orgCountriesList;
    private List<Countries> countriesList;
    private Filter countriesFilter;

    public NacionalidadSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Countries> countries) {
        super(context, resource, countries);
        this.mLayoutResourceId = resource;
        this.mContext = context;
        this.orgCountriesList = countries;
        this.countriesList = countries;
    }


    public Countries getItem(int position) {
        return countriesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return countriesList.get(position).hashCode();
    }

    @Override
    public int getCount() {
        return countriesList.size();
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        DropDownHolder holder;
        Countries item = countriesList.get(position);

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
        countriesList = orgCountriesList;
    }


    public String getItemIdString(int position) {
        return countriesList.get(position).getIdPais();
    }

    public String getItemName(int position) {
        return countriesList.get(position).getPais();
    }

    public int getPositionItemByName(String name) {
        for (int position = 0; position < countriesList.size(); position++) {
            if (countriesList.get(position).getPais().equals(name))
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
                results.values = orgCountriesList;
                results.count = orgCountriesList.size();
            } else {
                List<Countries> nCountriesList = new ArrayList<>();
                for (Countries country : orgCountriesList) {
                    if (removeDiacriticalMarks(country.getPais()).toUpperCase().contains(removeDiacriticalMarks(constraint.toString()).toUpperCase())) {
                        nCountriesList.add(country);
                    }
                }

                results.count = nCountriesList.size();
                results.values = nCountriesList;

                /**
                 * Si no tenenmos resultados, agregamos al leyenda de No existen Resultados
                 */
                if (results.count == 0) {
                    String noExisten = mContext.getResources()
                            .getString(R.string.no_existen_resultados);
                    nCountriesList.add(new Countries(
                                    999,
                                    noExisten,
                                    "999"
                            )
                    );
                    results.count = nCountriesList.size();
                    results.values = nCountriesList;
                }
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                countriesList = new ArrayList<>();
                notifyDataSetChanged();
            } else {
                countriesList = (List<Countries>) results.values;
                notifyDataSetChanged();
            }
        }

        private String removeDiacriticalMarks(String string) {
            return Normalizer.normalize(string, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        }
    }
}
