package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Servicios;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.ServiciosPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IServiciosPresenter;
import com.pagatodo.yaganaste.utils.NumberTagPase;
import com.pagatodo.yaganaste.utils.NumberTextWatcher;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;

import butterknife.BindView;

import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB2;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;

/**
 * Created by Jordan on 12/04/2017.
 */

public class ServiciosFormFragment extends PaymentFormBaseFragment implements PaymentsManager,
        View.OnClickListener {

    @BindView(R.id.referenceNumber)
    EditText referenceNumber;
    @BindView(R.id.layoutImageReference)
    RelativeLayout layoutImageReference;
    @BindView(R.id.serviceImport)
    EditText serviceImport;
    @BindView(R.id.serviceConcept)
    EditText serviceConcept;
    @BindView(R.id.comisionText)
    TextView comisionText;
    int maxLength;

    private boolean isValid = false;
    private IServiciosPresenter serviciosPresenter;

    public static ServiciosFormFragment newInstance() {
        ServiciosFormFragment fragment = new ServiciosFormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            tab = TAB2;
            paymentsTabPresenter = ((PaymentsTabFragment) getParentFragment()).getPresenter();
            comercioItem = paymentsTabPresenter.getCarouselItem().getComercio();
            favoriteItem = paymentsTabPresenter.getCarouselItem().getFavoritos();
            if (comercioItem == null && favoriteItem != null) {
                comercioItem = paymentsTabPresenter.getComercioById(favoriteItem.getIdComercio());
            }
            serviciosPresenter = new ServiciosPresenter(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_servicios_form, container, false);

        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        super.initViews();
        layoutImageReference.setOnClickListener(this);
        serviceImport.addTextChangedListener(new NumberTextWatcher(serviceImport));
        if (comercioItem.getLongitudReferencia() > 0) {
            InputFilter[] fArray = new InputFilter[1];
            maxLength = Utils.calculateFilterLength(comercioItem.getLongitudReferencia());
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            referenceNumber.setFilters(fArray);
        }

        if (comercioItem.getFormato().equals("AN")) {
            referenceNumber.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        referenceNumber.addTextChangedListener(new NumberTagPase(referenceNumber, maxLength));
        if (comercioItem.getSobrecargo() > 0) {
            comisionText.setText(String.format(getString(R.string.comision_service_payment), StringUtils.getCurrencyValue(comercioItem.getSobrecargo())));
        } else {
            comisionText.setVisibility(View.INVISIBLE);
        }
        if (favoriteItem != null) {
            referenceNumber.setText(favoriteItem.getReferencia());
            //referenceNumber.setEnabled(false);
        }
    }

    @Override
    protected void continuePayment() {
        if (!isValid) {
            showError();
            mySeekBar.setProgress(0);
        } else {
            //Toast.makeText(getContext(), "Realizar Pago", Toast.LENGTH_SHORT).show();
            //Se debe crear un objeto que se envía a la activity que realizará el pago
            payment = new Servicios(referencia, monto, concepto, comercioItem, favoriteItem != null);
            sendPayment();
        }
    }

    @Override
    public void showError() {
        if (errorText != null && !errorText.equals("")) {
            //Toast.makeText(getContext(), errorText, Toast.LENGTH_SHORT).show();
            UI.createSimpleCustomDialog("Error", errorText, getActivity().getSupportFragmentManager(), getFragmentTag());
        }
    }

    @Override
    public void onError(String error) {
        isValid = false;
        errorText = error;
    }

    @Override
    public void onSuccess(Double importe) {
        this.monto = importe;
        isValid = true;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layoutImageReference) {
            Intent intent = new Intent(getActivity(), ScannVisionActivity.class);
            getActivity().startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                    referenceNumber.setText(barcode.displayValue);
                }
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        referencia = referenceNumber.getText().toString().replaceAll(" ", "");
        concepto = serviceConcept.getText().toString().trim();
        serviciosPresenter.validateFields(referencia, serviceImport.getText().toString().trim(), concepto, comercioItem.getLongitudReferencia());
    }

}
