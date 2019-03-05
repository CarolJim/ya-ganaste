package com.pagatodo.yaganaste.modules.emisor.movements;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.pagatodo.view_manager.controllers.dataholders.MovementDataHolder;
import com.pagatodo.view_manager.recyclers.MovementsRecycler;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ConsultarMovimientosRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.modules.emisor.WalletMainActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovementsFragment extends GenericFragment implements MovementsContracts.Listener{

    private View rootView;
    private MovementsInteractor interactor;
    private boolean isUpdate;
    private MovmentsContentFragment.MovmentsContentListener listener;

    @BindView(R.id.movementsRecycler)
    MovementsRecycler recycler;

    public static MovementsFragment newInstance(){
        return new MovementsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interactor = new MovementsInteractor(this);
        isUpdate = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.movements_fragment,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
    }

    public boolean isUpdate(){
        return isUpdate;
    }

    public void getMovements(String month, String anio){
     /*
        ArrayList<MovementDataHolder> itemlist = new ArrayList<>();
        itemlist.add(MovementDataHolder.create("26","Ene","Venta con tarjeta",
                "Santader 5896","150",
                getContext().getResources().getDrawable(com.pagatodo.view_manager.R.drawable.ic_dove),null));
        itemlist.add(MovementDataHolder.create("26","Ene","Venta con tarjeta",
                "Santader 5896","150",
                getContext().getResources().getDrawable(com.pagatodo.view_manager.R.drawable.ic_dove),null));
        itemlist.add(MovementDataHolder.create("26","Ene","Venta con tarjeta",
                "Santader 5896","150",
                getContext().getResources().getDrawable(com.pagatodo.view_manager.R.drawable.ic_dove),null));

        itemlist.add(MovementDataHolder.create("26","Ene","Venta con tarjeta",
                "Santader 5896","150",
                getContext().getResources().getDrawable(com.pagatodo.view_manager.R.drawable.ic_dove),null));*/
        //recycler.setItems(itemlist);
        /*
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

         */
        interactor.getMovements(month,anio);
    }

    public void setListener(MovmentsContentFragment.MovmentsContentListener listener){
        this.listener = listener;
    }

    @Override
    public void onSuccessMovements(List<MovimientosResponse> list) {
        recycler.setItems(covertItems(list));
        isUpdate = false;
    }

    @Override
    public void showLoad() {
        if (listener!= null) {
            listener.showLoad();
        }
    }

    @Override
    public void hideLoad() {
        if (listener!= null) {
            listener.hideLoad();
        }
    }

    @Override
    public void onError(String msj) {
        UI.showErrorSnackBar(getActivity(),msj,Snackbar.LENGTH_SHORT);
    }

    private ArrayList<MovementDataHolder> covertItems(List<MovimientosResponse> list){
        ArrayList<MovementDataHolder> dataHolders = new ArrayList<>();

        for (MovimientosResponse mov:list){
            dataHolders.add(MovementDataHolder.create(mov.getFechaMovimiento(),
                    mov.getFechaMovimiento(),mov.getConcepto(),mov.getDescripcion(),
                    String.valueOf(mov.getImporte()),mov.getColorMarcaComercio(),mov));
        }
        return dataHolders;
    }

}
