package com.pagatodo.yaganaste.ui.maintabs.fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.android.material.snackbar.Snackbar;
import com.pagatodo.view_manager.components.HeadWallet;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.modules.newsend.SendNewActivity;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.EnvioFormularioWallet;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.ContainerBuilder;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.CLABE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TELEFONO;
import static com.pagatodo.yaganaste.modules.newsend.SendNewActivity.ID_ALL_FAVO;
import static com.pagatodo.yaganaste.modules.newsend.SendNewActivity.PAYMENT_CARD;
import static com.pagatodo.yaganaste.modules.newsend.SendNewActivity.PAYMENT_CLABE;
import static com.pagatodo.yaganaste.modules.newsend.SendNewActivity.PAYMENT_PHONE;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.RESUL_FAVORITES;
import static com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity.FAVORITE_PROCESS;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.BACK_FROM_PAYMENTS;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE_COMERCE;
import static com.pagatodo.yaganaste.utils.Constants.NEW_FAVORITE_FROM_CERO;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendsFragment extends GenericFragment implements View.OnClickListener, PaymentsCarrouselManager,
        OnClickItemHolderListener {

    View rootView;
    @BindView(R.id.btn_show_fav)
    StyleButton btn_show_fav;
    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    List<Favoritos> backUpResponseFavoritos;
    ArrayList<CarouselItem> backUpResponse, finalList, backUpResponsefinal, backUpResponsefavo;
    @BindView(R.id.content_linearlayout)
    LinearLayout mLinearLayout;
    private HeadWallet headWallet;
    private LinearLayout send_from_card, send_from_clabe, send_from_telephone, send_from_qr;
    int idTipoComercio, idComercio;
    private boolean isCuentaValida = true, isUp, bancoselected = false, solicitabanco = true,
            isfavo = false, isValid = true;
    Favoritos favoriteItem;
    Comercio comercioItem;
    Payments payment;
    TransferType selectedType;
    private String nombreDestinatario, referenciaNumber, referenceFavorite, myReferencia, errorText,
            referencia, formatoComercio, concepto;


    @Override
    public void onResume() {
        super.onResume();
        paymentsCarouselPresenter.getFavoriteCarouselItems();
    }


    public SendsFragment() {
        // Required empty public constructor
    }

    public static SendsFragment newInstance() {

        return new SendsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sends, container, false);
        paymentsCarouselPresenter = new PaymentsCarouselPresenter(Constants.PAYMENT_ENVIOS, this, getContext(), false);
        send_from_card = (LinearLayout) rootView.findViewById(R.id.send_from_card);
        send_from_telephone = (LinearLayout) rootView.findViewById(R.id.send_from_telephone);
        send_from_clabe = (LinearLayout) rootView.findViewById(R.id.send_from_clabe);
        send_from_qr = (LinearLayout) rootView.findViewById(R.id.send_from_qr);
        headWallet = (HeadWallet) rootView.findViewById(R.id.headWallet);

        initViews();

        if (!UtilsNet.isOnline(getActivity())) {
            UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
        } else {
            onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.synch_favorites));
            paymentsCarouselPresenter.getCarouselItems();
            paymentsCarouselPresenter.getFavoriteCarouselItems();
        }
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btn_show_fav.setOnClickListener(this::onClick);
        send_from_card.setOnClickListener(this::onClick);
        send_from_telephone.setOnClickListener(this::onClick);
        send_from_clabe.setOnClickListener(this::onClick);
        send_from_qr.setOnClickListener(this::onClick);
        headWallet.setAmount(App.getInstance().getPrefs().loadData(USER_BALANCE));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_from_card:
                startActivity(SendNewActivity.createIntent(getActivity(), PAYMENT_CARD));
                break;
            case R.id.send_from_telephone:
                startActivity(SendNewActivity.createIntent(getActivity(), PAYMENT_PHONE));
                break;
            case R.id.send_from_clabe:
                startActivity(SendNewActivity.createIntent(getActivity(), PAYMENT_CLABE));
                break;
            case R.id.send_from_qr:
                //Intent intent = new Intent(this, ScannVisionActivity.class);
                Intent intent = ScannVisionActivity.createIntent(getActivity(),false);
                intent.putExtra(ScannVisionActivity.QRObject, true);
                this.startActivityForResult(intent, BARCODE_READER_REQUEST_CODE_COMERCE);
                break;
            case R.id.btn_show_fav:
                startActivity(SendNewActivity.createIntent(getActivity(), ID_ALL_FAVO));
                break;
        }


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

        /**
         * Buscamos en nuestro orderBy cada elemento en un ciclo adicional de originalList, si el ID existe
         * lo agregamos a nuesta finalList. Y eliminamos ese elemnto de originalList
         */
        for (Integer miList : orderBy) {
            for (int x = 0; x < backUpResponse.size(); x++) {
                if (backUpResponse.get(x).getComercio().getIdComercio() == miList) {
                    finalList.add(backUpResponse.get(x));
                    backUpResponse.remove(x);
                }
            }
        }
        Collections.sort(backUpResponse, new Comparator<CarouselItem>() {
            @Override
            public int compare(CarouselItem o1, CarouselItem o2) {
                return o1.getComercio().getNombreComercio().compareToIgnoreCase(o2.getComercio().getNombreComercio());
            }

        });
        for (int x = 0; x < backUpResponse.size(); x++) {
            finalList.add(backUpResponse.get(x));
        }
    }


    private void setBackUpResponseFav(ArrayList<CarouselItem> mResponse) {
        backUpResponsefavo = new ArrayList<>();
        backUpResponseFavoritos = new ArrayList<>();

        for (CarouselItem carouselItem : mResponse) {
            backUpResponsefavo.add(carouselItem);
        }
        Collections.sort(backUpResponsefavo, (o1, o2) -> o1.getFavoritos().getNombre().compareToIgnoreCase(o2.getFavoritos().getNombre()));

    }

    @Override
    public void setCarouselData(ArrayList<CarouselItem> response) {
        setBackUpResponse(response);
    }


    @Override
    public void setDataBank(String idcomercio, String nombrebank) {

    }

    @Override
    public void errorgetdatabank() {

    }

    @Override
    public void setCarouselDataFavoritos(ArrayList<CarouselItem> response) {
        setBackUpResponseFav(response);
    }

    @Override
    public void setFavolist(List<Favoritos> lista) {
        backUpResponseFavoritos = new ArrayList<>();
        backUpResponseFavoritos = lista;
        mLinearLayout.removeAllViews();
        ContainerBuilder.FAVORITOS(getContext(), mLinearLayout, lista, this);
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
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

    private void clearContent() {
/*        comercioItem = null;
        favoriteItem = null;
        tipoEnvio.setSelection(0);
        editListServ.setText("");
        editListServ.clearFocus();
        referencia = "";
        receiverName.setText("");
        receiverName.clearFocus();
        concept.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));
        numberReference.setText("123456");*/
    }

    @Override
    public void onItemClick(Object item) {
        Favoritos favorito = (Favoritos) item;
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

            for (int x = 0; x < finalList.size(); x++) {
                if (finalList.get(x).getComercio().getIdComercio() == myIdComercio) {
                    comercioItem = finalList.get(x).getComercio();
                    idTipoComercio = finalList.get(x).getComercio().getIdTipoComercio();
                    idComercio = finalList.get(x).getComercio().getIdComercio();
                }
            }

            referencia = favorito.getReferencia();
            referencia = referencia.replaceAll(" ", "");
            concepto = "";
            nombreDestinatario = myName;
            referenciaNumber = referencia;
            payment = new Envios(selectedType,referenciaNumber , 0D, nombreDestinatario, concepto,referencia , comercioItem,
                    favoriteItem != null);
            Intent intent = new Intent(getContext(), EnvioFormularioWallet.class);
            intent.putExtra("pagoItem", payment);
            intent.putExtra("favoritoItem", favoriteItem);
            startActivityForResult(intent, BACK_FROM_PAYMENTS);
            clearContent();


        }
    }
}

