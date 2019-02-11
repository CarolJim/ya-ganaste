package com.pagatodo.yaganaste.ui.maintabs.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.pagatodo.view_manager.components.HeadWallet;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.modules.emisor.PaymentToQR.operations.QrOperationActivity;
import com.pagatodo.yaganaste.modules.newsend.SendNewActivity;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.ContainerBuilder;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.pagatodo.yaganaste.modules.newsend.SendNewActivity.ID_ALL_FAVO;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendsFragment extends GenericFragment implements View.OnClickListener, PaymentsCarrouselManager , OnClickItemHolderListener {

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
//            paymentsCarouselPresenter.getCarouselItems();
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
                    break;
                case R.id.send_from_telephone:
                    break;
                case R.id.send_from_clabe:
                    break;
                case R.id.send_from_qr:
                    break;
                case R.id.btn_show_fav:
                    startActivity(SendNewActivity.createIntent(getActivity(), ID_ALL_FAVO));
                    break;
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

        @Override
        public void onItemClick(Object item) {

        }
}

