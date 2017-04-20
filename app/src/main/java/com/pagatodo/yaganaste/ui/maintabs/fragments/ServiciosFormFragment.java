package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.ServiciosPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsFormPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsTabPresenter;

import butterknife.BindView;

import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;

/**
 * Created by Jordan on 12/04/2017.
 */

public class ServiciosFormFragment extends PaymentFormBaseFragment implements PaymentsManager,
        View.OnClickListener, TextWatcher {

    @BindView(R.id.referenceNumber)
    EditText referenceNumber;
    @BindView(R.id.layoutImageReference)
    RelativeLayout layoutImageReference;
    @BindView(R.id.serviceImport)
    EditText serviceImport;
    @BindView(R.id.serviceConcept)
    EditText serviceConcept;

    private String referencia;
    private String importe;
    private String concepto;
    private String errorText;
    private boolean isValid = false;

    private IPaymentsTabPresenter paymentsTabPresenter;
    private IPaymentsFormPresenter serviciosPresenter;
    private ComercioResponse comercioItem;

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
            paymentsTabPresenter = ((PaymentsTabFragment) getParentFragment()).getPresenter();
            comercioItem = paymentsTabPresenter.getCarouselItem().getComercio();
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

        serviceConcept.addTextChangedListener(this);
        serviceImport.addTextChangedListener(this);
        referenceNumber.addTextChangedListener(this);
    }

    @Override
    void continuePayment() {
        if (!isValid) {
            showError();
            mySeekBar.setProgress(0);
        } else {
            Toast.makeText(getContext(), "Realizar Pago", Toast.LENGTH_SHORT).show();
            //Se debe crear un objeto que se envía a la activity que realizará el pago
        }
    }

    @Override
    public void showError() {
        if (errorText != null && !errorText.equals("")) {
            Toast.makeText(getContext(), errorText, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(String error) {
        isValid = false;
        errorText = error;
    }

    @Override
    public void onSuccess() {
        isValid = true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

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
                    Point[] p = barcode.cornerPoints;
                    referenceNumber.setText(barcode.displayValue);
                } else {
                    referenceNumber.setText("Error");
                }
            }
        }
    }
}
