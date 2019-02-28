package com.pagatodo.yaganaste.modules.payments.payFragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.pagatodo.view_manager.components.LabelButton;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder;
import com.pagatodo.view_manager.recyclers.RechargesRecycler;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.modules.payments.payReloadsServices.PayReloadsServicesActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity;
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
import static com.pagatodo.yaganaste.modules.payments.payReloadsServices.PayReloadsServicesActivity.Type.ALL_RECHARGES;
import static com.pagatodo.yaganaste.modules.payments.payReloadsServices.PayReloadsServicesActivity.Type.ALL_RECHARGES_FAV;
import static com.pagatodo.yaganaste.modules.payments.payReloadsServices.PayReloadsServicesActivity.Type.ALL_SERVICES;
import static com.pagatodo.yaganaste.modules.payments.payReloadsServices.PayReloadsServicesActivity.Type.ALL_SERVICES_FAV;
import static com.pagatodo.yaganaste.modules.payments.payFragment.PayContracts.TYPE_PAY.RECHARGE;
import static com.pagatodo.yaganaste.modules.payments.payFragment.PayContracts.TYPE_PAY.SERVICES;
import static com.pagatodo.yaganaste.modules.payments.paymentContent.PaymentContentFragment.RECHARGE_FRAGMENT;
import static com.pagatodo.yaganaste.modules.payments.paymentContent.PaymentContentFragment.SERVICES_PAY_FRAGMENT;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity.FAVORITE_PROCESS;
import static com.pagatodo.yaganaste.utils.Constants.NEW_FAVORITE_FROM_CERO;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_RECARGAS;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_SERVICIOS;
import static com.pagatodo.yaganaste.utils.Recursos.TYPEPAYMENT;

