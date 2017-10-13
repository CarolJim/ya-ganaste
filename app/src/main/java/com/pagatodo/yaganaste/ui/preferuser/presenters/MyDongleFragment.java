package com.pagatodo.yaganaste.ui.preferuser.presenters;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Francisco Manzo  13/10/17
 */
public class MyDongleFragment extends GenericFragment {

    @BindView(R.id.txtCompanyName)
    StyleTextView txtCompanyName;
    @BindView(R.id.txtLastPayment)
    StyleTextView txtLastPayment;
    @BindView(R.id.txtNumberBattery)
    StyleTextView txtNumberBattery;
    @BindView(R.id.iconBattery)
    ImageView iconBattery;

    View rootview;
    public MyDongleFragment() {
        // Required empty public constructor
    }

    public static MyDongleFragment newInstance() {
        MyDongleFragment fragment = new MyDongleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
      //  return inflater.inflate(R.layout.fragment_my_dongle, container, false);
        rootview = inflater.inflate(R.layout.fragment_my_dongle, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        //  txtCompanyName txtLastPayment txtNumberBattery iconBattery
        setCompanyName("Floreria JessLou");
        setLastPayment("11 Oct 17, 14:24:00 hrs");
        setNumberBattery(75);
    }

    private void setNumberBattery(int mPorcentaje) {
        // Procesimiento para cambiar la imagen de manera dinamica, dependiendo del rango de carga

        if(mPorcentaje > 0 && mPorcentaje > 50){
            // Bateria Roja

            // iconBattery.setBackgroundResource(App.getContext().getDrawable(R.mipmap.ic_launcher));
        }else if(mPorcentaje > 50 && mPorcentaje > 75){
            // Bateria Amarilla

            // iconBattery.setBackgroundResource(App.getContext().getDrawable(R.mipmap.ic_launcher));
        }else if(mPorcentaje > 75){
            // Bateria Verde

            // iconBattery.setBackgroundResource(App.getContext().getDrawable(R.mipmap.ic_launcher));
        }
        txtNumberBattery.setText(mPorcentaje);
    }

    private void setLastPayment(String mDate) {
        // Proceso para convertir la fecha y obtgener el formato que necesitamos
        txtLastPayment.setText(mDate);
    }

    private void setCompanyName(String mName) {
        txtCompanyName.setText(mName);
    }
}
