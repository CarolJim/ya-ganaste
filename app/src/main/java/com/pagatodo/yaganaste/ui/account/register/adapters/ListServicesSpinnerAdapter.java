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
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Paises;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Francisco Manzo on 22/09/2017.
 */

public class ListServicesSpinnerAdapter extends ArrayAdapter<CarouselItem> implements Filterable {

    private Context mContext;
    private int mLayoutResourceId;
    private List<Paises> orgPaisesList;
    private List<Paises> paisesList;
    private List<CarouselItem> orgListServ;
    private List<CarouselItem> listServ;
    private Filter countriesFilter;

    public ListServicesSpinnerAdapter(@NonNull Context context, @LayoutRes int resource,
                                      @NonNull List<CarouselItem> listServ) {
        super(context, resource, listServ);
        this.mLayoutResourceId = resource;
        this.mContext = context;
        this.orgListServ = listServ;
        this.listServ = listServ;
    }

    public CarouselItem getItem(int position) {
        return listServ.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listServ.get(position).hashCode();
    }

    @Override
    public int getCount() {
        return listServ.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        DropDownHolder holder;
        CarouselItem item = listServ.get(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new DropDownHolder();
            holder.txtTitle = (StyleTextView) row.findViewById(R.id.textView_spinner);

            row.setTag(holder);
        } else {
            holder = (DropDownHolder) row.getTag();
        }
        if (item.getComercio()==null){

        }else {
            if (item.getComercio().getNombreComercio()=="Hola"){}else {

                holder.txtTitle.setText(item.getComercio().getNombreComercio());

            }
        }
        return row;
    }

    static class DropDownHolder {
        StyleTextView txtTitle;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (countriesFilter == null) {
            countriesFilter = new ServiceFilter();

        }
        return countriesFilter;
    }

    private class ServiceFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String charText = String.valueOf(constraint);
            charText = charText.toLowerCase(Locale.getDefault());

            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = orgListServ;
                results.count = orgListServ.size();
            } else {
                List<CarouselItem> nCountriesList = new ArrayList<>();
                for (CarouselItem country : orgListServ) {
                    if (country.getComercio().getNombreComercio().toLowerCase(Locale.getDefault()).contains(charText)) {
                        nCountriesList.add(country);
                    }

                }

               /* Collections.sort(nCountriesList, new Comparator<Paises>() {
                    @Override
                    public int compare(Paises countries, Paises t1) {
                        return countries.getPais().compareTo(t1.getPais());
                    }
                });
*/

                results.count = nCountriesList.size();
                results.values = nCountriesList;

                /**
                 * Si no tenenmos resultados, agregamos al leyenda de No existen Resultados
                 */
                if (results.count == 0) {
                    String noExisten = mContext.getResources()
                            .getString(R.string.without_items);
                    nCountriesList.add(new CarouselItem(new Comercio(999,999,noExisten,999,"Nada")));
                    results.count = nCountriesList.size();
                    results.values = nCountriesList;
                }
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                listServ = new ArrayList<>();
                notifyDataSetChanged();
            } else {
                listServ = (List<CarouselItem>) results.values;
                notifyDataSetChanged();
            }
        }

        private String removeDiacriticalMarks(String string) {
            return Normalizer.normalize(string, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        }
    }
}
