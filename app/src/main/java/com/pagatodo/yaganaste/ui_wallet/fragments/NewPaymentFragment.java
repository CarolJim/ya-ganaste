package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.ui._controllers.manager.AddToFavoritesActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.EditFavoritesActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui_wallet.PaymentActivity;
import com.pagatodo.yaganaste.ui_wallet.SearchCarrierActivity;
import com.pagatodo.yaganaste.ui_wallet.adapters.AdapterPagosClass;
import com.pagatodo.yaganaste.ui_wallet.adapters.PaymentAdapterGV;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IPaymentAdapter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IPaymentFragment;
import com.pagatodo.yaganaste.ui_wallet.presenter.INewPaymentPresenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.NewPaymentPresenter;
import com.pagatodo.yaganaste.ui_wallet.views.DataFavoritosGridView;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.NewListDialog;
import com.pagatodo.yaganaste.utils.customviews.NewListFavoriteDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui_wallet.PaymentActivity.PAYMENT_DATA;
import static com.pagatodo.yaganaste.ui_wallet.PaymentActivity.PAYMENT_IS_FAV;
import static com.pagatodo.yaganaste.ui_wallet.SearchCarrierActivity.SEARCH_DATA;
import static com.pagatodo.yaganaste.ui_wallet.SearchCarrierActivity.SEARCH_IS_RELOAD;
import static com.pagatodo.yaganaste.utils.Constants.NEW_FAVORITE;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_RECARGAS;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_SERVICIOS;

/**
 * Frank Manzo 27-12-17
 * Nuevo fragmento que contiene la nueva vista de pagos
 */
public class NewPaymentFragment extends GenericFragment implements IPaymentFragment, IPaymentAdapter {

    @BindView(R.id.gvRecargas)
    GridView gvRecargas;
    @BindView(R.id.gvServicios)
    GridView gvServicios;
    @BindView(R.id.btnSwitch)
    SwitchCompat btnSwitch;
    @BindView(R.id.tvErrorRecargas)
    TextView errorRecargas;
    @BindView(R.id.tvErrorServicios)
    TextView errorServicios;
    @BindView(R.id.mRVRecargas)
    RecyclerView mRVRecargas;
    @BindView(R.id.mRVPagos)
    RecyclerView mRVPagos;

    private View rootview;
    private ArrayList myDataset;
    private ArrayList mRecargarGrid;
    private ArrayList mPagarGrid;
    private int typeView;

    // Constantes para operaciones en el Grid
    public static final int TYPE_CARRIER = 1;
    public static final int TYPE_FAVORITE = 2;
    public static final int TYPE_POSITION = -1;
    // Esta constante es para lograr obtener la posicion en el AdapterPagos de 0,1,2,n
    // Pero en Carriesrs no es necesario trabajar posiciones de 0 a N

    public static final int SEARCH_CARRIER_RECARGA = 1;
    public static final int SEARCH_CARRIER_PAGOS = 2;
    public static final int SEARCH_FAVORITO_RECARGA = 3;
    public static final int SEARCH_FAVORITO_PAGOS = 4;

    private ArrayList myDatasetAux;
    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    INewPaymentPresenter newPaymentPresenter;
    private List<ComercioResponse> mDataRecargar;
    private List<ComercioResponse> mDataPagar;
    private List<DataFavoritos> mDataRecargarFav;
    private List<DataFavoritos> mDataPagarFav;
    ArrayList<ArrayList<DataFavoritos>> mFullListaRecar;
    ArrayList<ArrayList<DataFavoritos>> mFullListaServ;

    public NewPaymentFragment() {
        // Required empty public constructor
    }

