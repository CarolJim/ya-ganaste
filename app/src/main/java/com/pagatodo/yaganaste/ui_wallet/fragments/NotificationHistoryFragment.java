package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataListaNotificationArray;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ListaNotificationResponse;
import com.pagatodo.yaganaste.interfaces.INotificationHistory;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.RecyclerGenericBase;
import com.pagatodo.yaganaste.ui_wallet.interactors.UserNotificationInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IUserNotificationPresenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.UserNotificationPresenter;
import com.pagatodo.yaganaste.ui_wallet.views.GenericDummyData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui_wallet.adapters.RecyclerGenericBase.VERTICAL_ORIENTATION;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationHistoryFragment extends GenericFragment implements INotificationHistory {

    @BindView(R.id.rv_notification)
    RecyclerView rv_notification;
    ArrayList<GenericDummyData> myDataset;
    IUserNotificationPresenter mPresenter;
    ArrayList<DataListaNotificationArray> mDataset;
    ArrayList<DataListaNotificationArray> mDatasetNext;

    private View rootview;
    private RecyclerGenericBase recyclerGenericBase;

    public NotificationHistoryFragment() {
        // Required empty public constructor
    }

    public static NotificationHistoryFragment newInstance() {
        NotificationHistoryFragment fragment = new NotificationHistoryFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_notification_history, container, false);

        /**
         * Creamos nuestra instancia del presenter y hacemos la peticion del primer paquete de datos
         */
        mPresenter = new UserNotificationPresenter(this, new UserNotificationInteractor());
        mPresenter.getFirstDataToPresenter();

        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        // createDummyData();

    }

    private void createDummyData() {
        myDataset = new ArrayList<>();
        myDataset.add(new GenericDummyData("¡Nueva promoción disponible!",
                "El Tigre Toño te regala $20 por comprar productos Zucaritas, Valido al 31 de Diciembre de 2017",
                R.mipmap.ic_launcher, "2018:02:12 14:00", 1));
        myDataset.add(new GenericDummyData("¡Santiago te envió $150", "Tu dinero ya se encuentra en tu cuenta Ya Ganaste",
                R.drawable.cancel_canvas, "2018:02:10 15:00", 1));
        myDataset.add(new GenericDummyData("Paulina solicitó $300",
                "Te faltan solo $200 para completar “Fiesta de Cumpleaños",
                R.drawable.icono_camara_servicios, "2018:02:08 11:00", 1));
        myDataset.add(new GenericDummyData("¡Estar por llegar a tu meta!", "Te faltan solo $200 para completar “Fiesta de Cumpleaños",
                R.mipmap.ic_launcher, "2018:02:05 14:00", 2));
        myDataset.add(new GenericDummyData("Ganaste $20", "Tu recompensa ya esta disponible en tu cuenta Ya Ganaste",
                R.mipmap.ic_launcher, "2018:01:31 14:00", 2));
        myDataset.add(new GenericDummyData("Ganaste $2000", "Super Varo para tus Chelas con Tecate",
                R.mipmap.ic_launcher, "2018:01:15 14:00", 2));
    }

    /**
     * Obtenemos un exito al pedir la 1era parte de los atos a mostrar en pantalla
     *
     * @param dataSourceResult
     */
    @Override
    public void onSuccessFirstData(DataSourceResult dataSourceResult) {
        mDataset = new ArrayList<>();
        mDataset = (ArrayList<DataListaNotificationArray>) ((ListaNotificationResponse) dataSourceResult.getData()).getNotificaciones();

        /**
         * Creasmos una instancia de nuestra clase que crea toda la funcionadad del RV
         */
        recyclerGenericBase = new RecyclerGenericBase(rv_notification, VERTICAL_ORIENTATION);
        recyclerGenericBase.createRecyclerList(this, mDataset);
    }

    /**
     * Obtenemos un exito al pedir el siguiente bloque de notificaciones. Enviamos al controlador del
     * RecyclerView. RecyclerGenericBase
     * @param dataSourceResult
     */
    @Override
    public void onSuccessNextData(DataSourceResult dataSourceResult) {
        mDatasetNext = new ArrayList<>();
        mDatasetNext = (ArrayList<DataListaNotificationArray>) ((ListaNotificationResponse) dataSourceResult.getData()).getNotificaciones();

        recyclerGenericBase.loadNextData(mDatasetNext);
    }

    /**
     * Manejamos cualquier tipo de error al consultar alguna parte de los datos
     * @param error
     */
    @Override
    public void onErrorListNotif(DataSourceResult error) {

    }

    /**
     * Pedimos al servicio el siguiente bloque de datos, con respecto al ultimo elemento de la lista
     * anterior
     * @param idNotificacion
     */
    @Override
    public void loadNextDataToView(int idNotificacion) {
        mPresenter.getNextDataToPresenter(idNotificacion);
    }

}
