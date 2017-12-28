package com.pagatodo.yaganaste.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Frank Manzo 27-12-17
 * Nuevo fragmento que contiene la nueva vista de pagos
 */
public class NewPaymentFragment extends GenericFragment implements IPaymentFragment {

    @BindView(R.id.gvPayment)
    GridView gvPayment;
    @BindView(R.id.gv2)
    GridView gv2;

    private View rootview;
    private ArrayList myDataset;

    public static final int TYPE_RELOAD = 1;
    public static final int TYPE_PAYMENT = 2;
    private ArrayList myDatasetAux;
    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    INewPaymentPresenter newPaymentPresenter;

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

        // Hacemos al consulta de favoritos
        // paymentsCarouselPresenter = new PaymentsCarouselPresenter(1, this, getContext(), true);
        // paymentsCarouselPresenter.getFavoriteCarouselItems();

        newPaymentPresenter = new NewPaymentPresenter(this);
        newPaymentPresenter.testToPresenter();

        // Array Auxiliar para operaciones de GridView
        myDatasetAux = new ArrayList();

        //initList1();
        initList10();
        myDatasetAux = orderList(myDataset);
        gvPayment.setAdapter(new PaymentAdapterRV(myDatasetAux, this, TYPE_RELOAD));

        //initList7();
        initList17();
        myDatasetAux = orderList(myDataset);
        gv2.setAdapter(new PaymentAdapterRV(myDatasetAux, this, TYPE_PAYMENT));
    }

    /**
     * Se encarga de acomodar el Dataset que enviaremos al Adapter. Siguiendo las reglas de 4 casos
     * @param originalData Contiene la lista del arreglo original que nos brindan los servicios o DB
     * @return
     */
    private ArrayList orderList(ArrayList<DataFavoritosGridView> originalData) {
        // Array auxiliar para cuando debemos de hacer muchos cambios de posiciones
        ArrayList myDatasetExtra = new ArrayList();

        int lenghtArray = originalData.size();
        if (lenghtArray == 0) {
            for (int x = 0; x < 10; x++) {
                originalData.add(new DataFavoritosGridView("Nuevo", R.drawable.add_photo_canvas));
            }
        }

        // 7 -> 0-6  10 -> 0-9
        if (lenghtArray > 0 && lenghtArray < 11) {
            int diference = 10 - lenghtArray;
            for (int x = 0; x < diference; x++) {
                originalData.add(new DataFavoritosGridView("Nuevo", R.drawable.add_photo_canvas));
            }
        }

        if(lenghtArray == 10){

        }

        if (lenghtArray > 11) {
           // myDatasetExtra
            myDatasetExtra.add(new DataFavoritosGridView("Buscar", R.drawable.places_ic_search));
            for (int x = 0; x < 8; x++) {
                myDatasetExtra.add(new DataFavoritosGridView(originalData.get(x).getmName(), originalData.get(x).getmDrawable()));
            }
            myDatasetExtra.add(new DataFavoritosGridView("Nuevo", R.drawable.add_photo_canvas));

            originalData.clear();
            originalData = myDatasetExtra;
        }
        return originalData;
    }

    private void initList1() {
        myDataset = new ArrayList();
    }

    private void initList7() {
        myDataset = new ArrayList();
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.visa));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.camera));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.arrow_canvas));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.mastercard_canvas));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.upload_canvas));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.arrow_canvas_blue));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.visa));
    }

    private void initList10() {
        myDataset = new ArrayList();
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.share_canvas_blue));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.visa));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.menu_canvas_blue));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.arrow_canvas));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.mastercard_canvas));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.upload_canvas));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.arrow_canvas_blue));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.visa));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.camera));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.camara_white_blue_canvas));
    }

    private void initList17() {
        myDataset = new ArrayList();
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.share_canvas_blue));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.visa));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.menu_canvas_blue));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.arrow_canvas));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.mastercard_canvas));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.upload_canvas));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.arrow_canvas_blue));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.visa));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.camera));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.camara_white_blue_canvas));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.share_canvas_blue));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.visa));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.menu_canvas_blue));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.arrow_canvas));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.mastercard_canvas));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.upload_canvas));
        myDataset.add(new DataFavoritosGridView("Titulo", R.drawable.arrow_canvas_blue));
    }

    @Override
    public void sendData(int position, int mType) {
        Toast.makeText(getContext(), "Pos: " + position + " mType " + mType, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void resToView() {
        Toast.makeText(getContext(), "MVP Listo", Toast.LENGTH_SHORT).show();
    }
}