    /**
     * @return A new instance of fragment NewPaymentFragment.
     */
    public static NewPaymentFragment newInstance() {
        NewPaymentFragment fragment = new NewPaymentFragment();
        Bundle args = new Bundle();
        return fragment;
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
        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    boolean isOnline = Utils.isDeviceOnline();
                    if (isOnline) {
                        updateFavorites();
                    } else {
                        btnSwitch.setChecked(false);
                        createSimpleCustomDialog("", getResources().getString(R.string.no_internet_access), 0);
                    }
                } else {
                    updateCarriers();
                }
            }
        });
    }

    /**
     * Crea dialogos sencillos sin acciones, solo informativos
     *
     * @param mTittle
     * @param mMessage
     * @param mAction
     */
    private void createSimpleCustomDialog(String mTittle, String mMessage, int mAction) {
        UI.createSimpleCustomDialog(mTittle, mMessage,
                getActivity().getSupportFragmentManager(), getFragmentTag());
    }

    @Override
    public void onResume() {
        super.onResume();

        if (typeView == TYPE_CARRIER) {
            updateCarriers();
        } else {
            updateFavorites();
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
    }

    private void updateFavorites() {
        //  onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.synch_favorites));
        // onEventListener.onEvent("DISABLE_BACK", true);
        mRecargarGrid.clear();
        mPagarGrid.clear();
        newPaymentPresenter.getFavoritesItems(PAYMENT_RECARGAS);
        //newPaymentPresenter.getFavoritesItems(PAYMENT_SERVICIOS);
    }

    public void setCarouselData(List<ComercioResponse> comercios, int typeData) {
        // Ocultams el Loader siempre que tenemos el exito en la consulta en este paso
        //  onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        ////  onEventListener.onEvent("DISABLE_BACK", false);

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
                            mDataRecargar.get(x).getLogoURL()));
                }

                errorRecargas.setVisibility(View.GONE);
                gvRecargas.setAdapter(new PaymentAdapterGV(mRecargarGrid, this,
                        SEARCH_CARRIER_RECARGA, TYPE_CARRIER, TYPE_POSITION));
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
                            mDataPagar.get(x).getLogoURL()));
                }

                errorServicios.setVisibility(View.GONE);
                gvServicios.setAdapter(new PaymentAdapterGV(mPagarGrid, this,
                        SEARCH_CARRIER_PAGOS, TYPE_CARRIER, TYPE_POSITION));
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
    public void setDataFavorite(List<DataFavoritos> dataFavoritos, int typeDataFav) {

        /**
         * Mostramos los RV de favoritos y ocultamos los GV de Carriers
         */
        gvRecargas.setVisibility(View.GONE);
        gvServicios.setVisibility(View.GONE);
        mRVRecargas.setVisibility(View.VISIBLE);
        mRVPagos.setVisibility(View.VISIBLE);

        // Ocultams el Loader siempre que tenemos el exito en la consulta en este paso
        //onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        //onEventListener.onEvent("DISABLE_BACK", false);

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
            mDataRecargarFav = dataFavoritos;
            mDataRecargarFav = orderFavoritos(mDataRecargarFav, typeDataFav);

            AdapterPagosClass adapterPagosClass = new AdapterPagosClass(this, mDataRecargarFav,
                    mRVRecargas, gvRecargas);

            adapterPagosClass.createRecycler(SEARCH_FAVORITO_RECARGA, TYPE_FAVORITE);
            mFullListaRecar = adapterPagosClass.getmFullListaFav();

/*            // Creamos la lista que enviaremos al Grid con los datos del Recargas
                // TODO ELiminar despues de pruebas de estabilidad
            if (mDataRecargarFav.size() > 0) {
                *//**
             * Creamos llaveRecargas: Esto es para evitar que creemos mas de 8 iconos
             *//*
                int llaveRecargas = mDataRecargarFav.size() > 8 ? 8 : mDataRecargarFav.size();

                for (int x = 0; x < llaveRecargas; x++) {
                    mRecargarGrid.add(new DataFavoritosGridView(
                            mDataRecargarFav.get(x).getColorMarca(),
                            mDataRecargarFav.get(x).getNombre(),
                            mDataRecargarFav.get(x).getImagenURL()));
                }

                gvRecargas.setAdapter(new PaymentAdapterGV(mRecargarGrid, this,
                        SEARCH_FAVORITO_RECARGA, TYPE_FAVORITE));
                errorRecargas.setVisibility(View.GONE);
            } else {
                gvRecargas.setVisibility(View.GONE);
                errorRecargas.setVisibility(View.VISIBLE);
            }*/
        } else if (typeDataFav == 2) {
            mDataPagarFav = dataFavoritos;
            mDataPagarFav = orderFavoritos(mDataPagarFav, typeDataFav);

            AdapterPagosClass adapterPagosClass = new AdapterPagosClass(this, mDataPagarFav,
                    mRVPagos, gvRecargas);

            adapterPagosClass.createRecycler(SEARCH_FAVORITO_PAGOS, TYPE_FAVORITE);
            mFullListaServ = adapterPagosClass.getmFullListaFav();

            // Creamos la lista que enviaremos al Grid con los datos del Recargas
          /*
            // TODO ELiminar despues de pruebas de estabilidad
          if (mDataPagarFav.size() > 0) {
                *//**
             * Creamos llaveRecargas: Esto es para evitar que creemos mas de 8 iconos
             *//*
                int llaveServicios = mDataPagarFav.size() > 8 ? 8 : mDataPagarFav.size();

                for (int x = 0; x < llaveServicios; x++) {
                    mPagarGrid.add(new DataFavoritosGridView(
                            mDataPagarFav.get(x).getColorMarca(),
                            mDataPagarFav.get(x).getNombre(),
                            mDataPagarFav.get(x).getImagenURL()));
                }

                gvServicios.setAdapter(new PaymentAdapterGV(mPagarGrid, this,
                        SEARCH_FAVORITO_PAGOS, TYPE_FAVORITE, TYPE_POSITION));
                errorServicios.setVisibility(View.GONE);
            } else {
                gvServicios.setVisibility(View.GONE);
                errorServicios.setVisibility(View.VISIBLE);
            }*/
        }

        /**
         * Hacemos la peticion de favoritpos de Pagos, ahora que los favoritos de recargas estan listos
         */
        if (mDataPagarFav != null && mDataPagarFav.size() == 0) {
            // onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.synch_favorites));
            newPaymentPresenter.getFavoritesItems(PAYMENT_SERVICIOS);
            typeView = TYPE_FAVORITE;
        }
    }

    private List<ComercioResponse> orderCarriers(List<ComercioResponse> originalList, int typeData) {
        ArrayList<Integer> orderBy = new ArrayList<>();
        ArrayList<ComercioResponse> finalList = new ArrayList<>();

        // Agregamos la lupa solo si tenemos mas de 8 items en el servicio. En caso de 0 no agrega nada
        int lenghtArray = originalList.size();
        if (lenghtArray > 8 && lenghtArray != 0) {
            ComercioResponse itemLupa = new ComercioResponse();
            itemLupa.setLogoURL("R.mipmap.buscar_con_texto");
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

    private List<DataFavoritos> orderFavoritos(List<DataFavoritos> originalList, int typeDataFav) {
        ArrayList<DataFavoritos> finalList = new ArrayList<>();

        // Agregamos la lupa solo si tenemos mas de 8 items en el servicio. En caso de 0 no agrega nada
        int lenghtArray = originalList.size();
        if (lenghtArray < 9) {

            DataFavoritos itemAdd = new DataFavoritos(-2);
            itemAdd.setImagenURL("R.mipmap.ic_add_new_favorite");
            itemAdd.setNombre("Nuevo");
            finalList.add(itemAdd);

            // Agregamos nuestros elementos que van en la lista, estos tienen item valido
            for (int x = 0; x < lenghtArray; x++) {
                finalList.add(originalList.get(x));
            }
        }

        if (lenghtArray > 8 && lenghtArray != 0) {
            DataFavoritos itemAdd = new DataFavoritos(-2);
            itemAdd.setImagenURL("R.mipmap.ic_add_new_favorite");
            itemAdd.setNombre("Nuevo");
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

    @Override
    public void sendData(int position, int mType, int typePosition) {
        Intent intentPayment = new Intent(getActivity(), PaymentActivity.class);
        switch (mType) {
            case SEARCH_CARRIER_RECARGA:
                if (mDataRecargar != null) {
                    if (mDataRecargar.get(position).getNombreComercio().equals("Buscar")) {
                       // Iniciamos la actividad de busquedas, pasando el arreglo de mDataRecargar
                        Intent intent = new Intent(getActivity(), SearchCarrierActivity.class);
                        intent.putExtra(SEARCH_DATA, (Serializable) mDataRecargar);
                        intent.putExtra(SEARCH_IS_RELOAD, true);
                        startActivity(intent);
                    } else {
                        intentPayment.putExtra(PAYMENT_DATA, mDataRecargar.get(position));
                        intentPayment.putExtra(PAYMENT_IS_FAV, false);
                        startActivity(intentPayment);
                    }
                }
                break;
            case SEARCH_CARRIER_PAGOS:
                if (mDataPagar != null) {
                    if (mDataPagar.get(position).getNombreComercio().equals("Buscar")) {
                        // Iniciamos la actividad de busquedas, pasando el arreglo de mDataPagar
                        Intent intent = new Intent(getActivity(), SearchCarrierActivity.class);
                        intent.putExtra(SEARCH_DATA, (Serializable) mDataPagar);
                        intent.putExtra(SEARCH_IS_RELOAD, false);
                        startActivity(intent);
                    } else {
                        intentPayment.putExtra(PAYMENT_DATA, mDataPagar.get(position));
                        intentPayment.putExtra(PAYMENT_IS_FAV, false);
                        startActivity(intentPayment);
                    }
                }
                break;
            case SEARCH_FAVORITO_RECARGA:
                if (mFullListaRecar != null) {
                    if (mFullListaRecar.get(typePosition).get(position).getNombre().equals("Buscar")) {
                        NewListFavoriteDialog dialog = new NewListFavoriteDialog(getContext(), mDataRecargarFav,
                                newPaymentPresenter, mType);
                        dialog.show();
                    } else if (mFullListaRecar.get(typePosition).get(position).getNombre().equals("Nuevo")) {
                        // Iniciamos la actividad de Favoritos para recargas
                        Intent intent = new Intent(getContext(), AddToFavoritesActivity.class);
                        intent.putExtra(CURRENT_TAB_ID, PAYMENT_RECARGAS);
                        intent.putExtra(AddToFavoritesActivity.FAV_PROCESS, 2);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getActivity().startActivityForResult(intent, NEW_FAVORITE, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                        } else {
                            getActivity().startActivityForResult(intent, NEW_FAVORITE);
                        }
                    } else {
                        intentPayment.putExtra(PAYMENT_DATA, mFullListaRecar.get(typePosition).get(position));
                        intentPayment.putExtra(PAYMENT_IS_FAV, true);
                        startActivity(intentPayment);
                    }
                }
                break;
                /* // TODO eliminar despues de pruebas de estabilidad
                 if (mDataRecargarFav != null) {
                    if (mDataRecargarFav.get(position).getNombre().equals("Buscar")) {
                        NewListFavoriteDialog dialog = new NewListFavoriteDialog(getContext(), mDataRecargarFav,
                                newPaymentPresenter, mType);
                        dialog.show();
                    } else if (mDataRecargarFav.get(position).getNombre().equals("Nuevo")) {
                        // Iniciamos la actividad de Favoritos para recargas
                        Intent intent = new Intent(getContext(), AddToFavoritesActivity.class);
                        intent.putExtra(CURRENT_TAB_ID, PAYMENT_RECARGAS);
                        intent.putExtra(AddToFavoritesActivity.FAV_PROCESS, 2);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getActivity().startActivityForResult(intent, NEW_FAVORITE, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                        } else {
                            getActivity().startActivityForResult(intent, NEW_FAVORITE);
                        }
                    } else {
                        intentPayment.putExtra(PAYMENT_DATA, mDataRecargarFav.get(position));
                        intentPayment.putExtra(PAYMENT_IS_FAV, true);
                        startActivity(intentPayment);
                    }
                }
                break;*/
            case SEARCH_FAVORITO_PAGOS:
                if (mFullListaServ != null) {
                    if (mFullListaServ.get(typePosition).get(position).getNombre().equals("Buscar")) {
                        NewListFavoriteDialog dialog = new NewListFavoriteDialog(getContext(), mDataPagarFav,
                                newPaymentPresenter, mType);
                        dialog.show();
                    } else if (mFullListaServ.get(typePosition).get(position).getNombre().equals("Nuevo")) {
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
        Intent intentEditFav = new Intent(getActivity(), EditFavoritesActivity.class);
        switch (mType) {
            case SEARCH_FAVORITO_RECARGA:
                if (mFullListaRecar != null) {
                    if (!mFullListaRecar.get(typePosition).get(position).getNombreComercio().equals("Buscar")
                            && !mFullListaRecar.get(typePosition).get(position).getNombre().equals("Nuevo")) {
                        Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        v.vibrate(100);
                        intentEditFav.putExtra(getActivity().getString(R.string.favoritos_tag), mFullListaRecar.get(typePosition).get(position));
                        intentEditFav.putExtra(CURRENT_TAB_ID, mType);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(intentEditFav, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                        } else {
                            startActivity(intentEditFav);
                        }
                    }
                }
                break;
            case SEARCH_FAVORITO_PAGOS:
                if (mFullListaServ != null) {
                    if (!mFullListaServ.get(typePosition).get(position).getNombreComercio().equals("Buscar")
                            && !mFullListaServ.get(typePosition).get(position).getNombre().equals("Nuevo")) {
                        Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        v.vibrate(100);
                        intentEditFav.putExtra(getActivity().getString(R.string.favoritos_tag), mFullListaServ.get(typePosition).get(position));
                        intentEditFav.putExtra(CURRENT_TAB_ID, mType);
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

    public void sendCarrierToView(ComercioResponse mComercio, int mType) {
        Intent intentPayment = new Intent(getActivity(), PaymentActivity.class);
        intentPayment.putExtra(PAYMENT_DATA, mComercio);
        intentPayment.putExtra(PAYMENT_IS_FAV, false);
        startActivity(intentPayment);
    }

    @Override
    public void sendFavoriteToView(DataFavoritos dataFavoritos, int mType) {
        Intent intentPayment = new Intent(getActivity(), PaymentActivity.class);
        intentPayment.putExtra(PAYMENT_DATA, dataFavoritos);
        intentPayment.putExtra(PAYMENT_IS_FAV, true);
        startActivity(intentPayment);
    }

    @Override
    public void errorFail(DataSourceResult error) {
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        onEventListener.onEvent("DISABLE_BACK", false);

        btnSwitch.setChecked(false);
        updateCarriers();
        createSimpleCustomDialog("", error.getData().toString(), 0);
    }

    @Override
    public void errorService() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        onEventListener.onEvent("DISABLE_BACK", false);


        btnSwitch.setChecked(false);
        updateCarriers();
        createSimpleCustomDialog("", "Error en Consulta. Intente de Nuevo", 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        typeView = TYPE_CARRIER;
    }
}
