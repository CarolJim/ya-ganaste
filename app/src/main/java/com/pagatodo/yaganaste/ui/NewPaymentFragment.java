package com.pagatodo.yaganaste.ui;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.ui._controllers.manager.AddToFavoritesActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.utils.customviews.NewListDialog;
import com.pagatodo.yaganaste.utils.customviews.NewListFavoriteDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.NEW_FAVORITE;

/**
 * Frank Manzo 27-12-17
 * Nuevo fragmento que contiene la nueva vista de pagos
 */
public class NewPaymentFragment extends GenericFragment implements IPaymentFragment {

    @BindView(R.id.gvRecargas)
    GridView gvRecargas;
    @BindView(R.id.gvServicios)
    GridView gvServicios;
    @BindView(R.id.btnSwitch)
    Switch btnSwitch;
    @BindView(R.id.tvErrorRecargas)
    TextView errorRecargas;
    @BindView(R.id.tvErrorServicios)
    TextView errorServicios;

    private View rootview;
    private ArrayList myDataset;
    private ArrayList mRecargarGrid;
    private ArrayList mPagarGrid;
    private int typeView = 0;

    // Constantes para el Presenter
    public static final int TYPE_RELOAD = 1;
    public static final int TYPE_SERVICE = 2;
    // Constantes para operaciones en el Grid
    public static final int TYPE_CARRIER = 1;
    public static final int TYPE_FAVORITE = 2;

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

        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSwitch.isChecked()) {
                    updateFavorites();
                } else {
                    updateCarriers();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        updateCarriers();
    }

    private void updateCarriers() {
        // Reiniciamos las listas de datos y los grid
        mDataRecargarFav.clear();
        mDataPagarFav.clear();
        mRecargarGrid.clear();
        mPagarGrid.clear();

        // Reiniciamos el Switch a false
        btnSwitch.setChecked(false);

        newPaymentPresenter.getCarriersItems(TYPE_RELOAD);
        newPaymentPresenter.getCarriersItems(TYPE_SERVICE);
    }

    private void updateFavorites() {
        onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.synch_favorites));
        mRecargarGrid.clear();
        mPagarGrid.clear();
        newPaymentPresenter.getFavoritesItems(TYPE_RELOAD);
        //   newPaymentPresenter.getFavoritesItems(TYPE_SERVICE);
    }

    public void setCarouselData(List<ComercioResponse> comercios, int typeData) {
        // Ocultams el Loader siempre que tenemos el exito en la consulta en este paso
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");

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
            /**
             * Lineas de pruebas. Se eliminaran elementos para probar la logica
             Para probar lista sin lupa y menos de 8 elementos
             mDataRecargar.remove(9);
             mDataRecargar.remove(8);
             mDataRecargar.remove(7);
             mDataRecargar.remove(6);

             Para probar lista de 8
             mDataRecargar.remove(9);
             mDataRecargar.remove(8);

             Para probar lista vacia
             mDataRecargar.remove(9);
             mDataRecargar.remove(8);
             mDataRecargar.remove(7);
             mDataRecargar.remove(6);
             mDataRecargar.remove(5);
             mDataRecargar.remove(4);
             mDataRecargar.remove(3);
             mDataRecargar.remove(2);
             mDataRecargar.remove(1);
             mDataRecargar.remove(0);
             */


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
                gvRecargas.setAdapter(new PaymentAdapterRV(mRecargarGrid, this,
                        SEARCH_CARRIER_RECARGA, TYPE_CARRIER));
                onEventListener.onEvent(EVENT_HIDE_LOADER, "");
                // mRecargarGrid.clear();
            } else {
                gvRecargas.setVisibility(View.GONE);
                errorRecargas.setVisibility(View.VISIBLE);
            }
        } else if (typeData == 2) {

            mDataPagar = comercios;

            /**
             * Lineas de pruebas. Se eliminaran elementos para probar la logica
             mDataPagar.remove(9);
             mDataPagar.remove(8);
             mDataPagar.remove(7);
             */

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
                gvServicios.setAdapter(new PaymentAdapterRV(mPagarGrid, this,
                        SEARCH_CARRIER_PAGOS, TYPE_CARRIER));
                onEventListener.onEvent(EVENT_HIDE_LOADER, "");
                // mRecargarGrid.clear();
            } else {
                gvServicios.setVisibility(View.GONE);
                errorServicios.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void setDataFavorite(List<DataFavoritos> dataFavoritos, int typeDataFav) {
        // Ocultams el Loader siempre que tenemos el exito en la consulta en este paso
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");

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
            /**
             * Lineas de pruebas. Se eliminaran elementos para probar la logica
             Para probar lista sin lupa y menos de 8 elementos
             mDataRecargar.remove(9);
             mDataRecargar.remove(8);
             mDataRecargar.remove(7);
             mDataRecargar.remove(6);

             Para probar lista de 8
             mDataRecargar.remove(9);
             mDataRecargar.remove(8);

             Para probar lista vacia
             mDataRecargar.remove(9);
             mDataRecargar.remove(8);
             mDataRecargar.remove(7);
             mDataRecargar.remove(6);
             mDataRecargar.remove(5);
             mDataRecargar.remove(4);
             mDataRecargar.remove(3);
             mDataRecargar.remove(2);
             mDataRecargar.remove(1);
             mDataRecargar.remove(0);
             */

            mDataRecargarFav = orderFavoritos(mDataRecargarFav, typeDataFav);

            // Creamos la lista que enviaremos al Grid con los datos del Recargas
            if (mDataRecargarFav.size() > 0) {
                /**
                 * Creamos llaveRecargas: Esto es para evitar que creemos mas de 8 iconos
                 */
                int llaveRecargas = mDataRecargarFav.size() > 8 ? 8 : mDataRecargarFav.size();

                for (int x = 0; x < llaveRecargas; x++) {
                    mRecargarGrid.add(new DataFavoritosGridView(
                            mDataRecargarFav.get(x).getColorMarca(),
                            mDataRecargarFav.get(x).getNombre(),
                            mDataRecargarFav.get(x).getImagenURL()));
                    /*
                            mDataRecargar.get(x).getColorMarca(),
                            mDataRecargar.get(x).getNombreComercio(),
                            mDataRecargar.get(x).getLogoURL()));*/
                }

                gvRecargas.setAdapter(new PaymentAdapterRV(mRecargarGrid, this,
                        SEARCH_FAVORITO_RECARGA, TYPE_FAVORITE));
                errorRecargas.setVisibility(View.GONE);
            } else {
                gvRecargas.setVisibility(View.GONE);
                errorRecargas.setVisibility(View.VISIBLE);
            }
        } else if (typeDataFav == 2) {
            mDataPagarFav = dataFavoritos;
            //mDataPagarFav.clear();
            /**
             * Lineas de pruebas. Se eliminaran elementos para probar la logica
             mDataPagar.remove(9);
             mDataPagar.remove(8);
             mDataPagar.remove(7);
             */

            mDataPagarFav = orderFavoritos(mDataPagarFav, typeDataFav);

            // Creamos la lista que enviaremos al Grid con los datos del Recargas
            if (mDataPagarFav.size() > 0) {
                /**
                 * Creamos llaveRecargas: Esto es para evitar que creemos mas de 8 iconos
                 */
                int llaveServicios = mDataPagarFav.size() > 8 ? 8 : mDataPagarFav.size();

                for (int x = 0; x < llaveServicios; x++) {
                    mPagarGrid.add(new DataFavoritosGridView(
                            mDataPagarFav.get(x).getColorMarca(),
                            mDataPagarFav.get(x).getNombre(),
                            mDataPagarFav.get(x).getImagenURL()));
                }

                gvServicios.setAdapter(new PaymentAdapterRV(mPagarGrid, this,
                        SEARCH_FAVORITO_PAGOS, TYPE_FAVORITE));
                errorServicios.setVisibility(View.GONE);
            } else {
                gvServicios.setVisibility(View.GONE);
                errorServicios.setVisibility(View.VISIBLE);
            }
        }

        /**
         * Hacemos la peticion de favoritpos de Pagos, ahora que los favoritos de recargas estan listos
         */
        if (mDataPagarFav != null && mDataPagarFav.size() == 0) {
            onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.synch_favorites));
            newPaymentPresenter.getFavoritesItems(TYPE_SERVICE);
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

            // Agregamos nuestros elementos que van en la lista, estos tienen item valido
            for (int x = 0; x < lenghtArray; x++) {
                finalList.add(originalList.get(x));
            }

            DataFavoritos itemAdd = new DataFavoritos(-2);
            itemAdd.setImagenURL("R.mipmap.ic_add_new_favorite");
            itemAdd.setNombre("Nuevo");
            finalList.add(itemAdd);

            // CODIGO ALTERNATIVO para mostrar posiciones en las partes vacias de favoritos
            // Eliminar para version definitiva
            // Agregamos los elementos de Add a las posiciones que restan de la lista
          /*  int numAddItem = 8 - lenghtArray;
            for (int x = 0; x < numAddItem; x++) {
                DataFavoritos itemAdd = new DataFavoritos(-2);
                itemAdd.setImagenURL("R.mipmap.ic_add_new_favorite");
                itemAdd.setNombre("Nuevo");
                finalList.add(itemAdd);
            }*/
        }

        if (lenghtArray > 8 && lenghtArray != 0) {
            DataFavoritos itemLupa = new DataFavoritos(-1);
            itemLupa.setImagenURL("R.mipmap.buscar_con_texto");
            itemLupa.setNombre("Buscar");
            finalList.add(itemLupa);

            // Agregamos los elementos de Favortios en las posiciones 1-6. 0 = Lupa 7=Agregar
            for (int x = 0; x < 6; x++) {
                finalList.add(originalList.get(x));
            }

            DataFavoritos itemAdd = new DataFavoritos(-2);
            itemAdd.setImagenURL("R.mipmap.ic_add_new_favorite");
            itemAdd.setNombre("Nuevo");
            finalList.add(itemAdd);

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

        /**
         * Codigo de prueba para verificar el nombre
         */
        finalList.get(1).setImagenURL("");
        finalList.get(2).setImagenURL("");
        finalList.get(2).setNombre("Francisco");
        return finalList;
    }

    @Override
    public void sendData(String position, int mType) {
        if (position.equals("Buscar") && mType == SEARCH_CARRIER_RECARGA) {
            NewListDialog dialog = new NewListDialog(getContext(), mDataRecargar, newPaymentPresenter,
                    mType);
            dialog.show();
        } else if (position.equals("Buscar") && mType == SEARCH_CARRIER_PAGOS) {
            NewListDialog dialog = new NewListDialog(getContext(), mDataPagar, newPaymentPresenter,
                    mType);
            dialog.show();
        } else if (position.equals("Buscar") && mType == SEARCH_FAVORITO_RECARGA) {
            NewListFavoriteDialog dialog = new NewListFavoriteDialog(getContext(), mDataRecargarFav,
                    newPaymentPresenter, mType);
            dialog.show();
        } else if (position.equals("Buscar") && mType == SEARCH_FAVORITO_PAGOS) {
            NewListFavoriteDialog dialog = new NewListFavoriteDialog(getContext(), mDataPagarFav,
                    newPaymentPresenter, mType);
            dialog.show();
        } else if (position.equals("Nuevo") && mType == SEARCH_FAVORITO_RECARGA) {
           // Iniciamos la actividad de Favoritos para recargas
            Intent intent = new Intent(getContext(), AddToFavoritesActivity.class);
            intent.putExtra(CURRENT_TAB_ID, TYPE_RELOAD);
            intent.putExtra(AddToFavoritesActivity.FAV_PROCESS, 2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getActivity().startActivityForResult(intent, NEW_FAVORITE, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            } else {
                getActivity().startActivityForResult(intent, NEW_FAVORITE);
            }
        } else if (position.equals("Nuevo") && mType == SEARCH_FAVORITO_PAGOS) {
           // Iniciamos la actividad de Favoritos
            Intent intent = new Intent(getContext(), AddToFavoritesActivity.class);
            intent.putExtra(CURRENT_TAB_ID, TYPE_SERVICE);
            intent.putExtra(AddToFavoritesActivity.FAV_PROCESS, 2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getActivity().startActivityForResult(intent, NEW_FAVORITE, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            } else {
                getActivity().startActivityForResult(intent, NEW_FAVORITE);
            }
        }
    }

    public void sendCarrierToView(ComercioResponse mComercio, int mType) {
        Toast.makeText(App.getContext(), mComercio.getNombreComercio(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendFavoriteToView(DataFavoritos dataFavoritos, int mType) {
        Toast.makeText(App.getContext(), dataFavoritos.getNombre(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorFail(DataSourceResult error) {
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
    }

    @Override
    public void errorService() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
    }





    /**
     * Se encarga de acomodar el Dataset que enviaremos al Adapter. Siguiendo las reglas de 4 casos
     *
     * @param originalData Contiene la lista del arreglo original que nos brindan los servicios o DB
     * @return
     */
    private ArrayList orderList(ArrayList<DataFavoritosGridView> originalData) {
        // Array auxiliar para cuando debemos de hacer muchos cambios de posiciones
        ArrayList myDatasetExtra = new ArrayList();

        int lenghtArray = originalData.size();
        if (lenghtArray == 0) {
            for (int x = 0; x < 10; x++) {
                originalData.add(new DataFavoritosGridView(mDataRecargar.get(x).getColorMarca(),
                        "Nuevo", "R.drawable.add_photo_canvas"));
            }
        }

        // 7 -> 0-6  10 -> 0-9
        if (lenghtArray > 0 && lenghtArray < 11) {
            int diference = 10 - lenghtArray;
            for (int x = 0; x < diference; x++) {
                originalData.add(new DataFavoritosGridView(mDataRecargar.get(x).getColorMarca(),
                        "Nuevo", "R.drawable.add_photo_canvas"));
            }
        }

        if (lenghtArray == 10) {

        }

        if (lenghtArray > 11) {
            // myDatasetExtra
            myDatasetExtra.add(new DataFavoritosGridView("#888888", "Buscar", "R.drawable.places_ic_search"));
            for (int x = 0; x < 8; x++) {
                myDatasetExtra.add(new DataFavoritosGridView(mDataRecargar.get(x).getColorMarca(), originalData.get(x).getName(), originalData.get(x).getUrlLogo()));
            }
            myDatasetExtra.add(new DataFavoritosGridView("#888888", "Nuevo", "R.drawable.add_photo_canvas"));

            originalData.clear();
            originalData = myDatasetExtra;
        }
        return originalData;
    }

    private ArrayList orderListCarriers(ArrayList<DataFavoritosGridView> originalData) {
        // Array auxiliar para cuando debemos de hacer muchos cambios de posiciones
        ArrayList myDatasetExtra = new ArrayList();


        // TODO Simular el camino del Carrier cuando viene vacio, menor a 10, solo 10 o mas
        int lenghtArray = originalData.size();
     /*   if (lenghtArray == 0) {
            for (int x = 0; x < 10; x++) {
                originalData.add(new DataFavoritosGridView(mDataRecargar.get(x).getColorMarca(), "Nuevo", "R.drawable.add_photo_canvas"));
            }
        }

        // 7 -> 0-6  10 -> 0-9
        if (lenghtArray > 0 && lenghtArray < 11) {
            int diference = 10 - lenghtArray;
            for (int x = 0; x < diference; x++) {
                originalData.add(new DataFavoritosGridView(mDataRecargar.get(x).getColorMarca(), "Nuevo", "R.drawable.add_photo_canvas"));
            }
        }

        if (lenghtArray == 10) {

        }*/

        if (lenghtArray > 7) {
            // myDatasetExtra
            myDatasetExtra.add(new DataFavoritosGridView("#888888", "Buscar", "R.drawable.places_ic_search"));
            for (int x = 0; x < 7; x++) {
                myDatasetExtra.add(new DataFavoritosGridView(mDataRecargar.get(x).getColorMarca(), originalData.get(x).getName(), originalData.get(x).getUrlLogo()));
            }
            // myDatasetExtra.add(new DataFavoritosGridView("#888888", "Nuevo", "R.drawable.add_photo_canvas"));

            originalData.clear();
            originalData = myDatasetExtra;
        }
        return originalData;
    }

}
