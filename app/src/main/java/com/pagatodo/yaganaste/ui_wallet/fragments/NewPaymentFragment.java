package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui_wallet.PaymentActivity;
import com.pagatodo.yaganaste.ui_wallet.SearchCarrierActivity;
import com.pagatodo.yaganaste.ui_wallet.adapters.AdapterPagosClass;
import com.pagatodo.yaganaste.ui_wallet.adapters.PaymentAdapterGV;
import com.pagatodo.yaganaste.ui_wallet.interfaces.INewPaymentPresenter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IPaymentAdapter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IPaymentFragment;
import com.pagatodo.yaganaste.ui_wallet.presenter.NewPaymentPresenter;
import com.pagatodo.yaganaste.ui_wallet.views.DataFavoritosGridView;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomRadioButton;
import com.pagatodo.yaganaste.utils.customviews.NewListFavoriteDialog;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity.FAVORITE_PROCESS;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui_wallet.PaymentActivity.PAYMENT_DATA;
import static com.pagatodo.yaganaste.ui_wallet.PaymentActivity.PAYMENT_IS_FAV;
import static com.pagatodo.yaganaste.ui_wallet.SearchCarrierActivity.SEARCH_DATA;
import static com.pagatodo.yaganaste.ui_wallet.SearchCarrierActivity.SEARCH_IS_RELOAD;
import static com.pagatodo.yaganaste.utils.Constants.EDIT_FAVORITE;
import static com.pagatodo.yaganaste.utils.Constants.NEW_FAVORITE_FROM_CERO;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_RECARGAS;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_SERVICIOS;

/**
 * Frank Manzo 27-12-17
 * Nuevo fragmento que contiene la nueva vista de pagos
 */
public class NewPaymentFragment extends GenericFragment implements IPaymentFragment, IPaymentAdapter {


    @BindView(R.id.favoritosContenedor)
    LinearLayout favoritosContenedor;


    @BindView(R.id.gvRecargas)
    GridView gvRecargas;
    @BindView(R.id.gvServicios)
    GridView gvServicios;
    @BindView(R.id.btnSwitch)
    SwitchCompat btnSwitch;
    @BindView(R.id.newpayment_recarga_tv)
    StyleTextView recargaTittle;
    @BindView(R.id.newpayment_pds_tv)
    StyleTextView pdsTittle;
    @BindView(R.id.radiobutton_no)
    CustomRadioButton radiobutton_no;
    @BindView(R.id.radiobutton_si)
    CustomRadioButton radiobutton_si;
    @BindView(R.id.tvErrorRecargas)
    TextView errorRecargas;
    @BindView(R.id.tvErrorServicios)
    TextView errorServicios;
    @BindView(R.id.mRVRecargas)
    RecyclerView mRVRecargas;
    @BindView(R.id.mRVPagos)
    RecyclerView mRVPagos;
    @BindView(R.id.fragment_newpayment_editar_textview)
    TextView tvEditarFav;
    @BindView(R.id.newpayment_pagar_editar_tv)
    TextView tvPDSEditFav;

    private View rootview;
    private ArrayList myDataset;
    private ArrayList mRecargarGrid;
    private ArrayList mPagarGrid;
    private int typeView;
    private boolean isEditable = false;
    private boolean isPDSEditable = false;

    // Constantes para operaciones en el Grid
    public static final int TYPE_CARRIER = 1;
    public static final int TYPE_FAVORITE = 2;
    public static final int TYPE_POSITION = -1;
    // Esta constante es para lograr obtener la posicion en el AdapterPagos de 0,1,2,n
    // Pero en Carriesrs no es necesario trabajar posiciones de 0 a N

    public static final int ITEM_CARRIER_RECARGA = 1;
    public static final int ITEM_CARRIER_PAGOS = 2;
    public static final int ITEM_FAVORITO_RECARGA = 3;
    public static final int ITEM_FAVORITO_PAGOS = 4;

