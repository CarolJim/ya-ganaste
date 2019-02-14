package com.pagatodo.yaganaste.modules.register.DatosNegocio;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.Giros;
import com.pagatodo.yaganaste.data.model.RegisterUserNew;
import com.pagatodo.yaganaste.data.model.SubGiro;
import com.pagatodo.yaganaste.interfaces.IDatosNegView;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.DatosNegocioPresenter;
import com.pagatodo.yaganaste.ui.account.register.adapters.BussinesLineSpinnerAdapter;
import com.pagatodo.yaganaste.ui.account.register.adapters.SubBussinesLineSpinnerAdapter;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoGiro;
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

    private SubBussinesLineSpinnerAdapter subgiroArrayAdapter;
    @BindView(R.id.textsubgiroea)
    StyleTextView textsubgiro;
    @BindView(R.id.txtsubgiroea)
    LinearLayout txtsubgiro;

    @BindView(R.id.spinnerBussineLine)
    Spinner spinnerBussineLine;
    @BindView(R.id.spinnerSubBussineLine)
    Spinner spinnerSubBussineLine;
    @BindView(R.id.textgiro)
    StyleTextView textgiro;
    @BindView(R.id.editBussinesName)
    EditText editBussinesName;
    @BindView(R.id.txtgiro)
    LinearLayout txtgiro;
    @BindView(R.id.txtgirolya)
    LinearLayout txtgirolya;
    @BindView(R.id.spiner1)
    ImageView spiner1;
    boolean flag = false;
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


    private View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //Your code
            }
            return false;
        }
    };
    private static View.OnKeyListener spinnerOnKey = new View.OnKeyListener() {
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                //your code
                return true;
            } else {
                return false;
            }
        }
    };


    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        activityf.nextStep();
        rootView.findViewById(R.id.btnNextDatosNegocio).setOnClickListener(view -> {

            validateForm();
        });


        spinnerBussineLine.setOnTouchListener(spinnerOnTouch);
        spinnerBussineLine.setOnKeyListener(spinnerOnKey);




        txtgirolya.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                lManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                return false;
            }
        });

        spinnerBussineLine.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                lManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                return false;
            }
        });
        txtgiro.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                lManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                return false;
            }
        });
        spiner1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                lManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                return false;
            }
        });
        textgiro.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                lManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                return false;
            }
        });

        spiner1.setOnClickListener(view ->
                spinnerBussineLine.performClick());

        /*
        if (girosComercio == null) {
            girosComercio = new ArrayList<>();
            Giros giroHint = new Giros();
            giroHint.setGiro(getString(R.string.giro_commerce_spinner));
            giroHint.setIdGiro(-1);
            girosComercio.add(giroHint);
        }
        */

        if (girosComercio == null) {
            girosComercio = new ArrayList<>();
            Giros giroHint = new Giros();
            giroHint.setGiro(getString(R.string.giro_commerce_spinner));
            giroHint.setIdGiro(-1);
            List<SubGiro> subgiroHint = new ArrayList<SubGiro>();
            subgiroHint.add(new SubGiro(-1, getString(R.string.subgiro_commerce)));
            giroHint.setListaSubgiros(subgiroHint);
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

                if (position != 0) {
                    textgiro.setVisibility(View.VISIBLE);
                    textgiro.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                else
                    textgiro.setVisibility(View.GONE);
                List<SubGiro> list = girosComercio.get(position).getListaSubgiros();
                if (!list.contains(new SubGiro(-1, getString(R.string.subgiro_commerce)))) {
                    Collections.sort(list, new Comparator<SubGiro>() {
                        @Override
                        public int compare(SubGiro countries, SubGiro t1) {
                            return countries.getSubgiro().compareTo(t1.getSubgiro());
                        }
                    });
                    list.add(0, new SubGiro(-1, getString(R.string.subgiro_commerce)));
                }
                subgiroArrayAdapter = new SubBussinesLineSpinnerAdapter(getActivity(),
                        R.layout.spinner_layout, list, DatosNegocioEAFragment.this);
                spinnerSubBussineLine.setAdapter(subgiroArrayAdapter);
                if (list.size() == 2) {
                    spinnerSubBussineLine.setSelection(1);
                }


                if(flag){
                    InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    lManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                }

                String nombrenegocio = editBussinesName.getText().toString();
                if (position > 0 && !nombrenegocio.isEmpty()){
                    //btnNextDatosNegocio.setBackgroundResource(R.drawable.button_rounded_blue);
                    txtgiro.setBackgroundResource(R.drawable.inputtext_normal);
                }else {
                    //btnNextDatosNegocio.setBackgroundResource(R.drawable.button_rounded_gray);
                }

                if (position > 0 ){
                    txtgiro.setBackgroundResource(R.drawable.inputtext_normal);
                }

            flag = true;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                onSpinnerClick();
            }


        });

        spinnerSubBussineLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSubSpinnerClick();
                if (position != 0) {
                    textsubgiro.setVisibility(View.VISIBLE);
                    textsubgiro.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                else
                    textsubgiro.setVisibility(View.GONE);


                if(flag){
                    InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    lManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                }
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

                flag = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                onSubSpinnerClick();
            }
        });


        txtgiro.setBackgroundResource(R.drawable.inputtext_normal);



        editBussinesName.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
        imm.showSoftInput(editBussinesName, InputMethodManager.SHOW_IMPLICIT);

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
            activityf.backvisivility(false);

    }
    @Override
    public void onSpinnerClick() {
        InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        lManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public void onSubSpinnerClick() {

    }

    @Override
    public void hideKeyBoard() {
        InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        lManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
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
            text_nombrenegoci.setBackgroundResource(R.drawable.input_text_error);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_negocio_nombre), Snackbar.LENGTH_SHORT);
            isValid = false;
        }
        if (giroArrayAdapter.getItem(spinnerBussineLine.getSelectedItemPosition()).getIdGiro() == -1) {
            //showValidationError(spinnerBussineLine.getId(), getString(R.string.datos_negocio_giro));
            txtgiro.setBackgroundResource(R.drawable.input_text_error);
            isValid = false;
        }
        if (subgiroArrayAdapter.getItem(spinnerSubBussineLine.getSelectedItemPosition()).getIdSubgiro() == -1) {
            //showValidationError(spinnerSubBussineLine.getId(), getString(R.string.datos_negocio_subgiro));
            txtsubgiro.setBackgroundResource(R.drawable.input_text_error);
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
        registerAgent.setNombreNegocio(nombre);
        registerAgent.setIdGiro(giroArrayAdapter.getGiroId(spinnerBussineLine.getSelectedItemPosition()));
        registerAgent.setGiroComercio(giroArrayAdapter.getItemSelected(spinnerBussineLine.getSelectedItemPosition()));

        registerAgent.setSubGiros(subgiroArrayAdapter.getItemSelected(spinnerSubBussineLine.getSelectedItemPosition()));
        //activityf.getRouter().showPhysicalCode(Direction.FORDWARD);
        activityf.getRouter().showPhysicalCode(Direction.FORDWARD);
    }

    @Override
    public void getDataForm() {
        nombre = editBussinesName.getText().toString();
    }
}
