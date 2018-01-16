package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui_wallet.RequestPaymentActivity;
import com.pagatodo.yaganaste.ui_wallet.adapters.FavoritesRequestPaymentAdapter;
import com.pagatodo.yaganaste.ui_wallet.adapters.RecyclerViewOnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.adapters.RequestPaymentVerticalAdapter;
import com.pagatodo.yaganaste.ui_wallet.dialog.DialogAddRequestPayment;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoRequestPayment;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IAddRequestPayment;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_ENVIOS;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentRequestFragment extends GenericFragment implements View.OnClickListener, PaymentsCarrouselManager, RecyclerViewOnItemClickListener,
        IAddRequestPayment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String REQUEST_MONTO = "REQUEST_MONTO";

    View rootView;
    @BindView(R.id.request_amount)
    StyleTextView requestAmout;
    @BindView(R.id.lyt_favorites)
    LinearLayout lytFavorites;
    @BindView(R.id.rcv_fav_request_payment)
    RecyclerView rcvFavorites;
    @BindView(R.id.img_user_payment)
    CircleImageView imgUser;
    @BindView(R.id.txt_username_payment)
    StyleTextView txtUsername;
    @BindView(R.id.txt_balance_payment)
    StyleTextView txtBalance;
    @BindView(R.id.lyt_add_request_user)
    LinearLayout lytAddUser;
    @BindView(R.id.rlt_add_request)
    RelativeLayout rltAddRequest;
    @BindView(R.id.rcv_request_payment)
    RecyclerView rcvRequestPayment;
    @BindView(R.id.btn_continue_payment)
    StyleButton btnSolicitar;

    PaymentsCarouselPresenter paymentsCarouselPresenter;
    List<DataFavoritos> lstFavorites;
    ArrayList<DtoRequestPayment> lstRequestPayment;
    private float monto, amountPerPerson;

    public PaymentRequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param monto Parameter 1.
     * @return A new instance of fragment PaymentRequestFragment.
     */
    public static PaymentRequestFragment newInstance(float monto) {
        PaymentRequestFragment fragment = new PaymentRequestFragment();
        Bundle args = new Bundle();
        args.putFloat(REQUEST_MONTO, monto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            monto = getArguments().getFloat(REQUEST_MONTO);
            amountPerPerson = monto;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_payment_request, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        paymentsCarouselPresenter = new PaymentsCarouselPresenter(PAYMENT_ENVIOS, this, getContext(), false);
        lstRequestPayment = new ArrayList<>();
        requestAmout.setText(String.format("%s", StringUtils.getCurrencyValue(monto)));
        SingletonUser dataUser = SingletonUser.getInstance();
        txtUsername.setText("" + dataUser.getDataUser().getUsuario().getNombre());
        txtBalance.setText("" + Utils.getCurrencyValue(dataUser.getDatosSaldo().getSaldoEmisor()));
        String imagenavatar = dataUser.getDataUser().getUsuario().getImagenAvatarURL();
        if (!imagenavatar.equals("")) {
            Picasso.with(App.getContext())
                    .load(imagenavatar)
                    .placeholder(R.mipmap.icon_user)
                    .into(imgUser);
        }
        // Favorites
        rcvFavorites.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        // List Request's
        rcvRequestPayment.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        rltAddRequest.setOnClickListener(this);
        btnSolicitar.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.synch_favorites));
        paymentsCarouselPresenter.getFavoriteCarouselItems();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlt_add_request:
                // Add Request
                if (calculateAmountPerPerson() >= 1) {
                    DialogAddRequestPayment dialogAddRequestPayment = new DialogAddRequestPayment();
                    dialogAddRequestPayment.setAddRequestPaymentListener(this);
                    dialogAddRequestPayment.show(getFragmentManager(), "Dialog Add Request Payment");
                } else {
                    UI.createSimpleCustomDialog(getActivity().getString(R.string.title_error), getActivity().getString(R.string.txt_error_calculate_amount), getFragmentManager(), new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {
                        }

                        @Override
                        public void actionCancel(Object... params) {
                        }
                    }, true, false);
                }
                break;
            case R.id.btn_continue_payment:
                if (lstRequestPayment.size() > 0) {
                    lstRequestPayment.remove(lstRequestPayment.size() - 1); // Delete add item
                    ((RequestPaymentActivity) getActivity()).setListRequests(lstRequestPayment);
                } else {
                    UI.createSimpleCustomDialog(getActivity().getString(R.string.title_error), getActivity().getString(R.string.txt_message_request_empty), getFragmentManager(), new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {
                        }

                        @Override
                        public void actionCancel(Object... params) {
                        }
                    }, true, false);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showError() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
        lytFavorites.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
        lytFavorites.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(Double importe) {
    }

    @Override
    public void setCarouselData(ArrayList<CarouselItem> response) {
    }

    @Override
    public void setCarouselDataFavoritos(ArrayList<CarouselItem> response) {
    }

    @Override
    public void setFavolist(List<DataFavoritos> lista) {
        if (lista.size() > 1) {
            setBackUpResponseFav(lista);
        } else {
            onEventListener.onEvent(EVENT_HIDE_LOADER, null);
            lytFavorites.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorService() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
        UI.createSimpleCustomDialog("", getString(R.string.no_internet_access), getActivity().getSupportFragmentManager(), getFragmentTag());
    }

    // OnItemClickRecyclerView
    @Override
    public void onClick(View v, final int position) {
        switch (v.getId()) {
            case R.id.lyt_favorite_item:
                if (calculateAmountPerPerson() >= 1) {
                    DataFavoritos fav = lstFavorites.get(position);
                    DtoRequestPayment dto = new DtoRequestPayment(fav.getIdFavorito(), fav.getNombre(), fav.getReferencia(), fav.getColorMarca(), fav.getImagenURL());
                    if (!lstRequestPayment.contains(dto)) {
                        // Delete add request item
                        if (lstRequestPayment.size() > 1) {
                            lstRequestPayment.remove(lstRequestPayment.size() - 1);
                        }
                        lstRequestPayment.add(dto);
                        notifyRequestContainer();
                    } else {
                        UI.showToastShort(App.getContext().getString(R.string.error_add_fav), getActivity());
                    }
                } else {
                    UI.createSimpleCustomDialog(getActivity().getString(R.string.title_error), getActivity().getString(R.string.txt_error_calculate_amount), getFragmentManager(), new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {
                        }

                        @Override
                        public void actionCancel(Object... params) {
                        }
                    }, true, false);
                }
                break;
            case R.id.row_request_payment:
                if (lstRequestPayment.get(position).getIdFavorite() != -1) {
                    // Edit Request
                    UI.createSimpleCustomDialog(getString(R.string.txt_title_edit_request), getString(R.string.txt_message_edit_request), getFragmentManager(), new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {
                            if (lstRequestPayment.size() > 2) {
                                lstRequestPayment.remove(position);
                                // Delete add request item
                                lstRequestPayment.remove(lstRequestPayment.size() - 1);
                            } else {
                                lstRequestPayment.clear();
                            }
                            notifyRequestContainer();
                        }

                        @Override
                        public void actionCancel(Object... params) {

                        }
                    }, true, true);
                } else {
                    // Add Request
                    if (calculateAmountPerPerson() >= 1) {
                        DialogAddRequestPayment dialogAddRequestPayment = new DialogAddRequestPayment();
                        dialogAddRequestPayment.setAddRequestPaymentListener(this);
                        dialogAddRequestPayment.show(getFragmentManager(), "Dialog Add Request Payment");
                    } else {
                        UI.createSimpleCustomDialog(getActivity().getString(R.string.title_error), getActivity().getString(R.string.txt_error_calculate_amount), getFragmentManager(), new DialogDoubleActions() {
                            @Override
                            public void actionConfirm(Object... params) {
                            }

                            @Override
                            public void actionCancel(Object... params) {
                            }
                        }, true, false);
                    }
                }
                break;
            default:
                break;
        }
    }

    // OnItemLongClickRecyclerView
    @Override
    public void onLongClick(View v, int position) {
    }

    @Override
    public void onAddNewRequest(DtoRequestPayment dtoRequestPayment) {
        // Delete add request item
        if (lstRequestPayment.size() > 1) {
            lstRequestPayment.remove(lstRequestPayment.size() - 1);
        }
        lstRequestPayment.add(dtoRequestPayment);
        notifyRequestContainer();
    }

    private void setBackUpResponseFav(List<DataFavoritos> mResponse) {
        lstFavorites = new ArrayList<>();
        for (DataFavoritos item : mResponse) {
            if (item.getReferencia().length() == 10) {
                lstFavorites.add(item);
            }
        }
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
        if (lstFavorites.size() > 0) {
            Collections.sort(lstFavorites, new Comparator<DataFavoritos>() {
                @Override
                public int compare(DataFavoritos o1, DataFavoritos o2) {
                    return o1.getNombre().compareToIgnoreCase(o2.getNombre());
                }
            });
            rcvFavorites.setAdapter(new FavoritesRequestPaymentAdapter(lstFavorites, this));
        } else {
            lytFavorites.setVisibility(View.GONE);
        }
    }

    /* Method to set all logic of request's */
    private void notifyRequestContainer() {
        if (lstRequestPayment.size() == 0) {
            lytAddUser.setVisibility(View.VISIBLE);
            rcvRequestPayment.setVisibility(View.GONE);
        } else {
            lytAddUser.setVisibility(View.GONE);
            rcvRequestPayment.setVisibility(View.VISIBLE);
            // Cargar adaptador
            rcvRequestPayment.setAdapter(new RequestPaymentVerticalAdapter(lstRequestPayment, this, monto));
            rcvRequestPayment.scrollToPosition(lstRequestPayment.size() - 1);
        }
    }

    /* Method to calculate the total amount per person */
    private float calculateAmountPerPerson() {
        if (lstRequestPayment.size() >= 1) {
            return monto / (lstRequestPayment.size());
        } else {
            return monto / (lstRequestPayment.size() + 1);
        }
    }
}