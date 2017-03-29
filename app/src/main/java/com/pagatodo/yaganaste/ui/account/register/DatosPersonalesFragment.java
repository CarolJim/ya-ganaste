package com.pagatodo.yaganaste.ui.account.register;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.interfaces.IAccountView2;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.interfaces.enums.States;
import com.pagatodo.yaganaste.ui._adapters.EnumSpinnerAdapter;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.UI;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_ADDRESS_DATA;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_DATA_USER_BACK;

/**
 * A simple {@link GenericFragment} subclass.
 */
public class DatosPersonalesFragment extends GenericFragment implements View.OnClickListener, ValidationForms,IAccountView2 {

    private View rootview;
    @BindView(R.id.radioGender)
    RadioGroup radioGroupGender;
    @BindView(R.id.radioBtnFemale)
    AppCompatRadioButton radioBtnFemale;
    @BindView(R.id.radioBtnMale)
    AppCompatRadioButton radioBtnMale;
    @BindView(R.id.editNames)
    EditText editNames;
    @BindView(R.id.editFirstLastName)
    EditText editFirstLastName;
    @BindView(R.id.editSecoundLastName)
    EditText editSecoundLastName;
    @BindView(R.id.editBirthDay)
    EditText editBirthDay;
    @BindView(R.id.spinnerBirthPlace)
    Spinner spinnerBirthPlace;
    @BindView(R.id.btnBackDatosPersonales)
    Button btnBackDatosPersonales;
    @BindView(R.id.btnNextDatosPersonales)
    Button btnNextDatosPersonales;
    private String genero = "";
    private String nombre = "";
    private String apPaterno = "";
    private String apMaterno = "";
    private String fechaNacimiento = "";
    private String lugarNacimiento = "";

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
        editBirthDay.setOnClickListener(this);
        EnumSpinnerAdapter adapterBirthPlace = new EnumSpinnerAdapter(getContext(), R.layout.spinner_layout, States.values());
        spinnerBirthPlace.setAdapter(adapterBirthPlace);
        btnNextDatosPersonales.setOnClickListener(this);
        btnBackDatosPersonales.setOnClickListener(this);
        radioBtnMale.setChecked(true);
        setCurrentData();// Seteamos datos si hay registro en proceso.
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.editBirthDay:
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, date);
                        editBirthDay.setText(DateUtil.getBirthDateString(newDate));
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case R.id.btnBackDatosPersonales:
                backStepRegister(EVENT_DATA_USER_BACK,null);
                break;
            case  R.id.btnNextDatosPersonales:
                validateForm();
            default:
                break;
        }
    }

    /*Implementacion de ValidationForms*/
    @Override
    public void setValidationRules() {
    }

    @Override
    public void validateForm() {
        getDataForm();
        if(nombre.isEmpty()){
            showValidationError(getString(R.string.datos_personal_nombre));
            return;
        }
        if(apPaterno.isEmpty()){
            showValidationError(getString(R.string.datos_personal_paterno));
            return;
        }
        if(fechaNacimiento.isEmpty()){
            showValidationError(getString(R.string.datos_personal_fecha));
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
        UI.showToastShort(error.toString(),getActivity());
    }

    @Override
    public void onValidationSuccess() {
        //Almacenamos la información para el registro
        RegisterUser registerUser = RegisterUser.getInstance();
        registerUser.setGenero(genero);
        registerUser.setNombre(nombre);
        registerUser.setApellidoPaterno(apPaterno);
        registerUser.setApellidoMaterno(apMaterno);
        registerUser.setFechaNacimiento(fechaNacimiento);
        registerUser.setLugarNacimiento(lugarNacimiento);
        nextStepRegister(EVENT_ADDRESS_DATA,null);//Mostramos siguiente pantalla de registro.
    }

    @Override
    public void getDataForm() {
        genero = radioBtnMale.isChecked() ? "H" : "M";
        nombre = editNames.getText().toString();
        apPaterno = editFirstLastName.getText().toString();
        apMaterno = editSecoundLastName.getText().toString();
        fechaNacimiento = editBirthDay.getText().toString();
        lugarNacimiento = spinnerBirthPlace.getSelectedItem().toString();
    }

    private void setCurrentData(){
        RegisterUser registerUser = RegisterUser.getInstance();
        editNames.setText(registerUser.getNombre());
        editFirstLastName.setText(registerUser.getApellidoPaterno());
        editSecoundLastName.setText(registerUser.getApellidoMaterno());
        editBirthDay.setText(registerUser.getFechaNacimiento());
        EnumSpinnerAdapter adapter = (EnumSpinnerAdapter)spinnerBirthPlace.getAdapter();
        spinnerBirthPlace.setSelection(adapter.getPositionItemByName(registerUser.getLugarNacimiento()));
    }

    @Override
    public void nextStepRegister(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void backStepRegister(String event, Object data) {
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
}