public class PayFragment extends GenericFragment implements PayContracts.Listener,
        OnHolderListener<IconButtonDataHolder> {

    private static final String TAG_TYPE = "TAG_TYPE";
    private View rootView;
    private int type;

    @BindView(R.id.labelButton)
    LabelButton headBtn;
    @BindView(R.id.recharge_fav)
    LabelButton feetBtn;
    @BindView(R.id.rechargesRecycler)
    RechargesRecycler rechargesRecycler;
    @BindView(R.id.recharges_recycler_fav)
    RechargesRecycler rechargesRecyclerFav;
    private PayReloadsServicesActivity activity;

    public static PayFragment newInstance() {
        return new PayFragment();
    }

    public static PayFragment newInstance(int type) {
        PayFragment fragment = new PayFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(TAG_TYPE);
        } else {
            type = 0;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.pay_fragment, container, false);
        initViews();
        return rootView;

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);


        if (type != 0) {
            switch (type) {
                case RECHARGE_FRAGMENT:
                    PayInteractor interactor = new PayInteractor(this, RECHARGE);
                    interactor.getComerces();
                    interactor.getFavoritesLocal();
                    headBtn.setLabel("Seleccionar Recarga");
                    feetBtn.setLabel("Recargar a un Favorito");

                    headBtn.setOnClick(v -> startActivity(PayReloadsServicesActivity
                            .intentRecharges(getActivity(), ALL_RECHARGES)));
                    feetBtn.setOnClick(v -> startActivity(PayReloadsServicesActivity
                            .intentRecharges(getActivity(), ALL_RECHARGES_FAV)));
                    App.getInstance().getPrefs().saveDataBool(TYPEPAYMENT, true);
                    break;
                case SERVICES_PAY_FRAGMENT:
                    App.getInstance().getPrefs().saveDataBool(TYPEPAYMENT, false);
                    interactor = new PayInteractor(this, SERVICES);
                    interactor.getComerces();
                    interactor.getFavoritesLocal();
                    headBtn.setLabel("Seleccionar Servicio");
                    feetBtn.setLabel("Pagar un Favorito");
                    headBtn.setOnClick(v -> startActivity(PayReloadsServicesActivity
                            .intentRecharges(getActivity(), ALL_SERVICES)));
                    feetBtn.setOnClick(v -> startActivity(PayReloadsServicesActivity
                            .intentRecharges(getActivity(), ALL_SERVICES_FAV)));

                    break;

            }
        }
    }


    @Override
    public void onCommerceSuccess(List<Comercio> catalogos) {
        rechargesRecycler.setListItem(covertItems(catalogos));
        rechargesRecycler.setOnClickItems(this);
    }

    @Override
    public void onFavoritesSuccess(List<Favoritos> catalogos, PayContracts.TYPE_PAY typePay) {
        rechargesRecyclerFav.setListItem(covertFavItem(catalogos, typePay));
        rechargesRecyclerFav.setOnClickItems(this);
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void hideLoad() {

    }

    @Override
    public void onError(String msj) {
        UI.showErrorSnackBar(Objects.requireNonNull(getActivity()), msj, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private ArrayList<IconButtonDataHolder> covertItems(List<Comercio> catalogos) {
        ArrayList<IconButtonDataHolder> items = new ArrayList<>();
        for (Comercio comercio : catalogos) {
            IconButtonDataHolder dataHolder = new IconButtonDataHolder(comercio.getLogoURLColor(),
                    comercio.getNombreComercio(),
                    comercio.getNombreComercio(),
                    ITEM_RECHARGE);
            dataHolder.setObject(comercio);
            items.add(dataHolder);
        }
        return items;
    }

    private ArrayList<IconButtonDataHolder> covertFavItem(List<Favoritos> itemFav, PayContracts.TYPE_PAY typePay) {
        ArrayList<IconButtonDataHolder> items = new ArrayList<>();
        switch (typePay) {
            case RECHARGE:
                items.add(rechargesRecycler.addFav(ADD_RECHARGE));
                break;
            case SERVICES:
                items.add(rechargesRecycler.addFav(ADD_PAY));
                break;
        }

        for (Favoritos fav : itemFav) {
            String url = fav.getImagenURL();
            if (fav.getImagenURL().isEmpty()) {
                url = fav.getImagenURLComercioColor();
            }
            IconButtonDataHolder dataHolder = new IconButtonDataHolder(url,
                    fav.getNombre(),
                    fav.getNombreComercio(),
                    ITEM_RECHARGE_FAV);
            dataHolder.setObject(fav);
            items.add(dataHolder);
        }
        return items;
    }

    @Override
    public void onClickView(IconButtonDataHolder item, View view) {

        if (item.getObject() instanceof Comercio) {
            startActivity(PaymentActivity.creatIntent(getActivity(), (Comercio) item.getObject()));
        } else if (item.getObject() instanceof Favoritos) {
            startActivity(PaymentActivity.creatIntent(getActivity(), (Favoritos) item.getObject()));
        } else if (item.getType() == ADD_RECHARGE) {
            Intent intent = new Intent(getActivity(), FavoritesActivity.class);
            intent.putExtra(CURRENT_TAB_ID, PAYMENT_RECARGAS);
            intent.putExtra(FAVORITE_PROCESS, NEW_FAVORITE_FROM_CERO);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivityForResult(intent, NEW_FAVORITE_FROM_CERO,
                        ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            } else {
                startActivityForResult(intent, NEW_FAVORITE_FROM_CERO);
            }
        } else if (item.getType() == ADD_PAY) {
            Intent intent = new Intent(getActivity(), FavoritesActivity.class);
            intent.putExtra(CURRENT_TAB_ID, PAYMENT_SERVICIOS);
            intent.putExtra(FAVORITE_PROCESS, NEW_FAVORITE_FROM_CERO);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivityForResult(intent, NEW_FAVORITE_FROM_CERO,
                        ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            } else {
                startActivityForResult(intent, NEW_FAVORITE_FROM_CERO);
            }
        }
    }
}
