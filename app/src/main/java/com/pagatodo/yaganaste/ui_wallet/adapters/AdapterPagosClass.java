package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IPaymentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FranciscoManzo on 31/01/2018.
 * Controla el generar un RecycerView, en cada posicion del RV crea un GridView, que a su vez muestra
 * los items de favoritos
 */

public class AdapterPagosClass {
    private static final String TAG = AdapterPagosClass.class.getSimpleName();

    IPaymentFragment mView;
    ArrayList<DataFavoritos> mDataset;
    ArrayList<DataFavoritos> mDataAux;
    ArrayList<DataFavoritos> mDataAux2;
    ArrayList<ArrayList<DataFavoritos>> mFullListaFav;
    GridView mGridView;
    int maxItem, numGrids, numRest, countItem;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public AdapterPagosClass(IPaymentFragment mView, List<DataFavoritos> mDataset, RecyclerView mRecyclerView, GridView mGridView) {
        this.mView = mView;
        this.mDataset = (ArrayList<DataFavoritos>) mDataset;
        this.mRecyclerView = mRecyclerView;
        this.mGridView = mGridView;
        countItem = 0;
        initData();
    }

    /**
     * Iniciamos las listas, el objetivo es hacer una lista general, que contenga sublistas en formato
     * 8,8,3 por ejemplo.
     */
    private void initData() {
        maxItem = mDataset.size();
        numGrids = maxItem / 8;
        numRest = maxItem % 8;
        mDataAux = new ArrayList<>();
        mDataAux2 = new ArrayList<>();
        mFullListaFav = new ArrayList<>();

        for (int x = 0; x < maxItem; x++) {
            mDataAux.add(mDataset.get(x));
            if (countItem < 7) {
                countItem++;
            } else {
                mFullListaFav.add(mDataAux);
                mDataAux = new ArrayList<>();
                countItem = 0;
            }
        }

        if (mDataAux.size() > 0) {
            mFullListaFav.add(mDataAux);
            mDataAux = new ArrayList<>();
            countItem = 0;
        }
    }

    /**
     * Inicializa los elementos para el RV
     * @param mType
     * @param typeOperation
     */
    public void createRecycler(int mType, int typeOperation) {
        mRecyclerView.setHasFixedSize(true);

        // Hacemos el Set Horizontal para el RV
        mLayoutManager = new LinearLayoutManager(App.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if(mAdapter != null){
            mAdapter = null;
        }

        mAdapter = new AdapterPagosRV(mFullListaFav, mView, mType, typeOperation);
        mRecyclerView.setAdapter(mAdapter);

        if(typeOperation == 1) {
           // Toast.makeText(App.getContext(), "Pagamos Favoritos", Toast.LENGTH_SHORT).show();
        }else{
            // Toast.makeText(App.getContext(), "Editamos Favoritos", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<ArrayList<DataFavoritos>> getmFullListaFav() {
        return mFullListaFav;
    }
}
