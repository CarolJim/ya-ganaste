package com.pagatodo.yaganaste.modules.register.SeleccionaCP;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.RegisterUserNew;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.interfaces.IAccountRegisterView;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.register.adapters.ColoniasArrayAdapter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeleccionaCPFragment extends GenericFragment implements View.OnClickListener , IAccountRegisterView<Object> ,IOnSpinnerClick {

public  static RegActivity activityf;

    private List<ColoniasResponse> listaColonias;
    private String estadoDomicilio = "";

    private ColoniasArrayAdapter adapterColonia;
    private List<String> coloniasNombre;

    View rootView;
    private AccountPresenterNew accountPresenter;
    @BindView(R.id.btnNextDataBusiness)
    StyleButton btnNextDataBusiness;
    @BindView(R.id.sub_titulo_datos_cp_usuario)
    StyleTextView sub_titulo_datos_cp_usuario;
/*    @BindView(R.id.editState)
    CustomValidationEditText editState;*/
    @BindView(R.id.spcolonia)
    StyleTextView spcolonia;

    @BindView(R.id.spColonia)
    Spinner spColonia;
    private boolean cpDefault;

String street,numExterior,interiorNumber,codigoPostal;


    public SeleccionaCPFragment() {
        // Required empty public constructor
    }

    public  static  SeleccionaCPFragment newInstance(RegActivity activity){
       activityf=activity;
        return new SeleccionaCPFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountPresenter = new AccountPresenterNew(getActivity());
        accountPresenter.setIView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_selecciona_c, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        setData();
        coloniasNombre = new ArrayList<String>();
        coloniasNombre.add(getString(R.string.colonia));
        adapterColonia = new ColoniasArrayAdapter(getContext(), R.layout.spinner_layout, coloniasNombre, this);
        spColonia.setAdapter(adapterColonia);

        accountPresenter.getNeighborhoods(codigoPostal);//Buscamos por CP
        btnNextDataBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityf.showFragmentDatosNegocio();
            }
        });

    }

    private void setData() {
        RegisterUserNew registerUser = RegisterUserNew.getInstance();
        street =registerUser.getCalle();
        numExterior= registerUser.getNumExterior();
        interiorNumber=registerUser.getNumInterior();
        codigoPostal =registerUser.getCodigoPostal();
        if (interiorNumber.isEmpty()) {
            sub_titulo_datos_cp_usuario.setText(street + " #" + numExterior + " C.P. "+codigoPostal);
        }else {
            sub_titulo_datos_cp_usuario.setText(street + " #" + numExterior + "\nInterior "+interiorNumber+ " C.P. "+codigoPostal);
        }

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void clientCreatedSuccess(String message) {

    }

    @Override
    public void clientCreateFailed(String error) {

    }

    @Override
    public void zipCodeInvalid(String message) {

    }

    @Override
    public void setNeighborhoodsAvaliables(List<ColoniasResponse> listaColonias) {
        hideLoader();
        UI.hideKeyBoard(getActivity());
        this.listaColonias = listaColonias;
        this.estadoDomicilio = listaColonias.get(0).getEstado();
        fillAdapter();
    }

    private void fillAdapter() {
        coloniasNombre.clear();
        coloniasNombre.add(getString(R.string.colonia));
        for (ColoniasResponse coloniasResponse : this.listaColonias) {
            coloniasNombre.add(coloniasResponse.getColonia());
        }
        adapterColonia.notifyDataSetChanged();
        String estado =this.estadoDomicilio;


    }

    @Override
    public void setCurrentAddress(DataObtenerDomicilio domicilio) {

    }

    @Override
    public void nextScreen(String event, Object data) {

    }

    @Override
    public void backScreen(String event, Object data) {

    }

    @Override
    public void showLoader(String message) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void showError(Object error) {

    }

    @Override
    public void onSpinnerClick() {

    }

    @Override
    public void onSubSpinnerClick() {

    }

    @Override
    public void hideKeyBoard() {

    }
}
