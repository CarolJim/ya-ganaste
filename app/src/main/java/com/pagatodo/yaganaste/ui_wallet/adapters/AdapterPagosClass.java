package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.nfc.Tag;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.ui_wallet.fragments.NewPaymentFragment;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IPaymentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FranciscoManzo on 31/01/2018.
 */

public class AdapterPagosClass {
    private static final String TAG = AdapterPagosClass.class.getSimpleName();

    IPaymentAdapter mView;
    ArrayList<DataFavoritos> mDataset;
    ArrayList<DataFavoritos> mDataAux;
    ArrayList<DataFavoritos> mDataAux2;
    ArrayList<ArrayList<DataFavoritos>> mFullListaFav;
    GridView mGridView;
    int maxItem, numGrids, numRest, countItem;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public AdapterPagosClass(IPaymentAdapter mView, List<DataFavoritos> mDataset, RecyclerView mRecyclerView, GridView mGridView) {
        this.mView = mView;
        this.mDataset = (ArrayList<DataFavoritos>) mDataset;
        this.mRecyclerView = mRecyclerView;
        this.mGridView = mGridView;
        countItem = 0;
        initData();
    }

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
                mDataAux  = new ArrayList<>();
                countItem = 0;
            }
        }

        if(mDataAux.size() > 0){
            mFullListaFav.add(mDataAux);
            mDataAux  = new ArrayList<>();
            countItem = 0;
        }

        Log.d(TAG, "Punto Control");
    }


    public void createRecycler() {
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(App.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new AdapterPagosRV(mFullListaFav);
        mRecyclerView.setAdapter(mAdapter);
    }
}
