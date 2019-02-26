package com.pagatodo.yaganaste.modules.newsend.AllFavorites;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.modules.newsend.SendNewActivity;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.ui._controllers.EnvioFormularioWallet;
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
import java.util.Objects;

import static android.view.View.GONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.CLABE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TELEFONO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.RESUL_FAVORITES;
import static com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity.FAVORITE_PROCESS;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.BACK_FROM_PAYMENTS;
import static com.pagatodo.yaganaste.utils.Constants.NEW_FAVORITE_FROM_CERO;
import static com.pagatodo.yaganaste.utils.Recursos.IDCOMERCIO_YA_GANASTE;
import static com.pagatodo.yaganaste.utils.UtilsIntents.favoriteIntents;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFavoritesFragment extends GenericFragment implements View.OnClickListener, PaymentsCarrouselManager, IReciclerfavoritos {

    View rootView;

    @BindView(R.id.reciclerAllFavoritos)
    RecyclerView reciclerAllFavoritos;
    @BindView(R.id.search_favorites)
    EditText search_favorites;
    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    List<Favoritos> backUpResponseFavoritos;

    public static AllFavoritesFragment newInstance() {
        return new AllFavoritesFragment();
    }

    ArrayList<CarouselItem> backUpResponse, finalList, backUpResponsefinal, backUpResponsefavo;
    int idTipoComercio, idComercio;
    SendNewActivity activity;
    Favoritos favoriteItem;
    Comercio comercioItem;
    Payments payment;
    TransferType selectedType;
    private boolean isCuentaValida = true, isUp, bancoselected = false, solicitabanco = true,
            isfavo = false, isValid = true;


    private String nombreDestinatario, referenciaNumber, referenceFavorite, myReferencia, errorText,
            referencia, formatoComercio, concepto;

    public AllFavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (SendNewActivity) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        paymentsCarouselPresenter.getFavoriteCarouselItems();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        paymentsCarouselPresenter = new PaymentsCarouselPresenter(Constants.PAYMENT_ENVIOS, this, getContext(), false);
        rootView = inflater.inflate(R.layout.fragment_all_favorites, container, false);

        initViews();
        if (!UtilsNet.isOnline(Objects.requireNonNull(getActivity()))) {
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
        ButterKnife.bind(this, rootView);

        activity = (SendNewActivity) getActivity();
        activity.showaddfavo(true);
        activity = (SendNewActivity) getActivity();
        Objects.requireNonNull(activity).showaddfavo(true);
        /*imgAddfavo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddFavorite = new Intent(getContext(), FavoritesActivity.class);
                intentAddFavorite.putExtra(CURRENT_TAB_ID, Constants.PAYMENT_ENVIOS);
                intentAddFavorite.putExtra(FAVORITE_PROCESS, NEW_FAVORITE_FROM_CERO);
                startActivityForResult(intentAddFavorite, RESUL_FAVORITES);
            }
        });*/

        search_favorites.addTextChangedListener(new TextWatcher() {
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
    }

    private void filter(String s) {
        ArrayList<Favoritos> favs = new ArrayList<>();
        for (Favoritos favoritos : backUpResponseFavoritos) {
            if (favoritos.getNombre().toLowerCase().contains(s)){
                favs.add(favoritos);
            }
        }
        reciclerAllFavoritos.setAdapter(new AdapterSelecFavoNew(favs, getActivity(), backUpResponse, this));
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
        reciclerAllFavoritos.setAdapter(new AdapterSelecFavoNew(backUpResponseFavoritos, getActivity(), backUpResponse, this));
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

    @Override
    public void onRecyclerItemClick(View v, int position) {


    }

    @Override
    public void onRecyclerItemClick(View v, int position, boolean isEditable, Object item) {

        /**
         * Si es editable mandamos a Edittar, si no a proceso normal
         */
        Favoritos favorito = (Favoritos) item;
        search_favorites.setText("");
        if (isEditable) {
            if (favorito.getIdComercio() != 0) {
                Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                favoriteIntents(getActivity(), favorito);
                //Intent intentEditFav = new Intent(getActivity(), EditFavoritesActivity.class);
                /*Intent intentEditFav = new Intent(getActivity(), FavoritesActivity.class);
                intentEditFav.putExtra(getActivity().getString(R.string.favoritos_tag), favorito);
                intentEditFav.putExtra(CURRENT_TAB_ID, Constants.PAYMENT_ENVIOS);
                intentEditFav.putExtra(FAVORITE_PROCESS, EDIT_FAVORITE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intentEditFav, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                } else {
                    startActivity(intentEditFav);
                }*/
            }
        } else {
            if (favorito.getIdComercio() == 0) { // Click en item Agregar
                //Intent intentAddFavorite = new Intent(getActivity(), AddToFavoritesActivity.class);
                Intent intentAddFavorite = new Intent(getContext(), FavoritesActivity.class);
                intentAddFavorite.putExtra(CURRENT_TAB_ID, Constants.PAYMENT_ENVIOS);
                intentAddFavorite.putExtra(FAVORITE_PROCESS, NEW_FAVORITE_FROM_CERO);
                startActivityForResult(intentAddFavorite, RESUL_FAVORITES);
            } else {
                // Toast.makeText(getActivity(), "Favorito: " + backUpResponseFavoritos.get(position).getNombre(), Toast.LENGTH_SHORT).show();

                idComercio = 0;
                isfavo = true;
                bancoselected = true;
                bancoselected = true;
                favoriteItem = favorito;
                long myIdComercio = favorito.getIdComercio();
                String myName = favorito.getNombre();

                myReferencia = favorito.getReferencia();

                switch (favorito.getReferencia().length()) {
                    case 10:
                        myReferencia = favorito.getReferencia();
                        selectedType = NUMERO_TELEFONO;

                        break;
                    case 16:
                        myReferencia = favorito.getReferencia();
                        selectedType = NUMERO_TARJETA;

                        break;
                    case 18:
                        myReferencia = favorito.getReferencia();
                        selectedType = CLABE;
                        break;
                }

                for (int x = 0; x < backUpResponse.size(); x++) {
                    if (backUpResponse.get(x).getComercio().getIdComercio() == myIdComercio) {
                        comercioItem = backUpResponse.get(x).getComercio();
                        idTipoComercio = backUpResponse.get(x).getComercio().getIdTipoComercio();
                        idComercio = backUpResponse.get(x).getComercio().getIdComercio();
                    }
                }

                referencia = favorito.getReferencia();
                referencia = referencia.replaceAll(" ", "");
                concepto = "";
                nombreDestinatario = myName;
                referenciaNumber = referencia;
                payment = new Envios(selectedType, referenciaNumber, 0D, nombreDestinatario, concepto, referencia, comercioItem,
                        favoriteItem != null);
                Intent intent = new Intent(getContext(), EnvioFormularioWallet.class);
                intent.putExtra("pagoItem", payment);
                intent.putExtra("favoritoItem", favoriteItem);
                startActivityForResult(intent, BACK_FROM_PAYMENTS);


            }
        }
    }

}
