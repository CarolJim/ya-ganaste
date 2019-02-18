package com.pagatodo.yaganaste.modules.newsend.AllFavorites;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.modules.newsend.SendNewActivity;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.RESUL_FAVORITES;
import static com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity.FAVORITE_PROCESS;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.NEW_FAVORITE_FROM_CERO;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFavoritesFragment extends GenericFragment implements   View.OnClickListener, PaymentsCarrouselManager {

    View rootView;

    @BindView(R.id.reciclerAllFavoritos)
    RecyclerView reciclerAllFavoritos;

    @BindView(R.id.imgAddfavo)
    ImageView imgAddfavo;
    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    List<Favoritos> backUpResponseFavoritos;
    public  static  AllFavoritesFragment newInstance(){
        return new AllFavoritesFragment();
    }
    ArrayList<CarouselItem> backUpResponse, finalList, backUpResponsefinal, backUpResponsefavo;

    SendNewActivity activity;

    public AllFavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        paymentsCarouselPresenter = new PaymentsCarouselPresenter(Constants.PAYMENT_ENVIOS, this, getContext(), false);
        rootView = inflater.inflate(R.layout.fragment_all_favorites, container, false);
        activity.showaddfavo(true);
        initViews();
        if (!UtilsNet.isOnline(getActivity())) {
            UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
        } else {
            onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.synch_favorites));
            paymentsCarouselPresenter.getFavoriteCarouselItems();
            paymentsCarouselPresenter.getCarouselItems();
        }

        return rootView;
    }



    @Override
    public void onClick(View v) {

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        activity =(SendNewActivity) getActivity();
        activity.showaddfavo(true);
        imgAddfavo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddFavorite = new Intent(getContext(), FavoritesActivity.class);
                intentAddFavorite.putExtra(CURRENT_TAB_ID, Constants.PAYMENT_ENVIOS);
                intentAddFavorite.putExtra(FAVORITE_PROCESS, NEW_FAVORITE_FROM_CERO);
                startActivityForResult(intentAddFavorite, RESUL_FAVORITES);
            }
        });


    }

    @Override
    public void setCarouselData(ArrayList<CarouselItem> lista) {
        setBackUpResponse(lista);
    }
    private void setBackUpResponse(ArrayList<CarouselItem> mResponse) {
        backUpResponse = new ArrayList<>();
        ArrayList<Integer> orderBy = new ArrayList<>();
        finalList = new ArrayList<>();
        orderBy.add(8609);
        orderBy.add(785);
        orderBy.add(779);
        orderBy.add(787);
        orderBy.add(809);
        orderBy.add(790);
        orderBy.add(799);
        orderBy.add(796);
        orderBy.add(832);

        backUpResponsefinal = new ArrayList<>();

        mResponse = Utils.removeNullCarouselItem(mResponse);
        for (CarouselItem carouselItem : mResponse) {
            backUpResponse.add(carouselItem);
        }


    }
    @Override
    public void setDataBank(String idcomercio, String nombrebank) {

    }

    @Override
    public void errorgetdatabank() {

    }

    @Override
    public void setCarouselDataFavoritos(ArrayList<CarouselItem> response) {

    }

    @Override
    public void setFavolist(List<Favoritos> lista) {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
        backUpResponseFavoritos = new ArrayList<>();
        backUpResponseFavoritos = lista;
        setAdapter();

    }

    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        reciclerAllFavoritos.setLayoutManager(linearLayoutManager);
        reciclerAllFavoritos.setHasFixedSize(true);
        reciclerAllFavoritos.setAdapter(new AdapterSelecFavoNew(backUpResponseFavoritos,getActivity(),backUpResponse));
    }

    @Override
    public void showErrorService() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSuccess(Double importe) {

    }
}
