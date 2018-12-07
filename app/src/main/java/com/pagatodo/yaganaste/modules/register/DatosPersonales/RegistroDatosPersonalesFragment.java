package com.pagatodo.yaganaste.modules.register.DatosPersonales;


import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.RegisterUserNew;
import com.pagatodo.yaganaste.data.room_db.entities.CountryF;
import com.pagatodo.yaganaste.data.room_db.entities.Paises;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IDatosPersonalesManager;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.IRenapoView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.interfaces.enums.Genero;
import com.pagatodo.yaganaste.modules.register.CorreoUsuario.RegistroCorreoFragment;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.register.DatosPersonalesFragment;
import com.pagatodo.yaganaste.ui.account.register.adapters.EnumSpinnerAdapter;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoCountry;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoStates;
import com.pagatodo.yaganaste.ui_wallet.views.Color;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.CountriesDialogFragment;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
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
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_DATA_USER_BACK;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroDatosPersonalesFragment extends GenericFragment implements IOnSpinnerClick ,View.OnClickListener,ValidationForms,AdapterView.OnItemSelectedListener,IRenapoView ,IDatosPersonalesManager {

    private static RegActivity activityf;
    Boolean seencuentra = false;
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
    @BindView(R.id.text_nombre)
    TextInputLayout text_nombre;
    @BindView(R.id.text_apater)
    TextInputLayout text_apater;
    @BindView(R.id.generTitle)
    LinearLayout generTitle;
    @BindView(R.id.txtfecha)
    LinearLayout txtfecha;
    @BindView(R.id.txtlugarnacimiento)
    LinearLayout txtlugarnacimiento;
    @BindView(R.id.lugarnacimientomens)
    StyleTextView lugarnacimientomens;
    @BindView(R.id.editFirstLastName)
    CustomValidationEditText editFirstLastNameold;
    @BindView(R.id.edit_appater)
    EditText editFirstLastName;
    @BindView(R.id.editSecoundLastName)
    CustomValidationEditText editSecoundLastNameold;
    @BindView(R.id.edit_apmaterno)
    EditText editSecoundLastName;
    @BindView(R.id.editBirthDay)
    EditText editBirthDay;
    @BindView(R.id.spinnerBirthPlace)
    AppCompatSpinner spinnerBirthPlace;
    @BindView(R.id.spinnergenero)
    AppCompatSpinner spinnergenero;
    @BindView(R.id.btnNextDatosPersonales)
    StyleButton btnNextDatosPersonales;
    @BindView(R.id.lyt_country)
    TextInputLayout lytCountry;
    @BindView(R.id.editCountry)
    EditText editCountry;
    @BindView(R.id.background_h)
    LinearLayout background_h;
    @BindView(R.id.background_m)
    LinearLayout background_m;
    @BindView(R.id.letraH)
    StyleTextView letraH;
    @BindView(R.id.letraM)
    StyleTextView letraM;




    @BindView(R.id.seleccionaGenero)
    StyleTextView seleccionaGenero;

    AppCompatImageView imageViewCustomSpinner;

    StatesSpinnerAdapter adapterBirthPlace;
    EnumSpinnerAdapter adaptergenero;
    Calendar newDate;
    Calendar actualDate;
    private View rootview;
    private String genero = "";
    private String nombre = "";
    private String apPaterno = "";
    private String apMaterno = "";
    private String fechaNacimiento = "";
    private String curp = "";
    private Paises country;
    int year;
    int month;
    int day;
    private int errorVerificationData = 0;
    private List<DtoStates> states = new ArrayList<>();
    private List<DtoCountry> countrys = new ArrayList<>();

    List<Paises> paises = new ArrayList<Paises>();
    View rootView;



    View.OnClickListener onClickListenerDatePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          //  hideValidationError(editBirthDay.getId());
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

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_TRADITIONAL, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                    newDate = Calendar.getInstance(new Locale("es"));
                    newDate.set(year, month, date);
                    // editBirthDay.setText(DateUtil.getBirthDateCustomString(newDate));
                    editBirthDay.setText(DateUtil.getBirthDateSpecialCustom(year, month, date));
                    // editBirthDay.setIsValid();
                    txtfecha.setBackgroundResource(R.drawable.inputtext_normal);

                    fechaNacimiento = DateUtil.getDateStringFirstYear(newDate);

                    Calendar mCalendar = Calendar.getInstance();
                    mCalendar.set(actualDate.get(Calendar.YEAR) - 18, actualDate.get(Calendar.MONTH), actualDate.get(Calendar.DAY_OF_MONTH));

                    if (newDate.getTimeInMillis() > actualDate.getTimeInMillis()) {
                      //  showValidationError(editBirthDay.getId(), getString(R.string.datos_personal_fecha));
                        // editBirthDay.setIsInvalid();

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        UI.showErrorSnackBar(getActivity(), getString(R.string.datos_personal_fecha), Snackbar.LENGTH_SHORT);
                        txtfecha.setBackgroundResource(R.drawable.inputtext_error);
                        return;
                    }

                    if (newDate.getTimeInMillis() > mCalendar.getTimeInMillis()) {
                     //   showValidationError(editBirthDay.getId(), getString(R.string.datos_personal_mayor_edad));
                        //  editBirthDay.setIsInvalid();
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        UI.showErrorSnackBar(getActivity(), getString(R.string.datos_personal_mayor_edad), Snackbar.LENGTH_SHORT);
                        txtfecha.setBackgroundResource(R.drawable.inputtext_error);

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

    public static RegistroDatosPersonalesFragment newInstance(){
        return new RegistroDatosPersonalesFragment();
    }

    public static RegistroDatosPersonalesFragment newInstance(RegActivity activity){
        activityf = activity;
        return  new RegistroDatosPersonalesFragment();
    }
    public RegistroDatosPersonalesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountPresenter = new AccountPresenterNew(getActivity());
        accountPresenter.setIView(this);

        DatabaseReference ref = App.getDatabaseReference().child("Mexico").child("AppEngine").child("CatComponents");
        ref.child("County").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    states = new ArrayList<>();
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        DtoStates item = singleSnapshot.getValue(DtoStates.class);
                        states.add(item);
                    }
                    states.add(0, new DtoStates("0", "Lugar de nacimiento", "Prefijo"));
                    adapterBirthPlace = new StatesSpinnerAdapter(getContext(), R.layout.spinner_layout,
                            states, RegistroDatosPersonalesFragment.this);
                    spinnerBirthPlace.setAdapter(adapterBirthPlace);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref.child("Country").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    countrys = new ArrayList<>();
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        DtoCountry item = singleSnapshot.getValue(DtoCountry.class);
                        countrys.add(item);

                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void changecolorradio() {
        if (radioBtnMale.isChecked()) {
            background_h.setBackgroundResource(R.color.colorAccent);
            background_m.setBackgroundResource(R.color.whiteColor);
            radioBtnMale.setBackgroundResource(R.drawable.masc);
            radioBtnFemale.setBackgroundResource(R.drawable.fem);
            generTitle.setBackgroundResource(R.drawable.inputtext_normal);
            letraM.setTextColor(getResources().getColor(R.color.gristexto));
            letraH.setTextColor(getResources().getColor(R.color.whiteColor));
        } else {
            if (radioBtnFemale.isChecked()) {
                radioBtnMale.setBackgroundResource(R.drawable.masc);
                radioBtnFemale.setBackgroundResource(R.drawable.fem);
                background_h.setBackgroundResource(R.color.whiteColor);
                background_m.setBackgroundResource(R.color.colorAccent);
                generTitle.setBackgroundResource(R.drawable.inputtext_normal);
                letraM.setTextColor(getResources().getColor(R.color.whiteColor));
                letraH.setTextColor(getResources().getColor(R.color.gristexto));
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_registro_datos_personales, container, false);
        actualDate = Calendar.getInstance(new Locale("es"));
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        activityf.nextStep();
        radioBtnMale.setOnClickListener(this);
        radioBtnFemale.setOnClickListener(this);
        background_h.setOnClickListener(this);
        background_m.setOnClickListener(this);
        txtfecha.setOnClickListener(onClickListenerDatePicker);
        editBirthDay.setOnClickListener(onClickListenerDatePicker);
        adaptergenero = new EnumSpinnerAdapter(getContext(), R.layout.spinner_layout, Genero.values(), this);
        spinnerBirthPlace.setOnItemSelectedListener(this);
        spinnergenero.setAdapter(adaptergenero);
        spinnergenero.setOnItemSelectedListener(this);
        editCountry.setOnClickListener(this);
        btnNextDatosPersonales.setOnClickListener(this);
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

        setCurrentData();// Seteamos datos si hay registro en proceso.
        setValidationRules();


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

    private void onCountryClick() {

        paises.clear();
        for (DtoCountry dtoCountry : countrys){
            if(dtoCountry.Valido){
             Paises paisesa=  new Paises(Integer.parseInt(dtoCountry.IdPaisFirebase),dtoCountry.Nombre,dtoCountry.Valor);

            paises.add(paisesa );
            }

        }

        showDialogList(paises);

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


        if (registerUser.getPaisNacimiento() != null) {
            country = registerUser.getPaisNacimiento();
        }
        int position = 0;
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i).ID_EntidadNacimiento.equals(registerUser.getLugarNacimiento()))
                position = i;
        }
        spinnerBirthPlace.setSelection(position);
        fechaNacimiento = registerUser.getFechaNacimiento();
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
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBackPersonalInfo:
               // backScreen(EVENT_DATA_USER_BACK, null);
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
            case R.id.background_h:
                radioBtnMale.setChecked(true);
                radioBtnFemale.setChecked(false);
                changecolorradio();
                break;
            case R.id.background_m:
                radioBtnMale.setChecked(false);
                radioBtnFemale.setChecked(true);
                changecolorradio();
                break;
                case R.id.btnNextDatosPersonales:
                    validateForm();

                break;



            default:
                break;
        }

    }

    @Override
    public void setValidationRules() {
        editNames.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editNames.getId());
                    //  editNames.imageViewIsGone(true);
                    text_nombre.setBackgroundResource(R.drawable.inputtext_active);
                } else {
                    if (editNames.getText().toString().isEmpty()) {
                        //   showValidationError(editNames.getId(), getString(R.string.datos_personal_nombre));
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        UI.showErrorSnackBar(getActivity(), getString(R.string.datos_personal_nombre), Snackbar.LENGTH_SHORT);
                        text_nombre.setBackgroundResource(R.drawable.inputtext_error);
                        //    editNames.setIsInvalid();
                    } else {
                        //  hideValidationError(editNames.getId());
                        text_nombre.setBackgroundResource(R.drawable.inputtext_normal);
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
                    //  hideValidationError(editFirstLastName.getId());
                    text_apater.setBackgroundResource(R.drawable.inputtext_active);
                    //     editFirstLastName.imageViewIsGone(true);
                } else {
                    if (editFirstLastName.getText().toString().isEmpty()) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        UI.showErrorSnackBar(getActivity(), getString(R.string.datos_personal_paterno), Snackbar.LENGTH_SHORT);
                        text_apater.setBackgroundResource(R.drawable.inputtext_error);
                        // showValidationError(editFirstLastName.getId(), getString(R.string.datos_personal_paterno));
                        //       editFirstLastName.setIsInvalid();
                    } else {
                        //hideValidationError(editFirstLastName.getId());
                        text_apater.setBackgroundResource(R.drawable.inputtext_normal);
                        // editFirstLastName.setIsValid();
                    }
                }
            }
        });

    }

    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;
        if (genero == null || genero.equals("")) {
            // showValidationError(spinnergenero.getId(), getString(R.string.datos_personal_genero));
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_personal_genero), Snackbar.LENGTH_SHORT);
            generTitle.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }

        if (nombre.isEmpty()) {
            //showValidationError(editNames.getId(), getString(R.string.datos_personal_nombre));
            //  editNames.setIsInvalid();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_personal_nombre), Snackbar.LENGTH_SHORT);
            text_nombre.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }
        if (apPaterno.isEmpty()) {
            //  showValidationError(editFirstLastName.getId(), getString(R.string.datos_personal_paterno));
            //  editFirstLastName.setIsInvalid();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_personal_paterno), Snackbar.LENGTH_SHORT);
            text_apater.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }

        if (!fechaNacimiento.isEmpty() && newDate != null && (newDate.getTimeInMillis() > actualDate.getTimeInMillis())) {
            //showValidationError(editBirthDay.getId(), getString(R.string.datos_personal_fecha));
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_personal_fecha), Snackbar.LENGTH_SHORT);
            txtfecha.setBackgroundResource(R.drawable.inputtext_error);
            //editBirthDay.setIsInvalid();
            isValid = false;
        }

        if (!fechaNacimiento.isEmpty() && actualDate != null) {

            Calendar mCalendar = Calendar.getInstance();
            mCalendar.set(actualDate.get(Calendar.YEAR) - 18, actualDate.get(Calendar.MONTH), actualDate.get(Calendar.DAY_OF_MONTH));

            if (newDate.getTimeInMillis() > mCalendar.getTimeInMillis()) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                UI.showErrorSnackBar(getActivity(), getString(R.string.datos_personal_mayor_edad), Snackbar.LENGTH_SHORT);
                txtfecha.setBackgroundResource(R.drawable.inputtext_error);
                //
                showValidationError(editBirthDay.getId(), getString(R.string.datos_personal_mayor_edad));
                // editBirthDay.setIsInvalid();
                isValid = false;
            }
        }

        if (fechaNacimiento.isEmpty()) {
            //   showValidationError(editBirthDay.getId(), getString(R.string.datos_personal_fecha));
            // editBirthDay.setIsInvalid();
            //showValidationError(editBirthDay.getId(), getString(R.string.datos_personal_fecha));
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_personal_fecha), Snackbar.LENGTH_SHORT);
            txtfecha.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }

        if (lugarNacimiento.isEmpty()) {
            //  showValidationError(spinnerBirthPlace.getId(), getString(R.string.datos_personal_estado));

            //showValidationError(editBirthDay.getId(), getString(R.string.datos_personal_fecha));
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_personal_estado), Snackbar.LENGTH_SHORT);
            txtlugarnacimiento.setBackgroundResource(R.drawable.inputtext_error);

            isValid = false;
        }

        if (!lugarNacimiento.isEmpty() && lugarNacimiento.equals("Extranjero")) {
            if (country == null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                UI.showErrorSnackBar(getActivity(), getString(R.string.datos_personal_estado), Snackbar.LENGTH_SHORT);
                txtlugarnacimiento.setBackgroundResource(R.drawable.inputtext_error);
                // showValidationError(R.id.editCountry, getString(R.string.datos_personal_pais));
                isValid = false;
            }
        }
        if (isValid) {
                setPersonData();
          //  activityf.showFragmentDomicilioIngresaCP();
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
        activityf.showFragmentDomicilioIngresaCP();
    }
    private void setPersonData() {
        //Almacenamos la información para el registro
        RegisterUserNew registerUser = RegisterUserNew.getInstance();
        registerUser.setGenero(genero);
        registerUser.setNombre(nombre);
        registerUser.setApellidoPaterno(apPaterno);
        registerUser.setApellidoMaterno(apMaterno);
        registerUser.setFechaNacimientoToShow(editBirthDay.getText().toString());
        registerUser.setFechaNacimiento(fechaNacimiento);
        //registerUser.setNacionalidad("MX");
        if (country != null) {
            registerUser.setPaisNacimiento(country);
            //registerUser.setNacionalidad(country.getIdPais());
        } else {
            registerUser.setPaisNacimiento(new Paises(127, "Mexico", "MX"));
        }
        registerUser.setLugarNacimiento(lugarNacimiento);
        registerUser.setIdEstadoNacimineto(idEstadoNacimiento);

        if (BuildConfig.DEBUG) {
            onValidationSuccess();
        } else {
            accountPresenter.validatePersonDatanew();
        }
    }
    @Override
    public void getDataForm() {
        genero = radioBtnMale.isChecked() ? "H" : radioBtnFemale.isChecked() ? "M" : "";
        nombre = editNames.getText().toString();
        apPaterno = editFirstLastName.getText().toString();
        apMaterno = editSecoundLastName.getText().toString();
        if (spinnerBirthPlace.getSelectedItemPosition() != 0) {
            lugarNacimiento = spinnerBirthPlace.getSelectedItem().toString();
            idEstadoNacimiento = ((DtoStates) spinnerBirthPlace.getSelectedItem()).ID_EntidadNacimiento;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        DtoStates itemSelected = adapterBirthPlace.getItem(position);
        txtlugarnacimiento.setBackgroundResource(R.drawable.inputtext_normal);

        if (itemSelected.ID_EntidadNacimiento.equals("0")) {
        } else {
            lugarnacimientomens.setVisibility(VISIBLE);
            lugarnacimientomens.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        if (itemSelected.ID_EntidadNacimiento.equals("33")) {
            if (country != null) {
                lytCountry.setVisibility(VISIBLE);
                //errorCountryMessage.setVisibility(VISIBLE);
                editCountry.setText(country.getPais());
                lytCountry.setBackgroundResource(R.drawable.inputtext_normal);
            } else {
                editCountry.setText("");
                lytCountry.setVisibility(VISIBLE);
                lytCountry.setBackgroundResource(R.drawable.inputtext_error);
                //errorCountryMessage.setVisibility(VISIBLE);
            }
        } else {
            hideValidationError(editCountry.getId());
            lytCountry.setVisibility(GONE);
            //errorCountryMessage.setVisibility(GONE);
            //errorCountryMessage.setMessageText("");
            country = null;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onValidateUserDataSuccess() {
        onValidationSuccess();
    }

    @Override
    public void onHomonimiaError() {
        String CURP = "";
        UI.createSimpleCustomDialogCURP(getString(R.string.ingresa_tu_curp), "",
                getFragmentManager(), new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        accountPresenter.validatePersonDataHomonimia();
                    }

                    @Override
                    public void actionCancel(Object... params) {
                    }
                }, true, false);
    }

    @Override
    public void showDialogList(List<Paises> paises) {
        CountriesDialogFragment dialogFragment = CountriesDialogFragment.newInstance(paises);
        dialogFragment.setOnCountrySelectedListener(this);
        dialogFragment.show(getChildFragmentManager(), "FragmentDialog");
    }

    @Override
    public void showDialogListCountryF(List<CountryF> paises) {


    }

    @Override
    public void nextScreen(String event, Object data) {

    }

    @Override
    public void backScreen(String event, Object data) {

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
            UI.showAlertDialog(getActivity(), getResources().getString(R.string.app_name), text,
                    R.string.title_aceptar, (dialogInterface, i) -> {
                    });
        } else {
            text = getString(R.string.problem_with_register2);
            titulo = getString(R.string.titulo_extranjero);
            seencuentra = true;
            UI.showAlertDialogLlamar(getActivity(), titulo, text, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    llamar();
                }
            });

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
    @Override
    public void onCountrySelectedListener(Paises item) {
        country = item;
        editCountry.setText(country.getPais());
        lytCountry.setBackgroundResource(R.drawable.inputtext_normal);

    }
}
