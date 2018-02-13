package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.interfaces.INotificationHistory;
import com.pagatodo.yaganaste.ui_wallet.fragments.NotificationHistoryFragment;
import com.pagatodo.yaganaste.ui_wallet.views.GenericDummyData;

import java.util.ArrayList;

/**
 * Created by FranciscoManzo on 12/02/2018.
 * Se encarga de tener siempre las bases comunes de los recyclers, en su constructor, se pasan los datos
 * necesarios, y en su metodo, se pasa el AdapterCustom de cada clase. Asi evitamos lineas de codigo
 * extras en la vista principal. Y alimentamos por medio de su interfase de constructor
 */

public class RecyclerGenericBase {
    private static final String TAG = RecyclerGenericBase.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public RecyclerGenericBase(RecyclerView rv_notification, int mOrientation) {
        mRecyclerView = rv_notification;
        mRecyclerView.setHasFixedSize(true);

        // Hacemos de la orientacion
        if(mOrientation == 1){
            mLayoutManager = new LinearLayoutManager(App.getContext());
        }else{
            mLayoutManager = new LinearLayoutManager(App.getContext(), LinearLayoutManager.HORIZONTAL, false);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void createRecycler(INotificationHistory iView, ArrayList<GenericDummyData> myDataset) {
        mAdapter = new AdapterNotificationRV(iView, myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }
}
