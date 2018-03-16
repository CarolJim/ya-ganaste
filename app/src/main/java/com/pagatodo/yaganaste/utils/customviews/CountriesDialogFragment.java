package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Paises;
import com.pagatodo.yaganaste.interfaces.OnCountrySelectedListener;
import com.pagatodo.yaganaste.ui.account.register.adapters.NacionalidadSpinnerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 31/07/2017.
 */

public class CountriesDialogFragment extends DialogFragment implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {

    @BindView(R.id.listCountries)
    ListView listViewPaises;
    @BindView(R.id.searchCountry)
    SearchView searchViewPaises;

    private List<Paises> paises;
    private NacionalidadSpinnerAdapter arrayAdapter;

    private final static String COUNTRIES_LIST = "COUNTRIES_LIST";
    View rootView;
    private OnCountrySelectedListener onCountrySelectedListener;

    public final static CountriesDialogFragment newInstance(List<Paises> paises) {
        CountriesDialogFragment dialogFragment = new CountriesDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(COUNTRIES_LIST, (Serializable) paises);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paises = (List<Paises>) getArguments().getSerializable(COUNTRIES_LIST);
        Collections.sort(paises, new Comparator<Paises>() {
            @Override
            public int compare(Paises paises, Paises t1) {
                return paises.getPais().compareTo(t1.getPais());
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = inflater.inflate(R.layout.dialog_fragment_countries_list, null);
        initViews();
        return rootView;
    }

    private void initViews() {
        ButterKnife.bind(this, rootView);

        arrayAdapter = new NacionalidadSpinnerAdapter(getContext(), R.layout.spinner_layout, paises);
        listViewPaises.setAdapter(arrayAdapter);
        searchViewPaises.setQueryHint(getString(R.string.buscar_pais));
        searchViewPaises.setOnQueryTextListener(this);
        listViewPaises.setTextFilterEnabled(true);
        listViewPaises.setOnItemClickListener(this);
    }

    public void setOnCountrySelectedListener(OnCountrySelectedListener listener){
        this.onCountrySelectedListener = listener;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        arrayAdapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (onCountrySelectedListener != null) {
            onCountrySelectedListener.onCountrySelectedListener(arrayAdapter.getItem(position));
        }
        dismiss();
    }
}
