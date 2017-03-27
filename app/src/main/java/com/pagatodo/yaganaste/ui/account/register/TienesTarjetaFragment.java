package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.BindView;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class TienesTarjetaFragment extends GenericFragment implements View.OnClickListener{

    private View rootview;
    @BindView(R.id.radioHasCard)
    RadioGroup radioHasCard;
    @BindView(R.id.radioBtnYes)
    AppCompatRadioButton radioBtnYes;
    @BindView(R.id.radioBtnNo)
    AppCompatRadioButton radioBtnNo;
    @BindView(R.id.btnBackTienesTarjeta)
    Button btnBackTienesTarjeta;
    @BindView(R.id.btnNextTienesTarjeta)
    Button btnNextTienesTarjeta;

    public TienesTarjetaFragment() {
    }

    public static TienesTarjetaFragment newInstance() {
        TienesTarjetaFragment fragmentRegister = new TienesTarjetaFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;

        if (context instanceof Activity) {
            activity = (Activity) context;
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_tienes_tarjeta, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            default:
                break;
        }
    }

}

