package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataListaNotificationArray;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.interfaces.INotificationHistory;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ISearchCarrier;

import java.util.ArrayList;

/**
 * Created by FranciscoManzo on 12/02/2018.
 * Se encarga de tener siempre las bases comunes de los recyclers, en su constructor, se pasan los datos
 * necesarios, y en su metodo, se pasa el AdapterCustom de cada clase. Asi evitamos lineas de codigo
 * extras en la vista principal. Y alimentamos por medio de su interfase de constructor
 * <p>
 * Ademas de agregar un sistema de EndlessRecyclerViewScrollListener que detecta cuando vemos la ultima
 * parte de los datos
 */

public class RecyclerGenericBase {
    private static final String TAG = RecyclerGenericBase.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayoutManager mLayoutManager2;
    private int lastPos;
    private int mTotalItem;
    ArrayList<DataListaNotificationArray> myDataset;
    private ArrayList<Comercio> mDataSetCarrier;
    public static int VERTICAL_ORIENTATION = 1;
    public static int HORIZONTAL_ORIENTATION = 2;
    public EditText searchEditText;

    private EndlessRecyclerViewScrollListener scrollListener;

    public RecyclerGenericBase(RecyclerView rv_notification, int mOrientation) {
        /**
         * Bloque comun de funcionalidad de RV
         */
        mRecyclerView = rv_notification;
        mRecyclerView.setHasFixedSize(true);

        // Hacemos de la orientacion
        if (mOrientation == VERTICAL_ORIENTATION) {
            mLayoutManager2 = new LinearLayoutManager(App.getContext());
        } else {
            mLayoutManager2 = new LinearLayoutManager(App.getContext(), LinearLayoutManager.HORIZONTAL, false);
        }

        // Hacemos Set del LayoutManager en el RV
        mRecyclerView.setLayoutManager(mLayoutManager2);
    }

    /**
     * Creamos el RV con las especificaciones necesarias, y se agrega un Listener
     * @param iView
     * @param myDataset
     */
    public void createRecyclerList(final INotificationHistory iView, final ArrayList<DataListaNotificationArray> myDataset) {
        this.myDataset = myDataset;
        mAdapter = new AdapterNotificationRV(iView, myDataset);
        mRecyclerView.setAdapter(mAdapter);

        // Creamos nuestro EndlessRecyclerViewScrollListener que se encarga de detectar el final del Scroll
        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager2) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //Toast.makeText(App.getContext(), "LoadMoreData", Toast.LENGTH_SHORT).show();

                // Enviamos el control al View con IdNotificacion para pedir el siguiente bloque
                iView.loadNextDataToView(myDataset.get(myDataset.size() - 1).getIdNotificacion());
            }
        };
        mRecyclerView.addOnScrollListener(scrollListener);

    }

    /**
     * Cargamos el nuevo bloque de datos en nuestra lista original y actualizamos el adapter
     *
     * @param mDatasetNext
     */
    public void loadNextData(ArrayList<DataListaNotificationArray> mDatasetNext) {
        if (mDatasetNext != null && mDatasetNext.size() > 0) {
            for (int x = 0; x < mDatasetNext.size(); x++) {
                myDataset.add(mDatasetNext.get(x));
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Cargamos un RV especifico, para las busquedas, pasando nuestra referencia la EditText
     * @param iView
     * @param mDataSetCarrier
     * @param searchEditText
     */
    public void createRecyclerList(ISearchCarrier iView, ArrayList<Comercio> mDataSetCarrier,
                                   EditText searchEditText) {
        this.mDataSetCarrier = mDataSetCarrier;
        this.searchEditText = searchEditText;
        //mDataSetCarrier.remove(0); // Removemos el item 0 que es la Lupa en la lista original
        mAdapter = new AdapterSearchCarrierRV(iView, mDataSetCarrier, searchEditText);
        mRecyclerView.setAdapter(mAdapter);
    }
}
