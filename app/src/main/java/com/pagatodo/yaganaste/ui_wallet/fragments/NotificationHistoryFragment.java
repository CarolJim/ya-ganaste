package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.INotificationSideChannel;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.INotificationHistory;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.RecyclerGenericBase;
import com.pagatodo.yaganaste.ui_wallet.interactors.UserNotificationInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IUserNotificationInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IUserNotificationPresenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.UserNotificationPresenter;
import com.pagatodo.yaganaste.ui_wallet.views.GenericDummyData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationHistoryFragment extends GenericFragment implements INotificationHistory {

    @BindView(R.id.rv_notification)
    RecyclerView rv_notification;
    ArrayList<GenericDummyData> myDataset;
    IUserNotificationPresenter mPresenter;

    private View rootview;

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


        mPresenter = new UserNotificationPresenter(this, new UserNotificationInteractor());
        mPresenter.createTest();

        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        createDummyData();
        RecyclerGenericBase recyclerGenericBase = new RecyclerGenericBase(rv_notification, 1);
        recyclerGenericBase.createRecycler(this, myDataset);


    }

    private void createDummyData() {
        myDataset = new ArrayList<>();
        myDataset.add(new GenericDummyData("¡Nueva promoción disponible!",
                "El Tigre Toño te regala $20 por comprar productos Zucaritas, Valido al 31 de Diciembre de 2017",
                R.drawable.carrousel1, "2018:02:12 14:00", 1));
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
}
