package com.pagatodo.yaganaste.modules.payments.mobiletopup.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Recarga;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.modules.payments.mobiletopup.presenter.MobileTopUpPresenter;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.presenters.RecargasPresenter;
import com.pagatodo.yaganaste.ui_wallet.fragments.PaymentFormFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.MaterialButton;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import java.util.Objects;

import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;
import static com.pagatodo.yaganaste.utils.Constants.IAVE_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class MobileTopUpFragment extends GenericFragment implements MobileTopUpView, View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private Favoritos favoritos;
    private boolean isFavorite;
    private Comercio comercioResponse;
    private boolean isRecarga;

    private View rootView;

    @BindView(R.id.btn_continue_payment)
    StyleButton btnContinue;
    @BindView(R.id.imgMontoPayments)
    private ImageView imgmonto;
    @BindView(R.id.spAmountPayments)
    Spinner spnMontoRecarga;
    @BindView(R.id.etPhonePayments)
    EditText edtPhoneNumber;
    @BindView(R.id.layoutphonePayments)
    private LinearLayout layoutPhone;
    private MobileTopUpPresenter recargasPresenter;
    private boolean isValid;
    private String errorText;

    public MobileTopUpFragment() {
        // Required empty public constructor
    }

    public static GenericFragment newInstance(Object object){
        PaymentFormFragment fragment = new PaymentFormFragment();
        Bundle args = new Bundle();
        if (object instanceof Comercio)
            args.putSerializable(ARG_PARAM1, (Comercio)object);
        else if(object instanceof Favoritos)
            args.putSerializable(ARG_PARAM1, (Favoritos)object);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mobiletopup, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Creamos el presentes del favorito
        //iPresenterPayment = new PresenterPaymentFragment(this);
        isFavorite = false;
        /**
         * Sin importar que sea un proceso de comercioResponse o favoritos, siempre trabaajreos con
         * ccomercioResponse. En el caso de favorito, accesamos a las propiedades del comercio y lo
         * asignamos
         */
        if (getArguments() != null) {
            // Verifiamos si es una recarga o un pds

            if (getArguments().getSerializable(ARG_PARAM1) instanceof Favoritos) {
               /* favoritos = (Favoritos) getArguments().getSerializable(ARG_PARAM1);
                if (favoritos != null) {
                    if (favoritos.getIdFavorito() >= 0) {
                        comercioResponse = iPresenterPayment.getComercioById(favoritos.getIdComercio());
                    }
                    if (favoritos.getIdTipoComercio() == 1) {
                        isRecarga = true;
                    }
                }
                isFavorite = true;
                */
            } else {
                isFavorite = false;
                comercioResponse = (Comercio) getArguments().getSerializable(ARG_PARAM1);
                if (comercioResponse != null) {
                    if (comercioResponse.getIdTipoComercio() == 1) {
                        isRecarga = true;
                    }
                }
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnContinue.setOnClickListener(this);
        // edtPhoneNumber.setCursorVisible(true);


        imgmonto.setOnClickListener(view -> spnMontoRecarga.performClick());

        edtPhoneNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                layoutPhone.setBackgroundResource(R.drawable.inputtext_active);
            } else {
                layoutPhone.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layoutImageContact) {
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            Objects.requireNonNull(getActivity()).startActivityForResult(contactPickerIntent, CONTACTS_CONTRACT);
        }

        boolean isIAVE = comercioResponse.getIdComercio() == IAVE_ID;

        switch (v.getId()) {
            case R.id.btn_continue_payment:
                if (isRecarga) {
                    String phone = edtPhoneNumber.getText().toString().trim();
                    phone = phone.replaceAll(" ", "");
                    Double monto = (Double) spnMontoRecarga.getSelectedItem();
                    recargasPresenter.validateFields(phone, monto, comercioResponse.getLongitudReferencia(), isIAVE);

                } else {
                    /*referencia = edtReferenceNumber.getText().toString().replaceAll(" ", "");
                    //concepto = txtComisionServicio.getText().toString().trim();
                    concepto = edtServiceConcept.getText().toString().trim();
                    iPresenterPayment.validateFieldsCarrier(referencia, edtServiceImport.getText().toString().trim(),
                            concepto, comercioResponse.getLongitudReferencia());
                            */
                }
                break;

            case R.id.imgMakePaymentRef:
            case R.id.layoutImageReferenceIAVE:
                Intent intent = new Intent(getActivity(), ScannVisionActivity.class);
                getActivity().startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onError(String error) {
        isValid = false;
        errorText = error;
        showError();
    }

    @Override
    public void nextView() {

    }

    public void showError() {
        if (errorText != null && !errorText.equals("")) {
            /**
             * Comparamos la cadena que entrega el Servicio o el Presentes, con los mensajes que
             * tenemos en el archivo de Strings, dependiendo del mensaje, hacemos un set al errorTittle
             * para mostrarlo en el UI.createSimpleCustomDialog
             */
            String errorTittle = "";
            if (errorText.equals(getString(R.string.txt_importe_error))) {
                errorTittle = App.getContext().getResources().getString(R.string.txt_importe_error);
                errorText = App.getContext().getResources().getString(R.string.txt_importe_error);
            } else if (errorText.equals(App.getContext().getString(R.string.new_body_IAVE_error))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_recarga_iave_error_empty);
                errorText = App.getContext().getResources().getString(R.string.new_body_recargas_iave_error_empty);
            } else if (errorText.equals(App.getContext().getString(R.string.new_body_phone_error))) {
                errorTittle = App.getContext().getResources().getString(R.string.numero_telefono_incorrecto);
                errorText = App.getContext().getResources().getString(R.string.new_body_phone_error);
            } else if (errorText.equals(App.getContext().getString(R.string.favor_selecciona_importe))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_envios_importe_empty_error);
                errorText = getString(R.string.favor_selecciona_importe);
            } else if (errorText.equals(App.getContext().getString(R.string.numero_iave_vacio))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_recarga_iave_error_empty);
                errorText = App.getContext().getResources().getString(R.string.new_body_recargas_iave_error_empty);
            } else if (errorText.equals(App.getContext().getString(R.string.numero_telefono_vacio))) {
                errorTittle = App.getContext().getResources().getString(R.string.phone_invalid);
                errorText = App.getContext().getResources().getString(R.string.new_body_recargas_phone_error);
            } else if (errorText.equals(App.getContext().getResources().getString(R.string.txt_referencia_empty))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_envios_refer_error);
                errorText = App.getContext().getResources().getString(R.string.txt_referencia_empty);
            } else if (errorText.equals(App.getContext().getResources().getString(R.string.mount_valid))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_body_envios_importe_empty_error);
                errorText = App.getContext().getResources().getString(R.string.mount_valid);
            } else if (errorText.equals(App.getContext().getResources().getString(R.string.txt_concept_empty))) {
                errorTittle = App.getContext().getResources().getString(R.string.txt_concept_empty);
                errorText = App.getContext().getResources().getString(R.string.txt_concept_empty);
            } else if (errorText.equals(getString(R.string.txt_referencia_errornuevo))) {
                errorTittle = App.getContext().getResources().getString(R.string.txt_referencia_errornuevo);
                errorText = App.getContext().getResources().getString(R.string.txt_referencia_errornuevo);
            }
            //UI.createSimpleCustomDialog(errorTittle, errorText, getActivity().getSupportFragmentManager(), getFragmentTag());
            UI.showErrorSnackBar(getActivity(), errorText, Snackbar.LENGTH_SHORT);
        }
    }


}
