package com.pagatodo.yaganaste.ui.account.register;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
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
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.interfaces.enums.States;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_ADDRESS_DATA;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_DATA_USER_BACK;

/**
 * A simple {@link GenericFragment} subclass.
 */
public class DatosPersonalesFragment extends GenericFragment implements
        View.OnClickListener, ValidationForms, INavigationView,
        AdapterView.OnItemSelectedListener, IOnSpinnerClick {


    private final int MX = 1;
    private final int EXTRANJERO = 2;
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
    //@BindView(R.id.errorMessage)
    //ErrorMessage errorMessageView;

    @BindView(R.id.errorNameMessage)
    ErrorMessage errorNameMessage;
    @BindView(R.id.errorFLastNameMessage)
    ErrorMessage errorFLastNameMessage;
    @BindView(R.id.errorBirthDayMessage)
    ErrorMessage errorBirthDayMessage;
    @BindView(R.id.errorBirthPlaceMessage)
    ErrorMessage errorBirthPlaceMessage;
    @BindView(R.id.errorGenderMessage)
    ErrorMessage errorGenderMsessage;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_datos_personales, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        radioGroupGender.clearCheck();
        errorGenderMsessage.alingCenter();

        errorGenderMsessage.setVisibilityImageError(false);
        errorNameMessage.setVisibilityImageError(false);
        errorFLastNameMessage.setVisibilityImageError(false);
        errorBirthDayMessage.setVisibilityImageError(false);
        errorBirthPlaceMessage.setVisibilityImageError(false);

        editBirthDay.setFullOnClickListener(onClickListenerDatePicker);
        adapterBirthPlace = new StatesSpinnerAdapter(getContext(), R.layout.spinner_layout, States.values(), this);
        spinnerBirthPlace.setAdapter(adapterBirthPlace);
        spinnerBirthPlace.setOnItemSelectedListener(this);
        btnNextDatosPersonales.setOnClickListener(this);
        btnBackDatosPersonales.setOnClickListener(this);
        //radioBtnMale.setChecked(true);
        setCurrentData();// Seteamos datos si hay registro en proceso.
        setValidationRules();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editBirthDay:
                break;
            case R.id.btnBackPersonalInfo:
                backScreen(EVENT_DATA_USER_BACK, null);
                break;
            case R.id.btnNextPersonalInfo:
                validateForm();
                break;
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
                if (!editNames.isValidText()) {
                    hideErrorMessage(editNames.getId());
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
                if (!editFirstLastName.isValidText()) {
                    hideErrorMessage(editFirstLastName.getId());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editBirthDay.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!editBirthDay.isValidText()) {
                    hideErrorMessage(editBirthDay.getId());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                hideErrorMessage(radioGroupGender.getId());
            }
        });

    }

    @Override
    public void validateForm() {
        getDataForm();

        if (genero == null || genero.equals("")) {
            showValidationError(radioGroupGender.getId(), getString(R.string.datos_personal_genero));
            return;
        }

        if (nombre.isEmpty()) {
            showValidationError(editNames.getId(), getString(R.string.datos_personal_nombre));
            editNames.setIsInvalid();
            return;
        }
        if (apPaterno.isEmpty()) {
            showValidationError(editFirstLastName.getId(), getString(R.string.datos_personal_paterno));
            editFirstLastName.setIsInvalid();
            return;
        }
        if (fechaNacimiento.isEmpty()) {
            showValidationError(editBirthDay.getId(), getString(R.string.datos_personal_fecha));
            editBirthDay.setIsInvalid();
            return;
        }

        if (lugarNacimiento.isEmpty()) {
            showValidationError(spinnerBirthPlace.getId(), getString(R.string.datos_personal_estado));
            return;
        }

        onValidationSuccess();
    }

    @Override
    public void showValidationError(int id, Object error) {

        switch (id) {
            case R.id.editNames:
                errorNameMessage.setMessageText(error.toString());
                break;
            case R.id.editFirstLastName:
                errorFLastNameMessage.setMessageText(error.toString());
                break;
            case R.id.editBirthDay:
                errorBirthDayMessage.setMessageText(error.toString());
                break;
            case R.id.spinnerBirthPlace:
                errorBirthPlaceMessage.setMessageText(error.toString());
                break;
            case R.id.radioGender:
                errorGenderMsessage.setMessageText(error.toString());
                break;
        }
        UI.hideKeyBoard(getActivity());
        //UI.showToastShort(error.toString(),getActivity());
    }

    private void hideErrorMessage(int id) {
        //errorMessageView.setVisibilityImageError(false);

        switch (id) {
            case R.id.editNames:
                errorNameMessage.setVisibilityImageError(false);
                break;
            case R.id.editFirstLastName:
                errorFLastNameMessage.setVisibilityImageError(false);
                break;
            case R.id.editBirthDay:
                errorBirthDayMessage.setVisibilityImageError(false);
                break;
            case R.id.spinnerBirthPlace:
                errorBirthPlaceMessage.setVisibilityImageError(false);
                break;
            case R.id.radioGender:
                errorGenderMsessage.setVisibilityImageError(false);
                break;
        }
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
        errorNameMessage.setVisibilityImageError(false);
        errorFLastNameMessage.setVisibilityImageError(false);
        errorBirthDayMessage.setVisibilityImageError(false);
        errorBirthPlaceMessage.setVisibilityImageError(false);

        //Almacenamos la información para el registro
        RegisterUser registerUser = RegisterUser.getInstance();
        registerUser.setGenero(genero);
        registerUser.setNombre(nombre);
        registerUser.setApellidoPaterno(apPaterno);
        registerUser.setApellidoMaterno(apMaterno);
        registerUser.setFechaNacimientoToShow(editBirthDay.getText());
        registerUser.setFechaNacimiento(fechaNacimiento);
        registerUser.setNacionalidad("MX");
        registerUser.setLugarNacimiento(lugarNacimiento);
        registerUser.setIdEstadoNacimineto(idEstadoNacimiento);
        nextScreen(EVENT_ADDRESS_DATA, null);//Mostramos siguiente pantalla de registro.
    }

    @Override
    public void getDataForm() {
        genero = radioBtnMale.isChecked() ? "H" : radioBtnFemale.isChecked() ? "M" : "";
        nombre = editNames.getText().toString();
        apPaterno = editFirstLastName.getText().toString();
        apMaterno = editSecoundLastName.getText().toString();
        if (spinnerBirthPlace.getSelectedItemPosition() != 0) {
            lugarNacimiento = spinnerBirthPlace.getSelectedItem().toString();
            StatesSpinnerAdapter adapter = (StatesSpinnerAdapter) spinnerBirthPlace.getAdapter();
            idEstadoNacimiento = adapter.getItemIdString(spinnerBirthPlace.getSelectedItemPosition());
        }
    }

    private void setCurrentData() {
        RegisterUser registerUser = RegisterUser.getInstance();
        if (registerUser.getGenero().equals("H")) {
            radioBtnMale.setChecked(true);
        } else if (registerUser.getGenero().equals("M")) {
            radioBtnFemale.setChecked(true);
        } else {
            radioGroupGender.clearCheck();
        }

        editNames.setText(registerUser.getNombre());
        editFirstLastName.setText(registerUser.getApellidoPaterno());
        editSecoundLastName.setText(registerUser.getApellidoMaterno());
        editBirthDay.setText(registerUser.getFechaNacimientoToShow());
        fechaNacimiento = registerUser.getFechaNacimiento();
        StatesSpinnerAdapter adapter = (StatesSpinnerAdapter) spinnerBirthPlace.getAdapter();
        spinnerBirthPlace.setSelection(adapter.getPositionItemByName(registerUser.getLugarNacimiento()));
    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void showLoader(String message) {
    }

    @Override
    public void hideLoader() {
    }

    @Override
    public void showError(Object error) {
        if (!error.toString().isEmpty())
          //  UI.showToastShort(error.toString(), getActivity());
            UI.createSimpleCustomDialog("", error.toString(), getFragmentManager(),
                    new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {

                        }

                        @Override
                        public void actionCancel(Object... params) {

                        }
                    },
                    true, false);
    }

    View.OnClickListener onClickListenerDatePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar newCalendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int date) {
                    Calendar newDate = Calendar.getInstance(new Locale("es"));
                    newDate.set(year, month, date);
                    editBirthDay.setText(DateUtil.getBirthDateString(newDate));
                    fechaNacimiento = DateUtil.getDateStringFirstYear(newDate);
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }
    };

    @Override
    public void onSpinnerClick() {
        hideErrorMessage(spinnerBirthPlace.getId());
    }
}
