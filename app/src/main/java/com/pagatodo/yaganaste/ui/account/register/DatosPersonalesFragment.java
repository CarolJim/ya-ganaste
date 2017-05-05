package com.pagatodo.yaganaste.ui.account.register;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.interfaces.enums.States;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_ADDRESS_DATA;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_DATA_USER_BACK;

/**
 * A simple {@link GenericFragment} subclass.
 */
public class DatosPersonalesFragment extends GenericFragment implements View.OnClickListener, ValidationForms,INavigationView,AdapterView.OnItemSelectedListener {

    private View rootview;
    @BindView(R.id.radioGender)
    RadioGroup radioGroupGender;
    @BindView(R.id.radioBtnFemale)
    RadioButton radioBtnFemale;
    @BindView(R.id.radioBtnMale)
    RadioButton radioBtnMale;
    @BindView(R.id.editNames)
    CustomValidationEditText editNames;
    @BindView(R.id.editFirstLastName)
    CustomValidationEditText editFirstLastName;
    @BindView(R.id.editSecoundLastName)
    CustomValidationEditText editSecoundLastName;
    @BindView(R.id.editBirthDay)
    CustomValidationEditText editBirthDay;
    @BindView(R.id.spinnerBirthPlace)
    AppCompatSpinner spinnerBirthPlace;
    @BindView(R.id.btnBackPersonalInfo)
    Button btnBackDatosPersonales;
    @BindView(R.id.btnNextPersonalInfo)
    Button btnNextDatosPersonales;
    @BindView(R.id.errorMessage)
    ErrorMessage errorMessageView;
    StatesSpinnerAdapter adapterBirthPlace;

    private String genero = "";
    private String nombre = "";
    private String apPaterno = "";
    private String apMaterno = "";
    private String fechaNacimiento = "";
    private String lugarNacimiento = "";
    private String idEstadoNacimiento = "";

    public DatosPersonalesFragment() {
    }

    public static DatosPersonalesFragment newInstance() {
        DatosPersonalesFragment fragmentRegister = new DatosPersonalesFragment();
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
        rootview = inflater.inflate(R.layout.fragment_datos_personales, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        editBirthDay.setFullOnClickListener(onClickListenerDatePicker);
        errorMessageView.setVisibilityImageError(false);
        adapterBirthPlace = new StatesSpinnerAdapter(getContext(), R.layout.spinner_layout, States.values());
        spinnerBirthPlace.setAdapter(adapterBirthPlace);
        spinnerBirthPlace.setOnItemSelectedListener(this);
        btnNextDatosPersonales.setOnClickListener(this);
        btnBackDatosPersonales.setOnClickListener(this);
        radioBtnMale.setChecked(true);
        setCurrentData();// Seteamos datos si hay registro en proceso.
        setValidationRules();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.editBirthDay:

                break;
            case R.id.btnBackPersonalInfo:
                backScreen(EVENT_DATA_USER_BACK,null);
                break;
            case  R.id.btnNextPersonalInfo:
                validateForm();
            default:
                break;
        }
    }


    /*Implementacion de ValidationForms*/
    @Override
    public void setValidationRules() {

        editNames.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!editNames.isValidText()){
                    hideErrorMessage();
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editFirstLastName.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!editFirstLastName.isValidText()){
                    hideErrorMessage();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editBirthDay.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!editBirthDay.isValidText()){
                    hideErrorMessage();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    @Override
    public void validateForm() {
        getDataForm();
        if(nombre.isEmpty()){
            showValidationError(getString(R.string.datos_personal_nombre));
            editNames.setIsInvalid();
            return;
        }
        if(apPaterno.isEmpty()){
            showValidationError(getString(R.string.datos_personal_paterno));
            editFirstLastName.setIsInvalid();
            return;
        }
        if(fechaNacimiento.isEmpty()){
            showValidationError(getString(R.string.datos_personal_fecha));
            editBirthDay.setIsInvalid();
            return;
        }
        if(lugarNacimiento.isEmpty()){
            showValidationError(getString(R.string.datos_personal_estado));
            return;
        }
        onValidationSuccess();
    }

    @Override
    public void showValidationError(Object error) {
        errorMessageView.setMessageText(error.toString());
        UI.hideKeyBoard(getActivity());
        //UI.showToastShort(error.toString(),getActivity());
    }

    private void hideErrorMessage(){
        errorMessageView.setVisibilityImageError(false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        /*if(position > 0){
            Toast.makeText(getActivity(), adapterBirthPlace.getItemName(position), Toast.LENGTH_SHORT ).show();
        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onValidationSuccess() {
        //Almacenamos la información para el registro
        RegisterUser registerUser = RegisterUser.getInstance();
        registerUser.setGenero(genero);
        registerUser.setNombre(nombre);
        registerUser.setApellidoPaterno(apPaterno);
        registerUser.setApellidoMaterno(apMaterno);
        registerUser.setFechaNacimientoToShow(editBirthDay.getText());
        registerUser.setFechaNacimiento(fechaNacimiento);
        registerUser.setLugarNacimiento(lugarNacimiento);
        registerUser.setIdEstadoNacimineto(idEstadoNacimiento);
        nextScreen(EVENT_ADDRESS_DATA,null);//Mostramos siguiente pantalla de registro.
    }

    @Override
    public void getDataForm() {
        genero = radioBtnMale.isChecked() ? "H" : "M";
        nombre = editNames.getText().toString();
        apPaterno = editFirstLastName.getText().toString();
        apMaterno = editSecoundLastName.getText().toString();
        //fechaNacimiento se establece en el event de DatePicker
        if(spinnerBirthPlace.getSelectedItemPosition() != 0)
            lugarNacimiento = spinnerBirthPlace.getSelectedItem().toString();
        StatesSpinnerAdapter adapter = (StatesSpinnerAdapter)spinnerBirthPlace.getAdapter();
        idEstadoNacimiento = adapter.getItemIdString(spinnerBirthPlace.getSelectedItemPosition());
    }

    private void setCurrentData(){
        RegisterUser registerUser = RegisterUser.getInstance();
        if(registerUser.getGenero().equals("H")){
            radioBtnMale.setChecked(true);
        }else {
            radioBtnFemale.setChecked(true);
        }

        editNames.setText(registerUser.getNombre());
        editFirstLastName.setText(registerUser.getApellidoPaterno());
        editSecoundLastName.setText(registerUser.getApellidoMaterno());
        editBirthDay.setText(registerUser.getFechaNacimientoToShow());
        fechaNacimiento = registerUser.getFechaNacimiento();
        StatesSpinnerAdapter adapter = (StatesSpinnerAdapter)spinnerBirthPlace.getAdapter();
        spinnerBirthPlace.setSelection(adapter.getPositionItemByName(registerUser.getLugarNacimiento()));
    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void showLoader(String message) {
    }

    @Override
    public void hideLoader() {
    }

    @Override
    public void showError(Object error) {
        UI.showToastShort(error.toString(),getActivity());
    }

    View.OnClickListener onClickListenerDatePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar newCalendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int date) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, month, date);
                    editBirthDay.setText(DateUtil.getBirthDateString(newDate));
                    fechaNacimiento = DateUtil.getDateStringFirstYear(newDate);
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }
    };
}
