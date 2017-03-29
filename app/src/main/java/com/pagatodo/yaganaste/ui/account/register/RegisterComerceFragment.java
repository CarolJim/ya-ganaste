package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.GiroComercio;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.adapters.GiroArrayAdapter;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.interfaces.enums.GiroAdapterType.GIRO;
import static com.pagatodo.yaganaste.interfaces.enums.GiroAdapterType.SUBGIRO;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class RegisterComerceFragment extends GenericFragment implements View.OnClickListener{

    private View rootview;

    @BindView(R.id.edtxtRegisterCommerceName)
    StyleEdittext edtxtRegisterCommerceName;
    @BindView(R.id.edtxtRegisterCommerceRSocial)
    StyleEdittext edtxtRegisterCommerceRSocial;
    @BindView(R.id.edtxtRegisterCommerceNumber)
    StyleEdittext edtxtRegisterCommerceNumber;
    @BindView(R.id.spinnerRegisterCommerceGiro)
    AppCompatSpinner spinnerRegisterCommerceGiro;
    @BindView(R.id.spinnerRegisterCommerceSubGiro)
    AppCompatSpinner spinnerRegisterCommerceSubGiro;
    @BindView(R.id.edtxtRegisterCommerceRFC)
    StyleEdittext edtxtRegisterCommerceRFC;
    @BindView(R.id.edtxtRegisterCommerceRFCHomoclave)
    StyleEdittext edtxtRegisterCommerceRFCHomoclave;
    @BindView(R.id.btnRegisterCommerce)
    StyleButton btnRegisterCommerce;
    @BindView(R.id.txtRegisterCommerceMail)
    StyleTextView txtRegisterCommerceMail;

    private List<GiroComercio> girosComercioComplete;
    private List<GiroComercio> girosComercio;
    private List<GiroComercio> subGirosComercio;
    private GiroArrayAdapter giroArrayAdapter;
    private GiroArrayAdapter subGiroArrayAdapter;
    private int giroSelected;
    private GiroComercio giroComercioItem;

    public RegisterComerceFragment() {
    }

    public static RegisterComerceFragment newInstance() {
        RegisterComerceFragment fragmentRegister = new RegisterComerceFragment();
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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

        rootview = inflater.inflate(R.layout.fragment_register_commerce, container, false);
        initValues();
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnRegisterCommerce.setOnClickListener(this);
        giroArrayAdapter = new GiroArrayAdapter(getActivity(),R.layout.spinner_layout, girosComercio, GIRO);
        spinnerRegisterCommerceGiro.setAdapter(giroArrayAdapter);
        spinnerRegisterCommerceGiro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                giroSelected = giroArrayAdapter.getGiroId(position);
                generateSubgirosArray(giroSelected);
                subGiroArrayAdapter = new GiroArrayAdapter(getActivity(), R.layout.spinner_layout, subGirosComercio, SUBGIRO);
                subGiroArrayAdapter.notifyDataSetChanged();
                spinnerRegisterCommerceSubGiro.setAdapter(subGiroArrayAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerRegisterCommerceSubGiro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                giroComercioItem = subGiroArrayAdapter.getItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initValues() {
        girosComercioComplete = Utils.getGirosArray(getActivity());
        girosComercio = new ArrayList<>();
        subGirosComercio = new ArrayList<>();
        ArrayList<Integer> mGirosIds = new ArrayList<>();
        for (GiroComercio giroComercio : girosComercioComplete) {
            if (!mGirosIds.contains(giroComercio.getIdGiro())) {
                mGirosIds.add(giroComercio.getIdGiro());
                girosComercio.add(giroComercio);
            }
        }
    }

    private void generateSubgirosArray(int idGiroSelected) {
        subGirosComercio.clear();
        for (GiroComercio giroComercio : girosComercioComplete) {
            if (giroComercio.getIdGiro() == idGiroSelected) {
                subGirosComercio.add(giroComercio);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegisterCommerce:

                break;
            default:
                break;
        }
    }


}

