package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.interfaces.IDatosPersonalesManager;
import com.pagatodo.yaganaste.ui.account.register.DatosPersonalesFragment;
import com.pagatodo.yaganaste.ui.account.register.adapters.NacionalidadSpinnerAdapter;

import java.util.ArrayList;

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

    private ArrayList<Countries> paises;
    private NacionalidadSpinnerAdapter arrayAdapter;

    private final static String COUNTRIES_LIST = "COUNTRIES_LIST";
    View rootView;
    private IDatosPersonalesManager datosPersonalesManager;

    public final static CountriesDialogFragment newInstance(ArrayList<Countries> paises) {
        CountriesDialogFragment dialogFragment = new CountriesDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(COUNTRIES_LIST, paises);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paises = (ArrayList<Countries>) getArguments().getSerializable(COUNTRIES_LIST);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof DatosPersonalesFragment) {
            datosPersonalesManager = ((DatosPersonalesFragment) getParentFragment());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        if (datosPersonalesManager != null) {
            datosPersonalesManager.onCountrySelected(arrayAdapter.getItem(position));
        }
        dismiss();
    }
}
