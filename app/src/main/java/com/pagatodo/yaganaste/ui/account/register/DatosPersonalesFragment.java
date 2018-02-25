package com.pagatodo.yaganaste.ui.account.register;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IBuscaPais;
import com.pagatodo.yaganaste.interfaces.IDatosPersonalesManager;
import com.pagatodo.yaganaste.interfaces.IEnumSpinner;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.IRenapoView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.interfaces.enums.Genero;
import com.pagatodo.yaganaste.interfaces.enums.States;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.ui_wallet.views.Color;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.CustomDatePicker;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.CountriesDialogFragment;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_ADDRESS_DATA;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_DATA_USER_BACK;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_SELFIE;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;

/**
 * A simple {@link GenericFragment} subclass.
 */
public class DatosPersonalesFragment extends GenericFragment implements
        View.OnClickListener, IDatosPersonalesManager, ValidationForms, IRenapoView,
        AdapterView.OnItemSelectedListener, IOnSpinnerClick, IBuscaPais {


    Boolean seencuentra = false;
    private final int EXTRANJERO = 2;
    @BindView(R.id.radioGender)
    RadioGroup radioGroupGender;
    @BindView(R.id.radioBtnFemale)
    RadioButton radioBtnFemale;
    @BindView(R.id.radioBtnMale)
    RadioButton radioBtnMale;
    @BindView(R.id.editNames)
    CustomValidationEditText editNamesold;
    @BindView(R.id.edit_nombre)
    EditText editNames;

    @BindView(R.id.subfechanacimiento)
    StyleTextView subfechanacimiento;



    @BindView(R.id.editFirstLastName)
    CustomValidationEditText editFirstLastNameold;
    @BindView(R.id.edit_appater)
    EditText editFirstLastName;





    @BindView(R.id.editSecoundLastName)
    CustomValidationEditText editSecoundLastNameold;
    @BindView(R.id.edit_apmaterno)
    EditText editSecoundLastName;



    @BindView(R.id.editBirthDay)
    CustomValidationEditText editBirthDay;
    @BindView(R.id.spinnerBirthPlace)
    AppCompatSpinner spinnerBirthPlace;
    @BindView(R.id.spinnergenero)
    AppCompatSpinner spinnergenero;
    @BindView(R.id.btnBackPersonalInfo)
    Button btnBackDatosPersonales;
    @BindView(R.id.btnNextPersonalInfo)
    Button btnNextDatosPersonales;
    @BindView(R.id.errorNameMessage)
    ErrorMessage errorNameMessage;
    //@BindView(R.id.errorMessage)
    //ErrorMessage errorMessageView;
    @BindView(R.id.errorFLastNameMessage)
    ErrorMessage errorFLastNameMessage;
    @BindView(R.id.errorBirthDayMessage)
    ErrorMessage errorBirthDayMessage;
    @BindView(R.id.errorBirthPlaceMessage)
    ErrorMessage errorBirthPlaceMessage;
    @BindView(R.id.errorGenderMessage)
    ErrorMessage errorGenderMsessage;
    @BindView(R.id.editCountry)
    CustomValidationEditText editCountry;
    @BindView(R.id.errorCountryMessage)
    ErrorMessage errorCountryMessage;
    @BindView(R.id.editCurp)
    CustomValidationEditText editCurp;
    @BindView(R.id.errorCurp)
    ErrorMessage errorCurp;

    @BindView(R.id.seleccionaGenero)
    StyleTextView seleccionaGenero;





    StatesSpinnerAdapter adapterBirthPlace;
    StatesSpinnerAdapter adaptergenero;
    Calendar newDate;
    Calendar actualDate;
    private View rootview;
    private String genero = "";
    private String nombre = "";
    private String apPaterno = "";
    private String apMaterno = "";
    private String fechaNacimiento = "";
    private String curp = "";
    private Countries country;
    int year;
    int month;
    int day;
    private int errorVerificationData = 0;

    View.OnClickListener onClickListenerDatePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideValidationError(editBirthDay.getId());
            editBirthDay.setDrawableImage(R.drawable.calendar);
            Calendar newCalendar = Calendar.getInstance();
            year = 0;
            month = 0;
            day = 0;

            if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
                year = newDate.get(Calendar.YEAR);
                month = newDate.get(Calendar.MONTH);
                day = newDate.get(Calendar.DAY_OF_MONTH);
            } else {
                year = newCalendar.get(Calendar.YEAR);
                month = newCalendar.get(Calendar.MONTH);
                day = newCalendar.get(Calendar.DAY_OF_MONTH);
            }

            CustomDatePicker datePickerDialog = new CustomDatePicker(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int date) {
                    newDate = Calendar.getInstance(new Locale("es"));
                    newDate.set(year, month, date);
                    // editBirthDay.setText(DateUtil.getBirthDateCustomString(newDate));
                    editBirthDay.setText(DateUtil.getBirthDateSpecialCustom(year, month, date));
                    editBirthDay.setIsValid();
                    fechaNacimiento = DateUtil.getDateStringFirstYear(newDate);

                    Calendar mCalendar = Calendar.getInstance();
                    mCalendar.set(actualDate.get(Calendar.YEAR) - 18, actualDate.get(Calendar.MONTH), actualDate.get(Calendar.DAY_OF_MONTH));

                    if (newDate.getTimeInMillis() > actualDate.getTimeInMillis()) {
                        showValidationError(editBirthDay.getId(), getString(R.string.datos_personal_fecha));
                        editBirthDay.setIsInvalid();
                        return;
                    }

                    if (newDate.getTimeInMillis() > mCalendar.getTimeInMillis()) {
                        showValidationError(editBirthDay.getId(), getString(R.string.datos_personal_mayor_edad));
                        editBirthDay.setIsInvalid();
                        return;
                    }
                }

            }, year, month, day);
            datePickerDialog.show();
        }
    };

    private String lugarNacimiento = "";
    private String idEstadoNacimiento = "";
    private AccountPresenterNew accountPresenter;

    public DatosPersonalesFragment() {
    }

    public static DatosPersonalesFragment newInstance() {
        DatosPersonalesFragment fragmentRegister = new DatosPersonalesFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        accountPresenter.setIView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_datos_personales, container, false);
        actualDate = Calendar.getInstance(new Locale("es"));
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        radioBtnMale.setOnClickListener(this);
        radioBtnFemale.setOnClickListener(this);
        errorGenderMsessage.alingCenter();
        errorGenderMsessage.setVisibilityImageError(false);
        errorNameMessage.setVisibilityImageError(false);
        errorFLastNameMessage.setVisibilityImageError(false);
        errorBirthDayMessage.setVisibilityImageError(false);
        errorBirthPlaceMessage.setVisibilityImageError(false);
        errorCurp.setVisibilityImageError(false);

        editBirthDay.setFullOnClickListener(onClickListenerDatePicker);
        editBirthDay.setDrawableImage(R.drawable.calendar);
        editBirthDay.imageViewIsGone(true);
        adaptergenero= new StatesSpinnerAdapter(getContext(),R.layout.spinner_layout, Genero.values(),this);

        adapterBirthPlace = new StatesSpinnerAdapter(getContext(), R.layout.spinner_layout,
                States.values(), this);
        spinnerBirthPlace.setAdapter(adapterBirthPlace);
        spinnerBirthPlace.setOnItemSelectedListener(this);

        spinnerBirthPlace.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                subfechanacimiento.setVisibility(VISIBLE);
                return false;
            }
        });

        spinnergenero.setAdapter(adaptergenero);
        spinnergenero.setOnItemSelectedListener(this);


        editCountry.imageViewIsGone(false);
        editCountry.setEnabled(false);
        editCountry.setFullOnClickListener(this);

        btnNextDatosPersonales.setOnClickListener(this);
        btnBackDatosPersonales.setOnClickListener(this);

        editSecoundLastName.setImeOptions(IME_ACTION_DONE);
        editSecoundLastName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == IME_ACTION_DONE) {
                    UI.hideKeyBoard(getActivity());
                }
                return false;
            }
        });
        editCurp.setCurpFormat();

        //radioBtnMale.setChecked(true);/
        setCurrentData();// Seteamos datos si hay registro en proceso.
        setValidationRules();
    }

    public void changecolorradio(){
        if (radioBtnMale.isChecked()){
            //radioBtnMale.setHighlightColor(getResources().getColor(R.color.colorAccent));
            radioBtnMale.setBackgroundResource(R.drawable.ico_maleb);
            radioBtnFemale.setBackgroundResource(R.drawable.ico_female);
            seleccionaGenero.setTextColor((int) R.color.colorAccent);
        }else {
            if (radioBtnFemale.isChecked()){
                radioBtnMale.setBackgroundResource(R.drawable.ico_male);
                radioBtnFemale.setBackgroundResource(R.drawable.ico_femaleb);
                seleccionaGenero.setTextColor((int) R.color.colorAccent);
            }
        }

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnBackPersonalInfo:
                backScreen(EVENT_DATA_USER_BACK, null);
                break;
            case R.id.btnNextPersonalInfo:
                validateForm();
                break;
            case R.id.editCountry:
                onCountryClick();

                break;
            case R.id.imageViewValidation:
                onCountryClick();
                break;
            case R.id.radioBtnFemale:
                changecolorradio();
                break;
            case R.id.radioBtnMale:
                changecolorradio();
                break;

            default:
                break;
        }
    }

    public void llamar() {
        String number = getString(R.string.numero_telefono_paises);

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + number));


        if (!ValidatePermissions.isAllPermissionsActives(getActivity(), ValidatePermissions.getPermissionsCheck())) {
            ValidatePermissions.checkPermissions(getActivity(), new String[]{
                    Manifest.permission.CALL_PHONE}, PERMISSION_GENERAL);
        } else {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            getActivity().startActivity(callIntent);
        }
    }

    private void onCountryClick() {
        accountPresenter.getPaisesList();
        hideValidationError(R.id.editCountry);
    }

    @Override
    public void showDialogList(ArrayList<Countries> paises) {

        CountriesDialogFragment dialogFragment = CountriesDialogFragment.newInstance(paises);
        dialogFragment.setOnCountrySelectedListener(this);
        dialogFragment.show(getChildFragmentManager(), "FragmentDialog");
    }

    /*Implementacion de ValidationForms*/
    @Override
    public void setValidationRules() {

        editNames.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editNames.getId());
                  //  editNames.imageViewIsGone(true);
                } else {
                    if (editNames.getText().toString().isEmpty()) {
                        showValidationError(editNames.getId(), getString(R.string.datos_personal_nombre));
                    //    editNames.setIsInvalid();
                    } else {
                        hideValidationError(editNames.getId());
                        //editNames.setIsValid();
                    }
                }
            }
        });
        /*

        editNames.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editNames.getId());
                editNames.imageViewIsGone(true);
            }
        });
        */

        editFirstLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editFirstLastName.getId());
               //     editFirstLastName.imageViewIsGone(true);
                } else {
                    if (editFirstLastName.getText().toString().isEmpty()) {
                        showValidationError(editFirstLastName.getId(), getString(R.string.datos_personal_paterno));
                 //       editFirstLastName.setIsInvalid();
                    } else {
                        hideValidationError(editFirstLastName.getId());
                   //     editFirstLastName.setIsValid();
                    }
                }
            }
        });
        /*

        editFirstLastName.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editFirstLastName.getId());
                editFirstLastName.imageViewIsGone(true);
            }
        });
            */

    }

    @Override
    public void validateForm() {
        getDataForm();

        boolean isValid = true;

        if (genero == null || genero.equals("")) {
            showValidationError(spinnergenero.getId(), getString(R.string.datos_personal_genero));
            isValid = false;
        }

        if (nombre.isEmpty()) {
            showValidationError(editNames.getId(), getString(R.string.datos_personal_nombre));
          //  editNames.setIsInvalid();
            isValid = false;
        }
        if (apPaterno.isEmpty()) {
            showValidationError(editFirstLastName.getId(), getString(R.string.datos_personal_paterno));
          //  editFirstLastName.setIsInvalid();
            isValid = false;
        }

        if (!fechaNacimiento.isEmpty() && newDate != null && (newDate.getTimeInMillis() > actualDate.getTimeInMillis())) {
            showValidationError(editBirthDay.getId(), getString(R.string.datos_personal_fecha));
            editBirthDay.setIsInvalid();
            isValid = false;
        }

        if (!fechaNacimiento.isEmpty() && actualDate != null) {

            Calendar mCalendar = Calendar.getInstance();
            mCalendar.set(actualDate.get(Calendar.YEAR) - 18, actualDate.get(Calendar.MONTH), actualDate.get(Calendar.DAY_OF_MONTH));

            if (newDate.getTimeInMillis() > mCalendar.getTimeInMillis()) {
                showValidationError(editBirthDay.getId(), getString(R.string.datos_personal_mayor_edad));
                editBirthDay.setIsInvalid();
                isValid = false;
            }
        }

        if (fechaNacimiento.isEmpty()) {
            showValidationError(editBirthDay.getId(), getString(R.string.datos_personal_fecha));
            editBirthDay.setIsInvalid();
            isValid = false;
        }

        if (lugarNacimiento.isEmpty()) {
            showValidationError(spinnerBirthPlace.getId(), getString(R.string.datos_personal_estado));
            isValid = false;
        }

        if (!lugarNacimiento.isEmpty() && lugarNacimiento.equals(States.S33.getName())) {
            if (country == null) {
                showValidationError(R.id.editCountry, getString(R.string.datos_personal_pais));
                isValid = false;
            }
        }
        if (isValid) {
            if (country != null) {
                String pais = country.getIdPais();
                List<String> paises = new ArrayList<>();
                bucaPais(paises, pais);
            } else {
                setPersonData();
            }
        }
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
                editBirthDay.setIsInvalid();
                break;
            case R.id.spinnerBirthPlace:
                errorBirthPlaceMessage.setMessageText(error.toString());
                break;
            case R.id.spinnergenero:
                errorGenderMsessage.setMessageText(error.toString());
                break;
            case R.id.editCountry:
                errorCountryMessage.setMessageText(error.toString());
                editCountry.setIsInvalid();
                break;
        }
        UI.hideKeyBoard(getActivity());
        //UI.showToastShort(error.toString(),getActivity());
    }

    @Override
    public void hideValidationError(int id) {
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
                editBirthDay.setIsValid();
                break;
            case R.id.spinnerBirthPlace:
                errorBirthPlaceMessage.setVisibilityImageError(false);
                break;
            case R.id.spinnergenero:
                errorGenderMsessage.setVisibilityImageError(false);
                break;
            case R.id.editCountry:
                errorCountryMessage.setVisibilityImageError(false);
                editCountry.setDrawableImage(R.drawable.menu_canvas);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        IEnumSpinner itemSelected = adapterBirthPlace.getItem(position);
        if (itemSelected == States.S33) {
            if (country != null) {
                editCountry.setVisibility(VISIBLE);
                errorCountryMessage.setVisibility(VISIBLE);
                editCountry.setText(country.getPais());
                editCountry.setIsValid();
            } else {
                editCountry.setText("");
                editCountry.setVisibility(VISIBLE);
                editCountry.imageViewIsGone(false);
                editCountry.setDrawableImage(R.drawable.menu_canvas);
                errorCountryMessage.setVisibility(VISIBLE);
            }
        } else {
            hideValidationError(editCountry.getId());
            editCountry.setVisibility(GONE);
            errorCountryMessage.setVisibility(GONE);
            errorCountryMessage.setMessageText("");
            country = null;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setPersonData() {
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
        //registerUser.setNacionalidad("MX");
        if (country != null) {
            registerUser.setPaisNacimiento(country);
            //registerUser.setNacionalidad(country.getIdPais());
        } else {
            registerUser.setPaisNacimiento(new Countries(127, "Mexico", "MX"));
        }
        registerUser.setLugarNacimiento(lugarNacimiento);
        registerUser.setIdEstadoNacimineto(idEstadoNacimiento);

        if (BuildConfig.DEBUG) {
            onValidationSuccess();
        } else {
        accountPresenter.validatePersonData();
        }
    }

    @Override
    public void onValidationSuccess() {
        nextScreen(EVENT_SELFIE, null);//Mostramos siguiente pantalla de registro.
    }

    @Override
    public void getDataForm() {
        genero = radioBtnMale.isChecked() ? "H" : radioBtnFemale.isChecked() ? "M" : "";
        nombre = editNames.getText().toString();
        apPaterno = editFirstLastName.getText().toString();
        apMaterno = editSecoundLastName.getText().toString();
      /*
        if (spinnergenero.getSelectedItemPosition() != 0) {
            genero =spinnergenero.getSelectedItemPosition() == 1 ?"H":spinnergenero.getSelectedItemPosition() == 2? "M":"";
        }
        */
        if (spinnerBirthPlace.getSelectedItemPosition() != 0) {

            subfechanacimiento.setVisibility(View.VISIBLE);
            lugarNacimiento = spinnerBirthPlace.getSelectedItem().toString();
            StatesSpinnerAdapter adapter = (StatesSpinnerAdapter) spinnerBirthPlace.getAdapter();
            idEstadoNacimiento = Integer.toString(((IEnumSpinner) spinnerBirthPlace.getSelectedItem()).getId());
        }
    }

    private void setCurrentData() {
        RegisterUser registerUser = RegisterUser.getInstance();
        if (registerUser.getGenero().equals("H")) {
            radioBtnMale.setChecked(true);
            radioBtnMale.setBackgroundResource(R.drawable.ico_maleb);
        } else if (registerUser.getGenero().equals("M")) {
            radioBtnFemale.setChecked(true);
            radioBtnFemale.setBackgroundResource(R.drawable.ico_femaleb);
        }

        editNames.setText(registerUser.getNombre());
        editFirstLastName.setText(registerUser.getApellidoPaterno());
        editSecoundLastName.setText(registerUser.getApellidoMaterno());
        editBirthDay.setText(registerUser.getFechaNacimientoToShow());
        fechaNacimiento = registerUser.getFechaNacimiento();

        if (registerUser.getPaisNacimiento() != null) {
            country = registerUser.getPaisNacimiento();
        }
        spinnerBirthPlace.setSelection(States.getItemByName(registerUser.getLugarNacimiento()).getId());

        //Actualizamos el newDate para no tener null, solo en evento Back
        if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
            newDate = Calendar.getInstance(new Locale("es"));
            try {
                SimpleDateFormat format = new SimpleDateFormat(DateUtil.simpleDateFormatFirstYear, new Locale("es"));
                newDate.setTime(format.parse(fechaNacimiento));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //1975 - 06 - 29
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
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void showError(Object error) {
        errorVerificationData++;
        String titulo = "", text = "";
        if (!error.toString().isEmpty() && errorVerificationData < 4) {
            //  UI.showToastShort(error.toString(), getActivity());
            text = getString(R.string.problem_with_register1);
            UI.createSimpleCustomDialog("", text, getFragmentManager(),
                    new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {

                        }

                        @Override
                        public void actionCancel(Object... params) {

                        }
                    }, true, false);
        } else {
            text = getString(R.string.problem_with_register2);
            titulo = getString(R.string.titulo_extranjero);
            seencuentra = true;
            UI.createCustomDialogextranjero(titulo, text, getFragmentManager(), getFragmentTag(), new DialogDoubleActions() {
                @Override
                public void actionConfirm(Object... params) {
                    llamar();
                }

                @Override
                public void actionCancel(Object... params) {
                    /*llamar();*/
                }
            }, "", "Llamar");
        }
    }

    @Override
    public void onSpinnerClick() {
        hideValidationError(spinnerBirthPlace.getId());
    }

    @Override
    public void onSubSpinnerClick() {

    }

    @Override
    public void hideKeyBoard() {

    }

    @Override
    public void onValidateUserDataSuccess() {
        //onValidationSuccess();
    }

    @Override
    public void onCountrySelectedListener(Countries item) {
        country = item;
        editCountry.setText(country.getPais());
        editCountry.setIsValid();
    }

    @Override
    public void bucaPais(List<String> paises, String pais) {
        seencuentra = false;
        paises.add("AF");
        paises.add("ET");
        paises.add("IQ");
        paises.add("IR");
        paises.add("KP");
        paises.add("LA");
        paises.add("SY");
        paises.add("UG");
        paises.add("VU");
        paises.add("YE");
        paises.add("BA");
        for (int i = 0; i < paises.size(); i++) {
            if (paises.get(i).equals(pais)) {
                String text = getString(R.string.problem_with_register2);
                String titulo = getString(R.string.titulo_extranjero);
                seencuentra = true;
                UI.createCustomDialogextranjero(titulo, text, getFragmentManager(), getFragmentTag(), new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        llamar();
                    }

                    @Override
                    public void actionCancel(Object... params) {
                        llamar();
                    }
                }, " ", "Llamar");

            }
            if (!seencuentra && i == paises.size() - 1) {
                setPersonData();
            }
        }
    }
}