    private ArrayList myDatasetAux;
    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    INewPaymentPresenter newPaymentPresenter;
    private List<Comercio> mDataRecargar;
    private List<Comercio> mDataPagar;
    private List<Favoritos> mDataRecargarFav;
    private List<Favoritos> mDataPagarFav;
    ArrayList<ArrayList<Favoritos>> mFullListaRecar;
    ArrayList<ArrayList<Favoritos>> mFullListaServ;
    private AdapterPagosClass adapterPagosClass;
    private AdapterPagosClass adapterPDSClass;

    public static NewPaymentFragment newInstance() {
        return new NewPaymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_new_payment, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        if (SingletonUser.getInstance().getDataUser().getUsuario().getRoles().get(0).getIdRol() == 129) {
            favoritosContenedor.setVisibility(View.GONE);
        }

        // Array Auxiliar para operaciones de GridView
        mRecargarGrid = new ArrayList();
        mPagarGrid = new ArrayList();
        myDatasetAux = new ArrayList();

        mDataRecargar = new ArrayList<>();
        mDataPagar = new ArrayList<>();
        mDataRecargarFav = new ArrayList<>();
        mDataPagarFav = new ArrayList<>();

        gvRecargas.setVerticalScrollBarEnabled(false);
        gvServicios.setVerticalScrollBarEnabled(false);

        // Hacemos al consulta de favoritos
        // paymentsCarouselPresenter = new PaymentsCarouselPresenter(1, this, getContext(), true);
        // paymentsCarouselPresenter.getFavoriteCarouselItems();
        newPaymentPresenter = new NewPaymentPresenter(this, App.getContext());

        typeView = TYPE_CARRIER;
        radiobutton_si.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    boolean isOnline = Utils.isDeviceOnline();
                    if (isOnline) {
                        updateFavorites();
                    } else {
                        //btnSwitch.setChecked(false);
                        radiobutton_no.setChecked(true);
                        createSimpleCustomDialog("", getResources().getString(R.string.no_internet_access));
                    }
                }
            }
        });

        radiobutton_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    updateCarriers();
                }
            }
        });

        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    boolean isOnline = Utils.isDeviceOnline();
                    if (isOnline) {
                        updateFavorites();
                    } else {
                        btnSwitch.setChecked(false);
                        createSimpleCustomDialog("", getResources().getString(R.string.no_internet_access));
                    }
                } else {
                    updateCarriers();
                }
            }
        });
    }

    // Agregamos Listener para hacer Click en editar para recargas
    @OnClick(R.id.fragment_newpayment_editar_textview)
    public void submit(View view) {
        if (adapterPagosClass.getmFullListaFav().get(0).size() > 1) {
            if (isEditable) {
                tvEditarFav.setTextColor(getResources().getColor(R.color.texthint));
                isEditable = false;
                adapterPagosClass.createRecycler(ITEM_FAVORITO_RECARGA, 1);
            } else {
                tvEditarFav.setTextColor(getResources().getColor(R.color.colorTituloDialog));
                isEditable = true;
                adapterPagosClass.createRecycler(ITEM_FAVORITO_RECARGA, 2);
            }
        }
    }

    // Agregamos Listener para hacer Click en editar para Pagar
    @OnClick(R.id.newpayment_pagar_editar_tv)
    public void editarPDS(View view) {
        if (adapterPDSClass.getmFullListaFav().get(0).size() > 1) {
            if (isPDSEditable) {
                tvPDSEditFav.setTextColor(getResources().getColor(R.color.texthint));
                isPDSEditable = false;
                adapterPDSClass.createRecycler(ITEM_FAVORITO_PAGOS, 1);
            } else {
                tvPDSEditFav.setTextColor(getResources().getColor(R.color.colorTituloDialog));
                isPDSEditable = true;
                adapterPDSClass.createRecycler(ITEM_FAVORITO_PAGOS, 2);
            }
        }

    }

    /**
     * Crea dialogos sencillos sin acciones, solo informativos
     *  @param mTittle
     * @param mMessage
     */
    private void createSimpleCustomDialog(String mTittle, String mMessage) {
        //UI.createSimpleCustomDialog(mTittle, mMessage, getActivity().getSupportFragmentManager(), getFragmentTag());

        UI.showAlertDialog(getContext(), mTittle, mMessage, R.string.title_aceptar, (dialogInterface, i) -> {
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (typeView == TYPE_CARRIER) {
            updateCarriers();
        } else {
            updateFavorites();
            // Regresamos los estados de editar favoritos a false
            tvEditarFav.setTextColor(getResources().getColor(R.color.texthint));
            isEditable = false;
            tvPDSEditFav.setTextColor(getResources().getColor(R.color.texthint));
            isPDSEditable = false;
        }
        //UI.hideKeyBoard(getActivity());
    }

    private void updateCarriers() {
        // Reiniciamos las listas de datos y los grid
        mDataRecargarFav.clear();
        mDataPagarFav.clear();
        mRecargarGrid.clear();
        mPagarGrid.clear();
        // Reiniciamos el Switch a false
        btnSwitch.setChecked(false);
        radiobutton_no.setChecked(true);

        /**
         * Ocultamos los RV de favoritos y mostramos los GV de Carriers
         */
        gvRecargas.setVisibility(View.VISIBLE);
        gvServicios.setVisibility(View.VISIBLE);
        mRVRecargas.setVisibility(View.GONE);
        mRVPagos.setVisibility(View.GONE);

        newPaymentPresenter.getCarriersItems(PAYMENT_RECARGAS);
        newPaymentPresenter.getCarriersItems(PAYMENT_SERVICIOS);
        typeView = TYPE_CARRIER;

        // Ocultamos el boton para editar favoritos
        tvEditarFav.setVisibility(View.GONE);
        tvPDSEditFav.setVisibility(View.GONE);

        // Cambiamos los titulos de las CardView
        recargaTittle.setText("" + getResources().getString(R.string.btn_recharge_txt));
        pdsTittle.setText("" + getResources().getString(R.string.btn_payment_txt));
    }

    private void updateFavorites() {
        onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.synch_favorites));
        onEventListener.onEvent("DISABLE_BACK", true);
        mRecargarGrid.clear();
        mPagarGrid.clear();
        mDataRecargarFav.clear();
        mDataPagarFav.clear();
        newPaymentPresenter.getFavoritesItems(PAYMENT_RECARGAS);
        //newPaymentPresenter.getFavoritesItems(PAYMENT_SERVICIOS);

        // Mostramos el boton para editar favoritos
        tvEditarFav.setVisibility(View.VISIBLE);
        tvPDSEditFav.setVisibility(View.VISIBLE);

        // Cambiamos los titulos de las CardView
        recargaTittle.setText("" + getResources().getString(R.string.btn_recharge_favorites_txt));
        pdsTittle.setText("" + getResources().getString(R.string.btn_payment_favorites_txt));
    }

    public void setCarouselData(List<Comercio> comercios, int typeData) {
        // Ocultams el Loader siempre que tenemos el exito en la consulta en este paso
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        onEventListener.onEvent("DISABLE_BACK", false);

        /**
         * Guardamos siempre la lista de comercios en su Array especifico. Estos array contiene
         * la informacion que enviaremos al momento de hacer un pago. Es importante siempre tenerlos
         * integros y de acceso
         */
        if (typeData == 1) {
            /**
             * Ordenamos la lista como en el orden correcto de Carriers
             * - Recargas
             * - Servicios
             */
            mDataRecargar = comercios;

            mDataRecargar = orderCarriers(mDataRecargar, typeData);

            // Creamos la lista que enviaremos al Grid con los datos del Recargas
            if (mDataRecargar.size() > 0) {
                /**
                 * Creamos llaveRecargas: Esto es para evitar que creemos mas de 8 iconos
                 */
                int llaveRecargas = mDataRecargar.size() > 8 ? 8 : mDataRecargar.size();

                for (int x = 0; x < llaveRecargas; x++) {
                    mRecargarGrid.add(new DataFavoritosGridView(
                            mDataRecargar.get(x).getColorMarca(),
                            mDataRecargar.get(x).getNombreComercio(),
                            mDataRecargar.get(x).getLogoURLColor()));
                }

                errorRecargas.setVisibility(View.GONE);
                gvRecargas.setAdapter(new PaymentAdapterGV(mRecargarGrid, this,
                        ITEM_CARRIER_RECARGA, TYPE_CARRIER, TYPE_POSITION));
                onEventListener.onEvent(EVENT_HIDE_LOADER, "");
                onEventListener.onEvent("DISABLE_BACK", false);
                // mRecargarGrid.clear();
            } else {
                gvRecargas.setVisibility(View.GONE);
                errorRecargas.setVisibility(View.VISIBLE);
            }
        } else if (typeData == 2) {

            mDataPagar = comercios;
            mDataPagar = orderCarriers(mDataPagar, typeData);

            // Creamos la lista que enviaremos al Grid con los datos del Recargas
            if (mDataPagar.size() > 0) {
                /**
                 * Creamos llaveRecargas: Esto es para evitar que creemos mas de 8 iconos
                 */
                int llaveServicios = mDataPagar.size() > 8 ? 8 : mDataPagar.size();

                for (int x = 0; x < llaveServicios; x++) {
                    mPagarGrid.add(new DataFavoritosGridView(
                            mDataPagar.get(x).getColorMarca(),
                            mDataPagar.get(x).getNombreComercio(),
                            mDataPagar.get(x).getLogoURLColor()));
                }

                errorServicios.setVisibility(View.GONE);
                gvServicios.setAdapter(new PaymentAdapterGV(mPagarGrid, this,
                        ITEM_CARRIER_PAGOS, TYPE_CARRIER, TYPE_POSITION));
                onEventListener.onEvent(EVENT_HIDE_LOADER, "");
                onEventListener.onEvent("DISABLE_BACK", false);
                // mRecargarGrid.clear();
            } else {
                gvServicios.setVisibility(View.GONE);
                errorServicios.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void setDataFavorite(List<Favoritos> favoritos, int typeDataFav) {

        /**
         * Mostramos los RV de favoritos y ocultamos los GV de Carriers
         */
        gvRecargas.setVisibility(View.GONE);
        gvServicios.setVisibility(View.GONE);
        mRVRecargas.setVisibility(View.VISIBLE);
        mRVPagos.setVisibility(View.VISIBLE);

        // Ocultams el Loader siempre que tenemos el exito en la consulta en este paso
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        onEventListener.onEvent("DISABLE_BACK", false);

        /**
         * Guardamos siempre la lista de comercios en su Array especifico. Estos array contiene
         * la informacion que enviaremos al momento de hacer un pago. Es importante siempre tenerlos
         * integros y de acceso
         */
        if (typeDataFav == 1) {
            /**
             * Ordenamos la lista como en el orden correcto de Carriers
             * - Recargas
             * - Servicios
             */
            mDataRecargarFav = favoritos;
            mDataRecargarFav = orderFavoritos(mDataRecargarFav, typeDataFav);

            adapterPagosClass = new AdapterPagosClass(this, mDataRecargarFav,
                    mRVRecargas, gvRecargas);

            adapterPagosClass.createRecycler(ITEM_FAVORITO_RECARGA, 1);
            mFullListaRecar = adapterPagosClass.getmFullListaFav();

        } else if (typeDataFav == 2) {

            mDataPagarFav = favoritos;
            mDataPagarFav = orderFavoritos(mDataPagarFav, typeDataFav);

            adapterPDSClass = new AdapterPagosClass(this, mDataPagarFav,
                    mRVPagos, gvRecargas);

            adapterPDSClass.createRecycler(ITEM_FAVORITO_PAGOS, 1);
            mFullListaServ = adapterPDSClass.getmFullListaFav();

        }

        /**
         * Hacemos la peticion de favoritpos de Pagos, ahora que los favoritos de recargas estan listos
         */
        if (mDataPagarFav != null && mDataPagarFav.size() == 0) {
            onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.synch_favorites));
            newPaymentPresenter.getFavoritesItems(PAYMENT_SERVICIOS);
            typeView = TYPE_FAVORITE;
        }
    }

    private List<Comercio> orderCarriers(List<Comercio> originalList, int typeData) {
        ArrayList<Integer> orderBy = new ArrayList<>();
        ArrayList<Comercio> finalList = new ArrayList<>();

        // Agregamos la lupa solo si tenemos mas de 8 items en el servicio. En caso de 0 no agrega nada
        int lenghtArray = originalList.size();
        if (lenghtArray > 8 && lenghtArray != 0) {
            Comercio itemLupa = new Comercio();
            itemLupa.setLogoURLColor("R.mipmap.buscar_con_texto");
            itemLupa.setNombreComercio("Buscar");
            finalList.add(itemLupa);
        }

        switch (typeData) {
            case 1:
                orderBy.add(18);
                orderBy.add(7);
                orderBy.add(12);
                orderBy.add(8);
                orderBy.add(22);
                orderBy.add(13);
                orderBy.add(28);
                orderBy.add(23);
                orderBy.add(26);
                break;
            case 2:
                orderBy.add(4);
                orderBy.add(21);
                orderBy.add(20);
                orderBy.add(6);
                orderBy.add(3);
                orderBy.add(17);
                orderBy.add(25);
                orderBy.add(27);
                orderBy.add(2);
                break;
        }

        /**
         * Buscamos en nuestro orderBy cada elemento en un ciclo adicional de originalList, si el ID existe
         * lo agregamos a nuesta finalList. Y eliminamos ese elemnto de originalList
         */
        for (Integer miList : orderBy) {
            for (int x = 0; x < originalList.size(); x++) {
                if (originalList.get(x).getIdComercio() == miList) {
                    finalList.add(originalList.get(x));
                    originalList.remove(x);
                }
            }
        }

        /**
         * Terminado el proceso anterior, tomamos el resto de la originalList y lo agregamos a nuestra
         * finalList
         */
        for (int x = 0; x < originalList.size(); x++) {
            finalList.add(originalList.get(x));
        }

        return finalList;
    }

    private List<Favoritos> orderFavoritos(List<Favoritos> originalList, int typeDataFav) {
        ArrayList<Favoritos> finalList = new ArrayList<>();

        // Agregamos la lupa solo si tenemos mas de 8 items en el servicio. En caso de 0 no agrega nada
        int lenghtArray = originalList.size();
        if (lenghtArray < 9) {

            Favoritos itemAdd = new Favoritos(-2);
            itemAdd.setImagenURL("R.mipmap.ic_add_new_favorite");
            itemAdd.setNombre(App.getContext().getResources().getString(R.string.btn_req_payment_2_txt));
            finalList.add(itemAdd);

            // Agregamos nuestros elementos que van en la lista, estos tienen item valido
            for (int x = 0; x < lenghtArray; x++) {
                finalList.add(originalList.get(x));
            }
        }

        if (lenghtArray > 8 && lenghtArray != 0) {
            Favoritos itemAdd = new Favoritos(-2);
            itemAdd.setImagenURL("R.mipmap.ic_add_new_favorite");
            itemAdd.setNombre(App.getContext().getResources().getString(R.string.btn_req_payment_2_txt));
            finalList.add(itemAdd);

            // Agregamos los elementos de Favortios en las posiciones 1-6. 0 = Lupa 7=Agregar
            for (int x = 0; x < 6; x++) {
                finalList.add(originalList.get(x));
            }


            /**
             * Terminado el proceso anterior, tomamos el resto de la originalList y lo agregamos a nuestra
             * finalList
             */
            if (lenghtArray > 6) {

                for (int x = 6; x < originalList.size(); x++) {
                    finalList.add(originalList.get(x));
                }
            }
        }

        return finalList;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void sendData(int position, int mType, int typePosition) {
        Intent intentPayment = new Intent(getActivity(), PaymentActivity.class);
        switch (mType) {
            case ITEM_CARRIER_RECARGA:
                if (mDataRecargar != null) {
                    if (mDataRecargar.get(position).getNombreComercio().equals("Buscar")) {
                        // Iniciamos la actividad de busquedas, pasando el arreglo de mDataRecargar
                        Intent intent = new Intent(getActivity(), SearchCarrierActivity.class);
                        intent.putExtra(SEARCH_DATA, (Serializable) mDataRecargar);
                        intent.putExtra(SEARCH_IS_RELOAD, true);
                        intent.putExtra(CURRENT_TAB_ID, PAYMENT_RECARGAS);
                        startActivity(intent);
                    } else {
                        intentPayment.putExtra(PAYMENT_DATA, mDataRecargar.get(position));
                        intentPayment.putExtra(PAYMENT_IS_FAV, false);
                        startActivity(intentPayment);
                    }
                }
                break;
            case ITEM_CARRIER_PAGOS:
                if (mDataPagar != null) {
                    if (mDataPagar.get(position).getNombreComercio().equals("Buscar")) {
                        // Iniciamos la actividad de busquedas, pasando el arreglo de mDataPagar
                        Intent intent = new Intent(getActivity(), SearchCarrierActivity.class);
                        intent.putExtra(SEARCH_DATA, (Serializable) mDataPagar);
                        intent.putExtra(SEARCH_IS_RELOAD, false);
                        intent.putExtra(CURRENT_TAB_ID, PAYMENT_SERVICIOS);
                        startActivity(intent);
                    } else {
                        intentPayment.putExtra(PAYMENT_DATA, mDataPagar.get(position));
                        intentPayment.putExtra(PAYMENT_IS_FAV, false);
                        startActivity(intentPayment);
                    }
                }
                break;
            case ITEM_FAVORITO_RECARGA:
                if (mFullListaRecar != null) {
                    if (mFullListaRecar.get(typePosition).get(position).getNombre().equals("Buscar")) {
                        NewListFavoriteDialog dialog = new NewListFavoriteDialog(getContext(), mDataRecargarFav,
                                newPaymentPresenter, mType);
                        dialog.show();
                    } else if (mFullListaRecar.get(typePosition).get(position).getNombre().equals(
                            App.getContext().getResources().getString(R.string.btn_req_payment_2_txt))) {
                        // Iniciamos la actividad de Favoritos para recargas
                        //  Intent intent = new Intent(getContext(), AddToFavoritesActivity.class);
                        Intent intent = new Intent(getContext(), FavoritesActivity.class);
                        intent.putExtra(CURRENT_TAB_ID, PAYMENT_RECARGAS);
                        intent.putExtra(FAVORITE_PROCESS, NEW_FAVORITE_FROM_CERO);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getActivity().startActivityForResult(intent, NEW_FAVORITE_FROM_CERO, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                        } else {
                            getActivity().startActivityForResult(intent, NEW_FAVORITE_FROM_CERO);
                        }
                    } else {
                        intentPayment.putExtra(PAYMENT_DATA, mFullListaRecar.get(typePosition).get(position));
                        intentPayment.putExtra(PAYMENT_IS_FAV, true);
                        startActivity(intentPayment);
                    }
                }
                break;

            case ITEM_FAVORITO_PAGOS:
                if (mFullListaServ != null) {
                    if (mFullListaServ.get(typePosition).get(position).getNombre().equals("Buscar")) {
                        NewListFavoriteDialog dialog = new NewListFavoriteDialog(getContext(), mDataPagarFav,
                                newPaymentPresenter, mType);
                        dialog.show();
                    } else if (mFullListaServ.get(typePosition).get(position).getNombre().equals("Agregar")) {
                        // Iniciamos la actividad de Favoritos
                        Intent intent = new Intent(getContext(), FavoritesActivity.class);
                        intent.putExtra(CURRENT_TAB_ID, PAYMENT_SERVICIOS);
                        intent.putExtra(FAVORITE_PROCESS, NEW_FAVORITE_FROM_CERO);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getActivity().startActivityForResult(intent, NEW_FAVORITE_FROM_CERO, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                        } else {
                            getActivity().startActivityForResult(intent, NEW_FAVORITE_FROM_CERO);
                        }
                    } else {
                        intentPayment.putExtra(PAYMENT_DATA, mFullListaServ.get(typePosition).get(position));
                        intentPayment.putExtra(PAYMENT_IS_FAV, true);
                        startActivity(intentPayment);
                    }
                }
                break;
                /*
                // TODO eliminar despues de pruebas de estabilidad
                if (mDataPagarFav != null) {
                    if (mDataPagarFav.get(position).getNombre().equals("Buscar")) {
                        NewListFavoriteDialog dialog = new NewListFavoriteDialog(getContext(), mDataPagarFav,
                                newPaymentPresenter, mType);
                        dialog.show();
                    } else if (mDataPagarFav.get(position).getNombre().equals("Nuevo")) {
                        // Iniciamos la actividad de Favoritos
                        Intent intent = new Intent(getContext(), AddToFavoritesActivity.class);
                        intent.putExtra(CURRENT_TAB_ID, PAYMENT_SERVICIOS);
                        intent.putExtra(AddToFavoritesActivity.FAV_PROCESS, 2);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getActivity().startActivityForResult(intent, NEW_FAVORITE, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                        } else {
                            getActivity().startActivityForResult(intent, NEW_FAVORITE);
                        }
                    } else {
                        intentPayment.putExtra(PAYMENT_DATA, mDataPagarFav.get(position));
                        intentPayment.putExtra(PAYMENT_IS_FAV, true);
                        startActivity(intentPayment);
                    }
                }
                break;*/
            default:
                break;
        }

    }

    @Override
    public void editFavorite(int position, int mType, int typePosition) {
        //Intent intentEditFav = new Intent(getActivity(), EditFavoritesActivity.class);
        Intent intentEditFav = new Intent(getContext(), FavoritesActivity.class);
        switch (mType) {
            case ITEM_FAVORITO_RECARGA:
                if (mFullListaRecar != null) {
                    if (!mFullListaRecar.get(typePosition).get(position).getNombreComercio().equals("Buscar")
                            && !mFullListaRecar.get(typePosition).get(position).getNombre().equals("Nuevo")) {
                        Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        v.vibrate(100);
                        intentEditFav.putExtra(getActivity().getString(R.string.favoritos_tag), mFullListaRecar.get(typePosition).get(position));
                        intentEditFav.putExtra(CURRENT_TAB_ID, ITEM_CARRIER_RECARGA);
                        intentEditFav.putExtra(FAVORITE_PROCESS, EDIT_FAVORITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(intentEditFav, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                        } else {
                            startActivity(intentEditFav);
                        }
                    }
                }
                break;
            case ITEM_FAVORITO_PAGOS:
                if (mFullListaServ != null) {
                    if (!mFullListaServ.get(typePosition).get(position).getNombreComercio().equals("Buscar")
                            && !mFullListaServ.get(typePosition).get(position).getNombre().equals("Nuevo")) {
                        Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        v.vibrate(100);
                        intentEditFav.putExtra(getActivity().getString(R.string.favoritos_tag), mFullListaServ.get(typePosition).get(position));
                        intentEditFav.putExtra(CURRENT_TAB_ID, ITEM_CARRIER_PAGOS);
                        intentEditFav.putExtra(FAVORITE_PROCESS, EDIT_FAVORITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(intentEditFav, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                        } else {
                            startActivity(intentEditFav);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    public void sendCarrierToView(Comercio mComercio, int mType) {
        Intent intentPayment = new Intent(getActivity(), PaymentActivity.class);
        intentPayment.putExtra(PAYMENT_DATA, mComercio);
        intentPayment.putExtra(PAYMENT_IS_FAV, false);
        startActivity(intentPayment);
    }

    @Override
    public void sendFavoriteToView(Favoritos favoritos, int mType) {
        Intent intentPayment = new Intent(getActivity(), PaymentActivity.class);
        intentPayment.putExtra(PAYMENT_DATA, favoritos);
        intentPayment.putExtra(PAYMENT_IS_FAV, true);
        startActivity(intentPayment);
    }

    @Override
    public void errorFail(DataSourceResult error) {
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        onEventListener.onEvent("DISABLE_BACK", false);

        btnSwitch.setChecked(false);
        radiobutton_no.setChecked(true);
        updateCarriers();
        createSimpleCustomDialog("", error.getData().toString());
    }

    @Override
    public void errorService() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        onEventListener.onEvent("DISABLE_BACK", false);


        btnSwitch.setChecked(false);
        radiobutton_no.setChecked(true);
        updateCarriers();
        createSimpleCustomDialog("", "Error en Consulta. Intente de Nuevo");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        typeView = TYPE_CARRIER;
    }


}
