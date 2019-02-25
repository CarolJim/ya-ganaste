package com.pagatodo.yaganaste.modules.payments.payReloadsServices.allRecharges;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.RowFavDataHolder;
import com.pagatodo.view_manager.recyclers.AllFavoritesRecycler;
import com.pagatodo.view_manager.recyclers.adapters.AllFavoritesAdapter;
import com.pagatodo.view_manager.recyclers.interfaces.PencilListener;
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

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity.FAVORITE_PROCESS;
import static com.pagatodo.yaganaste.ui_wallet.fragments.NewPaymentFragment.ITEM_CARRIER_PAGOS;
import static com.pagatodo.yaganaste.ui_wallet.fragments.NewPaymentFragment.ITEM_CARRIER_RECARGA;
import static com.pagatodo.yaganaste.utils.Constants.EDIT_FAVORITE;

public class AllRechargesFragment extends GenericFragment implements AllRechargesContracts.Listener,
        OnHolderListener<RowFavDataHolder>, PencilListener<RowFavDataHolder> {

    private static final String TAG_TYPE = "TAG_LIST";
    private View rootView;
    private PayReloadsServicesActivity.Type type;
    private AllRechargesInteractor interactor;


    @BindView(R.id.all_recharge)
    AllFavoritesRecycler recAll;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text_subtitle)
    TextView textSubtitle;
    @BindView(R.id.search_alls)
    TextView search_alls;

    public static AllRechargesFragment newInstance(PayReloadsServicesActivity.Type type) {
        AllRechargesFragment fragment = new AllRechargesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.type = (PayReloadsServicesActivity.Type) getArguments().getSerializable(TAG_TYPE);
        }
        interactor = new AllRechargesInteractor(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.allrecharges_fragment, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);

        search_alls.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        Resources resources = Objects.requireNonNull(getContext()).getResources();
        switch (type) {
            case ALL_RECHARGES:
                textTitle.setText(resources.getString(R.string.child_tab_recharge));
                textSubtitle.setText(resources.getString(R.string.select_comerce));
                interactor.getRecharge();
                recAll.setOnClickItems(this);

                break;
            case ALL_SERVICES:
                textTitle.setText(resources.getString(R.string.child_tab_services));
                textSubtitle.setText(resources.getString(R.string.select_comerce));
                interactor.getServices();
                recAll.setOnClickItems(this);
                break;
            case ALL_RECHARGES_FAV:
                textTitle.setText(resources.getString(R.string.title_all_recharge_fav));
                textSubtitle.setText(resources.getString(R.string.select_comerce_fav));
                interactor.getRechargeFavorites();
                recAll.setOnClickItems(this);
                recAll.setPencilOnClickItem(item -> {
                    Intent intentEditFav = new Intent(getContext(), FavoritesActivity.class);
                    intentEditFav.putExtra(Objects.requireNonNull(getActivity()).getString(R.string.favoritos_tag),
                            item.getObject());
                    intentEditFav.putExtra(CURRENT_TAB_ID, ITEM_CARRIER_RECARGA);
                    intentEditFav.putExtra(FAVORITE_PROCESS, EDIT_FAVORITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intentEditFav, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    } else {
                        startActivity(intentEditFav);
                    }
                });
                break;
            case ALL_SERVICES_FAV:
                textTitle.setText(resources.getString(R.string.title_all_services_fav));
                textSubtitle.setText(resources.getString(R.string.select_service_fav));
                interactor.getServicesFavorites();
                recAll.setOnClickItems(this);
                recAll.setPencilOnClickItem(item -> {
                    Intent intentEditFav = new Intent(getContext(), FavoritesActivity.class);
                    intentEditFav.putExtra(Objects.requireNonNull(getActivity()).getString(R.string.favoritos_tag),
                            item.getObject());
                    intentEditFav.putExtra(CURRENT_TAB_ID, ITEM_CARRIER_PAGOS);
                    intentEditFav.putExtra(FAVORITE_PROCESS, EDIT_FAVORITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intentEditFav, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    } else {

                        startActivity(intentEditFav);
                    }
                });
                break;
        }
    }

    private void filter(String s) {
        ArrayList<RowFavDataHolder> lista = new ArrayList<>();

        /*for (Comercio comercio : comerciosNuevos) {
            if (comercio.getNombreComercio().equals("")) {

                *//*list.add(RowFavDataHolder.create(comercio.getLogoURLColor(),
                        comercio.getNombreComercio(), "",
                        "", comercio, false));*//*
            }
        }*/
        recAll.getRecycler().setAdapter(new AllFavoritesAdapter());
    }

    private ArrayList<RowFavDataHolder> converte(List<Comercio> comercios) {
        ArrayList<RowFavDataHolder> list = new ArrayList<>();
        for (Comercio comercio : comercios) {
            list.add(RowFavDataHolder.create(comercio.getLogoURLColor(),
                    comercio.getNombreComercio(), "",
                    "", comercio, false));
        }
        return list;
    }

    private ArrayList<RowFavDataHolder> convert(List<Favoritos> favs) {
        ArrayList<RowFavDataHolder> list = new ArrayList<>();
        for (Favoritos favoritos : favs) {
            String urlImage;
            if (favoritos.getImagenURL().isEmpty()) {
                urlImage = favoritos.getImagenURLComercioColor();
            } else {
                urlImage = favoritos.getImagenURL();
            }

            list.add(RowFavDataHolder.create(urlImage,
                    favoritos.getNombre(), favoritos.getReferencia(),
                    favoritos.getColorMarca(),
                    favoritos, true));

        }
        return list;
    }

    @Override
    public void onSuccsessRechargeAndServices(List<Comercio> comercios) {
        recAll.setListItem(converte(comercios));
    }

    @Override
    public void onFavoritesSuccess(List<Favoritos> favoritos) {
        recAll.setListItem(convert(favoritos));
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void hideLoad() {

    }

    @Override
    public void onError(String msj) {
        UI.showSuccessSnackBar(Objects.requireNonNull(getActivity()), msj);
    }

    @Override
    public void onClickView(RowFavDataHolder item, View view) {
        if (item.getObject() instanceof Comercio) {
            startActivity(PaymentActivity.creatIntent(getActivity(), (Comercio) item.getObject()));
        } else if (item.getObject() instanceof Favoritos) {
            startActivity(PaymentActivity.creatIntent(getActivity(), (Favoritos) item.getObject()));
        }
    }

    @Override
    public void pencilOnClick(RowFavDataHolder item) {
        if (item.getObject() instanceof Favoritos) {
            Intent intentEditFav = new Intent(getContext(), FavoritesActivity.class);
            intentEditFav.putExtra(Objects.requireNonNull(getActivity()).getString(R.string.favoritos_tag),
                    item.getObject());
            intentEditFav.putExtra(CURRENT_TAB_ID, ITEM_CARRIER_RECARGA);
            intentEditFav.putExtra(FAVORITE_PROCESS, EDIT_FAVORITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intentEditFav, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            } else {
                startActivity(intentEditFav);
            }
        }
    }

}
