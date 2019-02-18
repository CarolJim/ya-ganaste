package com.pagatodo.yaganaste.modules.PaymentImp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder;
import com.pagatodo.view_manager.recyclers.RechargesRecycler;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder.TYPE.ITEM_RECHARGE;
import static com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder.TYPE.ITEM_RECHARGE_FAV;

public class PayFragment extends GenericFragment implements PayContracts.Listener {

    private View rootView;
    private TabActivity activity;
    private PayInteractor interactor;

    @BindView(R.id.rechargesRecycler)
    RechargesRecycler rechargesRecycler;
    @BindView(R.id.recharges_recycler_fav)
    RechargesRecycler rechargesRecyclerFav;

    public static PayFragment newInstance(){
        return new PayFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (TabActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.interactor = new PayInteractor(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.pay_fragment,container,false);
        initViews();
        return rootView;

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        this.interactor.getRechargeCommerce();
        this.interactor.getRechargeFavorites();
    }

    @Override
    public void onRechargeCommerceSucces(List<Comercio> catalogos) {
        rechargesRecycler.bind(covertItems(catalogos),null);
    }

    @Override
    public void onRechargeFavorites(List<Favoritos> catalogos) {
        rechargesRecyclerFav.bind(covertFavItem(catalogos),null);
    }

    @Override
    public void showLoad() {
        this.activity.showLoader("");
    }

    @Override
    public void hideLoad() {
        this.activity.hideLoader();
    }

    @Override
    public void onError(String msj) {
        UI.showErrorSnackBar(Objects.requireNonNull(getActivity()),msj,Snackbar.LENGTH_SHORT);
    }

    private ArrayList<IconButtonDataHolder> covertItems(List<Comercio> catalogos){
        ArrayList<IconButtonDataHolder> items = new ArrayList<>();
        for (Comercio comercio:catalogos){
            //String imageUrl, String name, TYPE type
            items.add(new IconButtonDataHolder(comercio.getLogoURLColor(),
                    comercio.getNombreComercio(),
                    ITEM_RECHARGE));
        }
        return items;
    }

    private ArrayList<IconButtonDataHolder> covertFavItem(List<Favoritos> itemFav){
        ArrayList<IconButtonDataHolder> items = new ArrayList<>();
        for (Favoritos fav:itemFav){
            items.add(new IconButtonDataHolder("",
                    fav.getNombre(),ITEM_RECHARGE_FAV));
        }
        return items;
    }

}
