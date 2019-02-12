package com.pagatodo.yaganaste.ui_wallet.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ISearchCarrier;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by FranciscoManzo on 19/02/2018.
 */

public class AdapterSearchCarrierRV extends RecyclerView.Adapter<AdapterSearchCarrierRV.ViewHolder>
        implements Filterable {
    ArrayList<Comercio> mDataSetCarrier;
    ArrayList<Comercio> mDataSetCarrierFilter;
    ISearchCarrier mView;
    public EditText searchEditText;

    public AdapterSearchCarrierRV(ISearchCarrier mView, ArrayList<Comercio> mDataSetCarrier,
                                  EditText searchEditText) {
        this.mView = mView;
        this.searchEditText = searchEditText;
        this.mDataSetCarrier = mDataSetCarrier;
        this.mDataSetCarrierFilter = mDataSetCarrier;

        /**
         * Agregamos un TextWatcher para usar el filtro de palabra
         */
        TextWatcher filterTextWatcher = new TextWatcher() {

            public void afterTextChanged(Editable s) {
//
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                getFilter().filter(s);
            }
        };
        searchEditText.addTextChangedListener(filterTextWatcher);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTittle;
        public CircleImageView imageViewBorder;
        public LinearLayout mLinearl;
        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTittle = itemView.findViewById(R.id.adapter_search_carrier_tittle);
            imageViewBorder = itemView.findViewById(R.id.adapter_search_carrier_imgItemGalleryMark);
            mLinearl = itemView.findViewById(R.id.adapter_search_carrier_linearl);
            mImageView = itemView.findViewById(R.id.imgItemGalleryPay);
        }
    }

    @Override
    public AdapterSearchCarrierRV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_search_carrier_rv_item, parent, false);
        AdapterSearchCarrierRV.ViewHolder vh = new AdapterSearchCarrierRV.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterSearchCarrierRV.ViewHolder holder, final int position) {
        holder.mTittle.setText(mDataSetCarrierFilter.get(position).getNombreComercio());
        setImagePicasso(holder.mImageView, mDataSetCarrierFilter.get(position).getLogoURLColor());

        holder.mLinearl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Enviamos como resultado el objeto en el que hacemos clic, se envia el objeto
                // Porque la lista puede cambiar
                mView.setData(mDataSetCarrierFilter.get(position));
            }
        });
    }

    private void setImagePicasso(ImageView imageViewBorder, String urlLogo) {
        Picasso.get().load(App.getContext().getString(R.string.url_images_logos) + urlLogo)
                .placeholder(R.mipmap.logo_ya_ganaste)
                .error(R.mipmap.icon_user_fail)
                .into(imageViewBorder);
    }

    @Override
    public int getItemCount() {
        return mDataSetCarrierFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    //Si no existe el termino de busqueda o viene nulo
                    results.count = mDataSetCarrier.size();
                    results.values = mDataSetCarrier;
                } else {
                    //Si existen conlindancion en la cadena de busqueda las agregamos a la lista
                    ArrayList<Comercio> resultsData = new ArrayList<>();
                    String searchStr = constraint.toString().toUpperCase();
                    for (Comercio s : mDataSetCarrier) {
                        if (s.getNombreComercio().toUpperCase().contains(searchStr)) {
                            resultsData.add(s);
                        }
                        results.count = resultsData.size();
                        results.values = resultsData;
                    }

                    /**
                     * Si no tenenmos resultados, agregamos al leyenda de No existen Resultados
                     */
                    if (results.count == 0) {
                      /*  resultsData.add(App.getContext().getResources().getString(R.string.without_items));
                        results.count = resultsData.size();
                        results.values = resultsData;*/
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
                mDataSetCarrierFilter = (ArrayList<Comercio>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
