package com.pagatodo.yaganaste.modules.register.DatosNegocio;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Giros;
import com.pagatodo.yaganaste.data.model.SubGiro;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.modules.register.DatosPersonales.RegistroDatosPersonalesFragment;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.adapters.BussinesLineSpinnerAdapter;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.ui.account.register.adapters.SubBussinesLineSpinnerAdapter;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DatosNegocioFragment;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoCountry;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoGiro;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoStates;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoSubGiro;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatosNegocioEAFragment extends GenericFragment implements IOnSpinnerClick {
    private List<DtoGiro> giros= new ArrayList<>();
    private List<Giros> girosComercio;
    private BussinesLineSpinnerAdapter giroArrayAdapter;
    @BindView(R.id.spinnerBussineLine)
    Spinner spinnerBussineLine;
    @BindView(R.id.textgiro)
    StyleTextView textgiro;
    @BindView(R.id.editBussinesName)
    EditText editBussinesName;
    @BindView(R.id.txtgiro)
    LinearLayout txtgiro;

    private static RegActivity activityf;
    View rootView;
    public  static DatosNegocioEAFragment newInstance(RegActivity activity){
        activityf=activity;
        return new DatosNegocioEAFragment();
    }

    public DatosNegocioEAFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseReference ref = App.getDatabaseReference().child("Mexico").child("AppEngine").child("CatComponents");
        ref.child("BusActivities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    giros = new ArrayList<>();
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        DtoGiro item = singleSnapshot.getValue(DtoGiro.class);
                        giros.add(item);
                    }
                    if (giros.size()>0)
                    setAdapter();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        activityf.nextStep();
        rootView.findViewById(R.id.btnNextDatosUsuario).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityf.getRouter().showPhysicalCode();
            }
        });

        spinnerBussineLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                onSpinnerClick();
                if (position != 0) {
                    textgiro.setVisibility(View.VISIBLE);
                    textgiro.setTextColor(getResources().getColor(R.color.colorAccent));
                }else
                    textgiro.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                onSpinnerClick();
            }


        });

    }

    private void setAdapter() {
        girosComercio= new ArrayList<Giros>();
        girosComercio.clear();

        Giros giroHint = new Giros();
        giroHint.setGiro(getString(R.string.giro_commerce_spinner));
        giroHint.setIdGiro(-1);
        List<SubGiro> subgiroHint = new ArrayList<SubGiro>();
        subgiroHint.add(new SubGiro(-1, getString(R.string.subgiro_commerce)));
        giroHint.setListaSubgiros(subgiroHint);
        girosComercio.add(giroHint);

        for ( DtoGiro giro : giros){
            List<SubGiro> subGiros = new ArrayList<SubGiro>();
            for (DtoSubGiro subGiro : giro.Subgiros){
                subGiros.add(new SubGiro((subGiro.ID_Subgiro),subGiro.Valor));
            }
            girosComercio.add(new Giros((giro.ID_Giro),giro.Valor,subGiros));
        }
        giroArrayAdapter = new BussinesLineSpinnerAdapter(getActivity(),
                R.layout.spinner_layout, girosComercio, this);
        spinnerBussineLine.setAdapter(giroArrayAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_datos_negocio_ea, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void onSpinnerClick() {
        editBussinesName.clearFocus();
        spinnerBussineLine.requestFocus();
        txtgiro.setBackgroundResource(R.drawable.inputtext_normal);
    }

    @Override
    public void onSubSpinnerClick() {

    }

    @Override
    public void hideKeyBoard() {

    }
}
