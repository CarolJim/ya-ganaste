package com.pagatodo.yaganaste.ui.cupo.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterCupo;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.interfaces.enums.CupoSpinnerTypes;
import com.pagatodo.yaganaste.interfaces.enums.Relaciones;
import com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.ui.cupo.CupoSpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.cupo.managers.CupoActivityManager;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.PhoneTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.BorderTitleLayout;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT_FAMILIAR;

/**
 * Created by Jordan on 26/07/2017.
 */

public class CupoReferenciaFamiliarFragment extends GenericFragment implements View.OnClickListener, ValidationForms, AdapterView.OnItemSelectedListener {
    protected View rootview;

    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.btnNext)
    Button btnNext;
    private CupoActivityManager cupoActivityManager;
    @BindView(R.id.layoutContent)
    BorderTitleLayout borderTitleLayout;
    @BindView(R.id.spRelationshipCupo)
    Spinner spRelationshipCupo;
    @BindView(R.id.errorRelationshipCupo)
    ErrorMessage errorRelationshipCupo;
    @BindView(R.id.imgContact)
    ImageView imgContact;

    // Campos a llenar
    @BindView(R.id.editNameReferenciaCupo)
    CustomValidationEditText editNameReferenciaCupo;
    @BindView(R.id.editFirstLastNameReferencuaCupo)
    CustomValidationEditText editFirstLastNameReferencuaCupo;
    @BindView(R.id.editSecoundLastNameReferenciaCupo)
    CustomValidationEditText editSecoundLastNameReferenciaCupo;
    @BindView(R.id.editPhoneReferenciaCupo)
    StyleEdittext editPhoneReferenciaCupo;


    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String telefono;

    private String relacion = "";
    private int idRelacion = 0;

    // Errores Views
    @BindView(R.id.errorNameReferenciaCupo)
    ErrorMessage errorNameReferenciaCupo;
    @BindView(R.id.errorFirstLastNameReferencuaCupo)
    ErrorMessage errorFirstLastNameReferencuaCupo;
    @BindView(R.id.errorSecoundLastNameReferenciaCupo)
    ErrorMessage errorSecoundLastNameReferenciaCupo;
    @BindView(R.id.errorPhoneReferenciaCupo)
    ErrorMessage errorPhoneReferenciaCupo;

    private CupoSpinnerArrayAdapter relacionAdapter;
    private Boolean reenviar;


    public static CupoReferenciaFamiliarFragment newInstance(Boolean reenviar) {
        CupoReferenciaFamiliarFragment fragment = new CupoReferenciaFamiliarFragment();
        Bundle args = new Bundle();
        args.putBoolean("reenviar", reenviar);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cupoActivityManager = ((RegistryCupoActivity) getActivity()).getCupoActivityManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_cupo_referencias, container, false);
        reenviar = getArguments().getBoolean("reenviar");
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        borderTitleLayout.setTitle(getResources().getString(R.string.titulo_referencia_familiar));
        relacionAdapter = new CupoSpinnerArrayAdapter(getContext(), Relaciones.values(), CupoSpinnerTypes.RELACION);
        spRelationshipCupo.setAdapter(relacionAdapter);
        spRelationshipCupo.setOnItemSelectedListener(this);

        editPhoneReferenciaCupo.setInputType(InputType.TYPE_CLASS_NUMBER);
        editPhoneReferenciaCupo.addTextChangedListener(new PhoneTextWatcher(editPhoneReferenciaCupo));
        editPhoneReferenciaCupo.setHint(getString(R.string.phone_number_hint));

        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        setValidationRules();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                removeData();
                cupoActivityManager.onBtnBackPress();
                break;
            case R.id.btnNext:
                validateForm();
                break;
        }
    }


    @Override
    public void onResume() {
        String a = RegisterCupo.getInstance().getPersonalNombre();
        if (!a.equals("")) {
            cargarDatos();
        }
        super.onResume();
    }


    private void cargarDatos() {
        RegisterCupo registerCupo = RegisterCupo.getInstance();
        editNameReferenciaCupo.setText(registerCupo.getPersonalNombre());
        editFirstLastNameReferencuaCupo.setText(registerCupo.getPersonalApellidoPaterno());
        editSecoundLastNameReferenciaCupo.setText(registerCupo.getPersonalApellidoMaterno());
        editPhoneReferenciaCupo.setText(registerCupo.getPersonalTelefono());
    }


    private void removeData () {
        RegisterCupo registerCupo = RegisterCupo.getInstance();
        registerCupo.setFamiliarNombre("");
        registerCupo.setFamiliarApellidoPaterno("");
        registerCupo.setFamiliarApellidoMaterno("");
        registerCupo.setFamiliarTelefono("");
        registerCupo.setFamiliarRelacion("");
        registerCupo.setFamiliarIdRelacion(0);
    }

    @OnClick(R.id.imgContact)
    public void obtenerNumeroContacto(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        getActivity().startActivityForResult(contactPickerIntent, CONTACTS_CONTRACT_FAMILIAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CONTACTS_CONTRACT_FAMILIAR) {
                editPhoneReferenciaCupo.setText(Utils.contactPicked(data, getContext()));
            }
        }
    }




    @Override
    public void setValidationRules() {

        editNameReferenciaCupo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editNameReferenciaCupo.getId());
                    editNameReferenciaCupo.imageViewIsGone(true);
                } else {
                    if (editNameReferenciaCupo.getText().isEmpty()) {
                        showValidationError(editNameReferenciaCupo.getId(), getString(R.string.datos_personal_nombre));
                        editNameReferenciaCupo.setIsInvalid();
                    } else {
                        hideValidationError(editNameReferenciaCupo.getId());
                        editNameReferenciaCupo.setIsValid();
                    }
                }
            }
        });

        editNameReferenciaCupo.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editNameReferenciaCupo.getId());
                editNameReferenciaCupo.imageViewIsGone(true);
            }
        });

        editFirstLastNameReferencuaCupo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editFirstLastNameReferencuaCupo.getId());
                    editFirstLastNameReferencuaCupo.imageViewIsGone(true);
                } else {
                    if (editFirstLastNameReferencuaCupo.getText().isEmpty()) {
                        showValidationError(editFirstLastNameReferencuaCupo.getId(), getString(R.string.datos_personal_paterno));
                        editFirstLastNameReferencuaCupo.setIsInvalid();
                    } else {
                        hideValidationError(editFirstLastNameReferencuaCupo.getId());
                        editFirstLastNameReferencuaCupo.setIsValid();
                    }
                }
            }
        });

        editFirstLastNameReferencuaCupo.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editFirstLastNameReferencuaCupo.getId());
                editFirstLastNameReferencuaCupo.imageViewIsGone(true);
            }
        });

        editSecoundLastNameReferenciaCupo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editSecoundLastNameReferenciaCupo.getId());
                    editSecoundLastNameReferenciaCupo.imageViewIsGone(true);
                } else {
                    if (editSecoundLastNameReferenciaCupo.getText().isEmpty()) {
                        showValidationError(editSecoundLastNameReferenciaCupo.getId(), getString(R.string.datos_personal_materno));
                        editSecoundLastNameReferenciaCupo.setIsInvalid();
                    } else {
                        hideValidationError(editSecoundLastNameReferenciaCupo.getId());
                        editSecoundLastNameReferenciaCupo.setIsValid();
                    }
                }
            }
        });


        editSecoundLastNameReferenciaCupo.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editSecoundLastNameReferenciaCupo.getId());
                editSecoundLastNameReferenciaCupo.imageViewIsGone(true);
            }
        });


        editPhoneReferenciaCupo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editPhoneReferenciaCupo.getId());
                } else {
                    if (editPhoneReferenciaCupo.getText().toString().equals("")) {
                        showValidationError(editPhoneReferenciaCupo.getId(), getString(R.string.numero_telefono_vacio));
                    } else if (!ValidateForm.isValidCellPhone(editPhoneReferenciaCupo.getText().toString())) {
                        showValidationError(editPhoneReferenciaCupo.getId(), getString(R.string.numero_telefono_incorrecto));
                    } else {
                        hideValidationError(editPhoneReferenciaCupo.getId());
                    }
                }
            }
        });

    }

    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;

        if (nombre.isEmpty()) {
            showValidationError(editNameReferenciaCupo.getId(), getString(R.string.datos_personal_nombre));
            editNameReferenciaCupo.setIsInvalid();
            isValid = false;
        }

        if (primerApellido.isEmpty()) {
            showValidationError(editFirstLastNameReferencuaCupo.getId(), getString(R.string.datos_personal_paterno));
            editFirstLastNameReferencuaCupo.setIsInvalid();
            isValid = false;
        }

        if (segundoApellido.isEmpty()) {
            showValidationError(editSecoundLastNameReferenciaCupo.getId(), getString(R.string.datos_personal_materno));
            editSecoundLastNameReferenciaCupo.setIsInvalid();
            isValid = false;
        }

        if (relacion.isEmpty() || relacion.equals("")) {
            showValidationError(spRelationshipCupo.getId(), getString(R.string.relacion_requerida));
            isValid = false;
        }


        if (telefono.equals("")){
            showValidationError(editPhoneReferenciaCupo.getId(), getString(R.string.numero_telefono_vacio));
            isValid = false;
        } else if (!ValidateForm.isValidCellPhone(telefono)) {
            showValidationError(editPhoneReferenciaCupo.getId(), getString(R.string.numero_telefono_incorrecto));
            isValid = false;
        }

        if (isValid) {
            onValidationSuccess();
        }



    }

    @Override
    public void showValidationError(int id, Object error) {
        switch (id) {
            case R.id.editNameReferenciaCupo:
                errorNameReferenciaCupo.setMessageText(error.toString());
                break;
            case R.id.editFirstLastNameReferencuaCupo:
                errorFirstLastNameReferencuaCupo.setMessageText(error.toString());
                break;
            case R.id.editSecoundLastNameReferenciaCupo:
                errorSecoundLastNameReferenciaCupo.setMessageText(error.toString());
                break;
            case R.id.spRelationshipCupo:
                errorRelationshipCupo.setMessageText(error.toString());
                break;
            case R.id.editPhoneReferenciaCupo:
                errorPhoneReferenciaCupo.setMessageText(error.toString());
                break;
        }
        UI.hideKeyBoard(getActivity());
    }

    @Override
    public void hideValidationError(int id) {
        switch (id) {
            case R.id.editNameReferenciaCupo:
                errorNameReferenciaCupo.setVisibilityImageError(false);
                break;
            case R.id.editFirstLastNameReferencuaCupo:
                errorFirstLastNameReferencuaCupo.setVisibilityImageError(false);
                break;
            case R.id.editSecoundLastNameReferenciaCupo:
                errorSecoundLastNameReferenciaCupo.setVisibilityImageError(false);
                break;
            case R.id.spRelationshipCupo:
                errorRelationshipCupo.setVisibilityImageError(false);

            case R.id.editPhoneReferenciaCupo:
                errorPhoneReferenciaCupo.setVisibilityImageError(false);
                break;
        }
    }

    @Override
    public void onValidationSuccess() {

        if (reenviar) {
            Log.e("Test", "Entre a reenviar");
        } else {
            /*Guardamos datos en Singleton de registro.*/
            errorNameReferenciaCupo.setVisibilityImageError(false);
            errorFirstLastNameReferencuaCupo.setVisibilityImageError(false);
            errorSecoundLastNameReferenciaCupo.setVisibilityImageError(false);
            errorRelationshipCupo.setVisibilityImageError(false);
            errorPhoneReferenciaCupo.setVisibilityImageError(false);

            RegisterCupo registerCupo = RegisterCupo.getInstance();
            registerCupo.setFamiliarNombre(nombre);
            registerCupo.setFamiliarApellidoPaterno(primerApellido);
            registerCupo.setFamiliarApellidoMaterno(segundoApellido);
            registerCupo.setFamiliarTelefono(telefono);
            registerCupo.setFamiliarRelacion(relacion);
            registerCupo.setFamiliarIdRelacion(idRelacion);

            Log.e("Familiar nombre",        registerCupo.getFamiliarNombre() );
            Log.e("Familiar paterno", ""  + registerCupo.getFamiliarApellidoPaterno());
            Log.e("Familiar Materno", ""  + registerCupo.getFamiliarApellidoMaterno());
            Log.e("Familiar Telefono", "" + registerCupo.getFamiliarTelefono());
            Log.e("Familiar Relacion", "" + registerCupo.getFamiliarRelacion());
            Log.e("Familiar IdRelacion",""+ registerCupo.getFamiliarIdRelacion());
            cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_REFERENCIA_PERSONAL, null);
        }
    }

    @Override
    public void getDataForm() {
        nombre = editNameReferenciaCupo.getText().trim();
        primerApellido = editFirstLastNameReferencuaCupo.getText().trim();
        segundoApellido = editSecoundLastNameReferenciaCupo.getText().trim();

        if (spRelationshipCupo.getSelectedItemPosition() != 0) {
            relacion = spRelationshipCupo.getSelectedItem().toString();
            idRelacion = spRelationshipCupo.getSelectedItemPosition();
        } else {
            relacion = "";
            idRelacion = 0;
        }

        telefono = editPhoneReferenciaCupo.getText().toString().replace(" ", "");

        Log.e("Test", "Relacion seleccionada:" + relacion );
        Log.e("Test", "Id de Relación: " + idRelacion );
        Log.e("Test", "Telefono: " +  telefono);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        onSpinnerClick();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        onSpinnerClick();
    }

    private void onSpinnerClick() {
        hideValidationError(spRelationshipCupo.getId());
    }



}
