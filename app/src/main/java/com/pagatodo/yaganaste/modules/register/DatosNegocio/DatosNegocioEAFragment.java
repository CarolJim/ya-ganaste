package com.pagatodo.yaganaste.modules.register.DatosNegocio;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.Giros;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.RegisterUserNew;
import com.pagatodo.yaganaste.data.model.SubGiro;
import com.pagatodo.yaganaste.interfaces.IDatosNegView;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.modules.register.DatosPersonales.RegistroDatosPersonalesFragment;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.DatosNegocioPresenter;
import com.pagatodo.yaganaste.ui.account.register.adapters.BussinesLineSpinnerAdapter;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.ui.account.register.adapters.SubBussinesLineSpinnerAdapter;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DatosNegocioFragment;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoCountry;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoGiro;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoStates;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoSubGiro;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatosNegocioEAFragment extends GenericFragment implements IOnSpinnerClick,IDatosNegView<ErrorObject>,ValidationForms {
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
    @BindView(R.id.spiner1)
    ImageView spiner1;

    @BindView(R.id.btnNextDatosNegocio)
    StyleButton btnNextDatosNegocio;
    @BindView(R.id.text_nombrenegoci)
    TextInputLayout text_nombrenegoci;
    private String nombre = "";
    private int idGiro;
    private DatosNegocioPresenter datosNegocioPresenter;

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
        this.datosNegocioPresenter = new DatosNegocioPresenter(getActivity(), this);


    }



    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        activityf.nextStep();
        rootView.findViewById(R.id.btnNextDatosNegocio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        spiner1.setOnClickListener(view -> spinnerBussineLine.performClick());

        if (girosComercio == null) {
            girosComercio = new ArrayList<>();
            Giros giroHint = new Giros();
            giroHint.setGiro(getString(R.string.giro_commerce_spinner));
            giroHint.setIdGiro(-1);
            girosComercio.add(giroHint);
        }
        editBussinesName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b){
                    text_nombrenegoci.setBackgroundResource(R.drawable.inputtext_active);
                }else {
                    text_nombrenegoci.setBackgroundResource(R.drawable.inputtext_normal);
                }

            }
        });

        editBussinesName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editBussinesName.getText().length()>0&& spinnerBussineLine.getSelectedItemPosition() != 0 ){
                    btnNextDatosNegocio.setBackgroundResource(R.drawable.button_rounded_blue);
                }else {
                    btnNextDatosNegocio.setBackgroundResource(R.drawable.button_rounded_gray);
                }

                if (editBussinesName.getText().length()>0)
                    text_nombrenegoci.setBackgroundResource(R.drawable.inputtext_active);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        spinnerBussineLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String nombrenegocio = editBussinesName.getText().toString();

                if (position > 0 && !nombrenegocio.isEmpty()){
                    btnNextDatosNegocio.setBackgroundResource(R.drawable.button_rounded_blue);
                    txtgiro.setBackgroundResource(R.drawable.inputtext_normal);
                }else {
                    btnNextDatosNegocio.setBackgroundResource(R.drawable.button_rounded_gray);
                }

                if (position > 0 ){
                    txtgiro.setBackgroundResource(R.drawable.inputtext_normal);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                onSpinnerClick();
            }


        });
        txtgiro.setBackgroundResource(R.drawable.inputtext_normal);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_datos_negocio_ea, container, false);
        initViews();
        initValues();
      //  setCurrentData();
        return rootView;
    }
    private void initValues() {
            datosNegocioPresenter.getGiros();

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

    @Override
    public void setGiros(List<Giros> giros) {
        Collections.sort(giros, new Comparator<Giros>() {
            @Override
            public int compare(Giros countries, Giros t1) {
                return countries.getGiro().compareTo(t1.getGiro());
            }
        });
        girosComercio.addAll(giros);
        giroArrayAdapter = new BussinesLineSpinnerAdapter(getActivity(),
                R.layout.spinner_layout, girosComercio, this);
        spinnerBussineLine.setAdapter(giroArrayAdapter);
        txtgiro.setBackgroundResource(R.drawable.inputtext_normal);
    }

    private void setCurrentData() {
        RegisterUserNew registerAgent = RegisterUserNew.getInstance();
        if (!registerAgent.getNombre().isEmpty()) {
            editBussinesName.setText(registerAgent.getNombre());
        }
        spinnerBussineLine.setSelection(giroArrayAdapter.getItemPosition(registerAgent.getGiroComercio()));
    }

    @Override
    public void showLoader(String message) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        if (onEventListener != null) {
            onEventListener.onEvent(EVENT_HIDE_LOADER, null);
        }
    }


    @Override
    public void showError(ErrorObject error) {

    }

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;
        if (nombre.isEmpty()) {
            //   showValidationError(editBussinesName.getId(), getString(R.string.datos_negocio_nombre));
            // editBussinesName.setIsInvalid();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            //showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo));
            text_nombrenegoci.setBackgroundResource(R.drawable.inputtext_error);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_negocio_nombre), Snackbar.LENGTH_SHORT);
            isValid = false;
        }
        if (giroArrayAdapter.getItem(spinnerBussineLine.getSelectedItemPosition()).getIdGiro() == -1) {
            //showValidationError(spinnerBussineLine.getId(), getString(R.string.datos_negocio_giro));
            txtgiro.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }
        if (isValid) {
            onValidationSuccess();
        }
    }

    @Override
    public void showValidationError(int id, Object o) {

    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
        RegisterUserNew registerAgent = RegisterUserNew.getInstance();
        registerAgent.setNombre(nombre);
        registerAgent.setIdGiro(giroArrayAdapter.getGiroId(spinnerBussineLine.getSelectedItemPosition()));
        registerAgent.setGiroComercio(giroArrayAdapter.getItemSelected(spinnerBussineLine.getSelectedItemPosition()));
        activityf.getRouter().showPhysicalCode();
    }

    @Override
    public void getDataForm() {
        nombre = editBussinesName.getText().toString();
    }
}
