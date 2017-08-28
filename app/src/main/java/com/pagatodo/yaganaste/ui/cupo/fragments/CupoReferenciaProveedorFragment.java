package com.pagatodo.yaganaste.ui.cupo.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterCupo;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT_PROVEEDOR;

/**
 * Created by Jordan on 26/07/2017.
 */

public class CupoReferenciaProveedorFragment extends GenericFragment implements View.OnClickListener, ValidationForms {

    protected View rootview;

    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.layoutContent)
    BorderTitleLayout borderTitleLayout;
    private CupoActivityManager cupoActivityManager;
    @BindView(R.id.imgContact)
    ImageView imgContact;

    // Campos a llenar
    @BindView(R.id.editNameReferenciaCupo)
    CustomValidationEditText editNameReferenciaCupo;
    @BindView(R.id.editFirstLastNameReferencuaCupo)
    CustomValidationEditText editFirstLastNameReferencuaCupo;
    @BindView(R.id.editSecoundLastNameReferenciaCupo)
    CustomValidationEditText editSecoundLastNameReferenciaCupo;
    @BindView(R.id.editPhoneReferenciaCupoProveedor)
    StyleEdittext editPhoneReferenciaCupoProveedor;
    @BindView(R.id.editProductCupo)
    CustomValidationEditText editProductCupo;

    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String telefono;
    private String productoServicio;

    // Errores Views
    @BindView(R.id.errorNameReferenciaCupo)
    ErrorMessage errorNameReferenciaCupo;
    @BindView(R.id.errorFirstLastNameReferencuaCupo)
    ErrorMessage errorFirstLastNameReferencuaCupo;
    @BindView(R.id.errorSecoundLastNameReferenciaCupo)
    ErrorMessage errorSecoundLastNameReferenciaCupo;
    @BindView(R.id.errorPhoneReferenciaCupo)
    ErrorMessage errorPhoneReferenciaCupo;
    @BindView(R.id.errorProductCupo)
    ErrorMessage errorProductCupo;

    private Boolean reenviar;

    public static CupoReferenciaProveedorFragment newInstance(Boolean reenviar) {
        CupoReferenciaProveedorFragment fragment = new CupoReferenciaProveedorFragment();
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

    @Override
    public void onResume() {
        String a = RegisterCupo.getInstance().getProveedorNombre();
        if (!a.equals("")) {
            cargarDatos();
        }
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_cupo_referencia_proveedor, container, false);
        reenviar = getArguments().getBoolean("reenviar");
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        borderTitleLayout.setTitle(getResources().getString(R.string.titulo_referencia_proveedor));

        editPhoneReferenciaCupoProveedor.setInputType(InputType.TYPE_CLASS_NUMBER);
        editPhoneReferenciaCupoProveedor.addTextChangedListener(new PhoneTextWatcher(editPhoneReferenciaCupoProveedor));
        editPhoneReferenciaCupoProveedor.setHint(getString(R.string.phone_number_hint));

        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        setValidationRules();

    }

    private void cargarDatos() {
        RegisterCupo registerCupo = RegisterCupo.getInstance();
        editNameReferenciaCupo.setText(registerCupo.getProveedorNombre());
        editFirstLastNameReferencuaCupo.setText(registerCupo.getProveedorApellidoPaterno());
        editSecoundLastNameReferenciaCupo.setText(registerCupo.getPersonalApellidoMaterno());
        editPhoneReferenciaCupoProveedor.setText(registerCupo.getProveedorTelefono());
        editProductCupo.setText(registerCupo.getProveedorProductoServicio());
    }


    @OnClick(R.id.imgContact)
    public void obtenerNumeroProveedor(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        getActivity().startActivityForResult(contactPickerIntent, CONTACTS_CONTRACT_PROVEEDOR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CONTACTS_CONTRACT_PROVEEDOR) {
                editPhoneReferenciaCupoProveedor.setText(Utils.contactPicked(data, getContext()));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                removeData();
                cupoActivityManager.onBtnBackPress();
                break;
            case R.id.btnNext:
                //cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_DOMICILIO_PERSONAL, null);
                validateForm();
                break;
        }
    }

    private void removeData() {
        RegisterCupo registerCupo = RegisterCupo.getInstance();
        registerCupo.setProveedorNombre("");
        registerCupo.setProveedorApellidoPaterno("");
        registerCupo.setProveedorApellidoMaterno("");
        registerCupo.setProveedorTelefono("");
        registerCupo.setProveedorProductoServicio("");
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


        editPhoneReferenciaCupoProveedor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editPhoneReferenciaCupoProveedor.getId());
                } else {
                    if (editPhoneReferenciaCupoProveedor.getText().toString().equals("")) {
                        showValidationError(editPhoneReferenciaCupoProveedor.getId(), getString(R.string.numero_telefono_vacio));
                    } else if (!ValidateForm.isValidCellPhone(editPhoneReferenciaCupoProveedor.getText().toString())) {
                        showValidationError(editPhoneReferenciaCupoProveedor.getId(), getString(R.string.numero_telefono_incorrecto));
                    } else {
                        hideValidationError(editPhoneReferenciaCupoProveedor.getId());
                    }
                }
            }
        });


        editProductCupo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editProductCupo.getId());
                    editProductCupo.imageViewIsGone(true);
                } else {
                    if (editProductCupo.getText().isEmpty()) {
                        showValidationError(editProductCupo.getId(), getString(R.string.datos_personal_materno));
                        editProductCupo.setIsInvalid();
                    } else {
                        hideValidationError(editProductCupo.getId());
                        editProductCupo.setIsValid();
                    }
                }
            }
        });


        editProductCupo.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editProductCupo.getId());
                editProductCupo.imageViewIsGone(true);
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

        if (telefono.equals("")){
            showValidationError(editPhoneReferenciaCupoProveedor.getId(), getString(R.string.numero_telefono_vacio));
            isValid = false;
        } else if (!ValidateForm.isValidCellPhone(telefono)) {
            showValidationError(editPhoneReferenciaCupoProveedor.getId(), getString(R.string.numero_telefono_incorrecto));
            isValid = false;
        }

        if (productoServicio.isEmpty()) {
            showValidationError(editProductCupo.getId(), getString(R.string.producto_servicio_requerido));
            editProductCupo.setIsInvalid();
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
            case R.id.editPhoneReferenciaCupoProveedor:
                errorPhoneReferenciaCupo.setMessageText(error.toString());
                break;
            case R.id.editProductCupo:
                errorProductCupo.setMessageText(error.toString());
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
            case R.id.editPhoneReferenciaCupoProveedor:
                errorPhoneReferenciaCupo.setVisibilityImageError(false);
                break;
            case R.id.editProductCupo:
                errorProductCupo.setVisibilityImageError(false);
                break;
        }
    }

    @Override
    public void onValidationSuccess() {

        if (reenviar) {
            Log.e("Test", "Reenviar proveedor");

        } else {
            /*Guardamos datos en Singleton de registro.*/
            errorNameReferenciaCupo.setVisibilityImageError(false);
            errorFirstLastNameReferencuaCupo.setVisibilityImageError(false);
            errorSecoundLastNameReferenciaCupo.setVisibilityImageError(false);
            errorProductCupo.setVisibilityImageError(false);
            errorPhoneReferenciaCupo.setVisibilityImageError(false);

            RegisterCupo registerCupo = RegisterCupo.getInstance();
            registerCupo.setProveedorNombre(nombre);
            registerCupo.setProveedorApellidoPaterno(primerApellido);
            registerCupo.setProveedorApellidoMaterno(segundoApellido);
            registerCupo.setProveedorTelefono(telefono);
            registerCupo.setProveedorProductoServicio(productoServicio);

            Log.e("Proveedor nombre",        registerCupo.getProveedorNombre());
            Log.e("Proveedor paterno", ""  + registerCupo.getProveedorApellidoPaterno());
            Log.e("Proveedor Materno", ""  + registerCupo.getProveedorApellidoMaterno());
            Log.e("Proveedor Telefono", "" + registerCupo.getProveedorTelefono());
            Log.e("Proveedor Producto", "" + registerCupo.getProveedorProductoServicio());
            cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_DOMICILIO_PERSONAL, null);
        }



    }

    @Override
    public void getDataForm() {
        nombre = editNameReferenciaCupo.getText().trim();
        primerApellido = editFirstLastNameReferencuaCupo.getText().trim();
        segundoApellido = editSecoundLastNameReferenciaCupo.getText().trim();
        telefono = editPhoneReferenciaCupoProveedor.getText().toString().replace(" ", "");
        productoServicio = editProductCupo.getText().trim();
    }
}
