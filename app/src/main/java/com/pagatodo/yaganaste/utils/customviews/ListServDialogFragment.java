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
import com.pagatodo.yaganaste.interfaces.OnListServiceListener;
import com.pagatodo.yaganaste.ui.account.register.adapters.ListServicesSpinnerAdapter;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;
import com.pagatodo.yaganaste.utils.customviews.carousel.CustomCarouselItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Francisco Manzo on 22/09/2017.
 * Dialogo Custom para mostrar la lista de servicios.
 */

public class ListServDialogFragment extends DialogFragment implements SearchView.OnQueryTextListener,
        AdapterView.OnItemClickListener {

    @BindView(R.id.listService)
    ListView listService;
    @BindView(R.id.searchService)
    SearchView searchView;
    View rootView;
    private OnListServiceListener onListServiceListener;
    ArrayList<CarouselItem> backUpResponse;
    private ListServicesSpinnerAdapter arrayAdapter;

    /**
     * Recibimos el Array CustomCarouselItem para mostrar los nombres de los Comercios
     *
     * @param backUpResponse
     * @return
     */
    public static ListServDialogFragment newInstance(ArrayList<CarouselItem> backUpResponse) {
        ListServDialogFragment dialogFragment = new ListServDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("backUpResponse", backUpResponse);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backUpResponse = getArguments().getParcelable("backUpResponse");
     /*
        Collections.sort(backUpResponse, new Comparator<CarouselItem>() {
            @Override
            public int compare(CarouselItem countries, CarouselItem t1) {
                return countries.getComercio().getNombreComercio().compareTo(t1.getComercio().getNombreComercio());
            }
        });
        */
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_fragment_services_list, null);
        initViews();
        return rootView;
    }

    private void initViews() {
        ButterKnife.bind(this, rootView);
        /**
         * 1 - Hacemos SET de la lista en el Adapter Custm para mostrar el nombre del comercio
         * 2 - Agregamos Filtros para realizar la busqueda
         * 3 - Agregamos un Listener para obtener el Clic de alguno de sus items
         */
        arrayAdapter = new ListServicesSpinnerAdapter(getContext(), R.layout.spinner_layout, backUpResponse);
        listService.setAdapter(arrayAdapter);
        //searchView.setQueryHint(getString(R.string.buscar_pais));
        searchView.setOnQueryTextListener(this);
        listService.setTextFilterEnabled(true);
        listService.setOnItemClickListener(this);
    }

    /**
     * Hacemos SET dede AddNewFavorites para atrapar el resultado de hacer Clic
     *
     * @param listener
     */
    public void setOnListServiceListener(OnListServiceListener listener) {
        this.onListServiceListener = listener;
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

    /**
     * Usamos la interfase OnListServiceListener para comunicar este Dialog fragment con la actividad
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (onListServiceListener != null) {
            onListServiceListener.onListServiceListener(arrayAdapter.getItem(position), position);
        }
        dismiss();
    }
}
