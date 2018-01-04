package com.pagatodo.yaganaste.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private View rootview;
    private ArrayList myDataset;
    private ArrayList mRecargarGrid;
    private ArrayList mPagarGrid;
    private int typeView = 0;

    public static final int TYPE_RELOAD = 1;
    public static final int TYPE_SERVICE = 2;
    public static final int TYPE_CARRIER = 1;
    public static final int TYPE_FAVORITE = 2;

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
                    Toast.makeText(App.getContext(), "ON", Toast.LENGTH_SHORT).show();
                    updateFavorites();
                } else {
                    Toast.makeText(App.getContext(), "OFF", Toast.LENGTH_SHORT).show();
                    updateCarriers();
                }
            }
        });
    }

    private void updateFavorites() {
        mRecargarGrid.clear();
        mPagarGrid.clear();
        newPaymentPresenter.getFavoritesItems(TYPE_RELOAD);
        // newPaymentPresenter.getFavoritesItems(TYPE_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();

        updateCarriers();
        //updateCarriers();
    }

    private void updateCarriers() {
        mRecargarGrid.clear();
        mPagarGrid.clear();
        newPaymentPresenter.getCarriersItems(TYPE_RELOAD);
        newPaymentPresenter.getCarriersItems(TYPE_SERVICE);
    }

    private void initList1() {
        myDataset = new ArrayList();
    }

    private void initList7() {
        myDataset = new ArrayList();
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.visa));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.camera));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.arrow_canvas));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.mastercard_canvas));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.upload_canvas));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.arrow_canvas_blue));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.visa));
    }

    private void initList10() {
        myDataset = new ArrayList();
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.share_canvas_blue));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.visa));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.menu_canvas_blue));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.arrow_canvas));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.mastercard_canvas));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.upload_canvas));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.arrow_canvas_blue));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.visa));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.camera));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.camara_white_blue_canvas));
    }

    private void initList17() {
        myDataset = new ArrayList();
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.share_canvas_blue));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.visa));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.menu_canvas_blue));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.arrow_canvas));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.mastercard_canvas));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.upload_canvas));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.arrow_canvas_blue));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.visa));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.camera));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.camara_white_blue_canvas));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.share_canvas_blue));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.visa));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.menu_canvas_blue));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.arrow_canvas));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.mastercard_canvas));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.upload_canvas));
//        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.arrow_canvas_blue));
    }

    @Override
    public void sendData(int position, int mType) {
        Toast.makeText(getContext(), "Pos: " + position + " mType " + mType, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void resToView() {
        Toast.makeText(getContext(), "MVP Listo", Toast.LENGTH_SHORT).show();
    }

    public void setCarouselData(List<ComercioResponse> comercios, int typeData) {
        int comerciosSise = comercios.size();
        mDataRecargar = new ArrayList<>();
        mDataPagar = new ArrayList<>();

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

                gvRecargas.setAdapter(new PaymentAdapterRV(mRecargarGrid, this, TYPE_RELOAD,
                        TYPE_CARRIER));
                // mRecargarGrid.clear();
            }else{
                // TODO Mostrar un mensaje cuando la lista Recargas llegar vacia del servicio
                Toast.makeText(App.getContext(), "Sin Items de Recargas", Toast.LENGTH_SHORT).show();
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

                gvServicios.setAdapter(new PaymentAdapterRV(mPagarGrid, this, TYPE_SERVICE,
                        TYPE_CARRIER));
                // mRecargarGrid.clear();
            }else{
                // TODO Mostrar un mensaje cuando la lista Servicios llegar vacia del servicio
                Toast.makeText(App.getContext(), "Sin Items de Recargas", Toast.LENGTH_SHORT).show();
            }
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

    @Override
    public void setDataFavorite(List<DataFavoritos> dataFavoritos, int typeDataFav) {
        int dataFavSize = dataFavoritos.size();
        mDataRecargar = new ArrayList<>();
        mDataPagar = new ArrayList<>();

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

                gvRecargas.setAdapter(new PaymentAdapterRV(mRecargarGrid, this, TYPE_RELOAD,
                        TYPE_FAVORITE));
                // mRecargarGrid.clear();
            }else{
                // TODO Mostrar un mensaje cuando la lista Recargas llegar vacia del servicio
                Toast.makeText(App.getContext(), "Sin Items de Recargas", Toast.LENGTH_SHORT).show();
            }
        } else if (typeDataFav == 2) {
            mDataPagarFav  = dataFavoritos;

            /**
             * Lineas de pruebas. Se eliminaran elementos para probar la logica
             mDataPagar.remove(9);
             mDataPagar.remove(8);
             mDataPagar.remove(7);
             */

            mDataPagarFav = orderFavoritos(mDataRecargarFav, typeDataFav);

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

                gvServicios.setAdapter(new PaymentAdapterRV(mPagarGrid, this, TYPE_SERVICE,
                        TYPE_FAVORITE));
                // mRecargarGrid.clear();
            }else{
                // TODO Mostrar un mensaje cuando la lista Servicios llegar vacia del servicio
                Toast.makeText(App.getContext(), "Sin Items de Recargas", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<DataFavoritos> orderFavoritos(List<DataFavoritos> originalList, int typeDataFav) {

        ArrayList<DataFavoritos> finalList = new ArrayList<>();

        // Agregamos la lupa solo si tenemos mas de 8 items en el servicio. En caso de 0 no agrega nada
        int lenghtArray = originalList.size();
        if (lenghtArray > 8 && lenghtArray != 0) {
            DataFavoritos itemLupa = new DataFavoritos(-1);
            itemLupa.setImagenURL("R.mipmap.buscar_con_texto");
            itemLupa.setNombre("Buscar");
            finalList.add(itemLupa);
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
