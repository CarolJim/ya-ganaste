package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.EnviosPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IEnviosPresenter;
import com.pagatodo.yaganaste.utils.NumberTextWatcher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB3;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.CABLE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TELEFONO;

/**
 * Created by Jordan on 12/04/2017.
 */

public class EnviosFormFragment extends PaymentFormBaseFragment implements PaymentsManager, AdapterView.OnItemSelectedListener {

    @BindView(R.id.tipoEnvio)
    Spinner tipoEnvio;
    @BindView(R.id.cardNumber)
    EditText cardNumber;
    @BindView(R.id.layout_cardNumber)
    LinearLayout layout_cardNumber;
    @BindView(R.id.amountToSend)
    EditText amountToSend;
    @BindView(R.id.receiverName)
    EditText receiverName;
    @BindView(R.id.concept)
    EditText concept;
    @BindView(R.id.numberReference)
    EditText numberReference;

    TransferType selectedType;
    IEnviosPresenter enviosPresenter;
    private String nombreDestinatario;
    private String referenciaNumber;

    public static EnviosFormFragment newInstance() {
        EnviosFormFragment fragment = new EnviosFormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            tab = TAB3;
            paymentsTabPresenter = ((PaymentsTabFragment) getParentFragment()).getPresenter();
            comercioItem = paymentsTabPresenter.getCarouselItem().getComercio();
            enviosPresenter = new EnviosPresenter(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_envios_form, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        super.initViews();

        List<String> tipoPago = new ArrayList<>();

        tipoPago.add(0, "");
        tipoPago.add(NUMERO_TELEFONO.getId(), NUMERO_TELEFONO.getName(getContext()));
        tipoPago.add(NUMERO_TARJETA.getId(), NUMERO_TARJETA.getName(getContext()));

        if (comercioItem.getIdComercio() != 8609) {
            tipoPago.add(CABLE.getId(), CABLE.getName(getContext()));
        }
        SpinnerArrayAdapter dataAdapter = new SpinnerArrayAdapter(getContext(), TAB3, tipoPago);
        tipoEnvio.setAdapter(dataAdapter);
        tipoEnvio.setOnItemSelectedListener(this);
        amountToSend.addTextChangedListener(new NumberTextWatcher(amountToSend));
    }

    @Override
    public void continuePayment() {
        if (!isValid) {
            showError();
            mySeekBar.setProgress(0);
        } else {
            //Toast.makeText(getContext(), "Realizar Pago", Toast.LENGTH_SHORT).show();
            //Se debe crear un objeto que se envía a la activity que realizará el pago
            payment = new Envios(selectedType, referencia, monto, nombreDestinatario, concepto, referenciaNumber, comercioItem);
            sendPayment();
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
    public void onSuccess(Double monto) {
        this.monto = monto;
        isValid = true;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        referencia = cardNumber.getText().toString().trim();
        concepto = concept.getText().toString().trim();
        nombreDestinatario = receiverName.getText().toString().trim();
        referenciaNumber = numberReference.getText().toString().trim();

        enviosPresenter.validateForms(selectedType, referencia,
                amountToSend.getText().toString().trim(),
                nombreDestinatario,
                concepto,
                referenciaNumber);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        layout_cardNumber.setVisibility(View.VISIBLE);
        cardNumber.setText("");

        int maxLength;
        InputFilter[] fArray = new InputFilter[1];


        if (position == NUMERO_TARJETA.getId()) {
            maxLength = comercioItem.getIdComercio() == 814 ? 15 : 16;
            cardNumber.setHint(getString(R.string.card_number));
            selectedType = NUMERO_TARJETA;
        } else if (position == NUMERO_TELEFONO.getId()) {
            maxLength = 10;
            cardNumber.setHint(getString(R.string.transfer_phone));
            selectedType = NUMERO_TELEFONO;
        } else if (position == CABLE.getId()) {
            maxLength = 18;
            cardNumber.setHint(getString(R.string.transfer_cable));
            selectedType = CABLE;
        } else {
            maxLength = 2;
            cardNumber.setHint("");
            layout_cardNumber.setVisibility(View.GONE);
            selectedType = null;
        }

        fArray[0] = new InputFilter.LengthFilter(maxLength);
        cardNumber.setFilters(fArray);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
