package com.pagatodo.yaganaste.modules.PaymentImp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder;
import com.pagatodo.view_manager.recyclers.RechargesRecycler;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.PaymentActivity;
import com.pagatodo.yaganaste.utils.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder.TYPE.*;
import static com.pagatodo.yaganaste.modules.PaymentImp.PaymentContentFragment.*;

public class PayFragment extends GenericFragment implements PayContracts.Listener,
        OnHolderListener<IconButtonDataHolder> {

    private static final String TAG_TYPE = "TAG_TYPE";
    private View rootView;
    private TabActivity activity;
    private PayInteractor interactor;
    private int type;



    @BindView(R.id.rechargesRecycler)
    RechargesRecycler rechargesRecycler;
    @BindView(R.id.recharges_recycler_fav)
    RechargesRecycler rechargesRecyclerFav;

    public static PayFragment newInstance(){
        return new PayFragment();
    }

    public static PayFragment newInstance(int type){
        PayFragment fragment = new PayFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG_TYPE,type);
        fragment.setArguments(bundle);
        return fragment;
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
        if (getArguments() != null){
            type = getArguments().getInt(TAG_TYPE);
        } else {
            type = 0;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.pay_fragment,container,false);
        initViews();
        return rootView;

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        if (type != 0){
            switch (type){
                case RECHARGE_FRAGMENT:
                    this.interactor.getRechargeCommerce();
                    this.interactor.getRechargeFavorites();
                    this.interactor.getRechargeFavLocal();
                    break;
                case SERVICES_PAY_FRAGMENT:
                    this.interactor.getPayServices();
                    this.interactor.getPayServicesFavLocal();
                    break;

            }
        }
    }

    @Override
    public void onRechargeCommerceSucces(List<Comercio> catalogos) {
        //rechargesRecycler.bind(covertItems(catalogos),this);
        rechargesRecycler.setListItem(covertItems(catalogos));
        rechargesRecycler.setOnClickItems(this);
    }

    @Override
    public void onPayServicesSuccess(List<Comercio> catalogos) {
        rechargesRecycler.setListItem(covertItems(catalogos));
        rechargesRecycler.setOnClickItems(this);
        //rechargesRecycler.bind(covertItems(catalogos),this);
    }

    @Override
    public void onRechargeFavorites(List<Favoritos> catalogos) {
        //rechargesRecyclerFav.bind(covertFavItem(catalogos),this);
        rechargesRecyclerFav.setListItem(covertFavItem(catalogos));
        rechargesRecyclerFav.setOnClickItems(this);
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
            IconButtonDataHolder dataHolder  = new IconButtonDataHolder(comercio.getLogoURLColor(),
                    comercio.getNombreComercio(),
                    comercio.getNombreComercio(),
                    ITEM_RECHARGE);
            dataHolder.setT(comercio);
            items.add(dataHolder);
        }
        return items;
    }

    private ArrayList<IconButtonDataHolder> covertFavItem(List<Favoritos> itemFav){
        ArrayList<IconButtonDataHolder> items = new ArrayList<>();
        for (Favoritos fav:itemFav){
            IconButtonDataHolder dataHolder = new IconButtonDataHolder("",
                    fav.getNombre(),
                    fav.getNombreComercio(),
                    ITEM_RECHARGE_FAV);
            dataHolder.setT(fav);
            items.add(dataHolder);

        }
        return items;
    }

    @Override
    public void onClickView(IconButtonDataHolder item, View view) {
        if (item.getT() instanceof Comercio){
            startActivity(PaymentActivity.creatIntent(getActivity(),(Comercio)item.getT(),false));
        } else if(item.getT() instanceof  Favoritos){
            UI.showSuccessSnackBar(Objects.requireNonNull(getActivity()),"ES Comercio: " +
                    ((Favoritos)item.getT()).getNombreComercio());
        }
    }
}
